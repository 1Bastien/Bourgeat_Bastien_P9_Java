package com.medilabo.client.UI.proxies.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.proxies.PatientProxy;

@Service
public class PatientProxyImpl implements PatientProxy {

	private static final Logger logger = LogManager.getLogger(PatientProxyImpl.class);

	@Autowired
	private WebClient.Builder webClient;

	@Override
	public List<PatientDto> getPatientsList() {
		try {
			List<PatientDto> patients = webClient.build().get().uri("http://gateway/patient-api/patient/all").retrieve()
					.bodyToFlux(PatientDto.class).collectList().flux().blockLast();

			logger.info("Patients fetched successfully");
			return patients;
		} catch (Exception e) {
			logger.error("Error while trying to get patients: " + e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching patients");
		}
	}

	@Override
	public PatientDto getPatient(Long id) {
		try {
			PatientDto patient = webClient.build().get().uri("http://gateway/patient-api/patient/" + id).retrieve()
					.bodyToFlux(PatientDto.class).blockLast();

			logger.info("Patient fetched successfully");
			return patient;
		} catch (Exception e) {
			logger.error("Error while trying to get patient: " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching patient");
		}
	}

	@Override
	public void updatePatient(Long id, PatientDto newPatient) {
		try {
			webClient.build().post().uri("http://gateway/patient-api/patient/update/" + id).bodyValue(newPatient)
					.retrieve().bodyToFlux(PatientDto.class).blockLast();

			logger.info("Patient updated successfully");
		} catch (Exception e) {
			logger.error("Error while trying to update patient: " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while updating patient");
		}
	}

	@Override
	public void addPatient(PatientDto patient) {
		try {
			webClient.build().post().uri("http://gateway/patient-api/patient").bodyValue(patient).retrieve()
					.bodyToFlux(PatientDto.class).blockLast();

			logger.info("Patient added successfully");
		} catch (Exception e) {
			logger.error("Error while trying to add patient: " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while adding patient");
		}
	}

	@Override
	public void deletePatient(Long id) {
		try {
			webClient.build().get().uri("http://gateway/patient-api/patient/delete/" + id).retrieve()
					.bodyToFlux(Void.class).blockLast();

			logger.info("Patient deleted successfully");
		} catch (Exception e) {
			logger.error("Error while trying to delete patient: " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while deleting patient");
		}
	}
}
