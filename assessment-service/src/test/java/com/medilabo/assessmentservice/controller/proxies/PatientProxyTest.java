package com.medilabo.assessmentservice.controller.proxies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.medilabo.assessmentservice.Dto.PatientDto;
import com.medilabo.assessmentservice.proxies.impl.PatientProxyImpl;

import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
public class PatientProxyTest {

	@InjectMocks
	private PatientProxyImpl patientProxy;

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
	private WebClient.ResponseSpec responseMock;

	@SuppressWarnings("unchecked")
	@Test
	void testGetPatient() {
		Long patientId = 1L;
		PatientDto patientDto = new PatientDto();
		patientDto.setId(patientId);
		patientDto.setFirstName("John");

		when(webClientBuilder.build()).thenReturn(webClientMock);

		when(webClientMock.get()).thenReturn(requestHeadersUriMock);
		when(requestHeadersUriMock.uri("http://patient-api/patient/" + patientId)).thenReturn(requestHeadersMock);
		when(requestHeadersMock.retrieve()).thenReturn(responseMock);
		when(responseMock.bodyToFlux(PatientDto.class)).thenReturn(Flux.just(patientDto));

		PatientDto patient = patientProxy.getPatient(patientId);

		verify(webClientMock.get().uri("http://patient-api/patient/" + patientId).retrieve())
				.bodyToFlux(PatientDto.class);

		assertEquals(patientDto, patient);
	}

}