package org.example.warehouse.stock.take.service.dataaccess.stockTake.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(StockTakeItemEntityId.class)
@Table(name = "order_items")
@Entity
public class StockTakeItemEntity {
    @Id
    private Long id;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "STOCK_TAKE_ID")
    private StockTakeEntity stockTake;

    private UUID productId;
    private String name;
    private BigDecimal quantity;
    private BigDecimal totalPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockTakeItemEntity that = (StockTakeItemEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(stockTake, that.stockTake);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stockTake);
    }
}
