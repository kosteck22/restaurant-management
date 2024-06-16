package org.example.warehouse.stock.service.dataaccess.stock.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.domain.valueobject.InvoiceId;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.ProductId;
import org.example.domain.valueobject.Quantity;
import org.example.warehouse.stock.service.domain.entity.Stock;
import org.example.warehouse.stock.service.domain.valueobject.StockAddTransactionType;
import org.example.warehouse.stock.service.domain.valueobject.StockId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(StockAddTransactionEntityId.class)
@Table(name = "stock-add-transactions")
@Entity
public class StockAddTransactionEntity {
    @Id
    private Long id;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "STOCK_ID")
    private Stock stock;

    private LocalDateTime additionDate;
    private UUID productId;
    private BigDecimal quantity;
    private BigDecimal totalGrossPrice;
    private BigDecimal grossPrice;

    @Enumerated(value = EnumType.STRING)
    private StockAddTransactionType stockAddTransactionType;
    private UUID invoiceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockAddTransactionEntity that = (StockAddTransactionEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(stock, that.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock);
    }
}
