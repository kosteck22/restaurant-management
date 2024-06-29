package org.example.sale.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.valueobject.SaleId;
import org.example.sale.service.domain.dto.complete.CompleteSaleResponse;
import org.example.sale.service.domain.entity.Sale;
import org.example.sale.service.domain.event.SalePaidEvent;
import org.example.sale.service.domain.exception.SaleDomainException;
import org.example.sale.service.domain.exception.SaleNotFoundException;
import org.example.sale.service.domain.mapper.SaleDataMapper;
import org.example.sale.service.domain.ports.output.message.publisher.SalePaidEventMessagePublisher;
import org.example.sale.service.domain.ports.output.repository.SaleRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
public class SaleCompleteCommandHandler {

    private final SaleDomainService saleDomainService;
    private final SaleRepository saleRepository;
    private final SaleDataMapper saleDataMapper;
    private final SalePaidEventMessagePublisher salePaidEventMessagePublisher;

    public SaleCompleteCommandHandler(SaleDomainService saleDomainService,
                                      SaleRepository saleRepository,
                                      SaleDataMapper saleDataMapper,
                                      SalePaidEventMessagePublisher salePaidEventMessagePublisher) {
        this.saleDomainService = saleDomainService;
        this.saleRepository = saleRepository;
        this.saleDataMapper = saleDataMapper;
        this.salePaidEventMessagePublisher = salePaidEventMessagePublisher;
    }

    @Transactional
    public CompleteSaleResponse completeSale(UUID saleId) {
        Sale sale = findSale(saleId);
        SalePaidEvent salePaidEvent = saleDomainService.completeSale(sale, salePaidEventMessagePublisher);
        persistSale(sale);
        salePaidEvent.fire();
        return saleDataMapper.saleToCompleteSaleResponse(sale, "Sale is completed.");
    }

    private Sale findSale(UUID saleId) {
        return saleRepository.findById(new SaleId(saleId))
                .orElseThrow(() -> {
                    log.warn("Sale not found with id: {}", saleId);
                    return new SaleNotFoundException("Sale not found with id %s".formatted(saleId));
                });
    }

    private void persistSale(Sale sale) {
        Sale saleResult = saleRepository.save(sale);
        if (saleResult == null) {
            log.error("Could not update sale status!");
            throw new SaleDomainException("Could not update sale status!");
        }
        log.info("Sale is completed with id: {}", saleResult.getId().getValue());
    }
}
