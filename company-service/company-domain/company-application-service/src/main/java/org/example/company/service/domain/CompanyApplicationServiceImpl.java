package org.example.company.service.domain;

import org.example.company.service.domain.dto.CompanyResponse;
import org.example.company.service.domain.ports.input.service.CompanyApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class CompanyApplicationServiceImpl implements CompanyApplicationService {
    private final CompanyByTaxNumberHelper companyByTaxNumberHelper;

    public CompanyApplicationServiceImpl(CompanyByTaxNumberHelper companyByTaxNumberHelper) {
        this.companyByTaxNumberHelper = companyByTaxNumberHelper;
    }

    @Override
    public CompanyResponse getCompanyByTaxNumber(String taxNumber) {
        return companyByTaxNumberHelper.getCompanyByTaxNumber(taxNumber);
    }

}
