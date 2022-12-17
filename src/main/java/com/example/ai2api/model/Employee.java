package com.example.ai2api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Employee {

    public Employee(String name, String surname, Long pesel, double salary, Company company, List<Position> positions) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.salary = salary;
        this.companies = new ArrayList<>(Collections.singletonList(company));
        this.positions = new ArrayList<>(positions);
    }

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Surname cannot be null")
    private String surname;

    private Long pesel;
    private double salary;

    @ManyToMany
    @JsonIgnore
    private List<Company> companies;

    @ManyToMany(mappedBy = "employees")
    private List<Position> positions;
}
