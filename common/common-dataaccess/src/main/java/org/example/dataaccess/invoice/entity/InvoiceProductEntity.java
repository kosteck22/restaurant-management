package org.example.dataaccess.invoice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.domain.valueobject.VatRate;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice_products")
@Entity
public class InvoiceProductEntity {
    @Id
    private UUID id;
    private String name;
    private BigDecimal netPrice;
    private BigDecimal grossPrice;

    @Enumerated(value = EnumType.STRING)
    private VatRate vatRate;
    private String unitOfMeasure;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "order_item_id", referencedColumnName = "id"),
            @JoinColumn(name = "order_id", referencedColumnName = "ORDER_ID")
    })
    private OrderItemEntity orderItem;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceProductEntity that = (InvoiceProductEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
