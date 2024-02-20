package com.medilabo.assessmentservice.proxies.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.medilabo.assessmentservice.Dto.PatientDto;
import com.medilabo.assessmentservice.proxies.PatientProxy;

@Service
public class PatientProxyImpl implements PatientProxy {

	private static final Logger logger = LogManager.getLogger(PatientProxyImpl.class);

	@Autowired
	private WebClient.Builder webClient;

	@Override
	public PatientDto getPatient(Long id) {
		try {
			PatientDto patient = webClient.build().get().uri("http://patient-api/patient/" + id).retrieve()
					.bodyToFlux(PatientDto.class).blockLast();

			logger.info("Patient fetched successfully");
			return patient;
		} catch (Exception e) {
			logger.error("Error while trying to get patient: " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching patient");
		}
	}
}
