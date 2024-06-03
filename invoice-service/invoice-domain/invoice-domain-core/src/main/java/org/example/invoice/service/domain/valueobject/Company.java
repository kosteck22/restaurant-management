package org.example.invoice.service.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public class Company {
    private final UUID id;
    private final String name;
    private final String nip;
    private final String regon;
    private final String street1;
    private final String street2;
    private final String city;
    private final String postalCode;

    public Company(UUID id,
                   String name,
                   String nip,
                   String regon,
                   String street1,
                   String street2,
                   String city,
                   String postalCode) {
        this.id = id;
        this.name = name;
        this.nip = nip;
        this.regon = regon;
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.postalCode = postalCode;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNip() {
        return nip;
    }

    public String getRegon() {
        return regon;
    }

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(nip, company.nip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nip);
    }
}
