package com.bogdanbuga.unittestingdemo.repository;

import com.bogdanbuga.unittestingdemo.model.Department;
import com.bogdanbuga.unittestingdemo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUsername(String username);

    List<Employee> findByDepartment(Department department);

    void deleteAllByDepartment(Department department);
}
