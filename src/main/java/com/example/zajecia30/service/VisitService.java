package com.example.zajecia30.service;

import com.example.zajecia30.domain.Doctor;
import com.example.zajecia30.domain.Visit;
import com.example.zajecia30.exception.BusyTermException;
import com.example.zajecia30.exception.FutureDateException;
import com.example.zajecia30.exception.ObjectNotFoundException;
import com.example.zajecia30.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {ObjectNotFoundException.class, FutureDateException.class})
    public Visit add(Visit visit) {
//        1 CZY DOKTOR ISTNIEJE?
//        czy lepiej z doctorRepository? czy bez roznicy?
        if (!doctorService.doctorExist(visit.getDoctor().getId())) {
            throw new ObjectNotFoundException(visit.getDoctor().getId(), "doctor");
        }
//        2 CZY PACJENT ISTNIEJE?
        patientService.findById(visit.getPatient().getId());
//        3 CZY DATA Z PRZYSZLOSCI?
        if (visit.getDateTime().isBefore(LocalDateTime.now())) {
            throw new FutureDateException();
        }
//        4 CZY Lekarz jest wtedy zajety?
        Set<Visit> activeVisitsByDoctorId = doctorService.findActiveVisitsByDoctorId(visit.getDoctor().getId());
        if (isBusy(activeVisitsByDoctorId, visit.getDateTime(), visit.getDateTime().plusMinutes(visit.getTimeInMinutes()))){
            throw new BusyTermException();
        }
        return visitRepository.save(visit);
    }


    private boolean isBusy(Set<Visit> visits, LocalDateTime startVisit, LocalDateTime endVisit){
//  1 czy jakas wizyta zaczyna sie w trakcie wybranego terminu
        if (visits.stream().map(Visit::getDateTime).anyMatch(dateTime -> isDuringVisitTime(dateTime, startVisit, endVisit))){
            return true;
        }
//  2 czy jakas wizyta konczy sie w trakcie wybranego terminu
        if (visits.stream().map(visit-> visit.getDateTime().plusMinutes(visit.getTimeInMinutes())).anyMatch(dateTime -> isDuringVisitTime(dateTime, startVisit, endVisit))){
            return true;
        }
//  3 czy jakas wizyta zaczyna sie przed i konczy po naszej wizycie
        if (visits.stream().
        filter(visit -> visit.getDateTime().isBefore(startVisit))
                .anyMatch(visit -> visit.getDateTime().plusMinutes(visit.getTimeInMinutes()).isAfter(endVisit))){
            return true;
        }
        return false;
    }

    private boolean isDuringVisitTime(LocalDateTime dateTime, LocalDateTime startVisit, LocalDateTime endVisit){
        return dateTime.isAfter(startVisit) && dateTime.isBefore(endVisit);
    }



}
