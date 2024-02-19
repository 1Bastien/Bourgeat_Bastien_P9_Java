package com.medilabo.client.UI.services.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.reactive.function.client.WebClient;

import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.services.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

	private static final Logger logger = LogManager.getLogger(PatientServiceImpl.class);

	@Autowired
	private WebClient.Builder webClient;

	@Override
	public String getPatientsList(Model model) {
		try {

			List<PatientDto> patients = webClient.build().get().uri("http://gateway/patient-api/patient/all").retrieve()
					.bodyToFlux(PatientDto.class).collectList().flux().blockLast();

			model.addAttribute("patients", patients);

			return "patient/list";
		} catch (Exception e) {

			logger.error("Error while trying to get patients: " + e);
			return "redirect:/error";
		}
	}

	@Override
	public String getPatient(Long id, Model model) {
		try {

			PatientDto patient = webClient.build().get().uri("http://gateway/patient-api/patient/" + id).retrieve()
					.bodyToFlux(PatientDto.class).blockLast();

			model.addAttribute("patient", patient);
			return "patient/update";

		} catch (Exception e) {
			logger.error("Error while trying to get patient: " + e.getMessage());
			return "redirect:/error";
		}
	}

	@Override
	public String updatePatient(Long id, PatientDto newPatient) {
		try {

			webClient.build().post().uri("http://gateway/patient-api/patient/update/" + id).bodyValue(newPatient)
					.retrieve().bodyToFlux(PatientDto.class).blockLast();

			return "redirect:/patient/list";

		} catch (Exception e) {
			logger.error("Error while trying to update patient: " + e.getMessage());
			return "redirect:/error";
		}
	}

	@Override
	public String getFormAddPatient(Model model) {
		try {

			model.addAttribute("patient", new PatientDto());
			return "patient/add";

		} catch (Exception e) {
			logger.error("Error while trying to get form to add patient: " + e.getMessage());
			return "redirect:/error";
		}
	}

	@Override
	public String addPatient(PatientDto patient) {
		try {

			webClient.build().post().uri("http://gateway/patient-api/patient").bodyValue(patient).retrieve()
					.bodyToFlux(PatientDto.class).blockLast();

			return "redirect:/patient/list";
		} catch (Exception e) {
			logger.error("Error while trying to add patient: " + e.getMessage());
			return "redirect:/error";
		}
	}

	@Override
	public String deletePatient(Long id) {
		try {

			webClient.build().get().uri("http://gateway/patient-api/patient/delete/" + id).retrieve()
					.bodyToFlux(Void.class).blockLast();

			return "redirect:/patient/list";
		} catch (Exception e) {
			logger.error("Error while trying to delete patient: " + e.getMessage());
			return "redirect:/error";
		}
	}
}
