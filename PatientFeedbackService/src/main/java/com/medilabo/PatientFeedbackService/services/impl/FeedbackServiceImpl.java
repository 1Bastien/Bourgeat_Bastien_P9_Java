package com.medilabo.PatientFeedbackService.services.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.medilabo.PatientFeedbackService.model.Feedback;
import com.medilabo.PatientFeedbackService.model.repository.FeedbackRepository;
import com.medilabo.PatientFeedbackService.services.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	private static final Logger logger = LogManager.getLogger(FeedbackServiceImpl.class);

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Override
	public List<Feedback> getFeedbackByPatientId(Long patientId) {
		List<Feedback> feedbacks = feedbackRepository.findByPatientId(patientId);

		if (feedbacks.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No feedback found for the patient.");
		}

		return feedbacks;
	}

	@Override
	public Feedback addFeedback(Feedback feedback) {
		try {
			feedbackRepository.insert(feedback);
			return feedback;

		} catch (Exception e) {
			logger.error("Error while adding feedback", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while adding feedback");
		}
	}
}
