package com.example.demowithtests.dto.employee;

import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.dto.PhotoDto;
import com.example.demowithtests.dto.address.AddressRequest;
import com.example.demowithtests.util.annotations.validation.Country;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class EmployeeDto {

    @Schema(description = "Id of an employee. DO NOT FILL IN REQUEST", example = "1")
    public Integer id;

    @NotNull(message = "Name may not be null")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    public String name;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Date of birthday. ISO format", example = "yyyy-MM-dd", required = true)
    public LocalDate dateOfBirthday;

    @Country
    @Schema(description = "Name of the country.", example = "England", required = true)
    public String country;

    @Email
    @NotNull
    @Schema(description = "Email address of an employee.", example = "billys@mail.com", required = true)
    public String email;

    @Schema(description = "Addresses of an employee.")
    public Set<AddressRequest> addresses = new HashSet<>();

    @Schema(description = "Photos of employee.")
    public Set<PhotoDto> photos = new HashSet<>();

    @Schema(description = "Gender of an employee.")
    public Gender gender;

    @JsonIgnore
    public Boolean isFired = Boolean.FALSE;
}
