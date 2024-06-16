package org.example.warehouse.stock.service.dataaccess.stock.adapter;

import org.example.warehouse.stock.service.dataaccess.stock.mapper.StockDataAccessMapper;
import org.example.warehouse.stock.service.dataaccess.stock.repository.StockJpaRepository;
import org.example.warehouse.stock.service.domain.entity.Stock;
import org.example.warehouse.stock.service.domain.ports.output.repository.StockRepository;
import org.example.warehouse.stock.service.domain.valueobject.StockStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockRepositoryImpl implements StockRepository {
    private final StockJpaRepository stockJpaRepository;
    private final StockDataAccessMapper stockDataAccessMapper;

    public StockRepositoryImpl(StockJpaRepository stockJpaRepository,
                               StockDataAccessMapper stockDataAccessMapper) {
        this.stockJpaRepository = stockJpaRepository;
        this.stockDataAccessMapper = stockDataAccessMapper;
    }

    @Override
    public List<Stock> findByStatus(StockStatus status) {
        return stockJpaRepository.findByStatus(status);
    }

    @Override
    public Stock save(Stock stock) {
        return stockDataAccessMapper.stockEntityToStock(stockJpaRepository.save(stockDataAccessMapper.stockToStockEntity(stock)));
    }
}
