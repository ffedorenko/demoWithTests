package com.example.demowithtests.serviceTests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.domain.PassportStatus;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.employee.EmployeeServiceBean;
import com.example.demowithtests.service.passport.PassportServiceBean;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Employee Service Tests")
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private PassportServiceBean passportService;

    @InjectMocks
    private EmployeeServiceBean employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee
                .builder()
                .id(1)
                .name("Fedir")
                .country("UA")
                .email("fedir@gmail.com")
                .dateOfBirthday(LocalDate.parse("1999-02-21"))
                .isFired(Boolean.FALSE)
                .gender(Gender.M)
                .passport(new Passport())
                .addresses(new HashSet<>())
                .photos(new HashSet<>())
                .employeesCabinets(new HashSet<>())
                .build();
    }

    @Test
    @DisplayName("create employee test")
    public void createEmployeeTest() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        var created = employeeService.create(employee);

        assertThat(created.getName()).isSameAs(employee.getName());
        verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("read by id test")
    public void readByIdTest() {
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        Employee expected = employeeService.getById(employee.getId());

        assertThat(expected).isSameAs(employee);
        verify(employeeRepository).findById(employee.getId());
    }

    @Test
    @DisplayName("read all employees test")
    public void readAllTest() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        var list = employeeRepository.findAll();

        assertThat(list.size()).isGreaterThan(0);
        verify(employeeRepository).findAll();
    }

    @Test
    @DisplayName("delete employee test")
    public void deleteEmployeeTest() {
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        assertEquals(employee.getIsFired(), Boolean.FALSE);

        employeeService.removeById(employee.getId());

        assertEquals(employee.getIsFired(), Boolean.TRUE);
        verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("add passport to employee test")
    public void addPassportToEmployeeTest() {
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(passportService.getFree()).thenReturn(new Passport());
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        var result = employeeService.addPassportToEmployee(employee.getId());

        assertEquals(result.getPassport().getPassportStatus(), PassportStatus.ACTIVE);
        assertEquals(result.getPassport().getIsFree(), Boolean.FALSE);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    @DisplayName("delete pass from employee test")
    public void deletePassportFromEmployee() {
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        employeeService.deletePassportFromEmployee(employee.getId());

        assertNull(employee.getPassport());
    }

    @Test
    @DisplayName("throw exception when employee not found test")
    public void should_throw_exception_when_employee_doesnt_exist() {
        when(employeeRepository.findById(anyInt())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> employeeRepository.findById(anyInt()));
    }
}
