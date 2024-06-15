package org.example.sale.service.dataaccess.sale.adapter;

import org.example.sale.service.dataaccess.sale.mapper.SaleDataAccessMapper;
import org.example.sale.service.dataaccess.sale.repository.SaleJpaRepository;
import org.example.sale.service.domain.entity.Sale;
import org.example.sale.service.domain.ports.output.repository.SaleRepository;
import org.example.domain.valueobject.SaleId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SaleRepositoryImpl implements SaleRepository {

    private final SaleJpaRepository saleJpaRepository;
    private final SaleDataAccessMapper saleDataAccessMapper;

    public SaleRepositoryImpl(SaleJpaRepository saleJpaRepository,
                              SaleDataAccessMapper saleDataAccessMapper) {
        this.saleJpaRepository = saleJpaRepository;
        this.saleDataAccessMapper = saleDataAccessMapper;
    }

    @Override
    public Sale save(Sale sale) {
        return saleDataAccessMapper.saleEntityToSale(
                saleJpaRepository.save(saleDataAccessMapper.saleToSaleEntity(sale)));
    }

    @Override
    public Optional<Sale> findById(SaleId saleId) {
        return saleJpaRepository.findById(saleId.getValue())
                .map(saleDataAccessMapper::saleEntityToSale);
    }
}
