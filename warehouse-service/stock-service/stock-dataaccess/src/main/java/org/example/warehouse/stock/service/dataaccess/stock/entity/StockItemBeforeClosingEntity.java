package org.example.warehouse.stock.service.dataaccess.stock.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.domain.valueobject.InvoiceId;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.ProductId;
import org.example.domain.valueobject.Quantity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(StockItemBeforeClosingEntityId.class)
@Table(name = "stock-items-before-closing")
@Entity
public class StockItemBeforeClosingEntity {
    @Id
    private Long id;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "STOCK_ID")
    private StockEntity stock;

    private LocalDateTime additionDate;
    private UUID productId;
    private BigDecimal quantity;
    private BigDecimal grossPrice;
    private UUID invoiceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockItemBeforeClosingEntity that = (StockItemBeforeClosingEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(stock, that.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock);
    }
}
