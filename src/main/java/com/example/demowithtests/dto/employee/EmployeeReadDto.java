package com.example.demowithtests.dto.employee;

import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.PhotoDto;
import com.example.demowithtests.dto.address.AddressResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashSet;
import java.util.Set;

public class EmployeeReadDto {

    @Schema(description = "Name of an employee.", example = "Billy")
    public String name;

    @Schema(description = "Name of the country.", example = "EN")
    public String country;

    @Schema(description = "Email address of an employee.", example = "billys@mail.com")
    public String email;

    @Schema(description = "Addresses of an employee.")
    public Set<AddressResponse> addresses = new HashSet<>();

    @Schema(description = "Photos of employee.")
    public Set<PhotoDto> photos = new HashSet<>();

    @Schema(description = "Gender of an employee.")
    public Gender gender;

    @Schema(description = "Passport of employee.")
    public Passport passport;
}
