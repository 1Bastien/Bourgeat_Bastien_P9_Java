package com.medilabo.client.UI.proxies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.reactive.function.client.WebClient;

import com.medilabo.client.UI.proxies.impl.AssessmentProxyImpl;

import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
public class AssessmentProxyTest {

	@InjectMocks
	private AssessmentProxyImpl assessmentProxy;
	
	@Value("${assessment.service.url}")
	private String assessmentServiceUrl;

	@Mock
	private Model model;

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
	void testGetAssessment() {
		Long patientId = 1L;
		String assessment = "assessment";

		when(webClientBuilder.build()).thenReturn(webClientMock);

		when(webClientMock.get()).thenReturn(requestHeadersUriMock);
		when(requestHeadersUriMock.uri(assessmentServiceUrl + patientId))
				.thenReturn(requestHeadersMock);
		when(requestHeadersMock.retrieve()).thenReturn(responseMock);
		when(responseMock.bodyToFlux(String.class)).thenReturn(Flux.just(assessment));

		String result = assessmentProxy.getAssessment(patientId);

		verify(webClientMock.get().uri(assessmentServiceUrl + patientId).retrieve())
				.bodyToFlux(String.class);

		assertEquals(assessment, result);
	}
}