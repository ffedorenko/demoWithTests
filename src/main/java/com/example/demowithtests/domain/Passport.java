package com.example.demowithtests.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "passports")
@Builder
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Integer id;
    private String name;
    private LocalDate dateOfBirthday;
    @Builder.Default
    private String serialNumber = UUID.randomUUID().toString();
    private LocalDate expireDate;
    @Enumerated(EnumType.STRING)
    private PassportStatus passportStatus;
    @Builder.Default
    private Boolean isFree = Boolean.TRUE;
    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;
    @OneToOne(mappedBy = "passport")
    @JsonIgnore
    private Employee employee;
    @OneToOne
    @JsonIgnore
    private Passport previousPassport;
}
