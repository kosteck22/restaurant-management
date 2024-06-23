package org.example.company.service.application.rest;

import lombok.extern.slf4j.Slf4j;
import org.example.company.service.domain.dto.CompanyResponse;
import org.example.company.service.domain.ports.input.service.CompanyApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/companies", produces = "application/vnd.api.v1+json")
public class CompanyController {
    private final CompanyApplicationService companyApplicationService;

    public CompanyController(CompanyApplicationService companyApplicationService) {
        this.companyApplicationService = companyApplicationService;
    }

    @GetMapping("tax-number/{tax-number}")
    public ResponseEntity<CompanyResponse> getCompanyByNip(@PathVariable("tax-number") String taxNumber) {
        log.info("Getting company by tax number: {}", taxNumber);
        CompanyResponse companyResponse = companyApplicationService.getCompanyByTaxNumber(taxNumber);

        return ResponseEntity.ok(companyResponse);
    }
}
