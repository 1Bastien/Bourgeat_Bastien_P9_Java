package com.medilabo.client.UI.services.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.proxies.PatientProxy;
import com.medilabo.client.UI.services.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

	private static final Logger logger = LogManager.getLogger(PatientServiceImpl.class);

	@Autowired
	private PatientProxy patientProxy;

	@Override
	public String getPatientsList(Model model) {
		try {
			List<PatientDto> patients = patientProxy.getPatientsList();

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
			PatientDto patient = patientProxy.getPatient(id);

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
			patientProxy.updatePatient(id, newPatient);

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
			patientProxy.addPatient(patient);

			return "redirect:/patient/list";
		} catch (Exception e) {
			logger.error("Error while trying to add patient: " + e.getMessage());
			return "redirect:/error";
		}
	}

	@Override
	public String deletePatient(Long id) {
		try {
			patientProxy.deletePatient(id);

			return "redirect:/patient/list";
		} catch (Exception e) {
			logger.error("Error while trying to delete patient: " + e.getMessage());
			return "redirect:/error";
		}
	}
}
