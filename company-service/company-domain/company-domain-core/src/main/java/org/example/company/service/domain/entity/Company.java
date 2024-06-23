package org.example.company.service.domain.entity;

import org.example.company.service.domain.exception.CompanyDomainException;
import org.example.company.service.domain.valueobject.CompanyId;
import org.example.domain.entity.AggregateRoot;

import javax.xml.transform.Source;
import java.util.UUID;

public class Company extends AggregateRoot<CompanyId> {
    private final String name;
    private final String taxNumber;
    private final String regon;
    private final String street1;
    private final String street2;
    private final String city;
    private final String postalCode;

    private Company(Builder builder) {
        setId(builder.companyId);
        name = builder.name;
        taxNumber = builder.taxNumber;
        regon = builder.regon;
        street1 = builder.street1;
        street2 = builder.street2;
        city = builder.city;
        postalCode = builder.postalCode;
    }

    public void validateCompany() {
        if (isNullOrEmpty(name)) {
            throw new CompanyDomainException("Name is required!");
        }
        if (isNullOrEmpty(taxNumber)) {
            throw new CompanyDomainException("Tax Number is required!");
        }
        if (isNullOrEmpty(city)) {
            throw new CompanyDomainException("City is required!");
        }
        if (isNullOrEmpty(street1)) {
            throw new CompanyDomainException("Street is required!");
        }
        if (isNullOrEmpty(postalCode)) {
            throw new CompanyDomainException("Postal code is required!");
        }
        if (!isNullOrEmpty(postalCode) && isPostalCodeIncorrectFormat()) {
            throw new CompanyDomainException("Postal code must be in **-*** format, where * is digit!");
        }
        if (!isNullOrEmpty(taxNumber) && isTaxNumberIncorrectLength()) {
            throw new CompanyDomainException("Tax Number must contain 10 digits!");
        }
        if (!isNullOrEmpty(regon) && isRegonIncorrectLength()) {
            throw new CompanyDomainException("Regon number must contain 8 digits!");
        }
    }

    public void initializeCompany() {
        setId(new CompanyId(UUID.randomUUID()));
    }

    private boolean isNullOrEmpty(String field) {
        return (field == null || field.trim().isEmpty());
    }

    private boolean isPostalCodeIncorrectFormat() {
        return postalCode.matches("^\\d{2}-\\d{3}$");
    }

    private boolean isTaxNumberIncorrectLength() {
        String normalizedNip = taxNumber.replace("-", "");
        return !normalizedNip.matches("^\\d{10}$");
    }

    private boolean isRegonIncorrectLength() {
        return regon.matches("^\\d{8}$");
    }

    public String getName() {
        return name;
    }

    public String getTaxNumber() {
        return taxNumber;
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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private CompanyId companyId;
        private String name;
        private String taxNumber;
        private String regon;
        private String street1;
        private String street2;
        private String city;
        private String postalCode;

        private Builder() {
        }

        public Builder id(CompanyId val) {
            companyId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder taxNumber(String val) {
            taxNumber = val;
            return this;
        }

        public Builder regon(String val) {
            regon = val;
            return this;
        }

        public Builder street1(String val) {
            street1 = val;
            return this;
        }

        public Builder street2(String val) {
            street2 = val;
            return this;
        }

        public Builder city(String val) {
            city = val;
            return this;
        }

        public Builder postalCode(String val) {
            postalCode = val;
            return this;
        }

        public Company build() {
            return new Company(this);
        }
    }
}
