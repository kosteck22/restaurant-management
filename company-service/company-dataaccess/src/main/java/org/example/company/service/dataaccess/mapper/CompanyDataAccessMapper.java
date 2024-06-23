package org.example.company.service.dataaccess.mapper;

import org.example.company.service.dataaccess.entity.CompanyEntity;
import org.example.company.service.domain.entity.Company;
import org.example.company.service.domain.valueobject.CompanyId;
import org.springframework.stereotype.Component;

@Component
public class CompanyDataAccessMapper {
    public Company companyEntityToCompany(CompanyEntity companyEntity) {
        return Company.builder()
                .id(new CompanyId(companyEntity.getId()))
                .name(companyEntity.getName())
                .street1(companyEntity.getStreet1())
                .street2(companyEntity.getStreet2())
                .postalCode(companyEntity.getPostalCode())
                .city(companyEntity.getCity())
                .taxNumber(companyEntity.getTaxNumber())
                .regon(companyEntity.getRegon())
                .build();
    }

    public CompanyEntity companyToCompanyEntity(Company company) {
        return CompanyEntity.builder()
                .id(company.getId().getValue())
                .name(company.getName())
                .street1(company.getStreet1())
                .street2(company.getStreet2())
                .postalCode(company.getPostalCode())
                .city(company.getCity())
                .taxNumber(company.getTaxNumber())
                .regon(company.getRegon())
                .build();
    }
}
