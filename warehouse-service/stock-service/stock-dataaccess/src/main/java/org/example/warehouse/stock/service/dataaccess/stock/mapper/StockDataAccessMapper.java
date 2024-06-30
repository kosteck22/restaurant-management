package org.example.warehouse.stock.service.dataaccess.stock.mapper;

import org.example.domain.valueobject.*;
import org.example.warehouse.stock.service.dataaccess.stock.entity.StockAddTransactionEntity;
import org.example.warehouse.stock.service.dataaccess.stock.entity.StockItemBeforeClosingEntity;
import org.example.warehouse.stock.service.dataaccess.stock.entity.StockSubtractTransactionEntity;
import org.example.warehouse.stock.service.dataaccess.stock.entity.StockEntity;
import org.example.warehouse.stock.service.domain.entity.Stock;
import org.example.warehouse.stock.service.domain.entity.StockAddTransaction;
import org.example.warehouse.stock.service.domain.entity.StockItemBeforeClosing;
import org.example.warehouse.stock.service.domain.entity.StockSubtractTransaction;
import org.example.warehouse.stock.service.domain.valueobject.StockAddTransactionId;
import org.example.warehouse.stock.service.domain.valueobject.StockItemBeforeClosingId;
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
                .subtractTransactions(StockSubtractTransactionEntitiesToStockSubtractTransactions(stockEntity.getSubtractTransactions()))
                .stockItemsBeforeClosing(StockItemsBeforeClosingEntitiesToStockItemsBeforeClosing(stockEntity.getStockItemsBeforeClosing()))
                .fromStockTake(new StockTakeId(stockEntity.getFromStockTake()))
                .toStockTake(new StockTakeId(stockEntity.getToStockTake()))
                .totalGrossPrice(new Money(stockEntity.getTotalGrossPrice()))
                .status(stockEntity.getStatus())
                .build();
    }

    private List<StockItemBeforeClosing> StockItemsBeforeClosingEntitiesToStockItemsBeforeClosing(List<StockItemBeforeClosingEntity> stockItemsBeforeClosing) {
        return stockItemsBeforeClosing.stream()
                .map(stockItemBeforeClosingEntity ->
                        StockItemBeforeClosing.builder()
                                .id(new StockItemBeforeClosingId(stockItemBeforeClosingEntity.getId()))
                                .additionDate(stockItemBeforeClosingEntity.getAdditionDate())
                                .productId(new ProductId(stockItemBeforeClosingEntity.getProductId()))
                                .quantity(new Quantity(stockItemBeforeClosingEntity.getQuantity()))
                                .grossPrice(new Money(stockItemBeforeClosingEntity.getGrossPrice()))
                                .invoiceId(new InvoiceId(stockItemBeforeClosingEntity.getInvoiceId()))
                                .build())
                .collect(Collectors.toList());
    }

    private List<StockSubtractTransaction> StockSubtractTransactionEntitiesToStockSubtractTransactions(List<StockSubtractTransactionEntity> subtractTransactions) {
        return subtractTransactions.stream()
                .map(stockSubtractTransactionEntity ->
                        StockSubtractTransaction.builder()
                                .stockSubtractTransactionId(new StockSubtractTransactionId(stockSubtractTransactionEntity.getId()))
                                .subtractDate(stockSubtractTransactionEntity.getSubtractDate())
                                .productId(new ProductId(stockSubtractTransactionEntity.getProductId()))
                                .quantity(new Quantity(stockSubtractTransactionEntity.getQuantity()))
                                .stockSubtractTransactionType(stockSubtractTransactionEntity.getStockSubtractTransactionType())
                                .saleId(new SaleId(stockSubtractTransactionEntity.getSaleId()))
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
        return StockEntity.builder()
                .id(stock.getId().getValue())
                .totalGrossPrice(stock.getTotalGrossPrice().getAmount())
                .fromStockTake(stock.getFromStockTake().getValue())
                .toStockTake(stock.getToStockTake().getValue())
                .status(stock.getStatus())
                .addingTransactions(StockAddTransactionsToStockAddTransactionEntities(stock.getAddTransactions()))
                .subtractTransactions(StockSubtractTransactionsToStockSubtractTransactionEntities(stock.getSubtractTransactions()))
                .stockItemsBeforeClosing(StockItemsBeforeClosingToStockItemBeforeClosingEntities(stock.getStockItemsBeforeClosing()))
                .build();
    }

    private List<StockItemBeforeClosingEntity> StockItemsBeforeClosingToStockItemBeforeClosingEntities(List<StockItemBeforeClosing> stockItemsBeforeClosing) {
        return null;
    }

    private List<StockSubtractTransactionEntity> StockSubtractTransactionsToStockSubtractTransactionEntities(List<StockSubtractTransaction> subtractTransactions) {
        return null;
    }

    private List<StockAddTransactionEntity> StockAddTransactionsToStockAddTransactionEntities(List<StockAddTransaction> addTransactions) {
        return null;
    }
}
