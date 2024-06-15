package org.example.warehouse.stock.service.domain.ports.output.repository;

import org.example.domain.valueobject.InvoiceId;
import org.example.warehouse.stock.service.domain.entity.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository {
    Optional<Invoice> findById(InvoiceId invoiceId);
}
