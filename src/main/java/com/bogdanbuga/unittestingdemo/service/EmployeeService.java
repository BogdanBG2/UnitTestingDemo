package com.bogdanbuga.unittestingdemo.service;

import com.bogdanbuga.unittestingdemo.dto.EmployeeDTO;
import com.bogdanbuga.unittestingdemo.mapper.EmployeeMapper;
import com.bogdanbuga.unittestingdemo.model.Department;
import com.bogdanbuga.unittestingdemo.model.Employee;
import com.bogdanbuga.unittestingdemo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<EmployeeDTO> findOne(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDto);
    }

    public Optional<Employee> findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    public List<Employee> saveAll(List<Employee> employees) {
        return employeeRepository.saveAll(employees);
    }

    public List<EmployeeDTO> findAllByDepartment(Department department) {
        return employeeRepository.findByDepartment(department)
                .stream().map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    public void deleteAllByDepartment(Department department) {
        employeeRepository.deleteAllByDepartment(department);
    }

}
