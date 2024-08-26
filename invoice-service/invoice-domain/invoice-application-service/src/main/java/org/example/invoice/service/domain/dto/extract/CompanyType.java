package org.example.invoice.service.domain.dto.extract;


import org.example.invoice.service.domain.ApplicationContextProvider;
import org.example.invoice.service.domain.ports.output.service.InvoiceDataExtractor;

public enum CompanyType {
    KUCHNIE_SWIATA("kuchnieSwiataInvoiceExtractorImpl"),
    AGROHURT("agroHurtInvoiceExtractor");

    private final String extractorName;

    CompanyType(String extractorName) {
        this.extractorName = extractorName;
    }

    public InvoiceDataExtractor getExtractor() {
        return (InvoiceDataExtractor) ApplicationContextProvider.getApplicationContext().getBean(extractorName);
    }
}
