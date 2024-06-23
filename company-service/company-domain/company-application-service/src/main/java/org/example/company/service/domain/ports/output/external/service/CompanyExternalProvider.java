package org.example.company.service.domain.ports.output.external.service;

import org.example.company.service.domain.dto.CompanyRequest;
import org.example.company.service.domain.entity.Company;

import java.util.Optional;

public interface CompanyExternalProvider {
    Optional<CompanyRequest> getCompanyRequestByTaxNumber(String taxNumber);
}
