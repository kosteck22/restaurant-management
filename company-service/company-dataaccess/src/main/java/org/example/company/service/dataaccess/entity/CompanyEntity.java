package org.example.company.service.dataaccess.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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
    private String street1;
    private String street2;
    private String city;
    private String postalCode;
    private String taxNumber;
    private String regon;
}
