package org.example.invoice.service.domain.mapper;

import org.example.domain.valueobject.Quantity;
import org.example.invoice.service.domain.dto.create.CompanyRequest;
import org.example.invoice.service.domain.dto.create.CreateInvoiceCommand;
import org.example.invoice.service.domain.dto.create.CreateInvoiceResponse;
import org.example.invoice.service.domain.entity.Invoice;
import org.example.invoice.service.domain.entity.Order;
import org.example.invoice.service.domain.entity.OrderItem;
import org.example.invoice.service.domain.entity.Product;
import org.example.invoice.service.domain.valueobject.Company;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.UnitOfMeasure;
import org.example.domain.valueobject.VatRate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class InvoiceDataMapper {

    public Invoice createInvoiceCommandToInvoice(CreateInvoiceCommand invoiceCommand) {
        return Invoice.builder()
                .invoiceNumber(invoiceCommand.number())
                .createdAt(invoiceCommand.createdAt())
                .seller(companyRequestToCompany(invoiceCommand.seller()))
                .buyer(companyRequestToCompany(invoiceCommand.buyer()))
                .order(orderToOrderEntity(invoiceCommand.order()))
                .build();
    }

    private Order orderToOrderEntity(org.example.invoice.service.domain.dto.create.Order order) {
        List<OrderItem> items = orderItemsToOrderItemEntities(order.items());
        Money netTotal = Money.ZERO;
        Money vatTotal = Money.ZERO;
        Money grossTotal = Money.ZERO;

        for (OrderItem item : items) {
            netTotal = netTotal.add(item.getNetTotal());
            vatTotal = vatTotal.add(item.getVat());
            grossTotal = grossTotal.add(item.getGrossTotal());
        }

        return Order.builder()
                .items(items)
                .netPrice(netTotal)
                .vat(vatTotal)
                .grossPrice(grossTotal)
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(List<org.example.invoice.service.domain.dto.create.OrderItem> items) {
        return items.stream()
                .map(this::orderItemToOrderItemEntity)
                .collect(Collectors.toList());
    }

    private OrderItem orderItemToOrderItemEntity(org.example.invoice.service.domain.dto.create.OrderItem orderItem) {
        Product product = orderItemToProduct(orderItem);
        Money netTotal = new Money(orderItem.netPrice())
                .afterDiscount(orderItem.discount() == null ? 0 : orderItem.discount())
                .multiply(orderItem.quantity().intValue());

        BigDecimal add = BigDecimal.valueOf(orderItem.vatRate())
                .divide(BigDecimal.valueOf(100))
                .add(BigDecimal.ONE);
        BigDecimal amount = netTotal.getAmount()
                .multiply(add).setScale(2, RoundingMode.HALF_UP);
        Money grossTotal = new Money(amount);
        Money vat = grossTotal.subtract(netTotal);

        return OrderItem.builder()
                .product(product)
                .quantity(new Quantity(orderItem.quantity()))
                .discount(orderItem.discount() == null ? 0 : orderItem.discount())
                .netTotal(netTotal)
                .vat(vat)
                .grossTotal(grossTotal)
                .build();
    }

    private Product orderItemToProduct(org.example.invoice.service.domain.dto.create.OrderItem orderItem) {
        return Product.builder()
                .name(orderItem.productName())
                .netPrice(new Money(orderItem.netPrice()))
                .vatRate(VatRate.valueOf(orderItem.vatRate()))
                .grossPrice(new Money(orderItem.netPrice()
                        .multiply(BigDecimal.valueOf(orderItem.vatRate())
                                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                                .add(BigDecimal.ONE))
                        .setScale(2, RoundingMode.HALF_UP)))
                .unitOfMeasure(new UnitOfMeasure(orderItem.unitOfMeasure()))
                .build();
    }

    private Company companyRequestToCompany(CompanyRequest companyRequest) {
        return new Company(
                companyRequest.name(),
                companyRequest.nip(),
                companyRequest.regon(),
                companyRequest.street1(),
                companyRequest.street2(),
                companyRequest.city(),
                companyRequest.postalCode()
        );
    }

    public CreateInvoiceResponse invoiceToCreateInvoiceResponse(Invoice invoice, String message) {
        return new CreateInvoiceResponse(
                invoice.getId().getValue(),
                message
        );
    }
}
