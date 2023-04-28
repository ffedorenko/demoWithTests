package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
//@Component
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

    @NotNull
    Page<Employee> findAllByIsFiredIsFalseOrIsFiredIsNull(Pageable pageable);

    List<Employee> findAllByIsFiredIsFalseOrIsFiredIsNull();

    Page<Employee> findByName(String name, Pageable pageable);

    Page<Employee> findByCountryContainingAndIsFiredIsFalse(String country, Pageable pageable);

    @Query(value = "SELECT MAX(id) FROM public.users", nativeQuery = true)
    Integer findIdWithMaxValue();
}
