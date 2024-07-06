package org.example.invoice.service.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
public class Company {
    private final String name;
    private final String taxNumber;
    private final String regon;
    private final String street1;
    private final String street2;
    private final String city;
    private final String postalCode;

    private Company(Builder builder) {
        name = builder.name;
        taxNumber = builder.taxNumber;
        regon = builder.regon;
        street1 = builder.street1;
        street2 = builder.street2;
        city = builder.city;
        postalCode = builder.postalCode;
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
        private String name;
        private String taxNumber;
        private String regon;
        private String street1;
        private String street2;
        private String city;
        private String postalCode;

        private Builder() {
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
