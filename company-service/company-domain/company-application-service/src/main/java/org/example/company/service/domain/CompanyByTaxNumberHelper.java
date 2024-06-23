package org.example.company.service.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.example.company.service.domain.dto.CompanyRequest;
import org.example.company.service.domain.dto.CompanyResponse;
import org.example.company.service.domain.entity.Company;
import org.example.company.service.domain.exception.CompanyDomainException;
import org.example.company.service.domain.exception.CompanyNotFoundException;
import org.example.company.service.domain.mapper.CompanyDataMapper;
import org.example.company.service.domain.ports.output.external.service.CompanyExternalProvider;
import org.example.company.service.domain.ports.output.repository.CompanyRepository;
import org.springframework.stereotype.Component;

import javax.xml.validation.Validator;
import java.util.Set;

@Slf4j
@Component
public class CompanyByTaxNumberHelper {
    private final CompanyRepository companyRepository;
    private final CompanyDataMapper companyDataMapper;
    private final CompanyExternalProvider companyExternalProvider;
    private final CompanyDomainService companyDomainService;

    public CompanyByTaxNumberHelper(CompanyRepository companyRepository, CompanyDataMapper companyDataMapper, CompanyExternalProvider companyProvider, CompanyDomainService companyDomainService) {
        this.companyRepository = companyRepository;
        this.companyDataMapper = companyDataMapper;
        this.companyExternalProvider = companyProvider;
        this.companyDomainService = companyDomainService;
    }

    public CompanyResponse getCompanyByTaxNumber(String taxNumber) {
        return companyRepository.getCompanyByTaxNumber(taxNumber)
                .map(companyDataMapper::companyToCompanyResponse)
                .orElseGet(() -> fetchAndSaveCompanyFromExternalApi(taxNumber));
    }

    private CompanyResponse fetchAndSaveCompanyFromExternalApi(String taxNumber) {
        CompanyRequest companyRequest = companyExternalProvider.getCompanyRequestByTaxNumber(taxNumber.replace("-", ""))
                .orElseThrow(() -> {
                    log.warn("There is no company for tax number: {}", taxNumber);
                    return new CompanyNotFoundException(
                            "There is no company for tax number: %s".formatted(taxNumber));
                });
        Company company = companyDataMapper.companyRequestToCompany(companyRequest);
        companyDomainService.validateAndInitializeCompany(company);
        Company savedCompany = saveCompany(company);
        return companyDataMapper.companyToCompanyResponse(savedCompany);
    }

    private Company saveCompany(Company company) {
        Company companyResult = companyRepository.save(company);
        if(companyResult == null) {
            log.error("Could not save company!");
            throw new CompanyDomainException("Could not save company!");
        }
        log.info("Company is saved with id: {}", companyResult.getId().getValue());
        return companyResult;
    }
}
