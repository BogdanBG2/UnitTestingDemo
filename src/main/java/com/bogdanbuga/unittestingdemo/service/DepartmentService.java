package com.bogdanbuga.unittestingdemo.service;

import com.bogdanbuga.unittestingdemo.dto.DepartmentDTO;
import com.bogdanbuga.unittestingdemo.mapper.DepartmentMapper;
import com.bogdanbuga.unittestingdemo.model.Department;
import com.bogdanbuga.unittestingdemo.model.enums.LanguageType;
import com.bogdanbuga.unittestingdemo.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    public List<DepartmentDTO> findAll() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<DepartmentDTO> findOne(Long id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::toDto);
    }

    public DepartmentDTO save(DepartmentDTO departmentDTO) {
        Department department = departmentMapper.toEntity(departmentDTO);
        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

    public List<Department> saveAll(List<Department> departments) {
        return departmentRepository.saveAll(departments);
    }

    public Optional<Department> findByName(String name) {
        return departmentRepository.findByName(name);
    }

    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    public Map<String, Long> getNumberOfDepartmentsPerLanguage() {
        Map<String, Long> result = new HashMap<>();

        for (LanguageType language : LanguageType.values())
            result.put(language.name(), departmentRepository.countByLanguage(language));

        return result;
    }
}
