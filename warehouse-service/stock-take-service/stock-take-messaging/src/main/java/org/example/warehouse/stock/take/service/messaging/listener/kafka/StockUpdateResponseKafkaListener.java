package org.example.warehouse.stock.take.service.messaging.listener.kafka;


import lombok.extern.slf4j.Slf4j;
import org.example.kafka.consumer.KafkaConsumer;
import org.example.kafka.stock.take.avro.model.StockUpdateResponseAvroModel;
import org.example.warehouse.stock.take.service.domain.ports.input.message.listener.StockUpdateResponseMessageListener;
import org.example.warehouse.stock.take.service.messaging.mapper.StockTakeMessagingDataMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StockUpdateResponseKafkaListener implements KafkaConsumer<StockUpdateResponseAvroModel> {
    private final StockUpdateResponseMessageListener stockUpdateResponseMessageListener;
    private final StockTakeMessagingDataMapper stockTakeMessagingDataMapper;

    public StockUpdateResponseKafkaListener(StockUpdateResponseMessageListener stockUpdateResponseMessageListener, StockTakeMessagingDataMapper stockTakeMessagingDataMapper) {
        this.stockUpdateResponseMessageListener = stockUpdateResponseMessageListener;
        this.stockTakeMessagingDataMapper = stockTakeMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.stock-update-consumer-group-id}",
            topics = "${stock-take-service.stock-update-response-topic-name}")
    public void receive(@Payload List<StockUpdateResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of stock update responses received with keys:{}, partitions:{} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(stockUpdateResponseAvroModel -> {
            if (stockUpdateResponseAvroModel.getFailureMessages() == null ||
                    stockUpdateResponseAvroModel.getFailureMessages().isEmpty()) {
                log.info("Processing successful stock update for stock take id: {}", stockUpdateResponseAvroModel.getStockTakeId());
                stockUpdateResponseMessageListener.stockTakeAccepted(stockTakeMessagingDataMapper
                        .stockUpdateResponseAvroModelToStockUpdateResponse(stockUpdateResponseAvroModel));
            } else {
                log.info("Processing unsuccessful stock update for stock take id: {}", stockUpdateResponseAvroModel.getStockTakeId());
                stockUpdateResponseMessageListener.stockTakeRejected(stockTakeMessagingDataMapper
                        .stockUpdateResponseAvroModelToStockUpdateResponse(stockUpdateResponseAvroModel));
            }
        });
    }
}
