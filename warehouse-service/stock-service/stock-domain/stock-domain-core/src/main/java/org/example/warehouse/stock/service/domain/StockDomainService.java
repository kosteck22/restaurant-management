package org.example.warehouse.stock.service.domain;

import org.example.warehouse.stock.service.domain.entity.Invoice;
import org.example.warehouse.stock.service.domain.entity.Product;
import org.example.warehouse.stock.service.domain.entity.Stock;
import org.example.warehouse.stock.service.domain.entity.StockTake;
import org.example.warehouse.stock.service.domain.event.StockEvent;

import java.util.List;

public interface StockDomainService {
    public void addProductsToStock(Stock stock, Invoice invoice, List<Product> products);

    StockEvent closeActiveStock(Stock stock, StockTake stockTake, List<String> failureMessages);
}
