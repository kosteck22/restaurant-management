package org.example.warehouse.stock.service.dataaccess.stock.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.warehouse.stock.service.domain.entity.Stock;
import org.example.warehouse.stock.service.domain.valueobject.StockDeduceTransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(StockDeduceTransactionEntityId.class)
@Table(name = "stock-deduce-transactions")
@Entity
public class StockDeduceTransactionEntity {
    @Id
    private Long id;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "STOCK_ID")
    private Stock stock;

    private LocalDateTime deductionDate;
    private UUID productId;
    private BigDecimal quantity;

    @Enumerated(value = EnumType.STRING)
    private StockDeduceTransactionType stockDeduceTransactionType;
    private UUID saleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockDeduceTransactionEntity that = (StockDeduceTransactionEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(stock, that.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock);
    }
}
