package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cabinets")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Cabinet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Integer id;
    private Integer capacity;
    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;
    @ManyToMany(mappedBy = "cabinets")
    private Set<Employee> employees;
}
