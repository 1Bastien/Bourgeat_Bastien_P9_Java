package com.medilabo.client.UI.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.medilabo.client.UI.Dto.FeedbackDto;

import jakarta.servlet.http.HttpServletRequest;

@Service
public interface FeedbackService {

	String getFeedback(Long patientId, Model model, HttpServletRequest request);

	String addFeedback(FeedbackDto feedback, HttpServletRequest request);
}
