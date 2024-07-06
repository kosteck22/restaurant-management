package org.example.sale.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.sale.service.domain.dto.create.CreateSaleCommand;
import org.example.sale.service.domain.dto.create.CreateSaleResponse;
import org.example.sale.service.domain.entity.Menu;
import org.example.sale.service.domain.entity.Sale;
import org.example.sale.service.domain.event.SaleCreatedEvent;
import org.example.sale.service.domain.exception.SaleDomainException;
import org.example.sale.service.domain.mapper.SaleDataMapper;
import org.example.sale.service.domain.ports.output.repository.MenuRepository;
import org.example.sale.service.domain.ports.output.repository.SaleRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SaleCreateCommandHandler {

    private final SaleDomainService saleDomainService;
    private final SaleRepository saleRepository;
    private final MenuRepository menuRepository;
    private final SaleDataMapper saleDataMapper;

    public SaleCreateCommandHandler(SaleDomainService saleDomainService,
                                    SaleRepository saleRepository,
                                    MenuRepository menuRepository,
                                    SaleDataMapper saleDataMapper) {
        this.saleDomainService = saleDomainService;
        this.saleRepository = saleRepository;
        this.menuRepository = menuRepository;
        this.saleDataMapper = saleDataMapper;
    }

    @Transactional
    public CreateSaleResponse createSale(CreateSaleCommand createSaleCommand) {
        Menu menu = checkMenu(createSaleCommand);
        Sale sale = saleDataMapper.CreateSaleCommandToSale(createSaleCommand);
        SaleCreatedEvent saleCreatedEvent = saleDomainService.validateAndInitiateSale(sale, menu);
        saveSale(saleCreatedEvent.getSale());
        log.info("Sale is created with id: {}", saleCreatedEvent.getSale().getId().getValue());
        return saleDataMapper.saleToCreateSaleResponse(sale, "Sale created successfully");
    }

    private Sale saveSale(Sale sale) {
        Sale saleResult = saleRepository.save(sale);
        if (saleResult == null) {
            log.error("Could not save sale!");
            throw new SaleDomainException("Could not save sale!");
        }
        log.info("Sale is saved with id: {}", saleResult.getId().getValue());
        return saleResult;
    }

    private Menu checkMenu(CreateSaleCommand createSaleCommand) {
        Menu menu = saleDataMapper.createSaleCommandToMenu(createSaleCommand);
        Optional<Menu> optionalMenu = menuRepository.findMenuInformation(menu);
        if (optionalMenu.isEmpty()) {
            String menuItemIds = menu.getItems().stream()
                    .map(menuItem -> menuItem.getId().getValue().toString())
                    .collect(Collectors.joining(","));
            log.warn("Could not find menu for menu item ids: %s!".formatted(menuItemIds));
            throw new SaleDomainException("Could not find menu for menu item ids: %s"
                    .formatted(menuItemIds));
        }
        return optionalMenu.get();
    }

}
