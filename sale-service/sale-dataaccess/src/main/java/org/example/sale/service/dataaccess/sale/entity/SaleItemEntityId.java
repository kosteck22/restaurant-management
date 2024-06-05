package org.example.sale.service.dataaccess.sale.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemEntityId implements Serializable {
    private Long id;
    private SaleEntity sale;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleItemEntityId that = (SaleItemEntityId) o;
        return Objects.equals(id, that.id) && Objects.equals(sale, that.sale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sale);
    }
}
