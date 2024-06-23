package org.example.invoice.service.domain.extractor.amazon;


import lombok.extern.slf4j.Slf4j;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.Quantity;
import org.example.domain.valueobject.UnitOfMeasure;
import org.example.domain.valueobject.VatRate;
import org.example.invoice.service.domain.entity.Invoice;
import org.example.invoice.service.domain.entity.Order;
import org.example.invoice.service.domain.entity.OrderItem;
import org.example.invoice.service.domain.entity.Product;
import org.example.invoice.service.domain.exception.InvoiceDomainException;
import org.example.invoice.service.domain.extractor.KuchnieSwiataInvoiceExtractor;
import org.example.invoice.service.domain.valueobject.Company;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Component
public class KuchnieSwiataInvoiceExtractorImpl implements KuchnieSwiataInvoiceExtractor {
    private final TextractService textractService;

    private static class InvoiceInfo {
        static final int INVOICE_NUMBER_INDEX = 1;
    }

    private static class OrderInfo {
        static final int ORDER_DETAIL_BLOCK_OFFSET = 10;
        static final int ORDER_TOTAL_NET_PRICE_OFFSET = 1;
        static final int ORDER_TOTAL_VAT_OFFSET = 2;
        static final int ORDER_TOTAL_GROSS_PRICE_OFFSET = 3;
    }

    private static class OrderDetailInfo {
        static final int NAME_OFFSET = 1;
        static final int QUANTITY_OFFSET = 3;
        static final int UNIT_OF_MEASURE_OFFSET = 4;
        static final int NET_PRICE_OFFSET = 5;
        static final int NET_PRICE_TOTAL_OFFSET = 6;
        static final int VAT_OFFSET = 7;
        static final int VAT_TOTAL_OFFSET = 8;
        static final int GROSS_PRICE_TOTAL_OFFSET = 9;
    }

    private static class BuyerInfo {
        static final String COMPANY_NAME = "Zielona Baza Joanna Michno";
        static final String COMPANY_NIP = "5170201668";
        static final String COMPANY_REGON = "";
        static final String COMPANY_STREET = "ul. Kurpiowska 3/37";
        static final String COMPANY_CITY = "Rzeszow";
        static final String COMPANY_POSTAL_CODE = "35-620";
    }

    private static class SellerInfo {
        static final String COMPANY_NAME = "KUCHNIE ŚWIATA SPÓŁKA AKCYJNA";
        static final String COMPANY_NIP = "1180039859";
        static final String COMPANY_REGON = "";
        static final String COMPANY_STREET = "ul. SŁODOWIEC 10/10";
        static final String COMPANY_CITY = "Warszawa";
        static final String COMPANY_POSTAL_CODE = "01-708";
    }

    public KuchnieSwiataInvoiceExtractorImpl(TextractService textractService) {
        this.textractService = textractService;
    }

    @Override
    public Invoice extractInvoiceData(byte[] file) {
        List<String> pagesText = textractService.analyzeImg(file);
        return Invoice.builder()
                .invoiceNumber(extractInvoiceNumber(pagesText))
                .createdAt(extractDate(pagesText))
                .buyer(extractBuyer())
                .seller(extractSeller())
                .order(extractOrder(pagesText))
                .build();

    }

    private static String extractInvoiceNumber(List<String> pagesText) {
        String s = pagesText.get(InvoiceInfo.INVOICE_NUMBER_INDEX);
        return s.split(" ")[1];
    }

    private LocalDate extractDate(List<String> pagesText) {
        return pagesText.stream()
                .filter(text -> (text != null && text.contains("Data wystawienia")))
                .findFirst()
                .map(t -> {
                    String date = t.split(" ")[2];
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    return LocalDate.parse(date, formatter);
                })
                .orElse(null);
    }

    private Company extractSeller() {
        return Company.builder()
                .name(SellerInfo.COMPANY_NAME)
                .taxNumber(SellerInfo.COMPANY_NIP)
                .regon(SellerInfo.COMPANY_REGON)
                .street1(SellerInfo.COMPANY_STREET)
                .city(SellerInfo.COMPANY_CITY)
                .postalCode(SellerInfo.COMPANY_POSTAL_CODE)
                .build();
    }

    private Company extractBuyer() {
        return Company.builder()
                .name(BuyerInfo.COMPANY_NAME)
                .taxNumber(BuyerInfo.COMPANY_NIP)
                .regon(BuyerInfo.COMPANY_REGON)
                .street1(BuyerInfo.COMPANY_STREET)
                .city(BuyerInfo.COMPANY_CITY)
                .postalCode(BuyerInfo.COMPANY_POSTAL_CODE)
                .build();
    }

    private Order extractOrder(List<String> pagesText) {
        List<OrderItem> items = new ArrayList<>();
        int currentIndex = findOrderStartIndex(pagesText);
        if (currentIndex == -1) {
            log.error("Could not find Order table in given img");
            throw new InvoiceDomainException("Could not find Order table in given img");
        }

        while (isOrderDetail(pagesText, currentIndex)) {
            Integer positionIndex = parseIntFromText(pagesText.get(currentIndex));
            if (positionIndex == null) {
                currentIndex += 1;
            }

            OrderItem orderItem = buildOrderDetail(pagesText, currentIndex);
            items.add(orderItem);
            currentIndex += OrderInfo.ORDER_DETAIL_BLOCK_OFFSET;
        }

        return buildOrder(pagesText, items, currentIndex);
    }

    private Order buildOrder(List<String> pagesText, List<OrderItem> orderItems, int currentIndex) {
        return Order.builder()
                .items(orderItems)
                .netPrice(new Money(parsePriceUnitFromText(pagesText.get(currentIndex + OrderInfo.ORDER_TOTAL_NET_PRICE_OFFSET))))
                .vat(new Money(parsePriceUnitFromText(pagesText.get(currentIndex + OrderInfo.ORDER_TOTAL_VAT_OFFSET))))
                .grossPrice(new Money(parsePriceUnitFromText(pagesText.get(currentIndex + OrderInfo.ORDER_TOTAL_GROSS_PRICE_OFFSET))))
                .build();
    }

    private OrderItem buildOrderDetail(List<String> pagesText, int index) {
        return OrderItem.builder()
                .product(buildProduct(pagesText, index))
                .quantity(new Quantity(parsePriceUnitFromText(pagesText.get(index + OrderDetailInfo.QUANTITY_OFFSET))))
                .discount(0)
                .netTotal(new Money(parsePriceUnitFromText(pagesText.get(index + OrderDetailInfo.NET_PRICE_TOTAL_OFFSET))))
                .vat(new Money(parsePriceUnitFromText(pagesText.get(index + OrderDetailInfo.VAT_TOTAL_OFFSET))))
                .grossTotal(new Money(parsePriceUnitFromText(pagesText.get(index + OrderDetailInfo.GROSS_PRICE_TOTAL_OFFSET))))
                .build();
    }

    private Product buildProduct(List<String> pagesText, int index) {
        return Product.builder()
                .name(pagesText.get(index + OrderDetailInfo.NAME_OFFSET))
                .unitOfMeasure(new UnitOfMeasure(pagesText.get(index + OrderDetailInfo.UNIT_OF_MEASURE_OFFSET)))
                .netPrice(new Money(parsePriceUnitFromText(pagesText.get(index + OrderDetailInfo.NET_PRICE_OFFSET))))
                .vatRate(VatRate.valueOf(getVatRate(pagesText.get(index + OrderDetailInfo.VAT_OFFSET))))
                .build();
    }

    private static boolean isOrderDetail(List<String> pagesText, int index) {
        return !"RAZEM".equals(pagesText.get(index));
    }

    private int findOrderStartIndex(List<String> pagesText) {
        boolean beginOfTable = false;
        for (int i = 0; i < pagesText.size(); i++) {
            if (pagesText.get(i) != null && "Poz".equals(pagesText.get(i))) {
                beginOfTable = true;
            }
            if (beginOfTable && pagesText.get(i) != null && "1".equals(pagesText.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private Integer parseIntFromText(String text) {
        try {
            return Integer.parseInt(text);
            //return Integer.valueOf(text.replaceAll("\\D+", ""));
        } catch (NumberFormatException e) {
            log.error("Error parsing integer from: {}", text, e);
            return null;
        }
    }

    private BigDecimal parsePriceUnitFromText(String text) {
        try {
            Number number = NumberFormat.getNumberInstance(Locale.GERMANY).parse(text);
            return new BigDecimal(number.toString());
        } catch (ParseException e) {
            log.error("Error parsing price unit from: {}", text, e);
            return BigDecimal.ZERO;
        }
    }

    private Integer getVatRate(String text) {
        return parseIntFromText(text.replaceAll("[^\\d]", ""));
    }
}
