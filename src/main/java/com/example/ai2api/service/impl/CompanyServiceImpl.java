package com.example.ai2api.service.impl;

import com.example.ai2api.exception.ResourceNotFoundException;
import com.example.ai2api.model.Company;
import com.example.ai2api.repository.CompanyRepository;
import com.example.ai2api.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
    }

    @Override
    public List<Company> getCompaniesById(Set<Long> ids) {
        return companyRepository.findAllById(ids);
    }

    @Override
    public Company createCompany(String name) {
        Company company = new Company(name);
        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(Long id) {
        Company company = getCompanyById(id);
        company.getEmployees().forEach(employee -> employee.getCompanies().remove(company));
        company.getPositions().forEach(position -> position.setCompanies(null));
        companyRepository.delete(company);
    }
}
