package com.bogdanbuga.unittestingdemo.service;

import com.bogdanbuga.unittestingdemo.dto.EmployeeDTO;
import com.bogdanbuga.unittestingdemo.mapper.EmployeeMapper;
import com.bogdanbuga.unittestingdemo.model.Department;
import com.bogdanbuga.unittestingdemo.model.Employee;
import com.bogdanbuga.unittestingdemo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    private Employee employee1;

    private Department department1;

    private EmployeeDTO employee1DTO;

    private Employee employee2;

    private Department department2;

    private EmployeeDTO employee2DTO;

    private List<Employee> employees;

    private List<EmployeeDTO> employeeDTOs;

    private static final long DUMMY_ID = 100;

    private static final String DUMMY_NAME = "NAME_";

    private static final String DUMMY_USERNAME = "USERNAME_";

    @BeforeEach
    void setUp() {
        department1 = new Department()
                .id(DUMMY_ID + 1)
                .name(DUMMY_NAME + "1");

        employee1 = new Employee()
                .id(DUMMY_ID + 1)
                .name(DUMMY_NAME + "1")
                .username(DUMMY_USERNAME + "1");
        employee1.setDepartment(department1);

        department2 = new Department()
                .id(DUMMY_ID + 2)
                .name(DUMMY_NAME + "2");

        employee2 = new Employee()
                .id(DUMMY_ID + 2)
                .name(DUMMY_NAME + "2")
                .username(DUMMY_USERNAME + "2");
        employee2.setDepartment(department2);

        employees = List.of(employee1, employee2);

        employee1DTO = new EmployeeDTO();
        employee1DTO.setId(employee1.getId());
        employee1DTO.setName(employee1.getName());
        employee1DTO.setUsername(employee1.getUsername());
        employee1DTO.setDepartmentName(employee1.getDepartment().getName());

        employee2DTO = new EmployeeDTO();
        employee2DTO.setId(employee2.getId());
        employee2DTO.setName(employee2.getName());
        employee2DTO.setUsername(employee2.getUsername());
        employee2DTO.setDepartmentName(employee2.getDepartment().getName());

        employeeDTOs = List.of(employee1DTO, employee2DTO);
    }

    @Test
    void testFindAll() {
        // Given
        given(employeeRepository.findAll())
                .willReturn(employees);

        for (int i = 0; i < employees.size(); ++i)
            given(employeeMapper.toDto(employees.get(i)))
                    .willReturn(employeeDTOs.get(i));

        // When
        List<EmployeeDTO> resultedEmployeeDTOs = employeeService.findAll();

        // Then
        assertEquals(employeeDTOs, resultedEmployeeDTOs);
    }

    @Test
    void testFindOne() {
        // Given
        given(employeeRepository.findById(DUMMY_ID + 1))
                .willReturn(Optional.of(employee1));

        given(employeeMapper.toDto(employee1))
                .willReturn(employee1DTO);

        // When
        Optional<EmployeeDTO> resultedEmployee = employeeService.findOne(DUMMY_ID + 1);

        // Then
        assertEquals(Optional.of(employee1DTO), resultedEmployee);
    }

    @Test
    void testFindByUsername() {
        // Given
        given(employeeRepository.findByUsername(DUMMY_USERNAME + "1"))
                .willReturn(Optional.of(employee1));

        // When
        Optional<Employee> resultedEmployee = employeeService.findByUsername(DUMMY_USERNAME + "1");

        // Then
        assertEquals(Optional.of(employee1), resultedEmployee);
    }

    @Test
    void testSave() {
        // Given
        given(employeeMapper.toEntity(employee1DTO))
                .willReturn(employee1);

        given(employeeRepository.save(employee1))
                .willReturn(employee1);

        given(employeeMapper.toDto(employee1))
                .willReturn(employee1DTO);

        // When
        EmployeeDTO resultedEmployeeDTO = employeeService.save(employee1DTO);

        // Then
        assertEquals(employee1DTO, resultedEmployeeDTO);
    }

    @Test
    void testSaveAll() {
        // Given
        given(employeeRepository.saveAll(employees))
                .willReturn(employees);

        // When
        List<Employee> resultedEmployees = employeeService.saveAll(employees);

        // Then
        assertEquals(employees, resultedEmployees);
    }

    @Test
    void testFindAllByDepartment() {
        // Given
        given(employeeRepository.findByDepartment(department1))
                .willReturn(List.of(employee1));

        given(employeeMapper.toDto(employee1))
                .willReturn(employee1DTO);

        // When
        List<EmployeeDTO> resultedEmployee = employeeService.findAllByDepartment(department1);

        // Then
        assertEquals(List.of(employee1DTO), resultedEmployee);
    }

    @Test
    void testDelete() {
        // Given
        doNothing()
                .when(employeeRepository).deleteById(anyLong());

        // When
        employeeService.delete(DUMMY_ID + 1);

        // Then
        verify(employeeRepository)
                .deleteById(anyLong());
    }

    @Test
    void testDeleteAllByDepartment() {
        // Given
        doNothing()
                .when(employeeRepository).deleteAllByDepartment(any(Department.class));

        // When
        employeeService.deleteAllByDepartment(department1);

        // Then
        verify(employeeRepository)
                .deleteAllByDepartment(any(Department.class));
    }
}
