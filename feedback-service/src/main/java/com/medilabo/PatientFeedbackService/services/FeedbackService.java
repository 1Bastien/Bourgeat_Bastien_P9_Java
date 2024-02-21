package com.medilabo.PatientFeedbackService.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medilabo.PatientFeedbackService.model.Feedback;

@Service
public interface FeedbackService {

	List<Feedback> getFeedbackByPatientId(Long patientId);

	Feedback addFeedback(Feedback feedback);
	
	int countWordOccurrences(Long patientId, List<String> uriKeywords);
}
