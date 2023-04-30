package com.example.demowithtests.serviceTests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.domain.PassportStatus;
import com.example.demowithtests.repository.PassportRepository;
import com.example.demowithtests.service.passport.PassportServiceBean;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Passport Service Tests")
public class PassportServiceTests {
    @Mock
    private PassportRepository passportRepository;
    @InjectMocks
    private PassportServiceBean passportService;

    private Passport passport;

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
        passport = Passport.builder()
                .id(1)
                .name("Fedir")
                .dateOfBirthday(LocalDate.parse("1999-02-21"))
                .employee(employee)
                .isFree(Boolean.FALSE)
                .passportStatus(PassportStatus.ACTIVE)
                .build();
    }

    @Test
    @DisplayName("create passport test")
    public void createPassportTest() {
        when(passportService.create(any(Passport.class))).thenReturn(passport);

        var created = passportService.create(passport);

        assertThat(created.getName()).isSameAs(passport.getName());
        assertThat(created.getEmployee()).isSameAs(passport.getEmployee());
        verify(passportRepository).save(passport);
    }

    @Test
    @DisplayName("read by id test")
    public void readByIdTest() {
        when(passportRepository.findById(passport.getId())).thenReturn(Optional.of(passport));

        Passport expected = passportService.getById(passport.getId());

        assertThat(expected).isSameAs(passport);
        verify(passportRepository).findById(passport.getId());
    }

    @Test
    @DisplayName("read all passports test")
    public void readAllTest() {
        when(passportRepository.findAll()).thenReturn(List.of(passport));

        var list = passportRepository.findAll();

        assertThat(list.size()).isGreaterThan(0);
        verify(passportRepository).findAll();
    }

    @Test
    @DisplayName("delete passport test")
    public void deletePassportTest() {
        when(passportRepository.findById(passport.getId())).thenReturn(Optional.of(passport));
        when(passportRepository.save(any(Passport.class))).thenReturn(passport);

        assertEquals(passport.getIsDeleted(), Boolean.FALSE);

        passportService.removeById(passport.getId());

        assertEquals(passport.getIsDeleted(), Boolean.TRUE);
        verify(passportRepository).save(passport);
    }

    @Test
    @DisplayName("delete passport test")
    public void replaceTest() {
        when(passportRepository.save(any(Passport.class))).thenReturn(passport);
        var newPassport = Passport.builder()
                .name("Fedir")
                .dateOfBirthday(LocalDate.parse("1999-02-21"))
                .employee(employee)
                .isFree(Boolean.FALSE)
                .passportStatus(PassportStatus.ACTIVE)
                .build();
        PassportStatus status = PassportStatus.DAMAGED;

        var replaced = passportService.replacePassport(passport, status);

        assertThat(replaced.getName()).isSameAs(newPassport.getName());
        assertThat(replaced.getEmployee()).isSameAs(newPassport.getEmployee());
        assertThat(replaced.getPassportStatus()).isSameAs(status);
    }
}
