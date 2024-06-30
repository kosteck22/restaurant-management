package org.example.warehouse.stock.take.service.dataaccess.stockTake.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockTakeItemEntityId implements Serializable {
    private Long id;
    private StockTakeEntity stockTake;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockTakeItemEntityId that = (StockTakeItemEntityId) o;
        return Objects.equals(id, that.id) && Objects.equals(stockTake, that.stockTake);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stockTake);
    }
}
