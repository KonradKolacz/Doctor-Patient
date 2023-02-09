package com.example.zajecia30.repository;

import com.example.zajecia30.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Visit save(Visit visit);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsById(Long id);
}
