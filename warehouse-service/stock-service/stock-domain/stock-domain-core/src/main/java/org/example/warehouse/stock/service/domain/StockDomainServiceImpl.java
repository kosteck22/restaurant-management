package org.example.warehouse.stock.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.valueobject.ProductId;
import org.example.warehouse.stock.service.domain.entity.*;
import org.example.warehouse.stock.service.domain.valueobject.StockAddTransactionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class StockDomainServiceImpl implements StockDomainService {

    @Override
    public void addProductsToStock(Stock stock, Invoice invoice, List<Product> products) {
        initializeMissingProducts(invoice, products);
        List<StockAddTransaction> stockAddTransactions = invoiceToStockAddTransaction(invoice, products);
        stock.addStockAddTransactions(stockAddTransactions);
    }

    private void initializeMissingProducts(Invoice invoice, List<Product> products) {
        Map<String, Product> productNameProductMap = products.stream()
                .collect(Collectors.toMap(Product::getName, Function.identity()));
        invoice.getItems().forEach(invoiceItem -> {
            Product product = productNameProductMap.get(invoiceItem.getName());
            if (product == null) {
                products.add(Product.builder()
                        .productId(new ProductId(UUID.randomUUID()))
                        .name(invoiceItem.getName())
                        .unitOfMeasure(invoiceItem.getUnitOfMeasure())
                        .build());
            }
        });
    }

    private List<StockAddTransaction> invoiceToStockAddTransaction(Invoice invoice, List<Product> products) {
        List<StockAddTransaction> stockAddTransactions = new ArrayList<>();
        Map<String, Product> productNameToProductMap = products.stream()
                .collect(Collectors.toMap(Product::getName, Function.identity()));

        for (InvoiceItem invoiceItem : invoice.getItems()) {
            stockAddTransactions.add(StockAddTransaction.builder()
                    .productId(productNameToProductMap.get(invoiceItem.getName()).getId())
                    .additionDate(invoice.getDate().atStartOfDay())
                    .quantity(invoiceItem.getQuantity())
                    .grossPrice(invoiceItem.getGrossPrice())
                    .totalGrossPrice(invoiceItem.getGrossPrice().multiply(invoiceItem.getQuantity().getValue()))
                    .stockAddTransactionType(StockAddTransactionType.INVOICE)
                    .invoiceId(invoice.getId())
                     .build());

        }
        return stockAddTransactions;
    }
}
