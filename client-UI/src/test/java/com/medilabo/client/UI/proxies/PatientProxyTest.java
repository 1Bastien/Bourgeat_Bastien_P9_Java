package com.medilabo.client.UI.proxies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.reactive.function.client.WebClient;

import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.proxies.impl.PatientProxyImpl;

import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
public class PatientProxyTest {

	@InjectMocks
	private PatientProxyImpl patientProxy;

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
	void testGetPatientsList() {
		PatientDto patient = new PatientDto();
		patient.setId(1L);
		patient.setFirstName("John");

		List<PatientDto> patientsList = new ArrayList<>();
		patientsList.add(patient);

		when(webClientBuilder.build()).thenReturn(webClientMock);

		when(webClientMock.get()).thenReturn(requestHeadersUriMock);
		when(requestHeadersUriMock.uri("http://gateway/patient-api/patient/all")).thenReturn(requestHeadersMock);
		when(requestHeadersMock.retrieve()).thenReturn(responseMock);
		when(responseMock.bodyToFlux(PatientDto.class)).thenReturn(Flux.fromIterable(patientsList));

		List<PatientDto> patients = patientProxy.getPatientsList();

		verify(webClientMock.get().uri("http://gateway/patient-api/patient/all").retrieve())
				.bodyToFlux(PatientDto.class);

		assertEquals(patientsList, patients);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testGetPatient() {
		Long patientId = 1L;
		PatientDto patientDto = new PatientDto();
		patientDto.setId(patientId);
		patientDto.setFirstName("John");

		when(webClientBuilder.build()).thenReturn(webClientMock);

		when(webClientMock.get()).thenReturn(requestHeadersUriMock);
		when(requestHeadersUriMock.uri("http://gateway/patient-api/patient/" + patientId))
				.thenReturn(requestHeadersMock);
		when(requestHeadersMock.retrieve()).thenReturn(responseMock);
		when(responseMock.bodyToFlux(PatientDto.class)).thenReturn(Flux.just(patientDto));

		PatientDto patient = patientProxy.getPatient(patientId);

		verify(webClientMock.get().uri("http://gateway/patient-api/patient/" + patientId).retrieve())
				.bodyToFlux(PatientDto.class);

		assertEquals(patientDto, patient);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testUpdatePatient() {
		Long patientId = 1L;
		PatientDto newPatient = new PatientDto();
		newPatient.setId(patientId);
		newPatient.setFirstName("John");
		newPatient.setLastName("Doe");
		newPatient.setAddress("123 Main St");
		newPatient.setDateOfBirth(LocalDate.parse("1990-01-01"));
		newPatient.setPhoneNumber("123-456-7890");
		newPatient.setGender("M");

		when(webClientBuilder.build()).thenReturn(webClientMock);

		when(webClientMock.post()).thenReturn(requestBodyUriMock);
		when(requestBodyUriMock.uri("http://gateway/patient-api/patient/update/" + patientId))
				.thenReturn(requestBodyMock);
		when(requestBodyMock.bodyValue(newPatient)).thenReturn(requestHeadersMock);
		when(requestHeadersMock.retrieve()).thenReturn(responseMock);
		when(responseMock.bodyToFlux(PatientDto.class)).thenReturn(Flux.just(newPatient));

		patientProxy.updatePatient(patientId, newPatient);

		verify(webClientMock.post().uri("http://gateway/patient-api/patient/update/" + patientId).bodyValue(newPatient)
				.retrieve()).bodyToFlux(PatientDto.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testAddPatient() {
		PatientDto newPatient = new PatientDto();
		newPatient.setId(1L);
		newPatient.setFirstName("John");
		newPatient.setLastName("Doe");
		newPatient.setAddress("123 Main St");
		newPatient.setDateOfBirth(LocalDate.parse("1990-01-01"));
		newPatient.setPhoneNumber("123-456-7890");
		newPatient.setGender("M");

		when(webClientBuilder.build()).thenReturn(webClientMock);

		when(webClientMock.post()).thenReturn(requestBodyUriMock);
		when(requestBodyUriMock.uri("http://gateway/patient-api/patient")).thenReturn(requestBodyMock);
		when(requestBodyMock.bodyValue(newPatient)).thenReturn(requestHeadersMock);
		when(requestHeadersMock.retrieve()).thenReturn(responseMock);
		when(responseMock.bodyToFlux(PatientDto.class)).thenReturn(Flux.just(newPatient));

		patientProxy.addPatient(newPatient);

		verify(webClientMock.post().uri("http://gateway/patient-api/patient").bodyValue(newPatient).retrieve())
				.bodyToFlux(PatientDto.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testDeletePatient() {
		Long patientId = 1L;

		when(webClientBuilder.build()).thenReturn(webClientMock);
		when(webClientMock.get()).thenReturn(requestHeadersUriMock);
		when(requestHeadersUriMock.uri("http://gateway/patient-api/patient/delete/" + patientId))
				.thenReturn(requestHeadersMock);
		when(requestHeadersMock.retrieve()).thenReturn(responseMock);
		when(responseMock.bodyToFlux(Void.class)).thenReturn(Flux.empty());

		patientProxy.deletePatient(patientId);

		verify(webClientMock.get().uri("http://gateway/patient-api/patient/delete/" + patientId).retrieve())
				.bodyToFlux(Void.class);
	}

}