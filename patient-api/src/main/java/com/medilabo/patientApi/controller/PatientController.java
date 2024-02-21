package com.medilabo.patientApi.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.patientApi.model.Patient;
import com.medilabo.patientApi.service.PatientService;

@RestController
public class PatientController {

	Logger logger = LogManager.getLogger(PatientController.class);

	@Autowired
	private PatientService patientService;

	@PostMapping("/patient")
	public Patient addPatient(@RequestBody Patient patient) {
		logger.info("POST /patient with body: " + patient.toString());
		return patientService.addPatient(patient);
	}

	@GetMapping("/patient/all")
	public List<Patient> getPatients() {
		logger.info("GET /patient/all");
		return patientService.getPatients();
	}

	@GetMapping("/patient/{id}")
	public Patient getPatient(@PathVariable("id") Long id) {
		logger.info("GET /patient/" + id);
		return patientService.getPatientById(id);
	}

	@PostMapping("/patient/update/{id}")
	public Patient updatePatient(@PathVariable("id") Long id, @RequestBody Patient patient) {
		logger.info("POST /patient/update/" + id + " with body: " + patient.toString());
		return patientService.updatePatient(id, patient);
	}

	@GetMapping("/patient/delete/{id}")
	public void deletePatient(@PathVariable("id") Long id) {
		logger.info("GET /patient/delete/" + id);
		patientService.deletePatient(id);
	}
}
