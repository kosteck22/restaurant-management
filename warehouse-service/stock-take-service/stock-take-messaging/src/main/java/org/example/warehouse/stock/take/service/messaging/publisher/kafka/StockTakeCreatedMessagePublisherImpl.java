package org.example.warehouse.stock.take.service.messaging.publisher.kafka;

import lombok.extern.slf4j.Slf4j;
import org.example.kafka.producer.KafkaMessageHelper;
import org.example.kafka.producer.service.KafkaProducer;
import org.example.kafka.stock.take.avro.model.StockTakeAvroModel;
import org.example.warehouse.stock.take.service.domain.config.StockTakeServiceConfigData;
import org.example.warehouse.stock.take.service.domain.event.StockTakeCreatedEvent;
import org.example.warehouse.stock.take.service.domain.ports.output.message.publisher.StockTakeCreatedMessagePublisher;
import org.example.warehouse.stock.take.service.messaging.mapper.StockTakeMessagingDataMapper;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class StockTakeCreatedMessagePublisherImpl implements StockTakeCreatedMessagePublisher {

    private final StockTakeServiceConfigData stockTakeServiceConfigData;
    private final KafkaProducer<String, StockTakeAvroModel> kafkaProducer;
    private final StockTakeMessagingDataMapper stockTakeMessagingDataMapper;
    private final KafkaMessageHelper kafkaMessageHelper;

    public StockTakeCreatedMessagePublisherImpl(StockTakeServiceConfigData stockTakeServiceConfigData,
                                                KafkaProducer<String, StockTakeAvroModel> kafkaProducer,
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
            StockTakeAvroModel stockTakeCreatedAvroModel = stockTakeMessagingDataMapper
                    .stockTakeCreatedEventToStockTakeAvroModel(domainEvent);

            kafkaProducer.send(stockTakeServiceConfigData.getStockTakeCreatedTopicName(),
                    stockTakeId,
                    stockTakeCreatedAvroModel,
                    kafkaMessageHelper
                            .getKafkaCallback(stockTakeServiceConfigData.getStockTakeCreatedResponseTopicName(),
                                    stockTakeCreatedAvroModel,
                                    stockTakeId,
                                    "StockTakeCreatedAvroModel"));

            log.info("StockTakeCreatedAvroModel sent to Kafka for stock take id: {}", stockTakeCreatedAvroModel.getStockTakeId());
        } catch (Exception e) {
            log.error("Error while sending StockTakeCreatedAvroModel message" +
                    " to kafka with stock take id: {}, error: {}", stockTakeId, e.getMessage());
        }
    }
}
