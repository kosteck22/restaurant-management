package org.example.dataaccess.invoice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoices", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"number", "seller_name"})
})
@Entity
public class InvoiceEntity {
    @Id
    private UUID id;
    private String number;
    private LocalDate createdAt;
    private UUID trackingId;
    private String failureMessages;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "seller_name")),
            @AttributeOverride(name = "nip", column = @Column(name = "seller_nip")),
            @AttributeOverride(name = "regon", column = @Column(name = "seller_regon")),
            @AttributeOverride(name = "street1", column = @Column(name = "seller_street1")),
            @AttributeOverride(name = "street2", column = @Column(name = "seller_street2")),
            @AttributeOverride(name = "city", column = @Column(name = "seller_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "seller_postalCode"))
    })
    private Company seller;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "buyer_name")),
            @AttributeOverride(name = "nip", column = @Column(name = "buyer_nip")),
            @AttributeOverride(name = "regon", column = @Column(name = "buyer_regon")),
            @AttributeOverride(name = "street1", column = @Column(name = "buyer_street1")),
            @AttributeOverride(name = "street2", column = @Column(name = "buyer_street2")),
            @AttributeOverride(name = "city", column = @Column(name = "buyer_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "buyer_postalCode"))
    })
    private Company buyer;

    @OneToOne(mappedBy = "invoice", cascade = CascadeType.ALL)
    private OrderEntity order;

}
