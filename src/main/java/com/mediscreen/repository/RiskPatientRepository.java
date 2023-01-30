package com.mediscreen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediscreen.entities.Patient;

@Repository
public interface RiskPatientRepository extends JpaRepository<Patient, Long> {

	Patient findByPatientId(Long id);
}
