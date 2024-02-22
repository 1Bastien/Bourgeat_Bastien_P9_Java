package com.medilabo.assessmentservice.proxies.impl;

import java.net.URI;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.medilabo.assessmentservice.proxies.FeedbackProxy;

@Service
public class FeedbackProxyImpl implements FeedbackProxy {

	private static final Logger logger = LogManager.getLogger(FeedbackProxyImpl.class);

	@Autowired
	private WebClient.Builder webClient;
	
	@Value("${feedback.service.url}")
	private String feedbackServiceUrl;
	
	@Override
	public int countWordOccurrences(Long patientId, List<String> keywords) {
		try {
			URI uri = UriComponentsBuilder.fromUriString(feedbackServiceUrl + "{patientId}")
					.queryParam("keywords", keywords).buildAndExpand(patientId).toUri();
			
			int count = webClient.build().get().uri(uri).retrieve().bodyToMono(Integer.class).block();

			logger.info("Word occurrences fetched successfully");

			return count;
		} catch (Exception e) {
			logger.error("Error while trying to get word occurrences: " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error while fetching word occurrences");
		}
	}
}
