package com.example.demowithtests.dto.passport;

import java.time.LocalDate;
import java.util.UUID;

public class PassportRequest {
    public String name;
    public LocalDate dateOfBirthday;
    public String serialNumber = UUID.randomUUID().toString();
}
