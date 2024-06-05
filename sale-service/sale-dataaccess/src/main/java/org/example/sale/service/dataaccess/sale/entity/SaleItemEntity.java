package org.example.sale.service.dataaccess.sale.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.domain.valueobject.Money;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SaleItemEntityId.class)
@Table(name = "sale_items")
@Entity
public class SaleItemEntity {
    @Id
    private Long id;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SALE_ID")
    private SaleEntity sale;

    private UUID menuItemId;
    private Integer quantity;
    private Integer discount;
    private BigDecimal netPrice;
    private BigDecimal grossPrice;
    private BigDecimal grossPriceTotal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleItemEntity that = (SaleItemEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(sale, that.sale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sale);
    }
}
