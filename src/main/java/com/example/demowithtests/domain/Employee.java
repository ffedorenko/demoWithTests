package com.example.demowithtests.domain;

import com.example.demowithtests.util.annotations.validation.Name;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Integer id;
    @Name
    private String name;
    private LocalDate dateOfBirthday;
    private String country;
    private String email;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Set<Photo> photos = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pass_id", referencedColumnName = "id")
    private Passport passport;

    @Builder.Default
    private Boolean isFired = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "employee")
    private Set<EmployeesCabinets> employeesCabinets;
}
