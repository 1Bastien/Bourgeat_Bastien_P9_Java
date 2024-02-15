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

import com.medilabo.client.UI.Dto.FeedbackDto;
import com.medilabo.client.UI.services.FeedbackService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class FeedbackController {

	private static final Logger logger = LogManager.getLogger(FeedbackController.class);

	@Autowired
	private FeedbackService feedbackService;

	@GetMapping("/patient/feedback/{patientId}")
	public String getFeedback(@PathVariable("patientId") Long patientId, HttpServletRequest request, Model model) {
		logger.info("GET /patient/update/feedback/" + patientId);
		return feedbackService.getFeedback(patientId, model, request);
	}

	@PostMapping("/patient/feedback/add")
	public String addFeedback(@Valid @ModelAttribute("feedback") FeedbackDto feedback, BindingResult result,
			HttpServletRequest request, Model model) {

		logger.info("POST /patient/feedback/add/, with body: " + feedback.toString());

		if (result.hasErrors()) {
			logger.error("Error in form: " + result.getAllErrors().toString());
			logger.error("redirecting to /patient/feedback/" + feedback.getPatientId());
			return "redirect:/patient/feedback/" + feedback.getPatientId();
		}
		return feedbackService.addFeedback(feedback, request);
	}
}
