package com.medilabo.assessmentservice.services;

import org.springframework.stereotype.Service;

@Service
public interface AssessmentService {

	public String getAssessment(Long patientId);
}
