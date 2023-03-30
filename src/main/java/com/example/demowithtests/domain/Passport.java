package com.example.demowithtests.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "passport")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String secondName;
    private LocalDateTime dateOfBirthday;
    private String serialNumber;
    private LocalDateTime expireDate;
    private Boolean isFree = Boolean.TRUE;
    @OneToOne(mappedBy = "passport")
    private Employee employee;
}
