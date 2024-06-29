package org.example.waregouse.stock.service.messaging.listener.kafka;

import lombok.extern.slf4j.Slf4j;
import org.example.kafka.consumer.KafkaConsumer;
import org.example.kafka.stock.take.avro.model.StockUpdateRequestAvroModel;
import org.example.waregouse.stock.service.messaging.mapper.StockMessagingDataMapper;
import org.example.warehouse.stock.service.domain.ports.input.message.listener.StockUpdateRequestMessageListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StockUpdateRequestKafkaMessageListener implements KafkaConsumer<StockUpdateRequestAvroModel> {
    private final StockUpdateRequestMessageListener stockUpdateRequestMessageListener;
    private final StockMessagingDataMapper stockMessagingDataMapper;

    public StockUpdateRequestKafkaMessageListener(StockUpdateRequestMessageListener stockTakeCreatedMessageListener,
                                                  StockMessagingDataMapper stockMessagingDataMapper) {
        this.stockUpdateRequestMessageListener = stockTakeCreatedMessageListener;
        this.stockMessagingDataMapper = stockMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.stock-consumer-group-id}",
            topics = "${stock-service.stock-update-request-topic-name}")
    public void receive(@Payload List<StockUpdateRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of stock update requests received with keys:{}, partitions:{} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(stockTakeAvroModel -> {
            log.info("Processing update stock for stock take id: {}", stockTakeAvroModel.getStockTakeId());
            stockUpdateRequestMessageListener.updateStock(
                    stockMessagingDataMapper.stockUpdateRequestAvroModelToStockTakeCreatedRequest(
                            stockTakeAvroModel));
        });
    }
}
