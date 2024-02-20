package com.medilabo.assessmentservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.assessmentservice.services.AssessmentService;

@RestController
public class AssessmentController {

	private static final Logger logger = LogManager.getLogger(AssessmentController.class);

	@Autowired
	private AssessmentService assessmentService;

	@GetMapping("/assessment/{patientId}")
	public String getAssessment(@PathVariable("patientId") Long patientId) {
		logger.info("GET /assessment/" + patientId);
		return assessmentService.getAssessment(patientId);
	}
}
