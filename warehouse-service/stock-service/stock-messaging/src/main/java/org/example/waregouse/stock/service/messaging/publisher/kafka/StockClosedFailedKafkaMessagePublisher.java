package org.example.waregouse.stock.service.messaging.publisher.kafka;

import lombok.extern.slf4j.Slf4j;
import org.example.kafka.producer.KafkaMessageHelper;
import org.example.kafka.producer.service.KafkaProducer;
import org.example.kafka.stock.take.avro.model.StockUpdateResponseAvroModel;
import org.example.waregouse.stock.service.messaging.mapper.StockMessagingDataMapper;
import org.example.warehouse.stock.service.domain.config.StockServiceConfigData;
import org.example.warehouse.stock.service.domain.event.StockClosedFailedEvent;
import org.example.warehouse.stock.service.domain.ports.output.message.publisher.StockClosedFailedMessagePublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockClosedFailedKafkaMessagePublisher implements StockClosedFailedMessagePublisher {
    private final StockMessagingDataMapper stockMessagingDataMapper;
    private final KafkaProducer<String, StockUpdateResponseAvroModel> kafkaProducer;
    private final StockServiceConfigData stockServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public StockClosedFailedKafkaMessagePublisher(StockMessagingDataMapper stockMessagingDataMapper, KafkaProducer<String, StockUpdateResponseAvroModel> kafkaProducer, StockServiceConfigData stockServiceConfigData, KafkaMessageHelper kafkaMessageHelper) {
        this.stockMessagingDataMapper = stockMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.stockServiceConfigData = stockServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(StockClosedFailedEvent domainEvent) {
        String stockTakeId = domainEvent.getStockTakeId().getValue().toString();

        log.info("Received StockClosedFailedEvent for stock take id: {}", stockTakeId);

        try {
            StockUpdateResponseAvroModel stockAvroModel =
                    stockMessagingDataMapper.stockClosedFailedEventToStockUpdateResponseAvroModel(domainEvent);

            kafkaProducer.send(stockServiceConfigData.getStockUpdateResponseTopicName(),
                    stockTakeId,
                    stockAvroModel,
                    kafkaMessageHelper.getKafkaCallback(stockServiceConfigData.getStockUpdateResponseTopicName(),
                            stockAvroModel,
                            stockTakeId,
                            "StockUpdateResponseAvroModel"));

            log.info("StockUpdateResponseAvroModel sent to kafka for stockTakeId id: {}", stockTakeId);
        } catch (Exception e) {
            log.error("Error while sending StockUpdateResponseAvroModel message" +
                    " to kafka with stockTakeId id: {}, error: {}", stockTakeId, e.getMessage());
        }
    }
}
