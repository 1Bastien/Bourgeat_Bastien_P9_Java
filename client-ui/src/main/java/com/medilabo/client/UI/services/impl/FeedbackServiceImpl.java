package com.medilabo.client.UI.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.medilabo.client.UI.Dto.FeedbackDto;
import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.proxies.AssessmentProxy;
import com.medilabo.client.UI.proxies.FeedbackProxy;
import com.medilabo.client.UI.proxies.PatientProxy;
import com.medilabo.client.UI.services.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	private static final Logger logger = LogManager.getLogger(FeedbackServiceImpl.class);

	private FeedbackProxy feedbackProxy;
	private PatientProxy patientProxy;
	private AssessmentProxy assessmentProxy;

	public FeedbackServiceImpl(FeedbackProxy feedbackProxy, PatientProxy patientProxy,
			AssessmentProxy assessmentProxy) {
		this.feedbackProxy = feedbackProxy;
		this.patientProxy = patientProxy;
		this.assessmentProxy = assessmentProxy;
	}

	@Override
	public String getFeedback(Long patientId, Model model) {
		try {

			PatientDto patient = patientProxy.getPatient(patientId);
			List<FeedbackDto> feedbacks = feedbackProxy.getFeedbacks(patientId);
			String assessment = assessmentProxy.getAssessment(patientId);

			FeedbackDto feedback = new FeedbackDto();
			feedback.setPatientId(patientId);
			feedback.setPatientName(patient.getFirstName());
			feedback.setDate(LocalDateTime.now());

			Boolean alert = false;
			if (!assessment.equals("None")) {
				alert = true;
			}

			model.addAttribute("alert", alert);
			model.addAttribute("assessment", assessment);
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
	public String addFeedback(FeedbackDto feedback) {
		try {

			PatientDto patient = patientProxy.getPatient(feedback.getPatientId());

			if (patient == null) {
				logger.error("Patient with id " + feedback.getPatientId() + " not found.");
				return "redirect:/404";
			}

			feedback.setDate(LocalDateTime.now());

			feedbackProxy.addFeedback(feedback);

			return "redirect:/patient/feedback/" + feedback.getPatientId();
		} catch (Exception e) {
			logger.error("Error while trying to add feedback: " + e.getMessage());
			return "redirect:/error";
		}
	}
}
