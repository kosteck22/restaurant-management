package org.example.invoice.service.domain.extractor.amazon;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.Quantity;
import org.example.domain.valueobject.UnitOfMeasure;
import org.example.domain.valueobject.VatRate;
import org.example.invoice.service.domain.entity.Order;
import org.example.invoice.service.domain.entity.OrderItem;
import org.example.invoice.service.domain.entity.Product;
import org.example.invoice.service.domain.extractor.AgroHurtInvoiceExtractor;
import org.example.invoice.service.domain.valueobject.Company;
import org.example.invoice.service.domain.entity.Invoice;
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
public class AgroHurtInvoiceExtractorImpl implements AgroHurtInvoiceExtractor {
    private final TextractService textractService;

    private static final String FOLDER_NAME = "agro-hurt";

    private static class OrderInfo {
        static final int ORDER_DETAIL_BLOCK_OFFSET = 10;
        static final int ORDER_TOTAL_NET_PRICE_OFFSET = 1;
        static final int ORDER_TOTAL_VAT_OFFSET = 2;
        static final int ORDER_TOTAL_GROSS_PRICE_OFFSET = 3;
    }

    private static class OrderDetailInfo {
        static final int NAME_OFFSET = 1;
        static final int QUANTITY_OFFSET = 2;
        static final int UNIT_OF_MEASURE_OFFSET = 3;
        static final int NET_PRICE_OFFSET = 4;
        static final int NET_PRICE_TOTAL_OFFSET = 7;
        static final int VAT_OFFSET = 6;
        static final int VAT_TOTAL_OFFSET = 8;
        static final int GROSS_PRICE_TOTAL_OFFSET = 9;
    }

    private static class BuyerInfo {
        static final String COMPANY_NAME = "Zielona Baza Joanna Michno";
        static final String COMPANY_TAX_NUMBER = "5170201668";
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

    public AgroHurtInvoiceExtractorImpl(TextractService textractService) {
        this.textractService = textractService;
    }

    @Override
    public Invoice extractInvoiceData(byte[] file) {
        List<String> pagesText = textractService.extractTextFromDocument(file, FOLDER_NAME);
        return Invoice.builder()
                .invoiceNumber(findInvoiceNumber(pagesText))
                .createdAt(findDate(pagesText))
                .buyer(extractBuyer())
                .seller(extractSeller())
                .order(extractOrder(pagesText))
                .build();
    }

    private String findInvoiceNumber(List<String> pagesText) {
        return pagesText.stream()
                .filter(s -> s.contains("Faktura VAT"))
                .findFirst()
                .map(s -> s.split(" ")[2])
                .orElse(null);
    }

    private LocalDate findDate(List<String> pagesText) {
        return pagesText.stream()
                .filter(s -> s.matches("\\d{4}-\\d{2}-\\d{2}"))
                .findFirst()
                .map(d -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    return LocalDate.parse(d, formatter);
                })
                .orElse(null);

    }

    private Company extractBuyer() {
        return Company.builder()
                .name(BuyerInfo.COMPANY_NAME)
                .taxNumber(BuyerInfo.COMPANY_TAX_NUMBER)
                .regon(BuyerInfo.COMPANY_REGON)
                .street1(BuyerInfo.COMPANY_STREET)
                .city(BuyerInfo.COMPANY_CITY)
                .postalCode(BuyerInfo.COMPANY_POSTAL_CODE)
                .build();
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

    private Order extractOrder(List<String> pagesText) {
        List<OrderItem> items = new ArrayList<>();
        int currentIndex = 0;


        while ((currentIndex = findOrderStartIndex(pagesText, currentIndex)) != -1) //Loop until last order page
        {
            while (currentIndex < pagesText.size() && isOrderDetail(pagesText.get(currentIndex))) {
                OrderItem orderItem = buildOrderItem(pagesText, currentIndex);
                if (orderItem != null) {
                    items.add(orderItem);
                    currentIndex += OrderInfo.ORDER_DETAIL_BLOCK_OFFSET;
                } else {
                    currentIndex++; //Increment index if current line isn't valid
                }
            }
        }

        return buildOrder(pagesText, items);
    }

    private Order buildOrder(List<String> pagesText, List<OrderItem> orderItems) {
        return Order.builder()
                .items(orderItems)
                .netPrice(new Money(parsePriceUnitFromText(getTotal(pagesText, OrderInfo.ORDER_TOTAL_NET_PRICE_OFFSET))))
                .vat(new Money(parsePriceUnitFromText(getTotal(pagesText, OrderInfo.ORDER_TOTAL_VAT_OFFSET))))
                .grossPrice(new Money(parsePriceUnitFromText(getTotal(pagesText, OrderInfo.ORDER_TOTAL_GROSS_PRICE_OFFSET))))
                .build();
    }

    private String getTotal(List<String> pagesText, int offset) {
        int summaryIndex = getSummaryIndex(pagesText);
        if (summaryIndex > -1) {
            return pagesText.get(summaryIndex + offset);
        }

        return null;
    }

    private int getSummaryIndex(List<String> pagesText) {
        for (int i = 0; i < pagesText.size(); i++) {
            if ("Razem:".equals(pagesText.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private boolean isOrderDetail(String text) {
        return !"Ciag dalszy na nastepnej stronie".equals(text) && !"Razem:".equals(text);
    }

    private OrderItem buildOrderItem(List<String> pagesText, int index) {
        //invalid index position
        Integer positionNumber = parseIntFromText(pagesText.get(index));
        if (positionNumber == null) {
            return null;
        }

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
                .netPrice(new Money(parsePriceUnitFromText(pagesText.get(index + OrderDetailInfo.NET_PRICE_OFFSET))))
                .unitOfMeasure(new UnitOfMeasure(pagesText.get(index + OrderDetailInfo.UNIT_OF_MEASURE_OFFSET)))
                .vatRate(VatRate.valueOf(getVatRate(pagesText.get(index + OrderDetailInfo.VAT_OFFSET))))
                .build();
    }

    private int findOrderStartIndex(List<String> pagesText, int startIndex) {
        for (int i = startIndex; i < pagesText.size(); i++) {
            if ("Lp".equals(pagesText.get(i))) {
                int orderDetailPosition = i + OrderInfo.ORDER_DETAIL_BLOCK_OFFSET;
                return parseIntFromText(pagesText.get(orderDetailPosition)) != null ? orderDetailPosition : orderDetailPosition + 1;
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
