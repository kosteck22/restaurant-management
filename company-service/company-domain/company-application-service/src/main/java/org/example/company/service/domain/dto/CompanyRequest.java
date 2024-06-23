package org.example.company.service.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.example.application.validation.ValidPostalCode;
import org.example.application.validation.ValidRegon;
import org.example.application.validation.ValidTaxNumber;

@Builder
public record CompanyRequest (
        @NotBlank String name,
        @NotBlank String street1,
        String street2,
        @NotBlank String city,
        @NotBlank @ValidPostalCode String postalCode,
        @NotBlank @ValidTaxNumber String taxNumber,
        @ValidRegon String regon
) {
}
