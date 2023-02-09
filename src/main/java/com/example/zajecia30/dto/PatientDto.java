package com.example.zajecia30.dto;

import com.example.zajecia30.domain.Visit;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    private Long id;
    private String name;
    private String surname;
    private Set<Visit> visits;
}
