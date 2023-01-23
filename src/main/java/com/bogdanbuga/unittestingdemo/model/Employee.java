package com.bogdanbuga.unittestingdemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "joined_at")
    private Date joinedAt = new Date();

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee id(Long id) {
        this.id = id;
        return this;
    }

    public Employee name(String name) {
        this.name = name;
        return this;
    }

    public Employee username(String username) {
        this.username = username;
        return this;
    }
}
