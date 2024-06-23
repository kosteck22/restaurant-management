package org.example.company.service.domain;

import org.example.company.service.domain.entity.Company;

public interface CompanyDomainService {
    void validateAndInitializeCompany(Company company);
}
