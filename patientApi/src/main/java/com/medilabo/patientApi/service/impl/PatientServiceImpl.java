package com.medilabo.patientApi.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.medilabo.patientApi.model.Patient;
import com.medilabo.patientApi.model.repository.PatientRepository;
import com.medilabo.patientApi.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

	private static final Logger logger = LogManager.getLogger(PatientServiceImpl.class);

	@Autowired
	private PatientRepository patientRepository;

	@Override
	@Transactional
	public Patient addPatient(Patient patient) {
		if (patientRepository.existsByFirstNameAndLastNameAndDateOfBirth(patient.getFirstName(), patient.getLastName(),
				patient.getDateOfBirth())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Patient already exists");
		}

		try {
			patientRepository.save(patient);
		} catch (Exception e) {
			logger.error("Error while adding patient", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while adding patient");
		}

		return patient;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Patient> getPatients() {
		try {
			return patientRepository.findAll();
		} catch (Exception e) {
			logger.error("Error while fetching patients", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching patients");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Patient getPatientById(Long id) {
		if (!patientRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
		}
		try {
			return patientRepository.findById(id).get();
		} catch (Exception e) {
			logger.error("Error while fetching patients", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching patients");
		}
	}

	@Override
	@Transactional
	public Patient updatePatient(Long id, Patient patient) {
		if (!patientRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
		}

		try {
			Patient existingPatient = patientRepository.findById(id).get();
			existingPatient.setFirstName(patient.getFirstName());
			existingPatient.setLastName(patient.getLastName());
			existingPatient.setDateOfBirth(patient.getDateOfBirth());
			existingPatient.setGender(patient.getGender());
			existingPatient.setAddress(patient.getAddress());
			existingPatient.setPhoneNumber(patient.getPhoneNumber());

			patientRepository.save(existingPatient);
			return existingPatient;

		} catch (Exception e) {
			logger.error("Error while fetching patients", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching patients");
		}
	}

	@Override
	@Transactional
	public void deletePatient(Long id) {
		if (!patientRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
		}

		try {
			patientRepository.deleteById(id);
		} catch (Exception e) {
			logger.error("Error while fetching patients", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching patients");
		}
	}
}
