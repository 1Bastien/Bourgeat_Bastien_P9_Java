package com.medilabo.assessmentservice.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.medilabo.assessmentservice.services.AssessmentService;

@WebMvcTest(AssessmentController.class)
public class AssessmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AssessmentService assessmentService;

	@Test
	public void testGetAssessment() throws Exception {
		when(assessmentService.getAssessment(1L)).thenReturn(anyString());

		mockMvc.perform(get("/assessment/1")).andExpect(status().isOk());

		verify(assessmentService, times(1)).getAssessment(1L);
	}
}
