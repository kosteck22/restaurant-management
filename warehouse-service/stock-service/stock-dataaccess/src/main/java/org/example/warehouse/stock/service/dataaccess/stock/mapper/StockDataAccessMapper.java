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

import java.util.ArrayList;
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
        return stockItemsBeforeClosing == null ? new ArrayList<>() : stockItemsBeforeClosing.stream()
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
        return subtractTransactions == null ? new ArrayList<>() : subtractTransactions.stream()
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
        return addingTransactions == null ? new ArrayList<>() :
                addingTransactions.stream()
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
        StockEntity stockEntity = StockEntity.builder()
                .id(stock.getId().getValue())
                .totalGrossPrice(stock.getTotalGrossPrice() == null ? null : stock.getTotalGrossPrice().getAmount())
                .fromStockTake(stock.getFromStockTake() == null ? null : stock.getFromStockTake().getValue())
                .toStockTake(stock.getToStockTake() == null ? null : stock.getToStockTake().getValue())
                .status(stock.getStatus())
                .addingTransactions(StockAddTransactionsToStockAddTransactionEntities(stock.getAddTransactions()))
                .subtractTransactions(StockSubtractTransactionsToStockSubtractTransactionEntities(stock.getSubtractTransactions()))
                .stockItemsBeforeClosing(StockItemsBeforeClosingToStockItemBeforeClosingEntities(stock.getStockItemsBeforeClosing()))
                .build();
        stockEntity.getSubtractTransactions().forEach(stockSubtractTransactionEntity -> stockSubtractTransactionEntity.setStock(stockEntity));
        stockEntity.getStockItemsBeforeClosing().forEach(stockItemBeforeClosingEntity -> stockItemBeforeClosingEntity.setStock(stockEntity));
        stockEntity.getAddingTransactions().forEach(stockAddTransactionEntity -> stockAddTransactionEntity.setStock(stockEntity));
        return stockEntity;
    }

    private List<StockItemBeforeClosingEntity> StockItemsBeforeClosingToStockItemBeforeClosingEntities(List<StockItemBeforeClosing> stockItemsBeforeClosing) {
        return stockItemsBeforeClosing.stream()
                .map(stockItemBeforeClosing -> StockItemBeforeClosingEntity.builder()
                        .id(stockItemBeforeClosing.getId().getValue())
                        .additionDate(stockItemBeforeClosing.getAdditionDate())
                        .productId(stockItemBeforeClosing.getProductId().getValue())
                        .quantity(stockItemBeforeClosing.getQuantity().getValue())
                        .grossPrice(stockItemBeforeClosing.getGrossPrice().getAmount())
                        .invoiceId(stockItemBeforeClosing.getInvoiceId().getValue())
                        .build())
                .collect(Collectors.toList());
    }

    private List<StockSubtractTransactionEntity> StockSubtractTransactionsToStockSubtractTransactionEntities(List<StockSubtractTransaction> subtractTransactions) {
        return subtractTransactions.stream()
                .map(stockSubtractTransaction -> StockSubtractTransactionEntity.builder()
                        .id(stockSubtractTransaction.getId().getValue())
                        .subtractDate(stockSubtractTransaction.getSubtractDate())
                        .productId(stockSubtractTransaction.getProductId().getValue())
                        .quantity(stockSubtractTransaction.getQuantity().getValue())
                        .stockSubtractTransactionType(stockSubtractTransaction.getStockSubtractTransactionType())
                        .saleId(stockSubtractTransaction.getSaleId() == null ? null : stockSubtractTransaction.getSaleId().getValue())
                        .build())
                .collect(Collectors.toList());
    }

    private List<StockAddTransactionEntity> StockAddTransactionsToStockAddTransactionEntities(List<StockAddTransaction> addTransactions) {
        return addTransactions.stream()
                .map(addTransaction -> StockAddTransactionEntity.builder()
                        .id(addTransaction.getId().getValue())
                        .additionDate(addTransaction.getAdditionDate())
                        .productId(addTransaction.getProductId().getValue())
                        .quantity(addTransaction.getQuantity().getValue())
                        .grossPrice(addTransaction.getGrossPrice().getAmount())
                        .totalGrossPrice(addTransaction.getTotalGrossPrice().getAmount())
                        .stockAddTransactionType(addTransaction.getStockAddTransactionType())
                        .invoiceId(addTransaction.getStockAddTransactionType() == null ? null : addTransaction.getInvoiceId().getValue())
                        .build())
                .collect(Collectors.toList());
    }
}