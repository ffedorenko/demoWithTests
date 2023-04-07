package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users_cabinets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EmployeesCabinets {

    @EmbeddedId
    private EmployeesCabinetsKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cabinetId")
    private Cabinet cabinet;

    private Boolean isActive = Boolean.TRUE;
}
