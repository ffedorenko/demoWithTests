package com.example.demowithtests.service.employeesCabinets;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeesCabinets;

public interface EmployeesCabinetsService {
    EmployeesCabinets createRelation(Employee employee, Cabinet cabinet);
    EmployeesCabinets getRelation(Integer employeeId, Integer cabinetId);
    void deactivateRelation(Integer employeeId, Integer cabinetId);
}
