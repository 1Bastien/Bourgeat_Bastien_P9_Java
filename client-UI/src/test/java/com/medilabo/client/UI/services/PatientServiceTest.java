package com.medilabo.client.UI.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.proxies.GatewayProxy;
import com.medilabo.client.UI.services.impl.PatientServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

	@InjectMocks
	private PatientServiceImpl patientService;

	@Mock
	private GatewayProxy gatewayProxy;

	@Mock
	private CookieService cookieService;

	@Mock
	private Model model;

	@Mock
	private HttpServletRequest request;

	@Test
	public void testGetPatients() {
		String authorizationHeader = "mockedAuthorizationHeader";

		PatientDto patient = new PatientDto();
		patient.setId(1L);
		patient.setFirstName("John");
		patient.setLastName("Doe");

		List<PatientDto> mockedPatients = new ArrayList<>();
		mockedPatients.add(patient);

		when(cookieService.getCookie(request)).thenReturn(authorizationHeader);
		when(gatewayProxy.getPatients(authorizationHeader)).thenReturn(mockedPatients);

		String result = patientService.getPatientsList(model, request);

		verify(cookieService).getCookie(request);
		verify(gatewayProxy).getPatients(authorizationHeader);
		verify(model).addAttribute("patients", mockedPatients);

		assertEquals("patient/list", result);
	}

	@Test
	public void testGetPatientsWithNoAuthorizationHeader() {
		when(cookieService.getCookie(request)).thenReturn(null);

		String result = patientService.getPatientsList(model, request);

		verify(cookieService).getCookie(request);
		verify(gatewayProxy, never()).getPatients(anyString());
		verify(model, never()).addAttribute(anyString(), any());

		assertEquals("redirect:/login", result);
	}

	@Test
	public void testGetPatient() {
		String authorizationHeader = "mockedAuthorizationHeader";

		PatientDto patient = new PatientDto();
		patient.setId(1L);

		when(cookieService.getCookie(request)).thenReturn(authorizationHeader);
		when(gatewayProxy.getPatient(patient.getId(), authorizationHeader)).thenReturn(patient);

		String result = patientService.getPatient(patient.getId(), model, request);

		verify(cookieService).getCookie(request);
		verify(gatewayProxy).getPatient(1L, authorizationHeader);
		verify(model).addAttribute("patient", patient);

		assertEquals("patient/update", result);
	}

	@Test
	public void testGetPatientWithNoAuthorizationHeader() {
		when(cookieService.getCookie(request)).thenReturn(null);

		String result = patientService.getPatient(1L, model, request);

		verify(cookieService).getCookie(request);
		verify(gatewayProxy, never()).getPatient(anyLong(), anyString());
		verify(model, never()).addAttribute(anyString(), any());

		assertEquals("redirect:/login", result);
	}

	@Test
	public void testUpdatePatient() {
		String authorizationHeader = "mockedAuthorizationHeader";

		PatientDto patient = new PatientDto();
		patient.setId(1L);

		PatientDto newPatient = new PatientDto();
		newPatient.setId(1L);

		when(cookieService.getCookie(request)).thenReturn(authorizationHeader);
		when(gatewayProxy.updatePatient(patient.getId(), newPatient, authorizationHeader)).thenReturn(patient);

		String result = patientService.updatePatient(patient.getId(), newPatient, model, request);

		verify(cookieService).getCookie(request);
		verify(gatewayProxy).updatePatient(patient.getId(), newPatient, authorizationHeader);

		assertEquals("redirect:/patient/list", result);
	}

	@Test
	public void testUpdatePatientWithNoAuthorizationHeader() {
		when(cookieService.getCookie(request)).thenReturn(null);

		String result = patientService.updatePatient(1L, new PatientDto(), model, request);

		verify(cookieService).getCookie(request);
		verify(gatewayProxy, never()).updatePatient(anyLong(), any(), anyString());

		assertEquals("redirect:/login", result);
	}

	@Test
	public void testGetFormAddPatient() {
		String authorizationHeader = "mockedAuthorizationHeader";

		when(cookieService.getCookie(request)).thenReturn(authorizationHeader);

		String result = patientService.getFormAddPatient(model, request);

		verify(cookieService).getCookie(request);
		verify(model).addAttribute(anyString(), any(PatientDto.class));

		assertEquals("patient/add", result);
	}

	@Test
	public void testGetFormAddPatientWithNoAuthorizationHeader() {
		when(cookieService.getCookie(request)).thenReturn(null);

		String result = patientService.getFormAddPatient(model, request);

		verify(cookieService).getCookie(request);
		verify(model, never()).addAttribute(anyString(), any());

		assertEquals("redirect:/login", result);
	}

	@Test
	public void testAddPatient() {
		String authorizationHeader = "mockedAuthorizationHeader";

		PatientDto patient = new PatientDto();
		patient.setId(1L);

		when(cookieService.getCookie(request)).thenReturn(authorizationHeader);
		when(gatewayProxy.addPatient(patient, authorizationHeader)).thenReturn(patient);

		String result = patientService.addPatient(patient, model, request);

		verify(cookieService).getCookie(request);
		verify(gatewayProxy).addPatient(patient, authorizationHeader);

		assertEquals("redirect:/patient/list", result);
	}

	@Test
	public void testAddPatientWithNoAuthorizationHeader() {
		when(cookieService.getCookie(request)).thenReturn(null);

		String result = patientService.addPatient(new PatientDto(), model, request);

		verify(cookieService).getCookie(request);
		verify(gatewayProxy, never()).addPatient(any(), anyString());

		assertEquals("redirect:/login", result);
	}

	@Test
	public void testDeletePatient() {
		String authorizationHeader = "mockedAuthorizationHeader";

		Long id = 1L;

		when(cookieService.getCookie(request)).thenReturn(authorizationHeader);

		String result = patientService.deletePatient(id, model, request);

		verify(cookieService).getCookie(request);
		verify(gatewayProxy).deletePatient(id, authorizationHeader);

		assertEquals("redirect:/patient/list", result);
	}

	@Test
	public void testDeletePatientWithNoAuthorizationHeader() {
		when(cookieService.getCookie(request)).thenReturn(null);

		String result = patientService.deletePatient(1L, model, request);

		verify(cookieService).getCookie(request);
		verify(gatewayProxy, never()).deletePatient(anyLong(), anyString());

		assertEquals("redirect:/login", result);
	}
}
