package org.example.warehouse.stock.service.dataaccess.stock.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.warehouse.stock.service.domain.entity.Stock;
import org.example.warehouse.stock.service.domain.valueobject.StockSubtractTransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(StockSubtractTransactionEntityId.class)
@Table(name = "stock-subtract-transactions")
@Entity
public class StockSubtractTransactionEntity {
    @Id
    private Long id;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "STOCK_ID")
    private StockEntity stock;

    private LocalDateTime subtractDate;
    private UUID productId;
    private BigDecimal quantity;

    @Enumerated(value = EnumType.STRING)
    private StockSubtractTransactionType stockSubtractTransactionType;
    private UUID saleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockSubtractTransactionEntity that = (StockSubtractTransactionEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(stock, that.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock);
    }
}
