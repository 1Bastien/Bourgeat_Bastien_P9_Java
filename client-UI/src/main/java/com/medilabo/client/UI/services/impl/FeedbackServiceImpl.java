package com.medilabo.client.UI.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.medilabo.client.UI.Dto.FeedbackDto;
import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.proxies.GatewayProxy;
import com.medilabo.client.UI.services.CookieService;
import com.medilabo.client.UI.services.FeedbackService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	private static final Logger logger = LogManager.getLogger(FeedbackServiceImpl.class);

	private GatewayProxy gatewayProxy;
	private CookieService cookieService;

	public FeedbackServiceImpl(GatewayProxy gatewayProxy, CookieService cookieService) {
		this.gatewayProxy = gatewayProxy;
		this.cookieService = cookieService;
	}

	@Override
	public String getFeedback(Long patientId, Model model, HttpServletRequest request) {
		try {
			String authorizationHeader = cookieService.getCookie(request);

			if (authorizationHeader == null) {
				logger.error("Unauthorized access to /patient/feedback. Redirecting to /login.");
				return "redirect:/login";
			}

			PatientDto patient = gatewayProxy.getPatient(patientId, authorizationHeader);
			List<FeedbackDto> feedbacks = gatewayProxy.getPatientFeedback(patientId, authorizationHeader);

			FeedbackDto feedback = new FeedbackDto();
			feedback.setPatientId(patientId);
			feedback.setPatientName(patient.getFirstName());
			feedback.setDate(LocalDateTime.now());

			model.addAttribute("feedback", feedback);
			model.addAttribute("patient", patient);
			model.addAttribute("feedbacks", feedbacks);

			return "patient/feedback";

		} catch (Exception e) {
			logger.error("Error while trying to get feedback: " + e.getMessage());
			return "redirect:/error";
		}
	}

	@Override
	public String addFeedback(FeedbackDto feedback, HttpServletRequest request) {
		try {
			String authorizationHeader = cookieService.getCookie(request);

			if (authorizationHeader == null) {
				logger.error("Unauthorized access to /patient/feedback/add. Redirecting to /login.");
				return "redirect:/login";
			}

			PatientDto patient = gatewayProxy.getPatient(feedback.getPatientId(), authorizationHeader);
			if (patient == null) {
				logger.error("Patient with id " + feedback.getPatientId() + " not found.");
				return "redirect:/404";
			}

			feedback.setDate(LocalDateTime.now());
			gatewayProxy.addPatientFeedback(feedback, authorizationHeader);

			return "redirect:/patient/feedback/" + feedback.getPatientId();
		} catch (Exception e) {
			logger.error("Error while trying to add feedback: " + e.getMessage());
			return "redirect:/error";
		}
	}
}
