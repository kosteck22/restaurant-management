package org.example.invoice.service.dataaccess.invoice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
@Entity
public class CompanyEntity {
    @Id
    private UUID id;
    private String name;
    private String nip;
    private String regon;
    private String street1;
    private String street2;
    private String city;
    private String postalCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "INVOICE_ID")
    private InvoiceEntity invoice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyEntity that = (CompanyEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
