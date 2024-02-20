package com.medilabo.PatientFeedbackService.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.PatientFeedbackService.model.Feedback;
import com.medilabo.PatientFeedbackService.services.FeedbackService;

@RestController
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	Logger logger = LogManager.getLogger(FeedbackController.class);

	@GetMapping("/feedback/patient/{id}")
	public List<Feedback> getPatientFeedback(@PathVariable("id") Long patientId) {
		logger.info("GET /feedback/patient/" + patientId);
		return feedbackService.getFeedbackByPatientId(patientId);
	}

	@PostMapping("/feedback/patient")
	public Feedback addPatientFeedback(@RequestBody Feedback feedback) {
		logger.info("POST /feedback/patient with body: " + feedback.toString());
		return feedbackService.addFeedback(feedback);
	}
	
	@GetMapping("/feedback/patient/assessment/{id}")
	public int countWordOccurrences(@PathVariable("id") Long patientId, @RequestParam("keywords") List<String> keywords) {
		logger.info("GET /feedback/patient/assessment/" + patientId + "with request param" + keywords);
		return feedbackService.countWordOccurrences(patientId, keywords);
	}

}
