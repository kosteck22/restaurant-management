package org.example.invoice.service.domain.dto.create;

import jakarta.validation.constraints.NotBlank;
import org.example.application.validation.ValidTaxNumber;

public record CompanyRequest (
        @NotBlank String name,
        @NotBlank String street1,
        String street2,
        @NotBlank String city,
        @NotBlank String postalCode,
        @NotBlank @ValidTaxNumber String nip,
        String regon
) {}
