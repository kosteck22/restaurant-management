package org.example.company.service.domain.mapper;

import org.example.company.service.domain.dto.CompanyRequest;
import org.example.company.service.domain.dto.CompanyResponse;
import org.example.company.service.domain.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyDataMapper {
    public CompanyResponse companyToCompanyResponse(Company company) {
        return CompanyResponse.builder()
                .name(company.getName())
                .street1(company.getStreet1())
                .street2(company.getStreet2())
                .city(company.getCity())
                .postalCode(company.getPostalCode())
                .taxNumber(company.getTaxNumber())
                .regon(company.getRegon())
                .build();
    }

    public Company companyRequestToCompany(CompanyRequest companyRequest) {
        return Company.builder()
                .name(companyRequest.name())
                .street1(companyRequest.street1())
                .street2(companyRequest.street2())
                .postalCode(companyRequest.postalCode())
                .city(companyRequest.city())
                .taxNumber(companyRequest.taxNumber())
                .regon(companyRequest.regon())
                .build();
    }
}
