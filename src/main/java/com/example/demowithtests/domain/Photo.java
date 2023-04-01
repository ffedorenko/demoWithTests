package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "photos")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Integer id;
    private LocalDate addDate;
    private String description;
    private String fileName;
    private byte[] bytes;
    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;
}
