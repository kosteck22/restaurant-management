package org.example.warehouse.stock.take.service.dataaccess.stockTake.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.domain.valueobject.Money;
import org.example.warehouse.stock.take.service.domain.valueobject.StockTakeStatus;

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
@Table(name = "stock-takes")
@Entity
public class StockTakeEntity {
    private UUID id;
    private LocalDateTime preparedDate;
    private BigDecimal totalPrice;

    @Enumerated(value = EnumType.STRING)
    private StockTakeStatus status;

    @OneToMany(mappedBy = "stockTake", cascade = CascadeType.ALL)
    private List<StockItemEntity> items;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockTakeEntity that = (StockTakeEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
