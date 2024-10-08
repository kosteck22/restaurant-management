package org.example.sale.service.dataaccess.sale.mapper;

import org.example.domain.valueobject.*;
import org.example.sale.service.dataaccess.sale.entity.SaleEntity;
import org.example.sale.service.dataaccess.sale.entity.SaleItemEntity;
import org.example.sale.service.domain.entity.MenuItem;
import org.example.sale.service.domain.entity.Sale;
import org.example.sale.service.domain.entity.SaleItem;
import org.example.sale.service.domain.valueobject.SaleItemId;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.domain.DomainConstants.FAILURE_MESSAGE_DELIMITER;

@Component
public class SaleDataAccessMapper {
    public Sale saleEntityToSale(SaleEntity saleEntity) {
        return Sale.builder()
                .saleId(new SaleId(saleEntity.getId()))
                .date(saleEntity.getDate())
                .items(saleItemEntitiesToSaleItems(saleEntity.getItems()))
                .grossPrice(new Money(saleEntity.getGrossPrice()))
                .netPrice(new Money(saleEntity.getNetPrice()))
                .vat(new Money(saleEntity.getVat()))
                .trackingId(new TrackingId(saleEntity.getTrackingId()))
                .saleStatus(saleEntity.getSaleStatus())
                .failureMessages(saleEntity.getFailureMessages().isEmpty() ? new ArrayList<>() :
                        new ArrayList<>(Arrays.asList(saleEntity.getFailureMessages()
                                .split(FAILURE_MESSAGE_DELIMITER))))
                .build();
    }

    public SaleEntity saleToSaleEntity(Sale sale) {
        SaleEntity saleEntity = SaleEntity.builder()
                .id(sale.getId().getValue())
                .date(sale.getDate())
                .items(saleItemsToSaleItemEntities(sale.getItems()))
                .grossPrice(sale.getGrossPrice().getAmount())
                .netPrice(sale.getNetPrice().getAmount())
                .vat(sale.getVat().getAmount())
                .saleStatus(sale.getSaleStatus())
                .trackingId(sale.getTrackingId().getValue())
                .failureMessages(sale.getFailureMessages() != null ?
                        String.join(FAILURE_MESSAGE_DELIMITER, sale.getFailureMessages()) : "")
                .build();
        saleEntity.getItems().forEach(saleItemEntity -> saleItemEntity.setSale(saleEntity));

        return saleEntity;
    }

    private List<SaleItem> saleItemEntitiesToSaleItems(List<SaleItemEntity> items) {
        return items.stream()
                .map(saleItemEntity -> SaleItem.builder()
                        .saleItemId(new SaleItemId(saleItemEntity.getId()))
                        .menuItem(new MenuItem(new MenuItemId(saleItemEntity.getMenuItemId())))
                        .quantity(new Quantity(BigDecimal.valueOf(saleItemEntity.getQuantity())))
                        .discount(saleItemEntity.getDiscount())
                        .netPrice(new Money(saleItemEntity.getSale().getNetPrice()))
                        .grossPrice(new Money(saleItemEntity.getSale().getGrossPrice()))
                        .grossPriceTotal(new Money(saleItemEntity.getGrossPriceTotal()))
                        .build())
                .collect(Collectors.toList());
    }

    private List<SaleItemEntity> saleItemsToSaleItemEntities(List<SaleItem> items) {
        return items.stream()
                .map(saleItem -> SaleItemEntity.builder()
                        .id(saleItem.getId().getValue())
                        .menuItemId(saleItem.getMenuItem().getId().getValue())
                        .quantity(saleItem.getQuantity().getValue().intValue())
                        .discount(saleItem.getDiscount())
                        .netPrice(saleItem.getNetPrice().getAmount())
                        .grossPrice(saleItem.getGrossPrice().getAmount())
                        .grossPriceTotal(saleItem.getGrossPriceTotal().getAmount())
                        .build())
                .collect(Collectors.toList());
    }
}
