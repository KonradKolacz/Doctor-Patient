package com.example.zajecia30.repository;

import com.example.zajecia30.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsById(Long aLong);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Patient> findById(@Param("id") Long id);
}
