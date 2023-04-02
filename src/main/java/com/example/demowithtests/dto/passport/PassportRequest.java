package com.example.demowithtests.dto.passport;

import com.example.demowithtests.domain.PassportStatus;

import java.time.LocalDate;
import java.util.UUID;

public class PassportRequest {
    public String name;
    public LocalDate dateOfBirthday;
    public PassportStatus passportStatus;
    public String serialNumber = UUID.randomUUID().toString();
}
