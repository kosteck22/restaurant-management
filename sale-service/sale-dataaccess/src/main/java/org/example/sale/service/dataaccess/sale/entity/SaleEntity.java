package org.example.sale.service.dataaccess.sale.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.sale.service.domain.valueobject.SaleStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sales")
@Entity
public class SaleEntity {
    @Id
    private UUID id;
    private LocalDateTime date;
    private BigDecimal grossPrice;
    private BigDecimal netPrice;
    private BigDecimal vat;
    private SaleStatus saleStatus;
    private UUID trackingId;
    private String failureMessages;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleItemEntity> items;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleEntity that = (SaleEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
