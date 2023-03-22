package com.example.demowithtests.dto;

import java.time.LocalDate;

public class PhotoDto {
    public LocalDate addDate = LocalDate.now();
    public String description;
    public String fileName;
    public Boolean isDeleted = Boolean.FALSE;
}
