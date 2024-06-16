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
@Table(name = "invoices")
@Entity
public class InvoiceEntity {
    @Id
    private UUID id;
    private String number;
    private LocalDate createdAt;
    private UUID trackingId;
    private String failureMessages;

    @OneToOne(mappedBy = "invoice", cascade = CascadeType.ALL)
    private CompanyEntity seller;

    @OneToOne(mappedBy = "invoice", cascade = CascadeType.ALL)
    private CompanyEntity buyer;

    @OneToOne(mappedBy = "invoice", cascade = CascadeType.ALL)
    private OrderEntity order;

}
