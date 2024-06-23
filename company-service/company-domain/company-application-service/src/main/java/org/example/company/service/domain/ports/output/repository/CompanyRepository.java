package org.example.company.service.domain.ports.output.repository;

import org.example.company.service.domain.entity.Company;

import java.util.Optional;

public interface CompanyRepository {
    Optional<Company> getCompanyByTaxNumber(String taxNumber);
    Company save(Company company);
}
