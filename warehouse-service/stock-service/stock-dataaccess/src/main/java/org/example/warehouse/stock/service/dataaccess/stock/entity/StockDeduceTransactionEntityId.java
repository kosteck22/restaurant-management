package org.example.warehouse.stock.service.dataaccess.stock.entity;

import lombok.*;
import org.example.warehouse.stock.service.domain.entity.Stock;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDeduceTransactionEntityId implements Serializable {
    private Long id;
    private Stock stock;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockDeduceTransactionEntityId that = (StockDeduceTransactionEntityId) o;
        return Objects.equals(id, that.id) && Objects.equals(stock, that.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock);
    }
}
