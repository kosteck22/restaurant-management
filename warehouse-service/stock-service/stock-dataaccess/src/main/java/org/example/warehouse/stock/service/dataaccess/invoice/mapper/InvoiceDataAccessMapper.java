package org.example.warehouse.stock.service.dataaccess.invoice.mapper;

import org.example.dataaccess.invoice.entity.InvoiceEntity;
import org.example.dataaccess.invoice.entity.OrderItemEntity;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.Quantity;
import org.example.domain.valueobject.UnitOfMeasure;
import org.example.warehouse.stock.service.domain.entity.Invoice;
import org.example.warehouse.stock.service.domain.entity.InvoiceItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceDataAccessMapper {
    public Invoice invoiceEntityToinvoice(InvoiceEntity invoiceEntity) {
        return Invoice.builder()
                .date(invoiceEntity.getCreatedAt())
                .items(invoiceOrderItemEntitiesToInvoiceItem(invoiceEntity.getOrder().getItems()))
                .build();
    }

    private List<InvoiceItem> invoiceOrderItemEntitiesToInvoiceItem(List<OrderItemEntity> items) {
        return items.stream()
                .map(invoiceItemEntity -> InvoiceItem.builder()
                        .name(invoiceItemEntity.getProduct().getName())
                        .unitOfMeasure(new UnitOfMeasure(invoiceItemEntity.getProduct().getUnitOfMeasure()))
                        .quantity(new Quantity(BigDecimal.valueOf(invoiceItemEntity.getQuantity())))
                        .grossPrice(new Money(invoiceItemEntity.getProduct().getGrossPrice()))
                        .build())
                .collect(Collectors.toList());
    }
}
