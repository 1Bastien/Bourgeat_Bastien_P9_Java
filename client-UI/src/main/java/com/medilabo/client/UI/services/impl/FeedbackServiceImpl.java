package com.medilabo.client.UI.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.reactive.function.client.WebClient;

import com.medilabo.client.UI.Dto.FeedbackDto;
import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.services.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	private static final Logger logger = LogManager.getLogger(FeedbackServiceImpl.class);

	@Autowired
	private WebClient.Builder webClient;

	@Override
	public String getFeedback(Long patientId, Model model) {
		try {

			PatientDto patient = webClient.build().get().uri("http://gateway/patient-api/patient/" + patientId)
					.retrieve().bodyToFlux(PatientDto.class).blockLast();

			List<FeedbackDto> feedbacks = webClient.build().get()
					.uri("http://gateway/feedback-service/feedback/patient/" + patientId).retrieve()
					.bodyToFlux(FeedbackDto.class).collectList().flux().blockLast();

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
	public String addFeedback(FeedbackDto feedback) {
		try {

			PatientDto patient = webClient.build().get()
					.uri("http://gateway/patient-api/patient/" + feedback.getPatientId()).retrieve()
					.bodyToFlux(PatientDto.class).blockLast();

			if (patient == null) {
				logger.error("Patient with id " + feedback.getPatientId() + " not found.");
				return "redirect:/404";
			}

			feedback.setDate(LocalDateTime.now());

			webClient.build().post().uri("http://gateway/feedback-service/feedback/patient").bodyValue(feedback)
					.retrieve().bodyToFlux(FeedbackDto.class).blockLast();

			return "redirect:/patient/feedback/" + feedback.getPatientId();
		} catch (Exception e) {
			logger.error("Error while trying to add feedback: " + e.getMessage());
			return "redirect:/error";
		}
	}
}
