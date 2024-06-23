package org.example.company.service.dataaccess.adapter;

import org.example.company.service.dataaccess.mapper.CompanyDataAccessMapper;
import org.example.company.service.dataaccess.repository.CompanyJpaRepository;
import org.example.company.service.domain.entity.Company;
import org.example.company.service.domain.ports.output.repository.CompanyRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CompanyRepositoryImpl implements CompanyRepository {
    private final CompanyJpaRepository companyJpaRepository;
    private final CompanyDataAccessMapper companyDataAccessMapper;

    public CompanyRepositoryImpl(CompanyJpaRepository companyJpaRepository, CompanyDataAccessMapper companyDataAccessMapper) {
        this.companyJpaRepository = companyJpaRepository;
        this.companyDataAccessMapper = companyDataAccessMapper;
    }

    @Override
    public Optional<Company> getCompanyByTaxNumber(String taxNumber) {
        return companyJpaRepository.findByTaxNumber(taxNumber)
                .map(companyDataAccessMapper::companyEntityToCompany);
    }

    @Override
    public Company save(Company company) {
        return companyDataAccessMapper
                .companyEntityToCompany(companyJpaRepository
                        .save(companyDataAccessMapper.companyToCompanyEntity(company)));
    }
}
