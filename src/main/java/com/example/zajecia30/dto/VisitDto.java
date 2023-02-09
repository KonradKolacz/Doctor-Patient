package com.example.zajecia30.dto;

import com.example.zajecia30.domain.Doctor;
import com.example.zajecia30.domain.Patient;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VisitDto {

    private Long id;
    private String doctor;
    private String patient;
    private LocalDateTime dateTime;
    private int timeInMinutes;
}
