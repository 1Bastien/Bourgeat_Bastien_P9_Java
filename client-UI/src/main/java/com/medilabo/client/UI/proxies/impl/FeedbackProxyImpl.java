package com.medilabo.client.UI.proxies.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.medilabo.client.UI.Dto.FeedbackDto;
import com.medilabo.client.UI.proxies.FeedbackProxy;

@Service
public class FeedbackProxyImpl implements FeedbackProxy {

	private static final Logger logger = LogManager.getLogger(FeedbackProxyImpl.class);

	@Autowired
	private WebClient.Builder webClient;

	@Override
	public List<FeedbackDto> getFeedbacks(Long patientId) {
		try {
			List<FeedbackDto> feedbacks = webClient.build().get()
					.uri("http://gateway/feedback-service/feedback/patient/" + patientId).retrieve()
					.bodyToFlux(FeedbackDto.class).collectList().flux().blockLast();

			logger.info("Feedbacks fetched successfully");

			return feedbacks;
		} catch (Exception e) {
			logger.error("Error while trying to get feedbacks: " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching feedbacks");
		}
	}

	@Override
	public void addFeedback(FeedbackDto feedback) {
		try {
			webClient.build().post().uri("http://gateway/feedback-service/feedback/patient").bodyValue(feedback)
					.retrieve().bodyToFlux(FeedbackDto.class).blockLast();

			logger.info("Feedback added successfully");
		} catch (Exception e) {
			logger.error("Error while trying to add feedback: " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while adding feedback");
		}
	}

}
