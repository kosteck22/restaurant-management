package org.example.company.service.dataaccess.adapter;

import jakarta.validation.Valid;
import org.example.company.service.dataaccess.service.CompanyExternalProviderAleo;
import org.example.company.service.domain.dto.CompanyRequest;
import org.example.company.service.domain.ports.output.external.service.CompanyExternalProvider;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CompanyExternalProviderImpl implements CompanyExternalProvider {
    private final CompanyExternalProviderAleo companyExternalProviderAleo;

    public CompanyExternalProviderImpl(CompanyExternalProviderAleo companyExternalProviderAleo) {
        this.companyExternalProviderAleo = companyExternalProviderAleo;
    }

    @Override
    public Optional<CompanyRequest> getCompanyRequestByTaxNumber(String taxNumber) {
        return companyExternalProviderAleo.findCompanyByTaxNumber(taxNumber);
    }
}
