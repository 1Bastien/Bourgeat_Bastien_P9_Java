package com.medilabo.client.UI.proxies;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import com.medilabo.client.UI.Dto.FeedbackDto;
import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.proxies.impl.FeedbackProxyImpl;

import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
public class FeedbackProxyTest {

	@InjectMocks
	private FeedbackProxyImpl feedbackProxy;
	
	@Value("${feedback.service.url}")
	private String feedbackServiceUrl;

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

		when(webClientMock.post()).thenReturn(requestBodyUriMock);
		when(requestBodyUriMock.uri(feedbackServiceUrl)).thenReturn(requestBodyMock);
		when(requestBodyMock.bodyValue(feedbackDto)).thenReturn(requestHeadersMock);
		when(requestHeadersMock.retrieve()).thenReturn(responseMock);
		when(responseMock.bodyToFlux(FeedbackDto.class)).thenReturn(Flux.just(feedbackDto));

		feedbackProxy.addFeedback(feedbackDto);

		verify(webClientMock.post().uri(feedbackServiceUrl).bodyValue(feedbackDto)
				.retrieve()).bodyToFlux(FeedbackDto.class);
	}
	
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
		when(requestHeadersUriMock.uri(feedbackServiceUrl + "/" + patientId))
				.thenReturn(requestHeadersMock);
		when(requestHeadersMock.retrieve()).thenReturn(responseMock);
		when(responseMock.bodyToFlux(FeedbackDto.class)).thenReturn(Flux.fromIterable(feedbackDtoList));

		feedbackProxy.getFeedbacks(patientId);

		verify(webClientMock.get().uri(feedbackServiceUrl + "/" + patientId).retrieve())
				.bodyToFlux(FeedbackDto.class);
	}
}