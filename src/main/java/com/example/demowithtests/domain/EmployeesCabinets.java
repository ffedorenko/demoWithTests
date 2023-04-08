package com.example.demowithtests.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users_cabinets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class EmployeesCabinets {

    @EmbeddedId
    private EmployeesCabinetsKey id = new EmployeesCabinetsKey();

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @MapsId("employeeId")
    @JsonIgnore
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "cabinet_id")
    @MapsId("cabinetId")
    @JsonIgnore
    private Cabinet cabinet;

    private Boolean isActive = Boolean.TRUE;
}
