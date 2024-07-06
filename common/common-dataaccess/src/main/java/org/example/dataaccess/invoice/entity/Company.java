package org.example.dataaccess.invoice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {

    private String name;
    private String nip;
    private String regon;
    private String street1;
    private String street2;
    private String city;
    private String postalCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company that = (Company) o;
        return Objects.equals(nip, that.nip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nip);
    }
}
