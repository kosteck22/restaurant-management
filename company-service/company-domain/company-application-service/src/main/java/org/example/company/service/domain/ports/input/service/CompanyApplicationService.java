package org.example.company.service.domain.ports.input.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.example.application.validation.ValidTaxNumber;
import org.example.company.service.domain.dto.CompanyResponse;

public interface CompanyApplicationService {
    CompanyResponse getCompanyByTaxNumber(@Valid @NotBlank @ValidTaxNumber String taxNumber);
}
