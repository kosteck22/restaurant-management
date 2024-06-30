package org.example.waregouse.stock.service.messaging.listener.kafka;


import lombok.extern.slf4j.Slf4j;
import org.example.kafka.consumer.KafkaConsumer;
import org.example.kafka.stock.sale.avro.model.StockSubtractRequestAvroModel;
import org.example.waregouse.stock.service.messaging.mapper.StockMessagingDataMapper;
import org.example.warehouse.stock.service.domain.ports.input.message.listener.StockSubtractRequestMessageListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StockSubtractRequestKafkaMessageListener implements KafkaConsumer<StockSubtractRequestAvroModel> {
    private final StockSubtractRequestMessageListener stockSubtractRequestMessageListener;
    private final StockMessagingDataMapper stockMessagingDataMapper;

    public StockSubtractRequestKafkaMessageListener(StockSubtractRequestMessageListener stockSubtractRequestMessageListener,
                                                    StockMessagingDataMapper stockMessagingDataMapper) {
        this.stockSubtractRequestMessageListener = stockSubtractRequestMessageListener;
        this.stockMessagingDataMapper = stockMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.stock-subtract-consumer-group-id}",
            topics = "${stock-service.stock-subtract-request-topic-name}")
    public void receive(@Payload List<StockSubtractRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of stock subtract requests received with keys:{}, partitions:{} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(stockDeduceAvroModel -> {
            log.info("Processing subtract stock for sale id: {}", stockDeduceAvroModel.getSaleId());
            stockSubtractRequestMessageListener.subtractStock(
                    stockMessagingDataMapper.stockSubtractRequestAvroModelToStockSubtractRequest(
                            stockDeduceAvroModel));
        });
    }
}
