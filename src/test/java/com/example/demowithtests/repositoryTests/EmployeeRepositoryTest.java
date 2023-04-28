package com.example.demowithtests.repositoryTests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @Test
    @Order(1)
    @Rollback(value = false)
    @DisplayName("save employee test")
    public void saveEmployeeTest() {
        var employee = Employee.builder()
                .name("Fedir")
                .country("Ukraine")
                .gender(Gender.M)
                .build();

        repository.save(employee);

        assertThat(employee.getId()).isGreaterThan(0);
        assertThat(employee.getId()).isEqualTo(1);
        assertThat(employee.getName()).isEqualTo("Fedir");
    }

    @Test
    @Order(2)
    @DisplayName("find by id test")
    public void findEmployeeByIdTest() {
        var employee = repository.findById(1).orElseThrow();

        assertThat(employee.getId()).isEqualTo(1);
    }

    @Test
    @Order(3)
    @DisplayName("find all test")
    public void findAllEmployeesTest() {
        var employees = repository.findAll();

        assertThat(employees.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @DisplayName("update employee test")
    public void updateEmployeeTest() {
        var employee = repository.findById(1).orElseThrow();
        employee.setName("Fedir");
        var updated = repository.save(employee);

        assertThat(updated.getName()).isEqualTo("Fedir");
    }

    @Test
    @Order(5)
    @DisplayName("delete employee test")
    public void deleteEmployeeTest() {
        var employee = repository.findById(1).orElseThrow();
        repository.delete(employee);
        var deleted = repository.findById(1);
        assertThat(deleted.isPresent()).isEqualTo(false);
    }
}
