package com.example.zajecia30.service;

import com.example.zajecia30.domain.Patient;
import com.example.zajecia30.exception.FutureDateException;
import com.example.zajecia30.exception.ObjectNotFoundException;
import com.example.zajecia30.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public Patient save(Patient patient){
        return patientRepository.save(patient);
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Transactional
    public Patient findById(Long id) {
        return patientRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException(id, Patient.class.getName()));
    }

}
