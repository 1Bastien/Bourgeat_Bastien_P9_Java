package com.medilabo.client.UI.controller;

import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import com.medilabo.client.UI.Dto.PatientDto;
import com.medilabo.client.UI.services.PatientService;

@WebMvcTest(controllers = PatientController.class)

@AutoConfigureMockMvc(addFilters = false)
public class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientService patientService;

	@InjectMocks
	private PatientController patientController;

	@Test
	@WithMockUser(username = "admin")
	public void testList() throws Exception {
		List<PatientDto> patients = new ArrayList<>();
		PatientDto patient = new PatientDto();
		patients.add(patient);

		when(patientService.getPatientsList(any(Model.class))).thenReturn("patient/list");

		mockMvc.perform(get("/patient/list").flashAttr("patients", patients)).andExpect(status().isOk())
				.andExpect(view().name("patient/list")).andExpect(model().attribute("patients", patients));

		verify(patientService, times(1)).getPatientsList(any());
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	void testGetUpdateForm() throws Exception {
		Long id = 1L;
		PatientDto patient = new PatientDto();
		patient.setId(id);

		when(patientService.getPatient(any(Long.class), any(Model.class))).thenReturn("patient/update");

		mockMvc.perform(get("/patient/update/{id}", id).flashAttr("patient", patient)).andExpect(status().isOk())
				.andExpect(view().name("patient/update")).andExpect(model().attribute("patient", patient));

		verify(patientService, times(1)).getPatient(any(), any());
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	void testUpdatePatient() throws Exception {
		Long id = 1L;
		PatientDto newPatient = new PatientDto();
		newPatient.setFirstName("John");
		newPatient.setLastName("Doe");
		newPatient.setGender("M");
		newPatient.setDateOfBirth(LocalDate.parse("1990-01-01"));
		newPatient.setPhoneNumber("123456789");
		newPatient.setAddress("Fake street 123");

		String json = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"gender\":\"M\",\"dateOfBirth\":\"01-01-1990\",\"phoneNumber\":\"123456789\",\"address\":\"Fake street 123\"}";

		when(patientService.updatePatient(any(Long.class), any(PatientDto.class))).thenReturn("redirect:/patient/list");

		mockMvc.perform(post("/patient/update/{id}", id).content(json).flashAttr("patient", newPatient))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/patient/list"));

		verify(patientService).updatePatient(any(), any());
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	void testUpdatePatientWithError() throws Exception {
		Long id = 1L;
		PatientDto newPatient = new PatientDto();
		newPatient.setFirstName("John");

		mockMvc.perform(post("/patient/update/{id}", id)).andExpect(status().isOk())
				.andExpect(view().name("patient/update"));

		verify(patientService, never()).updatePatient(any(), any());
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	void testGetAddForm() throws Exception {
		PatientDto patient = new PatientDto();

		when(patientService.getFormAddPatient(any(Model.class))).thenReturn("patient/add");

		mockMvc.perform(get("/patient/add").flashAttr("patient", patient)).andExpect(status().isOk())
				.andExpect(view().name("patient/add"));

		verify(patientService, times(1)).getFormAddPatient(any());
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	void testAddPatient() throws Exception {
		PatientDto newPatient = new PatientDto();
		newPatient.setId(1L);
		newPatient.setFirstName("John");
		newPatient.setLastName("Doe");
		newPatient.setGender("M");
		newPatient.setDateOfBirth(LocalDate.parse("1990-01-01"));
		newPatient.setPhoneNumber("123456789");
		newPatient.setAddress("Fake street 123");

		String jsonString = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"gender\":\"M\",\"dateOfBirth\":\"01-01-1990\",\"phoneNumber\":\"123456789\",\"address\":\"Fake street 123\"}";

		when(patientService.addPatient(newPatient)).thenReturn("redirect:/patient/list");

		mockMvc.perform(post("/patient/add").content(jsonString).flashAttr("patient", newPatient))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/patient/list"));

		verify(patientService).addPatient(any());
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	void testAddPatientWithError() throws Exception {
		PatientDto newPatient = new PatientDto();
		newPatient.setId(1L);

		mockMvc.perform(post("/patient/add").flashAttr("patient", newPatient)).andExpect(status().isOk())
				.andExpect(view().name("patient/add"));

		verify(patientService, never()).addPatient(any());
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	void testDeletePatient() throws Exception {
		Long id = 1L;

		when(patientService.deletePatient(any(Long.class))).thenReturn("redirect:/patient/list");

		mockMvc.perform(get("/patient/delete/{id}", id)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/patient/list"));

		verify(patientService).deletePatient(any());
	}

}
