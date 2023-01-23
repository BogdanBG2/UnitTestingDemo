package com.bogdanbuga.unittestingdemo.mapper;

import com.bogdanbuga.unittestingdemo.dto.EmployeeDTO;
import com.bogdanbuga.unittestingdemo.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeDTO employeeDTO) {
        if (employeeDTO == null)
            return null;

        return new Employee()
                .id(employeeDTO.getId())
                .name(employeeDTO.getName())
                .username(employeeDTO.getUsername());
    }

    public EmployeeDTO toDto(Employee employee) {
        if (employee == null)
            return null;

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setUsername(employee.getUsername());
        employeeDTO.setDepartmentName(
                employee.getDepartment() != null ?
                        employee.getDepartment().getName() :
                        ""
        );

        return employeeDTO;
    }
}
