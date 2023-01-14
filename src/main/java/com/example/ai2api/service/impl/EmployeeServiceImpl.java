package com.example.ai2api.service.impl;

import com.example.ai2api.exception.BadRequestException;
import com.example.ai2api.exception.ResourceNotFoundException;
import com.example.ai2api.model.Company;
import com.example.ai2api.model.Employee;
import com.example.ai2api.model.Position;
import com.example.ai2api.repository.EmployeeRepository;
import com.example.ai2api.repository.PositionRepository;
import com.example.ai2api.service.CompanyService;
import com.example.ai2api.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.hv.pl.PESELValidator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final CompanyService companyService;

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;

    @Override
    public List<Employee> getCompanyEmployees(Long companyId) {
        Company company = companyService.getCompanyById(companyId);
        List<Employee> employees = company.getEmployees();
        List<Position> positions = company.getPositions();
        employees = employees.stream()
                .peek(employee -> {
                    List<Position> employeePositionsForCompany = employee.getPositions()
                            .stream()
                            .filter(position -> positions.stream().anyMatch(p -> p.getId().equals(position.getId())))
                            .toList();
                    employee.setPositions(employeePositionsForCompany);
                })
                .toList();
        return employees;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    @Override
    public Employee createEmployee(String name, String surname, Long pesel, double salary, Company company, List<Position> position) {
        if (salary < 0) {
            throw new BadRequestException("Salary cannot be less than 0");
        }
        if (pesel < 0 || pesel.toString().length() != 11) {
            throw new BadRequestException("PESEL is not valid");
        }
        Employee employee = new Employee(name, surname, pesel, salary, company, position);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee addEmployeeToCompany(Employee employee, Company companies, List<Position> positions) {
        boolean isInCompany = employee.getCompanies().stream()
                .anyMatch(company -> company.getId().equals(companies.getId()));
        if (isInCompany) {
            throw new BadRequestException("Employee is already in company");
        }
        positions.forEach(position -> position.getEmployees().add(employee));
        employee.getCompanies().add(companies);
        employee.getPositions().addAll(positions);
        return employeeRepository.save(employee);
    }

    @Override
    public void removeEmployeeFromCompany(Employee employee, Company company) {
        employee.getCompanies().remove(company);
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public Employee updateEmployee(Long id, String name, String surname, double salary) {
        if (salary < 0) {
            throw new BadRequestException("Salary cannot be less than 0");
        }
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        if (name != null && !name.isEmpty()) {
            employee.setName(name);
        }
        if (surname != null && !surname.isEmpty()) {
            employee.setSurname(surname);
        }
        if (salary > 0) {
            employee.setSalary(salary);
        }
        employeeRepository.save(employee);
        return employee;
    }

    @Override
    public Employee setEmployeePositions(Employee employee, Company company, List<Position> positions) {
        employee.getPositions().forEach(position -> position.getEmployees().clear());
        employee.getPositions().clear();
        for (Position position : positions) {
            if (!company.getPositions().contains(position)) {
                throw new BadRequestException("Position not found in company");
            }
            boolean hasPosition = hasEmployeePosition(employee, position);
            if (hasPosition) continue;
            employee.getPositions().add(position);
            position.getEmployees().add(employee);
        }
        return employeeRepository.save(employee);
    }

    /**
     * > The function returns a list of all employees in the database
     *
     * @return A list of all employees
     */
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Return true if the employee has the position
     *
     * @param employee The employee to check for the position.
     * @param position The position to check for.
     * @return A boolean value.
     */
    @Override
    public boolean hasEmployeePosition(Employee employee, Position position) {
        return employee.getPositions()
                .stream()
                .anyMatch(p -> p.getId().equals(position.getId()));
    }

}
