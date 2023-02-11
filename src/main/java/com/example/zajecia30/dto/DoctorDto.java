package com.example.zajecia30.dto;

import com.example.zajecia30.domain.Doctor;
import com.example.zajecia30.domain.Visit;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto extends RepresentationModel<DoctorDto> {
    private Long id;
    private String name;
    private String surname;
    private String specialty;
    private Set<Visit> visits;
}
