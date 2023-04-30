package com.example.demowithtests.repositoryTests;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.repository.PassportRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PassportRepositoryTest {
    @Autowired
    private PassportRepository repository;

    @Test
    @Order(1)
    @Rollback(value = false)
    @DisplayName("save passport test")
    public void savePassportTest() {
        var passport = Passport.builder()
                .name("Fedir")
                .build();

        repository.save(passport);

        assertThat(passport.getId()).isGreaterThan(0);
        assertThat(passport.getId()).isEqualTo(1);
        assertThat(passport.getName()).isEqualTo("Fedir");
    }

    @Test
    @Order(2)
    @DisplayName("find by id test")
    public void findPassportByIdTest() {
        var passport = repository.findById(1).orElseThrow();

        assertThat(passport.getId()).isEqualTo(1);
    }

    @Test
    @Order(3)
    @DisplayName("find all test")
    public void findAllPassportsTest() {
        var passports = repository.findAll();

        assertThat(passports.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @DisplayName("update passport test")
    public void updatePassportTest() {
        var passport = repository.findById(1).orElseThrow();
        passport.setName("FedirTest");
        var updated = repository.save(passport);

        assertThat(updated.getName()).isEqualTo("FedirTest");
    }

    @Test
    @Order(5)
    @DisplayName("delete passport test")
    public void deletePassportTest() {
        var passport = repository.findById(1).orElseThrow();
        repository.delete(passport);
        var deleted = repository.findById(1);
        assertThat(deleted.isPresent()).isEqualTo(false);
    }
}
