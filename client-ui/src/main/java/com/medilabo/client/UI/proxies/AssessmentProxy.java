package com.medilabo.client.UI.proxies;

import org.springframework.stereotype.Service;

@Service
public interface AssessmentProxy {

	String getAssessment(Long patientId);
}
