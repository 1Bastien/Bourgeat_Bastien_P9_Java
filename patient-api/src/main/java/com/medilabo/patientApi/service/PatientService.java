package com.medilabo.patientApi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medilabo.patientApi.model.Patient;

@Service
public interface PatientService {

	public Patient addPatient(Patient patient);

	public List<Patient> getPatients();

	public Patient getPatientById(Long id);

	public Patient updatePatient(Long id, Patient patient);

	public void deletePatient(Long id);

}
