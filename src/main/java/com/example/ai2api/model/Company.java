package com.example.ai2api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
public class Company {

    public Company(String name) {
        this.name = name;
        this.employees = new ArrayList<>();
        this.positions = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "companies")
    private List<Employee> employees;

    @OneToMany(mappedBy = "companies")
    private List<Position> positions;

}
