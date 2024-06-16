package org.example.invoice.service.dataaccess.invoice.adapter;

import org.example.invoice.service.dataaccess.invoice.mapper.InvoiceDataAccessMapper;
import org.example.dataaccess.invoice.repository.InvoiceJpaRepository;
import org.example.invoice.service.domain.entity.Invoice;
import org.example.invoice.service.domain.ports.output.repository.InvoiceRepository;
import org.springframework.stereotype.Component;

@Component
public class InvoiceRepositoryImpl implements InvoiceRepository {
    private final InvoiceJpaRepository invoiceJpaRepository;

    private final InvoiceDataAccessMapper invoiceDataAccessMapper;

    public InvoiceRepositoryImpl(InvoiceJpaRepository invoiceJpaRepository,
                                 InvoiceDataAccessMapper invoiceDataAccessMapper) {
        this.invoiceJpaRepository = invoiceJpaRepository;
        this.invoiceDataAccessMapper = invoiceDataAccessMapper;
    }

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceDataAccessMapper.invoiceEntityToInvoice(invoiceJpaRepository.
                save(invoiceDataAccessMapper.invoiceToInvoiceEntity(invoice)));
    }
}
