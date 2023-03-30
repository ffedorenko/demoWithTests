package com.example.demowithtests.dto.passport;

import com.example.demowithtests.domain.Employee;

import java.time.LocalDateTime;

public class PassportResponse {
    public String firstName;
    public String secondName;
    public LocalDateTime dateOfBirthday;
    private LocalDateTime expireDate;
    private Employee employee;
}
