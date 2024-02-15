package com.medilabo.client.UI.services.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.proxies.GatewayProxy;
import com.medilabo.client.UI.services.CookieService;
import com.medilabo.client.UI.services.PatientService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PatientServiceImpl implements PatientService {

	private static final Logger logger = LogManager.getLogger(PatientServiceImpl.class);

	private GatewayProxy gatewayProxy;
	private CookieService cookieService;

	public PatientServiceImpl(GatewayProxy gatewayProxy, CookieService cookieService) {
		this.gatewayProxy = gatewayProxy;
		this.cookieService = cookieService;
	}

	@Override
	public String getPatientsList(Model model, HttpServletRequest request) {
		try {
			String authorizationHeader = cookieService.getCookie(request);

			if (authorizationHeader == null) {
				logger.error("Unauthorized access to /patient/list. Redirecting to /login.");
				return "redirect:/login";
			}

			List<PatientDto> patients = gatewayProxy.getPatients(authorizationHeader);

			model.addAttribute("patients", patients);
			return "patient/list";
		} catch (Exception e) {

			logger.error("Error while trying to get patients: " + e.getMessage());
			return "redirect:/error";
		}
	}

	@Override
	public String getPatient(Long id, Model model, HttpServletRequest request) {
		try {
			String authorizationHeader = cookieService.getCookie(request);

			if (authorizationHeader == null) {
				logger.error("Unauthorized access to /patient/detail. Redirecting to /login.");
				return "redirect:/login";
			}

			PatientDto patient = gatewayProxy.getPatient(id, authorizationHeader);

			model.addAttribute("patient", patient);
			return "patient/update";

		} catch (Exception e) {
			logger.error("Error while trying to get patient: " + e.getMessage());
			return "redirect:/error";
		}
	}

	@Override
	public String updatePatient(Long id, PatientDto newPatient, Model model, HttpServletRequest request) {
		try {
			String authorizationHeader = cookieService.getCookie(request);

			if (authorizationHeader == null) {
				logger.error("Unauthorized access to /patient/update. Redirecting to /login.");
				return "redirect:/login";
			}

			gatewayProxy.updatePatient(id, newPatient, authorizationHeader);

			return "redirect:/patient/list";

		} catch (Exception e) {
			logger.error("Error while trying to update patient: " + e.getMessage());
			return "redirect:/error";
		}
	}

	@Override
	public String getFormAddPatient(Model model, HttpServletRequest request) {
		try {

			String authorizationHeader = cookieService.getCookie(request);

			if (authorizationHeader == null) {
				logger.error("Unauthorized access to /patient/add. Redirecting to /login.");
				return "redirect:/login";
			}

			model.addAttribute("patient", new PatientDto());
			return "patient/add";

		} catch (Exception e) {
			logger.error("Error while trying to get form to add patient: " + e.getMessage());
			return "redirect:/error";
		}
	}

	@Override
	public String addPatient(PatientDto patient, Model model, HttpServletRequest request) {
		try {
			String authorizationHeader = cookieService.getCookie(request);

			if (authorizationHeader == null) {
				logger.error("Unauthorized access to /patient/add. Redirecting to /login.");
				return "redirect:/login";
			}

			gatewayProxy.addPatient(patient, authorizationHeader);

			return "redirect:/patient/list";
		} catch (Exception e) {
			logger.error("Error while trying to add patient: " + e.getMessage());
			return "redirect:/error";
		}
	}

	@Override
	public String deletePatient(Long id, Model model, HttpServletRequest request) {
		try {
			String authorizationHeader = cookieService.getCookie(request);

			if (authorizationHeader == null) {
				logger.error("Unauthorized access to /patient/delete. Redirecting to /login.");
				return "redirect:/login";
			}

			gatewayProxy.deletePatient(id, authorizationHeader);

			return "redirect:/patient/list";
		} catch (Exception e) {
			logger.error("Error while trying to delete patient: " + e.getMessage());
			return "redirect:/error";
		}
	}
}
