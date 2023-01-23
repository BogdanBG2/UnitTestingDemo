package com.bogdanbuga.unittestingdemo.mapper;

import com.bogdanbuga.unittestingdemo.dto.DepartmentDTO;
import com.bogdanbuga.unittestingdemo.model.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentDTO departmentDTO) {
        if (departmentDTO == null)
            return null;

        return new Department()
                .id(departmentDTO.getId())
                .name(departmentDTO.getName())
                .language(departmentDTO.getLanguage());
    }

    public DepartmentDTO toDto(Department department) {
        if (department == null)
            return null;

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(department.getId());
        departmentDTO.setName(department.getName());
        departmentDTO.setLanguage(department.getLanguage());

        return departmentDTO;
    }
}
