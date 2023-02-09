package com.example.zajecia30.repository;

import com.example.zajecia30.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Doctor> findById(@Param("id") Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsById(Long id);
}
