
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
import org.springframework.web.reactive.function.client.WebClient;

import com.medilabo.client.UI.Dto.FeedbackDto;
import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.services.impl.FeedbackServiceImpl;

import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {

	@InjectMocks
	private FeedbackServiceImpl feedbackService;

	@Mock
	private WebClient.Builder webClientBuilder;

	@Mock
	private WebClient webClientMock;

	@SuppressWarnings("rawtypes")
	@Mock
	private WebClient.RequestHeadersSpec requestHeadersMock;

	@SuppressWarnings("rawtypes")
	@Mock
	private WebClient.RequestHeadersUriSpec requestHeadersUriMock;

	@Mock
	private WebClient.RequestBodySpec requestBodyMock;

	@Mock
	private WebClient.RequestBodyUriSpec requestBodyUriMock;

	@Mock
	private WebClient.ResponseSpec responseMock;

	@SuppressWarnings("unchecked")
	@Test
	void getFeedback() {
		Long patientId = 1L;
		PatientDto patientDto = new PatientDto();
		patientDto.setId(patientId);
		patientDto.setFirstName("John");
		patientDto.setLastName("Doe");

		List<FeedbackDto> feedbackDtoList = new ArrayList<>();
		feedbackDtoList.add(new FeedbackDto());

		when(webClientBuilder.build()).thenReturn(webClientMock);

		when(webClientMock.get()).thenReturn(requestHeadersUriMock);
		when(requestHeadersUriMock.uri("http://gateway/patient-api/patient/" + patientId))
				.thenReturn(requestHeadersMock);
		when(requestHeadersMock.retrieve()).thenReturn(responseMock);
		when(responseMock.bodyToFlux(PatientDto.class)).thenReturn(Flux.just(patientDto));

		when(webClientMock.get()).thenReturn(requestHeadersUriMock);
		when(requestHeadersUriMock.uri("http://gateway/feedback-service/feedback/patient/" + patientId))
				.thenReturn(requestHeadersMock);
		when(requestHeadersMock.retrieve()).thenReturn(responseMock);
		when(responseMock.bodyToFlux(FeedbackDto.class)).thenReturn(Flux.fromIterable(feedbackDtoList));

		String result = feedbackService.getFeedback(patientId, mock(Model.class));

		verify(webClientMock.get().uri("http://gateway/patient-api/patient/" + patientId).retrieve())
				.bodyToFlux(PatientDto.class);
		verify(webClientMock.get().uri("http://gateway/feedback-service/feedback/patient/" + patientId).retrieve())
				.bodyToFlux(FeedbackDto.class);

		assertEquals("patient/feedback", result);
	}

	@SuppressWarnings("unchecked")
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

		when(webClientBuilder.build()).thenReturn(webClientMock);

		when(webClientMock.get()).thenReturn(requestHeadersUriMock);
		when(requestHeadersUriMock.uri("http://gateway/patient-api/patient/" + patientId))
				.thenReturn(requestHeadersMock);
		when(requestHeadersMock.retrieve()).thenReturn(responseMock);
		when(responseMock.bodyToFlux(PatientDto.class)).thenReturn(Flux.just(patientDto));

		when(webClientMock.post()).thenReturn(requestBodyUriMock);
		when(requestBodyUriMock.uri("http://gateway/feedback-service/feedback/patient")).thenReturn(requestBodyMock);
		when(requestBodyMock.bodyValue(feedbackDto)).thenReturn(requestHeadersMock);
		when(responseMock.bodyToFlux(FeedbackDto.class)).thenReturn(Flux.just(feedbackDto));

		String result = feedbackService.addFeedback(feedbackDto);

		verify(webClientMock.get().uri("http://gateway/patient-api/patient/" + patientId).retrieve())
				.bodyToFlux(PatientDto.class);
		verify(webClientMock.post().uri("http://gateway/feedback-service/feedback/patient").bodyValue(feedbackDto)
				.retrieve()).bodyToFlux(FeedbackDto.class);

		assertEquals("redirect:/patient/feedback/" + patientId, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	void addFeedback_PatientNotFound() {
		Long patientId = 1L;
		FeedbackDto feedbackDto = new FeedbackDto();
		feedbackDto.setPatientId(patientId);
		feedbackDto.setPatientName("John Doe");
		feedbackDto.setDate(LocalDateTime.now());
		feedbackDto.setContent("Good service");

		when(webClientBuilder.build()).thenReturn(webClientMock);

		when(webClientMock.get()).thenReturn(requestHeadersUriMock);
		when(requestHeadersUriMock.uri("http://gateway/patient-api/patient/" + patientId))
				.thenReturn(requestHeadersMock);
		when(requestHeadersMock.retrieve()).thenReturn(responseMock);
		when(responseMock.bodyToFlux(PatientDto.class)).thenReturn(Flux.empty());

		String result = feedbackService.addFeedback(feedbackDto);

		assertEquals("redirect:/404", result);
	}

}
