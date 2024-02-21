package com.medilabo.client.UI.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;

import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.proxies.PatientProxy;
import com.medilabo.client.UI.services.impl.PatientServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

	@InjectMocks
	private PatientServiceImpl patientService;

	@Mock
	private Model model;

	@Mock
	private PatientProxy patientProxy;

	@Test
	void testGetPatientsList() {
		PatientDto patient = new PatientDto();
		patient.setId(1L);
		patient.setFirstName("John");

		List<PatientDto> patientsList = new ArrayList<>();
		patientsList.add(patient);

		when(patientProxy.getPatientsList()).thenReturn(patientsList);

		String result = patientService.getPatientsList(model);

		verify(patientProxy).getPatientsList();
		verify(model).addAttribute("patients", patientsList);

		assertEquals("patient/list", result);
	}

	@Test
	void testGetPatient() {
		Long patientId = 1L;
		PatientDto patientDto = new PatientDto();
		patientDto.setId(patientId);
		patientDto.setFirstName("John");

		when(patientProxy.getPatient(patientId)).thenReturn(patientDto);

		String result = patientService.getPatient(patientId, model);

		verify(patientProxy).getPatient(patientId);
		verify(model).addAttribute("patient", patientDto);

		assertEquals("patient/update", result);
	}

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

		String result = patientService.updatePatient(patientId, newPatient);

		verify(patientProxy).updatePatient(patientId, newPatient);

		assertEquals("redirect:/patient/list", result);
	}

	@Test
	void testGetFormAddPatient() {

		String result = patientService.getFormAddPatient(model);

		verify(model).addAttribute(eq("patient"), any(PatientDto.class));

		assertEquals("patient/add", result);
	}

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

		String result = patientService.addPatient(newPatient);

		verify(patientProxy).addPatient(newPatient);

		assertEquals("redirect:/patient/list", result);
	}

	@Test
	void testDeletePatient() {
		Long patientId = 1L;

		String result = patientService.deletePatient(patientId);

		verify(patientProxy).deletePatient(patientId);

		assertEquals("redirect:/patient/list", result);
	}

}
