package org.example.company.service.dataaccess.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.example.company.service.domain.dto.CompanyRequest;
import org.example.company.service.domain.entity.Company;
import org.example.company.service.domain.exception.CompanyNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class CompanyExternalProviderAleo {
    private final CompanyAleApiClient companyAleApiClient;

    public CompanyExternalProviderAleo(CompanyAleApiClient companyAleApiClient) {
        this.companyAleApiClient = companyAleApiClient;
    }

    public Optional<CompanyRequest> findCompanyByTaxNumber(String taxNumber) {
        log.info("Searching for company with tax number: {}, Aleo Provider", taxNumber);
        String html = companyAleApiClient.getSite(taxNumber);
        if (html == null) {
            log.warn("No html found for tax number: {}, Aleo Provider", taxNumber);
            return Optional.empty();
        }

        try {
            JsonObject contentObject = extractCompanyDataFromHtml(taxNumber, html);
            if (contentObject == null) {
                log.warn("No data found in aleo html for tax number: {}", taxNumber);
                return Optional.empty();
            }

            return Optional.of(createCompanyRequestFromJsonObject(contentObject));
        } catch (Exception ex) {
            log.warn("Company not found for tax number: {}", taxNumber);
            return Optional.empty();
        }
    }

    private CompanyRequest createCompanyRequestFromJsonObject(JsonObject contentObject) {
        JsonObject identification = contentObject.getAsJsonObject("identification");
        JsonObject address = contentObject.getAsJsonObject("address");
        JsonElement regon = identification.get("regon");

        return CompanyRequest.builder()
                .name(contentObject.get("name").getAsString())
                .taxNumber(identification.get("nip").getAsString())
                .regon(regon == null ? null : identification.get("regon").getAsString())
                .city(address.get("city").getAsString())
                .street1(address.get("address").getAsString())
                .postalCode(address.get("zipCode").getAsString()).build();
    }

    private JsonObject extractCompanyDataFromHtml(String taxNumber, String html) {
        Document doc = Jsoup.parse(html);
        Element scriptElement = doc.selectFirst("script[type=application/json]");
        if (scriptElement == null) {
            log.warn("Could not extract java script file form aleo html for tax number: {}", taxNumber);
            throw new CompanyNotFoundException("Company not found for tax number: %s".formatted(taxNumber));
        }

        String jsonData = scriptElement.data().replace("&q;", "\"");
        JsonElement jsonElement = JsonParser.parseString(jsonData);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonObject companyCatalog = jsonObject.getAsJsonObject("company-catalog-search-result-%s-1".formatted(taxNumber));
        if (!companyCatalog.getAsJsonArray("content").isJsonArray()) {
            log.warn("Company not found for tax number: {}", taxNumber);
            throw new CompanyNotFoundException("Company not found for tax number: %s".formatted(taxNumber));
        }
        return companyCatalog.getAsJsonArray("content").get(0).getAsJsonObject();
    }
}
