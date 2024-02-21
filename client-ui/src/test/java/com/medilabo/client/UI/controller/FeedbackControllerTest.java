package com.medilabo.client.UI.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import com.medilabo.client.UI.Dto.FeedbackDto;
import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.services.FeedbackService;

@WebMvcTest(FeedbackController.class)
@AutoConfigureMockMvc(addFilters = false)
public class FeedbackControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FeedbackService feedbackService;

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void testGetFeedback() throws Exception {
		PatientDto patient = new PatientDto();
		FeedbackDto feedback = new FeedbackDto();

		when(feedbackService.getFeedback(any(Long.class), any(Model.class))).thenReturn("patient/feedback");

		mockMvc.perform(get("/patient/feedback/1").flashAttr("patient", patient).flashAttr("feedback", feedback))
				.andExpect(status().isOk()).andExpect(view().name("patient/feedback"));

		verify(feedbackService, times(1)).getFeedback(any(Long.class), any(Model.class));
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void testAddFeedbackWithError() throws Exception {
		FeedbackDto feedback = new FeedbackDto();
		feedback.setPatientId(1L);

		when(feedbackService.addFeedback(any(FeedbackDto.class))).thenReturn("redirect:/patient/feedback/1");

		mockMvc.perform(post("/patient/feedback/add").flashAttr("feedback", feedback))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/patient/feedback/1"));

		verify(feedbackService, never()).addFeedback(any(FeedbackDto.class));
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void testAddFeedback() throws Exception {
		FeedbackDto feedback = new FeedbackDto();
		feedback.setPatientId(1L);
		feedback.setPatientName("patient");
		feedback.setDate(LocalDateTime.parse("2021-01-01T00:00:00"));
		feedback.setContent("comment");

		when(feedbackService.addFeedback(any(FeedbackDto.class))).thenReturn("redirect:/patient/feedback/1");

		mockMvc.perform(post("/patient/feedback/add").flashAttr("feedback", feedback))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/patient/feedback/1"));

		verify(feedbackService, times(1)).addFeedback(any(FeedbackDto.class));
	}
}
