package org.example.invoice.service.domain.ports.output.service;

import org.example.invoice.service.domain.entity.Invoice;

public interface InvoiceDataExtractor {
    Invoice extractInvoiceData(byte[] img);
}
