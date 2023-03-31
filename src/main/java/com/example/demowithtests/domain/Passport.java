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
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "passport")
@Builder
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private LocalDate dateOfBirthday;
    @Builder.Default
    private String serialNumber = UUID.randomUUID().toString();
    private LocalDate expireDate;
    @Builder.Default
    private Boolean isFree = Boolean.TRUE;
    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;
    @OneToOne(mappedBy = "passport")
    @JsonIgnore
    private Employee employee;
}
