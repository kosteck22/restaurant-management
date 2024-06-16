package org.example.waregouse.stock.service.messaging.listener.kafka;

import lombok.extern.slf4j.Slf4j;
import org.example.kafka.consumer.KafkaConsumer;
import org.example.kafka.stock.take.avro.model.StockTakeAvroModel;
import org.example.waregouse.stock.service.messaging.mapper.StockMessagingDataMapper;
import org.example.warehouse.stock.service.domain.ports.input.message.listener.StockTakeCreatedMessageListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StockTakeCreatedKafkaListener implements KafkaConsumer<StockTakeAvroModel> {
    private final StockTakeCreatedMessageListener stockTakeCreatedMessageListener;
    private final StockMessagingDataMapper stockMessagingDataMapper;

    public StockTakeCreatedKafkaListener(StockTakeCreatedMessageListener stockTakeCreatedMessageListener,
                                         StockMessagingDataMapper stockMessagingDataMapper) {
        this.stockTakeCreatedMessageListener = stockTakeCreatedMessageListener;
        this.stockMessagingDataMapper = stockMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.payment-consumer-group-id}",
            topics = "${payment-service.payment-request-topic-name}")
    public void receive(@Payload List<StockTakeAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of stock take created requests received with keys:{}, partitions:{} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(stockTakeAvroModel -> {
            log.info("Processing close stock and open a new one for stock take id: {}", stockTakeAvroModel.getStockTakeId());
            stockTakeCreatedMessageListener.updateStock(
                    stockMessagingDataMapper.stockTakeAvroModelToStockTakeCreatedRequest(
                            stockTakeAvroModel));
        });
    }
}
