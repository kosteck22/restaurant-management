package org.example.invoice.service.dataaccess.invoice.mapper;

import org.example.dataaccess.invoice.entity.*;
import org.example.domain.valueobject.*;
import org.example.invoice.service.domain.entity.Invoice;
import org.example.invoice.service.domain.entity.Order;
import org.example.invoice.service.domain.entity.OrderItem;
import org.example.invoice.service.domain.entity.Product;
import org.example.invoice.service.domain.valueobject.OrderId;
import org.example.invoice.service.domain.valueobject.OrderItemId;
import org.example.invoice.service.domain.valueobject.ProductId;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.domain.DomainConstants.FAILURE_MESSAGE_DELIMITER;

@Component
public class InvoiceDataAccessMapper {
    public Invoice invoiceEntityToInvoice(InvoiceEntity invoiceEntity) {
        return Invoice.builder()
                .invoiceId(new InvoiceId(invoiceEntity.getId()))
                .invoiceNumber(invoiceEntity.getNumber())
                .createdAt(invoiceEntity.getCreatedAt())
                .seller(companyEntityToCompany(invoiceEntity.getSeller()))
                .buyer(companyEntityToCompany(invoiceEntity.getBuyer()))
                .order(orderEntityToOrder(invoiceEntity.getOrder()))
                .trackingId(new TrackingId(invoiceEntity.getTrackingId()))
                .failureMessages(invoiceEntity.getFailureMessages().isEmpty() ? new ArrayList<>() :
                        new ArrayList<>(Arrays.asList(invoiceEntity.getFailureMessages()
                                .split(FAILURE_MESSAGE_DELIMITER))))
                .build();
    }

    public InvoiceEntity invoiceToInvoiceEntity(Invoice invoice) {
        InvoiceEntity invoiceEntity = InvoiceEntity.builder()
                .id(invoice.getId().getValue())
                .number(invoice.getInvoiceNumber())
                .createdAt(invoice.getCreatedAt())
                .seller(companyToCompanyEntity(invoice.getSeller()))
                .buyer(companyToCompanyEntity(invoice.getBuyer()))
                .order(orderToOrderEntity(invoice.getOrder()))
                .failureMessages(invoice.getFailureMessages() != null ?
                        String.join(FAILURE_MESSAGE_DELIMITER, invoice.getFailureMessages()) : "")
                .trackingId(invoice.getTrackingId().getValue())
                .build();
        invoiceEntity.getOrder().setInvoice(invoiceEntity);

        return invoiceEntity;
    }

    private Order orderEntityToOrder(OrderEntity order) {
        return Order.builder()
                .invoiceId(new InvoiceId(order.getInvoice().getId()))
                .netPrice(new Money(order.getNetPrice()))
                .vat(new Money(order.getVat()))
                .grossPrice(new Money(order.getGrossPrice()))
                .items(orderItemEntitiesToOrderItems(order.getItems()))
                .build();
    }

    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> items) {
        return items.stream().map(orderItemEntity -> OrderItem.builder()
                        .orderItemId(new OrderItemId(orderItemEntity.getId()))
                        .orderId(new OrderId(orderItemEntity.getOrder().getId()))
                        .product(productEntityToProduct(orderItemEntity.getProduct()))
                        .quantity(new Quantity(BigDecimal.valueOf(orderItemEntity.getQuantity())))
                        .discount(orderItemEntity.getDiscount())
                        .netTotal(new Money(orderItemEntity.getNetTotal()))
                        .vat(new Money(orderItemEntity.getVat()))
                        .grossTotal(new Money(orderItemEntity.getGrossTotal()))
                        .build())
                .collect(Collectors.toList());
    }

    private Product productEntityToProduct(InvoiceProductEntity product) {
        return Product.builder()
                .productId(new ProductId(product.getId()))
                .name(product.getName())
                .netPrice(new Money(product.getNetPrice()))
                .grossPrice(new Money(product.getGrossPrice()))
                .vatRate(product.getVatRate())
                .unitOfMeasure(new UnitOfMeasure(product.getUnitOfMeasure()))
                .build();
    }

    private org.example.invoice.service.domain.valueobject.Company companyEntityToCompany(Company companyEntity) {
        return new org.example.invoice.service.domain.valueobject.Company(
                companyEntity.getName(),
                companyEntity.getNip(),
                companyEntity.getRegon(),
                companyEntity.getStreet1(),
                companyEntity.getStreet2(),
                companyEntity.getCity(),
                companyEntity.getPostalCode()
        );
    }

    private OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .netPrice(order.getNetPrice().getAmount())
                .vat(order.getVat().getAmount())
                .grossPrice(order.getGrossPrice().getAmount())
                .items(orderItemsToOrderItemEntities(order.getItems()))
                .build();
        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));

        return orderEntity;
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(List<OrderItem> items) {
        return items.stream()
                .map(orderItem -> {
                    OrderItemEntity orderItemEntity = OrderItemEntity.builder()
                            .id(orderItem.getId().getValue())
                            .quantity(orderItem.getQuantity().getValue().intValue())
                            .discount(orderItem.getDiscount())
                            .netTotal(orderItem.getNetTotal().getAmount())
                            .vat(orderItem.getVat().getAmount())
                            .grossTotal(orderItem.getGrossTotal().getAmount())
                            .product(productToProductEntity(orderItem.getProduct()))
                            .build();
                    orderItemEntity.getProduct().setOrderItem(orderItemEntity);

                    return orderItemEntity;
                })
                .collect(Collectors.toList());
    }

    private InvoiceProductEntity productToProductEntity(Product product) {
        return InvoiceProductEntity.builder()
                .id(product.getId().getValue())
                .name(product.getName())
                .netPrice(product.getNetPrice().getAmount())
                .grossPrice(product.getGrossPrice().getAmount())
                .vatRate(product.getVatRate())
                .unitOfMeasure(product.getUnitOfMeasure().getName())
                .build();
    }

    private Company companyToCompanyEntity(org.example.invoice.service.domain.valueobject.Company company) {
        return Company.builder()
                .name(company.getName())
                .nip(company.getTaxNumber())
                .regon(company.getRegon())
                .street1(company.getStreet1())
                .street2(company.getStreet2())
                .city(company.getCity())
                .postalCode(company.getPostalCode())
                .build();
    }
}
