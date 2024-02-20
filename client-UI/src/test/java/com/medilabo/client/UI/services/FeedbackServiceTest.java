
package com.medilabo.client.UI.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.medilabo.client.UI.Dto.FeedbackDto;
import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.proxies.FeedbackProxy;
import com.medilabo.client.UI.proxies.PatientProxy;
import com.medilabo.client.UI.services.impl.FeedbackServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {

	@InjectMocks
	private FeedbackServiceImpl feedbackService;

	@Mock
	private FeedbackProxy feedbackProxy;
	
	@Mock
	private PatientProxy patientProxy;

	@Test
	void getFeedback() {
		Long patientId = 1L;
		PatientDto patientDto = new PatientDto();
		patientDto.setId(patientId);
		patientDto.setFirstName("John");
		patientDto.setLastName("Doe");

		List<FeedbackDto> feedbackDtoList = new ArrayList<>();
		feedbackDtoList.add(new FeedbackDto());

		when(patientProxy.getPatient(patientId)).thenReturn(patientDto);
		when(feedbackProxy.getFeedbacks(patientId)).thenReturn(feedbackDtoList);

		String result = feedbackService.getFeedback(patientId, mock(Model.class));

		verify(feedbackProxy).getFeedbacks(patientId);

		assertEquals("patient/feedback", result);
	}

	@Test
	void addFeedback() {
		Long patientId = 1L;
		FeedbackDto feedbackDto = new FeedbackDto();
		feedbackDto.setPatientId(patientId);
		feedbackDto.setPatientName("John Doe");
		feedbackDto.setDate(LocalDateTime.now());
		feedbackDto.setContent("Good service");

		PatientDto patientDto = new PatientDto();
		patientDto.setId(patientId);
		patientDto.setFirstName("John");
		patientDto.setLastName("Doe");
		
		when(patientProxy.getPatient(patientId)).thenReturn(patientDto);

		String result = feedbackService.addFeedback(feedbackDto);
		
		verify(feedbackProxy).addFeedback(feedbackDto);
		verify(patientProxy).getPatient(patientId);

		assertEquals("redirect:/patient/feedback/" + patientId, result);
	}

	@Test
	void addFeedback_PatientNotFound() {
		Long patientId = 1L;
		FeedbackDto feedbackDto = new FeedbackDto();
		feedbackDto.setPatientId(patientId);
		feedbackDto.setPatientName("John Doe");
		feedbackDto.setDate(LocalDateTime.now());
		feedbackDto.setContent("Good service");

		when(patientProxy.getPatient(patientId)).thenReturn(null);

		String result = feedbackService.addFeedback(feedbackDto);

		verify(feedbackProxy, never()).addFeedback(feedbackDto);
		verify(patientProxy).getPatient(patientId);
		
		assertEquals("redirect:/404", result);
	}

}
