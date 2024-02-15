package com.medilabo.client.UI.services;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.medilabo.client.UI.Dto.FeedbackDto;
import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.proxies.GatewayProxy;
import com.medilabo.client.UI.services.impl.FeedbackServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {

	@InjectMocks
	private FeedbackServiceImpl feedbackService;

	@Mock
	private GatewayProxy gatewayProxy;

	@Mock
	private CookieService cookieService;

	@Mock
	private Model model;

	@Mock
	private HttpServletRequest request;

	@Test
	public void testGetFeedback() {
		String authorizationHeader = "mockedAuthorizationHeader";

		when(cookieService.getCookie(request)).thenReturn(authorizationHeader);
		when(gatewayProxy.getPatient(1L, authorizationHeader)).thenReturn(new PatientDto());
		when(gatewayProxy.getPatientFeedback(1L, authorizationHeader)).thenReturn(new ArrayList<FeedbackDto>());

		String result = feedbackService.getFeedback(1L, model, request);

		verify(cookieService).getCookie(request);
		verify(gatewayProxy).getPatient(1L, authorizationHeader);
		verify(gatewayProxy).getPatientFeedback(1L, authorizationHeader);

		assertEquals("patient/feedback", result);
	}

	@Test
	public void testGetFeedbackWhenUnauthorized() {
		when(cookieService.getCookie(request)).thenReturn(null);

		String result = feedbackService.getFeedback(1L, model, request);

		verify(cookieService).getCookie(request);
		verify(gatewayProxy, never()).getPatient(1L, null);
		verify(gatewayProxy, never()).getPatientFeedback(1L, null);

		assertEquals("redirect:/login", result);
	}

	@Test
	public void testAddFeedback() {
		String authorizationHeader = "mockedAuthorizationHeader";

		PatientDto patient = new PatientDto();
		patient.setId(1L);

		FeedbackDto feedback = new FeedbackDto();
		feedback.setPatientId(patient.getId());

		when(cookieService.getCookie(request)).thenReturn(authorizationHeader);
		when(gatewayProxy.getPatient(patient.getId(), authorizationHeader)).thenReturn(patient);
		when(gatewayProxy.addPatientFeedback(feedback, authorizationHeader)).thenReturn(feedback);

		String result = feedbackService.addFeedback(feedback, request);

		verify(cookieService).getCookie(request);
		verify(gatewayProxy).getPatient(1L, authorizationHeader);
		verify(gatewayProxy).addPatientFeedback(feedback, authorizationHeader);

		assertEquals("redirect:/patient/feedback/1", result);
	}

	@Test
	public void testAddFeedbackWithoutAutorization() {
		FeedbackDto feedback = new FeedbackDto();

		when(cookieService.getCookie(request)).thenReturn(null);

		String result = feedbackService.addFeedback(feedback, request);

		verify(cookieService).getCookie(request);
		verify(gatewayProxy, never()).getPatient(any(), any());
		verify(gatewayProxy, never()).addPatientFeedback(any(), any());

		assertEquals("redirect:/login", result);
	}

	@Test
	public void testAddFeedbackWithPatientNotFound() {
		String authorizationHeader = "mockedAuthorizationHeader";

		PatientDto patient = new PatientDto();
		patient.setId(1L);

		FeedbackDto feedback = new FeedbackDto();
		feedback.setPatientId(patient.getId());

		when(cookieService.getCookie(request)).thenReturn(authorizationHeader);
		when(gatewayProxy.getPatient(patient.getId(), authorizationHeader)).thenReturn(null);

		String result = feedbackService.addFeedback(feedback, request);

		verify(cookieService).getCookie(request);
		verify(gatewayProxy).getPatient(1L, authorizationHeader);
		verify(gatewayProxy, never()).addPatientFeedback(any(), any());

		assertEquals("redirect:/404", result);
	}
}
