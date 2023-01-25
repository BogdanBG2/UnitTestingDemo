package com.bogdanbuga.unittestingdemo.service;

import com.bogdanbuga.unittestingdemo.dto.DepartmentDTO;
import com.bogdanbuga.unittestingdemo.mapper.DepartmentMapper;
import com.bogdanbuga.unittestingdemo.model.Department;
import com.bogdanbuga.unittestingdemo.model.enums.LanguageType;
import com.bogdanbuga.unittestingdemo.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @InjectMocks
    private DepartmentService departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    private Department department1;

    private DepartmentDTO department1DTO;

    private Department department2;

    private DepartmentDTO department2DTO;

    private List<Department> departments;

    private List<DepartmentDTO> departmentDTOs;

    private static final long DUMMY_ID = 100;

    private static final String DUMMY_NAME = "NAME_";

    @BeforeEach
    void setUp() {
        department1 = new Department()
                .id(DUMMY_ID + 1)
                .name(DUMMY_NAME + "1")
                .language(LanguageType.JAVA);

        department2 = new Department()
                .id(DUMMY_ID + 2)
                .name(DUMMY_NAME + "2")
                .language(LanguageType.PYTHON);

        departments = List.of(department1, department2);

        department1DTO = new DepartmentDTO();
        department1DTO.setId(department1.getId());
        department1DTO.setName(department1DTO.getName());
        department1DTO.setLanguage(department1.getLanguage());

        department2DTO = new DepartmentDTO();
        department2DTO.setId(department2.getId());
        department2DTO.setName(department2DTO.getName());
        department2DTO.setLanguage(department2.getLanguage());

        departmentDTOs = List.of(department1DTO, department2DTO);
    }

    @Test
    void testFindAll() {
        // Given
        given(departmentRepository.findAll())
                .willReturn(departments);

        for (int i = 0; i < departments.size(); ++i)
            given(departmentMapper.toDto(departments.get(i)))
                    .willReturn(departmentDTOs.get(i));

        // When
        List<DepartmentDTO> resultedDepartmentDTOs = departmentService.findAll();

        // Then
        assertEquals(departmentDTOs, resultedDepartmentDTOs);
    }

    @Test
    void testFindOne() {
        // Given
        given(departmentRepository.findById(DUMMY_ID + 1))
                .willReturn(Optional.of(department1));

        given(departmentMapper.toDto(department1))
                .willReturn(department1DTO);

        // When
        Optional<DepartmentDTO> resultedDepartmentDTO = departmentService.findOne(DUMMY_ID + 1);

        // Then
        assertEquals(Optional.of(department1DTO), resultedDepartmentDTO);
    }

    @Test
    void testSave() {
        // Given
        given(departmentMapper.toEntity(department1DTO))
                .willReturn(department1);

        given(departmentRepository.save(department1))
                .willReturn(department1);

        given(departmentMapper.toDto(department1))
                .willReturn(department1DTO);

        // When
        DepartmentDTO resultedDepartmentDTO = departmentService.save(department1DTO);

        // Then
        assertEquals(department1DTO, resultedDepartmentDTO);
    }

    @Test
    void testSaveAll() {
        // Given
        given(departmentRepository.saveAll(departments))
                .willReturn(departments);

        // When
        List<Department> resultedDepartments = departmentService.saveAll(departments);

        // Then
        assertEquals(departments, resultedDepartments);
    }

    @Test
    void testFindByName() {
        // Given
        given(departmentRepository.findByName(DUMMY_NAME + "1"))
                .willReturn(Optional.of(department1));

        // When
        Optional<Department> resultedDepartment = departmentService.findByName(DUMMY_NAME + "1");

        // Then
        assertEquals(Optional.of(department1), resultedDepartment);
    }

    @Test
    void testDelete() {
        // Given
        doNothing()
                .when(departmentRepository).deleteById(anyLong());

        // When
        departmentService.delete(DUMMY_ID + 1);

        // Then
        verify(departmentRepository)
                .deleteById(anyLong());
    }

    @Test
    void testGetNumberOfDepartmentsPerLanguage() {
        // Given
        given(departmentRepository.countByLanguage(any(LanguageType.class)))
                .willReturn(0L);

        given(departmentRepository.countByLanguage(LanguageType.JAVA))
                .willReturn(1L);

        given(departmentRepository.countByLanguage(LanguageType.PYTHON))
                .willReturn(1L);


        // When
        Map<String, Long> actual = departmentService.getNumberOfDepartmentsPerLanguage();

        // Then
        Map<String, Long> expected = Map.of(
                "JAVA", 1L,
                "PYTHON", 1L,
                "PHP", 0L,
                "RUST", 0L
        );

        assertEquals(expected, actual);
    }
}
