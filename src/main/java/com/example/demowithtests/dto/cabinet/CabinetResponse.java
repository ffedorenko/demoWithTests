package com.example.demowithtests.dto.cabinet;

import com.example.demowithtests.domain.EmployeesCabinets;

import java.util.Set;

public class CabinetResponse {
    public Integer id;
    public Integer capacity;
    public Set<EmployeesCabinets> employeesCabinets;
}
