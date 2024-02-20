package com.medilabo.assessmentservice.controller.proxies;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.medilabo.assessmentservice.proxies.impl.FeedbackProxyImpl;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class FeedbackProxyTest {

	@InjectMocks
	private FeedbackProxyImpl feedbackProxy;

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
	void testCountWordOccurrences() {
		Long patientId = 1L;
		List<String> keywords = Arrays.asList("good", "bad");
		int count = 2;

		when(webClientBuilder.build()).thenReturn(webClientMock);
		when(webClientMock.get()).thenReturn(requestHeadersUriMock);
		when(requestHeadersUriMock.uri(any(URI.class))).thenReturn(requestHeadersMock);
		when(requestHeadersMock.retrieve()).thenReturn(responseMock);
		when(responseMock.bodyToMono(Integer.class)).thenReturn(Mono.just(count));

		int result = feedbackProxy.countWordOccurrences(patientId, keywords);

		assertEquals(count, result);
	}

}