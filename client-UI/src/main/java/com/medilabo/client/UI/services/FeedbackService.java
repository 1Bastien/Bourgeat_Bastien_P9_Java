package com.medilabo.client.UI.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.medilabo.client.UI.Dto.FeedbackDto;

@Service
public interface FeedbackService {

	String getFeedback(Long patientId, Model model);

	String addFeedback(FeedbackDto feedback);
}
