package org.example.waregouse.stock.service.messaging.listener.kafka;


import lombok.extern.slf4j.Slf4j;
import org.example.kafka.consumer.KafkaConsumer;
import org.example.kafka.stock.sale.avro.model.StockDeduceRequestAvroModel;
import org.example.kafka.stock.take.avro.model.StockUpdateRequestAvroModel;
import org.example.waregouse.stock.service.messaging.mapper.StockMessagingDataMapper;
import org.example.warehouse.stock.service.domain.ports.input.message.listener.StockDeduceRequestMessageListener;
import org.example.warehouse.stock.service.domain.ports.input.message.listener.StockUpdateRequestMessageListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StockDeduceRequestKafkaMessageListener implements KafkaConsumer<StockDeduceRequestAvroModel> {
    private final StockDeduceRequestMessageListener stockDeduceRequestMessageListener;
    private final StockMessagingDataMapper stockMessagingDataMapper;

    public StockDeduceRequestKafkaMessageListener(StockDeduceRequestMessageListener stockDeduceRequestMessageListener,
                                                  StockMessagingDataMapper stockMessagingDataMapper) {
        this.stockDeduceRequestMessageListener = stockDeduceRequestMessageListener;
        this.stockMessagingDataMapper = stockMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.stock-consumer-group-id}",
            topics = "${stock-service.stock-deduce-request-topic-name}")
    public void receive(@Payload List<StockDeduceRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of stock deduce requests received with keys:{}, partitions:{} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(stockDeduceAvroModel -> {
            log.info("Processing deduce stock for sale id: {}", stockDeduceAvroModel.getSaleId());
            stockDeduceRequestMessageListener.deduceStock(
                    stockMessagingDataMapper.stockDeduceRequestAvroModelToStockDeduceRequest(
                            stockDeduceAvroModel));
        });
    }
}
