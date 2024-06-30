package org.example.invoice.service.domain.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateInvoiceCommand (
        @NotBlank(message = "Invoice number cannot be blank") String number,
        @NotNull(message = "Invoice date cannot be null") LocalDate createdAt,
        @NotNull(message = "Seller cannot be null") CompanyRequest seller,
        @NotNull(message = "Buyer cannot be null") CompanyRequest buyer,
        @NotNull(message = "Order cannot be null") Order order
) {}
