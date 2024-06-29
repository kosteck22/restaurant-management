package org.example.warehouse.stock.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.valueobject.InvoiceId;
import org.example.warehouse.stock.service.domain.dto.service.add.AddProductsFromInvoiceCommand;
import org.example.warehouse.stock.service.domain.dto.service.add.AddProductsFromInvoiceResponse;
import org.example.warehouse.stock.service.domain.entity.Invoice;
import org.example.warehouse.stock.service.domain.entity.Product;
import org.example.warehouse.stock.service.domain.entity.Stock;
import org.example.warehouse.stock.service.domain.exception.StockDomainException;
import org.example.warehouse.stock.service.domain.mapper.StockDataMapper;
import org.example.warehouse.stock.service.domain.ports.output.repository.InvoiceRepository;
import org.example.warehouse.stock.service.domain.ports.output.repository.ProductRepository;
import org.example.warehouse.stock.service.domain.ports.output.repository.StockRepository;
import org.example.warehouse.stock.service.domain.valueobject.StockStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class AddProductsFromInvoiceCommandHandler {
    private final ProductRepository productRepository;
    private final InvoiceRepository invoiceRepository;
    private final StockRepository stockRepository;
    private final StockDomainService stockDomainService;
    private final StockDataMapper stockDataMapper;

    public AddProductsFromInvoiceCommandHandler(ProductRepository productRepository,
                                                InvoiceRepository invoiceRepository,
                                                StockRepository stockRepository,
                                                StockDomainService stockDomainService,
                                                StockDataMapper stockDataMapper) {
        this.productRepository = productRepository;
        this.invoiceRepository = invoiceRepository;
        this.stockRepository = stockRepository;
        this.stockDomainService = stockDomainService;
        this.stockDataMapper = stockDataMapper;
    }

    @Transactional
    public AddProductsFromInvoiceResponse addProductsFromInvoice(AddProductsFromInvoiceCommand addProductsFromInvoiceCommand) {
        Stock stock = checkStock();
        Invoice invoice = checkInvoice(addProductsFromInvoiceCommand);
        List<Product> products = productRepository.findAll();
        stockDomainService.addStock(stock, invoice, products);
        persistDbObjects(products, stock);
        log.info("Products added successfully to stock for invoice id: {}", invoice.getId().getValue());
        return stockDataMapper.stockToAddProductsFromInvoiceResponse(stock, "Products added successfully to stock for invoice id: %s".formatted(invoice.getId().getValue()));
    }

    private void persistDbObjects(List<Product> products, Stock stock) {
        stockRepository.save(stock);
        productRepository.saveAll(products);
    }

    private Stock checkStock() {
        List<Stock> stocks = stockRepository.findByStatus(StockStatus.ACTIVE);
        if (stocks.isEmpty()) {
            log.warn("There is no active stock right now!");
            throw new StockDomainException("There is no active stock right now! You must initialize a new one.");
        }
        return stocks.get(0);
    }

    private Invoice checkInvoice(AddProductsFromInvoiceCommand addProductsFromInvoiceCommand) {
        UUID invoiceId = addProductsFromInvoiceCommand.invoiceId();
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(new InvoiceId(invoiceId));
        if (optionalInvoice.isEmpty()) {
            log.warn("Could not find invoice with invoice id: {}", invoiceId);
            throw new StockDomainException("Could not find invoice with invoice id: %s".formatted(invoiceId));
        }
        return optionalInvoice.get();

    }
}
