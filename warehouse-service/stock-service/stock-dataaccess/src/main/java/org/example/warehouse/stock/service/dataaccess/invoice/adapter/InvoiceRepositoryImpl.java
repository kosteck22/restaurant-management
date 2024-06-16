package org.example.warehouse.stock.service.dataaccess.invoice.adapter;

import org.example.dataaccess.invoice.entity.InvoiceEntity;
import org.example.dataaccess.invoice.repository.InvoiceJpaRepository;
import org.example.domain.valueobject.InvoiceId;
import org.example.warehouse.stock.service.dataaccess.invoice.mapper.InvoiceDataAccessMapper;
import org.example.warehouse.stock.service.domain.entity.Invoice;
import org.example.warehouse.stock.service.domain.ports.output.repository.InvoiceRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
    public Optional<Invoice> findById(InvoiceId invoiceId) {
        return invoiceJpaRepository.findById(invoiceId.getValue())
                .map(invoiceDataAccessMapper::invoiceEntityToinvoice);
    }
}
