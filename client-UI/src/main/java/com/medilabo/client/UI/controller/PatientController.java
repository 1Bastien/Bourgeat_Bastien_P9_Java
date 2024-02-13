package com.medilabo.client.UI.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.services.PatientService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class PatientController {

	private static final Logger logger = LogManager.getLogger(PatientController.class);

	@Autowired
	private PatientService patientService;

	@GetMapping("/patient/list")
	public String getPatients(HttpServletRequest request, Model model) {
		logger.info("GET /patient/list");
		return patientService.getPatientsList(model, request);
	}

	@GetMapping("/patient/update/{id}")
	public String getUpdateForm(@PathVariable("id") Long id, HttpServletRequest request, Model model) {
		logger.info("GET /patient/update/" + id);
		return patientService.getPatient(id, model, request);
	}

	@PostMapping("/patient/update/{id}")
	public String updatePatient(@PathVariable("id") Long id, @Valid @ModelAttribute("patient") PatientDto newPatient,
			BindingResult result, HttpServletRequest request, Model model) {

		logger.info("POST /patient/update/" + id + " with body: " + newPatient.toString());

		if (result.hasErrors()) {
			model.addAttribute("id", id);
			logger.error("Error in form: " + result.getAllErrors().toString());
			logger.error("redirecting to /patient/update/" + id);
			return "patient/update";
		}
		return patientService.updatePatient(id, newPatient, model, request);
	}

	@GetMapping("/patient/add")
	public String getAddForm(Model model, HttpServletRequest request) {
		logger.info("GET /patient/add");
		return patientService.getFormAddPatient(model, request);
	}

	@PostMapping("/patient/add")
	public String addPatient(@Valid @ModelAttribute("patient") PatientDto patient, BindingResult result,
			HttpServletRequest request, Model model) {

		logger.info("POST /patient/add with body: " + patient.toString());

		if (result.hasErrors()) {
			logger.error("Error in form: " + result.getAllErrors().toString());
			logger.error("redirecting to /patient/add");
			return "patient/add";
		}
		return patientService.addPatient(patient, model, request);
	}

	@GetMapping("/patient/delete/{id}")
	public String deletePatient(@PathVariable("id") Long id, HttpServletRequest request, Model model) {
		logger.info("GET /patient/delete/" + id);
		logger.info("redirecting to /patient/list");
		return patientService.deletePatient(id, model, request);
	}
}
