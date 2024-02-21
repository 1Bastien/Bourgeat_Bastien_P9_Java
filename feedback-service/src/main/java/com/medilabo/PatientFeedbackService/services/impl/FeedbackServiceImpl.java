package com.medilabo.PatientFeedbackService.services.impl;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
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

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Feedback> getFeedbackByPatientId(Long patientId) {
		try {
			List<Feedback> feedbacks = feedbackRepository.findByPatientId(patientId);
			return feedbacks;

		} catch (Exception e) {
			logger.error("Error while getting feedbacks", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while getting feedbacks");
		}
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

	@Override
	public int countWordOccurrences(Long patientId, List<String> uriKeywords) {
		try {
			List<String> keywords = uriKeywords.stream()
					.map(keyword -> URLDecoder.decode(keyword, StandardCharsets.UTF_8)).collect(Collectors.toList());

			int totalOccurrences = 0;

			for (String keyword : keywords) {
				Aggregation aggregation = Aggregation.newAggregation(
						Aggregation.match(Criteria.where("patientId").is(patientId).and("content").regex(keyword, "i")),
						Aggregation.count().as("total"));

				AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "feedbacks",
						Document.class);

				if (!results.getMappedResults().isEmpty()) {
					Document resultDocument = results.getMappedResults().get(0);
					totalOccurrences += resultDocument.getInteger("total");
				}
			}

			return totalOccurrences;

		} catch (Exception e) {
			logger.error("Error while counting word occurrences", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error while counting word occurrences");
		}
	}

}
