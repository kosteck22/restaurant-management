package org.example.sale.service.messaging.publisher.kafka;


import lombok.extern.slf4j.Slf4j;
import org.example.kafka.producer.KafkaMessageHelper;
import org.example.kafka.producer.service.KafkaProducer;
import org.example.kafka.stock.sale.avro.model.StockSubtractRequestAvroModel;
import org.example.sale.service.domain.config.SaleServiceConfigData;
import org.example.sale.service.domain.event.SalePaidEvent;
import org.example.sale.service.domain.ports.output.message.publisher.SalePaidMessagePublisher;
import org.example.sale.service.messaging.mapper.SaleMessagingDataMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SalePaidKafkaMessagePublisher implements SalePaidMessagePublisher {
    private final SaleServiceConfigData saleServiceConfigData;
    private final KafkaProducer<String, StockSubtractRequestAvroModel> kafkaProducer;
    private final SaleMessagingDataMapper saleMessagingDataMapper;
    private final KafkaMessageHelper kafkaMessageHelper;

    public SalePaidKafkaMessagePublisher(SaleServiceConfigData saleServiceConfigData,
                                         KafkaProducer<String, StockSubtractRequestAvroModel> kafkaProducer,
                                         SaleMessagingDataMapper saleMessagingDataMapper,
                                         KafkaMessageHelper kafkaMessageHelper) {
        this.saleServiceConfigData = saleServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.saleMessagingDataMapper = saleMessagingDataMapper;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(SalePaidEvent domainEvent) {
        String saleId = domainEvent.getSale().getId().getValue().toString();
        log.info("Received SalePaidEvent for sale id: {}", saleId);

        try {
            StockSubtractRequestAvroModel stockDeduceRequestAvroModel = saleMessagingDataMapper
                    .SalePaidEventToStockSubtractRequestAvroModel(domainEvent);

            kafkaProducer.send(saleServiceConfigData.getStockSubtractRequestTopicName(),
                    saleId,
                    stockDeduceRequestAvroModel,
                    kafkaMessageHelper
                            .getKafkaCallback(saleServiceConfigData.getStockSubtractRequestTopicName(),
                                    stockDeduceRequestAvroModel,
                                    saleId,
                                    "StockSubtractRequestAvroModel"));

            log.info("StockSubtractRequestAvroModel sent to Kafka for sale id: {}", stockDeduceRequestAvroModel.getSaleId());
        } catch (Exception e) {
            log.error("Error while sending StockSubtractRequestAvroModel message" +
                    " to kafka with sale id: {}, error: {}", saleId, e.getMessage());
        }
    }
}
