package org.example.company.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.company.service.domain.entity.Company;

@Slf4j
public class CompanyDomainServiceImpl implements CompanyDomainService {
    @Override
    public void validateAndInitializeCompany(Company company) {
        company.validateCompany();
        company.initializeCompany();
        log.info("Company with id: {} is initiated", company.getId().getValue());
    }
}
