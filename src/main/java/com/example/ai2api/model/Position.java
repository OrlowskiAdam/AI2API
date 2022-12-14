package com.example.ai2api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Position {

    public Position(String positionName, Company company) {
        this.positionName = positionName;
        this.companies = company;
    }

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String positionName;

    @ManyToMany
    @JsonIgnore
    private List<Employee> employees;

    @ManyToOne
    @JsonIgnore
    private Company companies;
}
