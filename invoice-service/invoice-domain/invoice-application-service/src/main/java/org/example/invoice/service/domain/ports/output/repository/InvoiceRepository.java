package org.example.invoice.service.domain.ports.output.repository;

import org.example.invoice.service.domain.entity.Invoice;

public interface InvoiceRepository {
    Invoice save(Invoice invoice);
}
