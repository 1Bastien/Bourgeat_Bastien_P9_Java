package com.medilabo.assessmentservice.services;

import org.springframework.stereotype.Service;

import com.medilabo.assessmentservice.Dto.AssessmentType;

@Service
public interface AssessmentService {

	public AssessmentType getAssessment(Long patientId);
}
