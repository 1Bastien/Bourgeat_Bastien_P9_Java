package com.medilabo.PatientFeedbackService.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import com.medilabo.PatientFeedbackService.model.Feedback;
import com.medilabo.PatientFeedbackService.model.repository.FeedbackRepository;
import com.medilabo.PatientFeedbackService.services.impl.FeedbackServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {

	@InjectMocks
	private FeedbackServiceImpl feedbackService;

	@Mock
	private FeedbackRepository feedbackRepository;

	@Mock
	private MongoTemplate mongoTemplate;

	private final static Feedback feedback = new Feedback();

	@BeforeAll
	public static void setUp() {
		feedback.setId("1");
		feedback.setPatientId(1L);
		feedback.setContent("Good");
	}

	@Test
	public void testAddFeedback() {
		when(feedbackRepository.insert(feedback)).thenReturn(feedback);

		Feedback addedFeedback = feedbackService.addFeedback(feedback);

		verify(feedbackRepository, times(1)).insert(feedback);

		assertEquals(feedback, addedFeedback);
	}

	@Test
	public void testGetFeedbackByPatientId() {
		List<Feedback> feedbackList = new ArrayList<>();
		feedbackList.add(feedback);

		when(feedbackRepository.findByPatientId(1L)).thenReturn(feedbackList);

		List<Feedback> feedbacks = feedbackService.getFeedbackByPatientId(1L);

		verify(feedbackRepository, times(1)).findByPatientId(1L);

		assertEquals(feedbackList, feedbacks);
	}

	@Test
	void testCountWordWithNoOccurrences() {
		Long patientId = 1L;
		List<String> uriKeywords = Arrays.asList("test", "keyword", "search");

		AggregationResults<Document> aggregationResults = new AggregationResults<>(Collections.emptyList(),
				new Document());

		when(mongoTemplate.aggregate(any(Aggregation.class), anyString(), eq(Document.class)))
				.thenReturn(aggregationResults);

		int occurrences = feedbackService.countWordOccurrences(patientId, uriKeywords);

		verify(mongoTemplate, times(3)).aggregate(any(Aggregation.class), anyString(), any());

		assertEquals(0, occurrences);
	}
	
	@Test
	void testCountWordWithOccurrences() {
		Long patientId = 1L;
		List<String> uriKeywords = Arrays.asList("test", "keyword", "search");

		AggregationResults<Document> aggregationResults = new AggregationResults<>(
				Arrays.asList(new Document("total", 1)), new Document());

		when(mongoTemplate.aggregate(any(Aggregation.class), anyString(), eq(Document.class)))
				.thenReturn(aggregationResults);

		int occurrences = feedbackService.countWordOccurrences(patientId, uriKeywords);

		verify(mongoTemplate, times(3)).aggregate(any(Aggregation.class), anyString(), any());

		assertEquals(3, occurrences);
	}

}
