package com.example.ai2api.service;

import com.example.ai2api.model.Company;
import com.example.ai2api.model.Employee;
import com.example.ai2api.model.Position;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    List<Employee> getCompanyEmployees(Long companyId);

    Employee getEmployeeById(Long id);

    Employee createEmployee(String name, String surname, Long pesel, double salary, Company company);

    Employee addEmployeeToCompany(Employee employee, Company companies, List<Position> positions);

    void removeEmployeeFromCompany(Employee employee, Company company);

    void deleteEmployee(Long employeeId);

    Employee updateEmployee(Long id, String name, String surname, double salary);

    Employee setEmployeePositions(Employee employee, Company company, List<Position> positions);

    List<Employee> getAllEmployees();
    boolean hasEmployeePosition(Employee employee, Position position);
}
