package com.example.ai2api.service;

import com.example.ai2api.model.Company;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface CompanyService {
    List<Company> getCompanies();

    Company getCompanyById(Long id);

    List<Company> getCompaniesById(Set<Long> ids);

    Company createCompany(String name);

    void deleteCompany(Long id);

    Company updateCompany(Long companyId, String name);
}
