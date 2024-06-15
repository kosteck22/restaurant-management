package org.example.warehouse.stock.take.service.dataaccess.stockTake.adapter;

import org.example.warehouse.stock.take.service.dataaccess.stockTake.entity.StockTakeEntity;
import org.example.warehouse.stock.take.service.dataaccess.stockTake.mapper.StockTakeDataAccessMapper;
import org.example.warehouse.stock.take.service.dataaccess.stockTake.repository.StockTakeJpaRepository;
import org.example.warehouse.stock.take.service.domain.entity.StockTake;
import org.example.warehouse.stock.take.service.domain.mapper.StockTakeDataMapper;
import org.example.warehouse.stock.take.service.domain.ports.output.repository.StockTakeRepository;
import org.example.warehouse.stock.take.service.domain.valueobject.StockTakeStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockTakeRepositoryImpl implements StockTakeRepository {

    private final StockTakeJpaRepository stockTakeJpaRepository;
    private final StockTakeDataAccessMapper stockTakeDataAccessMapper;

    public StockTakeRepositoryImpl(StockTakeJpaRepository stockTakeJpaRepository,
                                   StockTakeDataAccessMapper stockTakeDataAccessMapper) {
        this.stockTakeJpaRepository = stockTakeJpaRepository;
        this.stockTakeDataAccessMapper = stockTakeDataAccessMapper;
    }

    @Override
    public StockTake save(StockTake stockTake) {
        return stockTakeDataAccessMapper.stockTakeEntityToStockTake(
                stockTakeJpaRepository.save(
                        stockTakeDataAccessMapper.stockTakeToStockTakeEntity(stockTake)));
    }

    @Override
    public List<StockTake> findByStatusAndPreparedDateAfter(StockTakeStatus status, LocalDateTime preparedDate) {
        List<StockTakeEntity> stockTakeEntities = stockTakeJpaRepository.findByStatusAndPreparedDateAfter(status, preparedDate);
        return stockTakeEntities.stream()
                .map(stockTakeDataAccessMapper::stockTakeEntityToStockTake)
                .collect(Collectors.toList());
    }
}
