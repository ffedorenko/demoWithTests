package com.example.demowithtests.dto.passport;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.domain.PassportStatus;
import com.example.demowithtests.dto.employee.EmployeePassportRead;
import java.time.LocalDate;

public class PassportResponse {
    public String name;
    public LocalDate dateOfBirthday;
    public LocalDate expireDate;
    public PassportStatus passportStatus;
    public EmployeePassportRead employee;
    public Passport previousPassport;
}
