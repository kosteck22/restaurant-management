package org.example.warehouse.stock.service.dataaccess.stock.mapper;

import org.example.domain.valueobject.*;
import org.example.warehouse.stock.service.dataaccess.stock.entity.StockAddTransactionEntity;
import org.example.warehouse.stock.service.dataaccess.stock.entity.StockDeduceTransactionEntity;
import org.example.warehouse.stock.service.dataaccess.stock.entity.StockEntity;
import org.example.warehouse.stock.service.domain.entity.Stock;
import org.example.warehouse.stock.service.domain.entity.StockAddTransaction;
import org.example.warehouse.stock.service.domain.entity.StockSubtractTransaction;
import org.example.warehouse.stock.service.domain.valueobject.StockAddTransactionId;
import org.example.warehouse.stock.service.domain.valueobject.StockSubtractTransactionId;
import org.example.warehouse.stock.service.domain.valueobject.StockId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockDataAccessMapper {
    public Stock stockEntityToStock(StockEntity stockEntity) {
        return Stock.builder()
                .stockId(new StockId(stockEntity.getId()))
                .addingTransactions(StockAddTransactionEntitiesToStockAddTransactions(stockEntity.getAddingTransactions()))
                .deducingTransactions(StockDeduceTransactionEntitiesToStockDeduceTransactions(stockEntity.getDeducingTransactions()))
                .fromStockTake(new StockTakeId(stockEntity.getFromStockTake()))
                .toStockTake(new StockTakeId(stockEntity.getToStockTake()))
                .totalGrossPrice(new Money(stockEntity.getTotalGrossPrice()))
                .status(stockEntity.getStatus())
                .build();
    }

    private List<StockSubtractTransaction> StockDeduceTransactionEntitiesToStockDeduceTransactions(List<StockDeduceTransactionEntity> deducingTransactions) {
        return deducingTransactions.stream()
                .map(stockDeduceTransactionEntity ->
                        StockSubtractTransaction.builder()
                                .stockDeduceTransactionId(new StockSubtractTransactionId(stockDeduceTransactionEntity.getId()))
                                .deductionDate(stockDeduceTransactionEntity.getDeductionDate())
                                .productId(new ProductId(stockDeduceTransactionEntity.getProductId()))
                                .quantity(new Quantity(stockDeduceTransactionEntity.getQuantity()))
                                .stockDeduceTransactionType(stockDeduceTransactionEntity.getStockDeduceTransactionType())
                                .saleId(new SaleId(stockDeduceTransactionEntity.getSaleId()))
                                .build())
                .collect(Collectors.toList());
    }

    private List<StockAddTransaction> StockAddTransactionEntitiesToStockAddTransactions(List<StockAddTransactionEntity> addingTransactions) {
        return addingTransactions.stream()
                .map(stockAddTransactionEntity ->
                        StockAddTransaction.builder()
                                .stockAddTransactionId(new StockAddTransactionId(stockAddTransactionEntity.getId()))
                                .additionDate(stockAddTransactionEntity.getAdditionDate())
                                .productId(new ProductId(stockAddTransactionEntity.getProductId()))
                                .grossPrice(new Money(stockAddTransactionEntity.getGrossPrice()))
                                .totalGrossPrice(new Money(stockAddTransactionEntity.getTotalGrossPrice()))
                                .quantity(new Quantity(stockAddTransactionEntity.getQuantity()))
                                .stockAddTransactionType(stockAddTransactionEntity.getStockAddTransactionType())
                                .invoiceId(new InvoiceId(stockAddTransactionEntity.getInvoiceId()))
                                .build())
                .collect(Collectors.toList());
    }

    public StockEntity stockToStockEntity(Stock stock) {
        return null;
    }
}
