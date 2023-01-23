package com.bogdanbuga.unittestingdemo.model;

import com.bogdanbuga.unittestingdemo.model.enums.LanguageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    private LanguageType language;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();

    public Department id(Long id) {
        this.id = id;
        return this;
    }

    public Department name(String name) {
        this.name = name;
        return this;
    }

    public Department language(LanguageType language) {
        this.language = language;
        return this;
    }
}
