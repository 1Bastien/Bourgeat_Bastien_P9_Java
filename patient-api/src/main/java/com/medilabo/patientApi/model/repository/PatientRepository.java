package com.medilabo.patientApi.model.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medilabo.patientApi.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	public boolean existsByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);

}
