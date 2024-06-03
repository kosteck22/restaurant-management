package org.example.invoice.service.domain.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateInvoiceCommand (
        @NotBlank String number,
        @NotNull LocalDate createdAt,
        @NotNull CompanyRequest seller,
        @NotNull CompanyRequest buyer,
        @NotNull Order order
) {}
