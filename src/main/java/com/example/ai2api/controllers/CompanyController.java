package com.example.ai2api.controllers;

import com.example.ai2api.model.Company;
import com.example.ai2api.dto.CreateCompany;
import com.example.ai2api.dto.UpdateCompany;
import com.example.ai2api.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
@CrossOrigin
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<Company>> getCompanies() {
        List<Company> companies = companyService.getCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long companyId) {
        Company company = companyService.getCompanyById(companyId);
        return ResponseEntity.ok(company);
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody CreateCompany body) {
        Company createdCompany = companyService.createCompany(body.getName());
        return ResponseEntity.ok(createdCompany);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long companyId) {
        companyService.deleteCompany(companyId);
        return ResponseEntity.ok().build();
    }

    /**
     * It updates the company name.
     *
     * @param companyId The id of the company to update.
     * @param body The request body.
     * @return A ResponseEntity object is being returned.
     */
    @PutMapping("/update/{companyId}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long companyId,
                                                 @RequestBody UpdateCompany body) {
        Company company =companyService.updateCompany(companyId, body.getName());
        return ResponseEntity.ok(company);
    }
}
