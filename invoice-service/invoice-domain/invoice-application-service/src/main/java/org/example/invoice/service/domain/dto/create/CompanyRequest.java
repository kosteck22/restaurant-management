package org.example.invoice.service.domain.dto.create;

import jakarta.validation.constraints.NotBlank;
import org.example.invoice.service.domain.dto.validation.ValidNip;

public record CompanyRequest (
        @NotBlank String name,
        @NotBlank String street1,
        @NotBlank String street2,
        @NotBlank String city,
        @NotBlank String postalCode,
        @NotBlank @ValidNip String nip,
        String regon
) {}
