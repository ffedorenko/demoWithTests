package com.example.demowithtests.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "photos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate addDate;
    private String description;
    private String fileName;
    private byte[] bytes;
    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;
}
