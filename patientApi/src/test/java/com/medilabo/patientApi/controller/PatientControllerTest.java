package com.medilabo.patientApi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.patientApi.model.Gender;
import com.medilabo.patientApi.model.Patient;
import com.medilabo.patientApi.service.PatientService;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientService patientService;

	private final static Patient patient = new Patient();

	@BeforeAll
	public static void setUp() {
		patient.setId(1L);
		patient.setFirstName("John");
		patient.setLastName("Doe");
		patient.setGender(Gender.M);
		patient.setPhoneNumber("1234567890");
		patient.setAddress("123 Main St");
	}

	@Test
	public void testAddPatient() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		String patientJson = objectMapper.writeValueAsString(patient);

		when(patientService.addPatient(patient)).thenReturn(patient);

		mockMvc.perform(post("/patient").content(patientJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(patientService, times(1)).addPatient(any());
	}

	@Test
	public void testGetPatients() throws Exception {
		Patient patient2 = new Patient();
		patient2.setFirstName("Jane");
		patient2.setLastName("Doe");
		patient2.setGender(Gender.F);
		patient2.setPhoneNumber("1234567890");
		patient2.setAddress("123 Main St");

		List<Patient> patients = new ArrayList<>();
		patients.add(patient);
		patients.add(patient2);

		ObjectMapper objectMapper = new ObjectMapper();
		String patientJson = objectMapper.writeValueAsString(patients);

		when(patientService.getPatients()).thenReturn(patients);

		mockMvc.perform(get("/patient/all")).andExpect(content().json(patientJson)).andExpect(status().isOk());

		verify(patientService, times(1)).getPatients();
	}

	@Test
	public void testGetPatient() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String patientJson = objectMapper.writeValueAsString(patient);

		when(patientService.getPatientById(1L)).thenReturn(patient);

		mockMvc.perform(get("/patient/1")).andExpect(content().json(patientJson)).andExpect(status().isOk());

		verify(patientService, times(1)).getPatientById(1L);
	}

	@Test
	public void testUpdatePatient() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String patientJson = objectMapper.writeValueAsString(patient);

		when(patientService.updatePatient(patient.getId(), patient)).thenReturn(patient);

		mockMvc.perform(post("/patient/update/1").content(patientJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(patientService, times(1)).updatePatient(any(), any());
	}

	@Test
	public void testDeletePatient() throws Exception {
		mockMvc.perform(get("/patient/delete/1")).andExpect(status().isOk());

		verify(patientService, times(1)).deletePatient(1L);
	}

}
