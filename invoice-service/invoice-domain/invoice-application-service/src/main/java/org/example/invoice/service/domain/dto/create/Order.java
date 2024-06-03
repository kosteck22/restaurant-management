package org.example.invoice.service.domain.dto.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record Order(
        @NotNull List<OrderItem> items
) {}
