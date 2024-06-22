package org.example.warehouse.stock.take.service.messaging.publisher.kafka;

import lombok.extern.slf4j.Slf4j;
import org.example.kafka.producer.KafkaMessageHelper;
import org.example.kafka.producer.service.KafkaProducer;
import org.example.kafka.stock.take.avro.model.StockUpdateRequestAvroModel;
import org.example.warehouse.stock.take.service.domain.config.StockTakeServiceConfigData;
import org.example.warehouse.stock.take.service.domain.event.StockTakeCreatedEvent;
import org.example.warehouse.stock.take.service.domain.ports.output.message.publisher.StockTakeCreatedMessagePublisher;
import org.example.warehouse.stock.take.service.messaging.mapper.StockTakeMessagingDataMapper;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class StockTakeCreatedMessagePublisherImpl implements StockTakeCreatedMessagePublisher {

    private final StockTakeServiceConfigData stockTakeServiceConfigData;
    private final KafkaProducer<String, StockUpdateRequestAvroModel> kafkaProducer;
    private final StockTakeMessagingDataMapper stockTakeMessagingDataMapper;
    private final KafkaMessageHelper kafkaMessageHelper;

    public StockTakeCreatedMessagePublisherImpl(StockTakeServiceConfigData stockTakeServiceConfigData,
                                                KafkaProducer<String, StockUpdateRequestAvroModel> kafkaProducer,
                                                StockTakeMessagingDataMapper stockTakeMessagingDataMapper,
                                                KafkaMessageHelper kafkaMessageHelper) {
        this.stockTakeServiceConfigData = stockTakeServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.stockTakeMessagingDataMapper = stockTakeMessagingDataMapper;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(StockTakeCreatedEvent domainEvent) {
        String stockTakeId = domainEvent.getStockTake().getId().getValue().toString();
        log.info("Received StockTakeCreatedEvent for stock take id: {}", stockTakeId);

        try {
            StockUpdateRequestAvroModel stockUpdateRequestAvroModel = stockTakeMessagingDataMapper
                    .stockTakeCreatedEventToStockUpdateRequestAvroModel(domainEvent);

            kafkaProducer.send(stockTakeServiceConfigData.getStockUpdateRequestTopicName(),
                    stockTakeId,
                    stockUpdateRequestAvroModel,
                    kafkaMessageHelper
                            .getKafkaCallback(stockTakeServiceConfigData.getStockUpdateRequestTopicName(),
                                    stockUpdateRequestAvroModel,
                                    stockTakeId,
                                    "StockUpdateRequestAvroModel"));

            log.info("StockUpdateRequestAvroModel sent to Kafka for stock take id: {}", stockUpdateRequestAvroModel.getStockTakeId());
        } catch (Exception e) {
            log.error("Error while sending StockUpdateRequestAvroModel message" +
                    " to kafka with stock take id: {}, error: {}", stockTakeId, e.getMessage());
        }
    }
}
