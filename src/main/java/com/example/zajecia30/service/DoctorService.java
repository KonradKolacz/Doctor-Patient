package com.example.zajecia30.service;

import com.example.zajecia30.domain.Doctor;
import com.example.zajecia30.domain.Patient;
import com.example.zajecia30.domain.Visit;
import com.example.zajecia30.exception.ObjectNotFoundException;
import com.example.zajecia30.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public Doctor save(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    @Transactional
    public boolean doctorExist(Long id){
        return doctorRepository.existsById(id);
//        return doctorRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException(id, "doctor")) != null;
    }

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    @Transactional
    public Doctor findById(Long id) {
        return doctorRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException(id, "doctor"));
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Set<Visit> findVisitsByDoctorId(Long id){
        return findById(id).getVisits();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Set<Visit> findActiveVisitsByDoctorId(Long id){
        return findById(id).getVisits().stream().filter(visit -> visit.getDateTime().isAfter(LocalDateTime.now())).collect(Collectors.toSet());
    }

}
