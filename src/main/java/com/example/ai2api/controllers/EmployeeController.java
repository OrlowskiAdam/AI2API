package com.example.ai2api.controllers;

import com.example.ai2api.model.Company;
import com.example.ai2api.model.Employee;
import com.example.ai2api.model.Position;
import com.example.ai2api.payload.CreateEmployee;
import com.example.ai2api.payload.RemoveEmployeeCompany;
import com.example.ai2api.payload.SetEmployeeCompany;
import com.example.ai2api.payload.SetEmployeePositionsInCompany;
import com.example.ai2api.service.CompanyService;
import com.example.ai2api.service.EmployeeService;
import com.example.ai2api.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final PositionService positionService;

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Employee>> getCompanyEmployees(@PathVariable Long companyId) {
        List<Employee> employees = employeeService.getCompanyEmployees(companyId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody CreateEmployee createEmployee) {
        Company company = companyService.getCompanyById(createEmployee.getCompanyId());
        List<Position> positions = positionService.getCompanyPositionsById(company, createEmployee.getPositionIds());
        Employee employee = employeeService.createEmployee(
                createEmployee.getName(),
                createEmployee.getSurname(),
                createEmployee.getPesel(),
                company,
                positions
        );
        return ResponseEntity.ok(employee);
    }

    @PatchMapping("/company/add")
    public ResponseEntity<Employee> addEmployeeToCompany(@RequestBody SetEmployeeCompany body) {
        Company company = companyService.getCompanyById(body.getCompanyId());
        Employee employee = employeeService.getEmployeeById(body.getEmployeeId());
        List<Position> positions = positionService.getCompanyPositionsById(company, body.getPositionIds());
        employee = employeeService.addEmployeeToCompany(employee, company, positions);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/company/remove")
    public ResponseEntity<?> removeEmployeeFromCompany(@RequestBody RemoveEmployeeCompany body) {
        Company company = companyService.getCompanyById(body.getCompanyId());
        Employee employee = employeeService.getEmployeeById(body.getEmployeeId());
        employeeService.removeEmployeeFromCompany(employee, company);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/positions")
    public ResponseEntity<Employee> setEmployeePositions(@RequestBody SetEmployeePositionsInCompany body) {
        Company company = companyService.getCompanyById(body.getCompanyId());
        Employee employee = employeeService.getEmployeeById(body.getEmployeeId());
        List<Position> positions = positionService.getCompanyPositionsById(company, body.getPositionIds());
        employee = employeeService.setEmployeePositions(employee, company, positions);
        return ResponseEntity.ok(employee);
    }
}
