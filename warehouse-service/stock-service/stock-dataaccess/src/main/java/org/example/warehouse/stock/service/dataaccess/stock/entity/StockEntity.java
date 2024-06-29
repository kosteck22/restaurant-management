package org.example.warehouse.stock.service.dataaccess.stock.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.warehouse.stock.service.domain.valueobject.StockStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stocks")
@Entity
public class StockEntity {
    @Id
    private UUID id;
    private BigDecimal totalGrossPrice;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    private List<StockAddTransactionEntity> addingTransactions;


    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    private List<StockDeduceTransactionEntity> deducingTransactions;

    private UUID fromStockTake;
    private UUID toStockTake;

    @Enumerated(value = EnumType.STRING)
    private StockStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockEntity that = (StockEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
