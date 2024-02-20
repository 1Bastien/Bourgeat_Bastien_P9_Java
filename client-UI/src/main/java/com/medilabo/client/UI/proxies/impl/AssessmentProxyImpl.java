package com.medilabo.client.UI.proxies.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.medilabo.client.UI.proxies.AssessmentProxy;

@Service
public class AssessmentProxyImpl implements AssessmentProxy {

	private static final Logger logger = LogManager.getLogger(AssessmentProxyImpl.class);

	@Autowired
	private WebClient.Builder webClient;

	@Override
	public String getAssessment(Long patientId) {
		try {
			String AssessmentType = webClient.build().get()
					.uri("http://gateway/assessment-service/assessment/" + patientId).retrieve()
					.bodyToFlux(String.class).blockLast();

			logger.info("Assessment fetched successfully");
			return AssessmentType;
		} catch (Exception e) {
			logger.error("Error while trying to get assessment: " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching assessment");
		}
	}
}
