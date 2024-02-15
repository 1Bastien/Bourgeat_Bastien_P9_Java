package com.medilabo.client.UI.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.medilabo.client.UI.Dto.FeedbackDto;
import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.configuration.GatewayFeignConfiguration;

@FeignClient(name = "gateway", configuration = GatewayFeignConfiguration.class)
public interface GatewayProxy {

	@PostMapping("/patient-api/patient")
	PatientDto addPatient(PatientDto patient, @RequestHeader("Authorization") String authorizationHeader);

	@GetMapping("/patient-api/patient/all")
	List<PatientDto> getPatients(@RequestHeader("Authorization") String authorizationHeader);

	@GetMapping("/patient-api/patient/{id}")
	PatientDto getPatient(@PathVariable("id") Long id, @RequestHeader("Authorization") String authorizationHeader);

	@PostMapping("/patient-api/patient/update/{id}")
	PatientDto updatePatient(@PathVariable("id") Long id, PatientDto patient,
			@RequestHeader("Authorization") String authorizationHeader);

	@GetMapping("/patient-api/patient/delete/{id}")
	void deletePatient(@PathVariable("id") Long id, @RequestHeader("Authorization") String authorizationHeader);

	@GetMapping("/login")
	ResponseEntity<String> login(@RequestHeader("Authorization") String authorizationHeader);

	@GetMapping("/feedback-service/feedback/patient/{id}")
	List<FeedbackDto> getPatientFeedback(@PathVariable("id") Long patientId, @RequestHeader("Authorization") String authorizationHeader);

	@PostMapping("/feedback-service/feedback/patient")
	FeedbackDto addPatientFeedback(FeedbackDto feedback, @RequestHeader("Authorization") String authorizationHeader);

}
