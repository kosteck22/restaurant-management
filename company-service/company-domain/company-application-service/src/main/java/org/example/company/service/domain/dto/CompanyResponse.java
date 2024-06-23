package org.example.company.service.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.example.application.validation.ValidTaxNumber;

@Builder
public record CompanyResponse (
        @NotBlank String name,
        @NotBlank String street1,
        String street2,
        @NotBlank String city,
        @NotBlank String postalCode,
        @NotBlank @ValidTaxNumber String taxNumber,
        String regon
){
}
