package com.medilabo.client.UI.controller;

import static org.mockito.Mockito.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.services.PatientService;

import jakarta.servlet.http.HttpServletRequest;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientService patientService;

	@Test
	public void testList() throws Exception {
		HttpServletRequest response = mock(HttpServletRequest.class);
		Model model = mock(Model.class);

		when(patientService.getPatientsList(model, response)).thenReturn("patients/list");

		mockMvc.perform(get("/patient/list")).andExpect(status().isOk()).andExpect(view().name("patient/list"));

		verify(patientService, times(1)).getPatientsList(any(), any());
	}

	@Test
	void testGetUpdateForm() throws Exception {
		Long id = 1L;
		PatientDto patient = new PatientDto();
		patient.setId(id);

		when(patientService.getPatient(any(Long.class), any(Model.class), any(HttpServletRequest.class)))
				.thenReturn("patient/update");

		mockMvc.perform(get("/patient/update/{id}", id).flashAttr("patient", patient)).andExpect(status().isOk())
				.andExpect(view().name("patient/update"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("patient"));

		verify(patientService, times(1)).getPatient(any(), any(), any());
	}

	@Test
	void testUpdatePatient() throws Exception {
		Long id = 1L;
		PatientDto newPatient = new PatientDto();
		newPatient.setId(id);
		newPatient.setFirstName("John");
		newPatient.setLastName("Doe");
		newPatient.setDateOfBirth(LocalDate.parse("1990-01-01"));
		newPatient.setGender("M");
		newPatient.setPhoneNumber("123456789");
		newPatient.setAddress("Fake street, 123");

		when(patientService.updatePatient(any(Long.class), any(PatientDto.class), any(Model.class),
				any(HttpServletRequest.class))).thenReturn("redirect:/patient/list");

		mockMvc.perform(post("/patient/update/{id}", id).flashAttr("patient", newPatient))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/patient/list"));

		verify(patientService).updatePatient(any(), any(), any(), any());
	}

	@Test
	void testUpdatePatientWithError() throws Exception {
		Long id = 1L;
		PatientDto newPatient = new PatientDto();
		newPatient.setId(id);
		newPatient.setFirstName("John");

		BindingResult result = mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(true);

		HttpServletRequest request = mock(HttpServletRequest.class);
		Model model = mock(Model.class);

		mockMvc.perform(post("/patient/update/{id}", id).flashAttr("id", id)).andExpect(status().isOk())
				.andExpect(view().name("patient/update"));

		verify(patientService, never()).updatePatient(id, newPatient, model, request);
	}

	@Test
	void testGetAddForm() throws Exception {
		PatientDto patient = new PatientDto();

		when(patientService.getFormAddPatient(any(Model.class), any(HttpServletRequest.class)))
				.thenReturn("patient/add");

		mockMvc.perform(get("/patient/add").flashAttr("patient", patient)).andExpect(status().isOk())
				.andExpect(view().name("patient/add"));

		verify(patientService, times(1)).getFormAddPatient(any(), any());
	}

	@Test
	void testAddPatient() throws Exception {
		PatientDto newPatient = new PatientDto();
		newPatient.setId(1L);
		newPatient.setFirstName("John");
		newPatient.setLastName("Doe");
		newPatient.setDateOfBirth(LocalDate.parse("1990-01-01"));
		newPatient.setGender("M");
		newPatient.setPhoneNumber("123456789");
		newPatient.setAddress("Fake street, 123");

		when(patientService.addPatient(any(PatientDto.class), any(Model.class), any(HttpServletRequest.class)))
				.thenReturn("patient/add");

		mockMvc.perform(post("/patient/add").flashAttr("patient", newPatient)).andExpect(status().isOk())
				.andExpect(view().name("patient/add"));

		verify(patientService).addPatient(any(), any(), any());
	}
	
	@Test
	void testAddPatientWithError() throws Exception {
		PatientDto newPatient = new PatientDto();
		newPatient.setId(1L);
		newPatient.setFirstName("John");

		mockMvc.perform(post("/patient/add").flashAttr("patient", newPatient)).andExpect(status().isOk())
				.andExpect(view().name("patient/add"));

		verify(patientService, never()).addPatient(any(), any(), any());
	}
	
	@Test
	void testDeletePatient() throws Exception {
		Long id = 1L;
		
		when(patientService.deletePatient(any(Long.class), any(Model.class), any(HttpServletRequest.class)))
				.thenReturn("redirect:/patient/list");

		mockMvc.perform(get("/patient/delete/{id}", id)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/patient/list"));

		verify(patientService).deletePatient(any(), any(), any());
	}

}
