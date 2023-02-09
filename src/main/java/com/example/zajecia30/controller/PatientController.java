package com.example.zajecia30.controller;

import com.example.zajecia30.command.PatientCommand;
import com.example.zajecia30.domain.Patient;
import com.example.zajecia30.dto.PatientDto;
import com.example.zajecia30.dto.VisitDto;
import com.example.zajecia30.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<PatientDto> addPatient(@Valid @RequestBody PatientCommand command) {
        final Patient patient = modelMapper.map(command, Patient.class);
        return new ResponseEntity<>(modelMapper.map(patientService.save(patient), PatientDto.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAll() {
        final List<Patient> patients = patientService.findAll();
        return new ResponseEntity<>(patients.stream().map(patient -> modelMapper.map(patient, PatientDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable("id") Long id) {
        final Patient patient = patientService.findById(id);
        return new ResponseEntity<>(modelMapper.map(patient, PatientDto.class), HttpStatus.OK);
    }


    @GetMapping("/{id}/visit")
    public ResponseEntity<Set<VisitDto>> getVisitsByPatientId(@PathVariable("id") Long id) {
        final Patient patient = patientService.findById(id);
        return new ResponseEntity<>(patient.getVisits().stream().map(visit -> modelMapper.map(visit, VisitDto.class)).collect(Collectors.toSet()), HttpStatus.OK);
    }
}
