package com.medilabo.PatientFeedbackService.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.medilabo.PatientFeedbackService.model.Feedback;
import com.medilabo.PatientFeedbackService.model.repository.FeedbackRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FeedbackControllerIT {

	@Container
	private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.2.3");

	private static MongoClient mongoClient;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private FeedbackRepository feedbackRepository;

	@BeforeAll
	static void beforeAll() {
		mongoDBContainer.start();
		String connectionString = mongoDBContainer.getReplicaSetUrl();
		mongoClient = MongoClients.create(connectionString);
	}

	@AfterAll
	static void afterAll() {
		mongoClient.close();
	}

	@BeforeEach
	public void setUp() {
		feedbackRepository.deleteAll();
	}

	@AfterEach
	public void tearDown() {
		feedbackRepository.deleteAll();
	}

	@Test
	public void testAddFeedback() throws Exception {
		mockMvc.perform(post("/feedback/patient").contentType(MediaType.APPLICATION_JSON).content(
				"{\"patientId\": 1,\"patientName\": \"TestIT\",\"date\": \"2024-02-14T12:00\",\"content\": \"Feedback test\"}"))
				.andExpect(status().isOk());

		List<Feedback> feedbacks = feedbackRepository.findAll();

		assertEquals(1, feedbacks.size());
		assertEquals(1, feedbacks.get(0).getPatientId());
		assertEquals("Feedback test", feedbacks.get(0).getContent());
		assertEquals("TestIT", feedbacks.get(0).getPatientName());
	}

	@Test
	public void testGetPatientFeedback() throws Exception {
		Feedback feedback = new Feedback();
		feedback.setPatientId(1L);
		feedback.setDate(LocalDateTime.parse("2024-02-14T12:00"));
		feedback.setPatientName("TestIT");
		feedback.setContent("Feedback test");
		feedbackRepository.insert(feedback);

		mockMvc.perform(get("/feedback/patient/1")).andExpect(status().isOk()).andExpect(content().json(
				"[{\"patientId\":1,\"patientName\":\"TestIT\",\"date\":\"2024-02-14T12:00:00\",\"content\":\"Feedback test\"}]"));
	}
}
