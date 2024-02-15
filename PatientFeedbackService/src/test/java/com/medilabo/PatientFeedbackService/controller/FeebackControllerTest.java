package com.medilabo.PatientFeedbackService.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.PatientFeedbackService.model.Feedback;
import com.medilabo.PatientFeedbackService.services.FeedbackService;

@WebMvcTest(FeedbackController.class)
public class FeebackControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FeedbackService feedbackService;

	private final static Feedback feedback = new Feedback();

	@BeforeAll
	public static void setUp() {
		feedback.setId("1");
		feedback.setPatientId(1L);
		feedback.setContent("Good");
	}

	@Test
	public void testAddFeedback() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		String patientJson = objectMapper.writeValueAsString(feedback);

		when(feedbackService.addFeedback(feedback)).thenReturn(feedback);

		mockMvc.perform(post("/feedback/patient").content(patientJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString().equals(objectMapper.writeValueAsString(feedback));

		verify(feedbackService, times(1)).addFeedback(any());
	}

	@Test
	public void testGetPatientFeedback() throws Exception {
		List<Feedback> feedbackList = new ArrayList<>();
		feedbackList.add(feedback);

		when(feedbackService.getFeedbackByPatientId(1L)).thenReturn(feedbackList);

		mockMvc.perform(get("/feedback/patient/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString().equals(new ObjectMapper().writeValueAsString(feedbackList));
		
		verify(feedbackService, times(1)).getFeedbackByPatientId(1L);
	}
}
