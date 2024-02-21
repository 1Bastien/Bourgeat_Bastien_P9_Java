package com.medilabo.patientApi.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.medilabo.patientApi.model.Gender;
import com.medilabo.patientApi.model.Patient;
import com.medilabo.patientApi.model.repository.PatientRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.properties" })
public class PatientControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PatientRepository patientRepository;

	@BeforeEach
	public void setUp() {
		patientRepository.deleteAll();
	}

	@AfterEach
	public void tearDown() {
		patientRepository.deleteAll();
	}

	@Test
	public void testAddPatient() throws Exception {

		mockMvc.perform(post("/patient").contentType(MediaType.APPLICATION_JSON).content(
				"{\"firstName\":\"John\",\"lastName\":\"Doe\",\"gender\":\"M\",\"dateOfBirth\":\"2004-06-18\",\"phoneNumber\":\"1234567890\",\"address\":\"123 Main St\"}"))
				.andExpect(status().isOk());

		List<Patient> patients = patientRepository.findAll();
		assertEquals(1, patients.size());
		assertEquals("John", patients.get(0).getFirstName());
		assertEquals("Doe", patients.get(0).getLastName());
		assertEquals(Gender.M, patients.get(0).getGender());
		assertEquals("1234567890", patients.get(0).getPhoneNumber());
		assertEquals("123 Main St", patients.get(0).getAddress());
	}

	@Test
	public void testGetPatients() throws Exception {
		Patient patient = new Patient();
		patient.setFirstName("John");
		patient.setLastName("Doe");
		patient.setGender(Gender.M);
		patient.setDateOfBirth(LocalDate.parse("2004-06-18"));
		patient.setPhoneNumber("1234567890");
		patient.setAddress("123 Main St");
		patientRepository.save(patient);

		mockMvc.perform(get("/patient/all")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].firstName", is("John"))).andExpect(jsonPath("$[0].lastName", is("Doe")))
				.andExpect(jsonPath("$[0].gender", is("M"))).andExpect(jsonPath("$[0].dateOfBirth", is("2004-06-18")))
				.andExpect(jsonPath("$[0].phoneNumber", is("1234567890")))
				.andExpect(jsonPath("$[0].address", is("123 Main St")));

	}

	@Test
	public void testGetPatient() throws Exception {
		Patient patient = new Patient();
		patient.setId(1L);
		patient.setFirstName("John");
		patient.setLastName("Doe");
		patient.setGender(Gender.M);
		patient.setDateOfBirth(LocalDate.parse("2004-06-18"));
		patient.setPhoneNumber("1234567890");
		patient.setAddress("123 Main St");
		patientRepository.save(patient);

		mockMvc.perform(get("/patient/" + patient.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("John"))).andExpect(jsonPath("$.lastName", is("Doe")))
				.andExpect(jsonPath("$.dateOfBirth", is("2004-06-18")))
				.andExpect(jsonPath("$.phoneNumber", is("1234567890")))
				.andExpect(jsonPath("$.address", is("123 Main St")));

	}

	@Test
	public void testUpdatePatient() throws Exception {
		Patient patient = new Patient();
		patient.setId(1L);
		patient.setFirstName("John");
		patient.setLastName("Doe");
		patient.setGender(Gender.M);
		patient.setDateOfBirth(LocalDate.parse("2004-06-18"));
		patient.setPhoneNumber("1234567890");
		patient.setAddress("123 Main St");
		patientRepository.save(patient);

		mockMvc.perform(post("/patient/update/" + patient.getId()).contentType(MediaType.APPLICATION_JSON).content(
				"{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"gender\":\"F\",\"dateOfBirth\":\"2004-06-18\",\"phoneNumber\":\"1234567890\",\"address\":\"123 Main St\"}"))
				.andExpect(status().isOk());

		Patient updatedPatient = patientRepository.findById(patient.getId()).get();
		assertEquals("Jane", updatedPatient.getFirstName());
		assertEquals("Doe", updatedPatient.getLastName());
		assertEquals("F", updatedPatient.getGender().toString());
		assertEquals("1234567890", updatedPatient.getPhoneNumber());
		assertEquals("123 Main St", updatedPatient.getAddress());
		assertEquals(LocalDate.parse("2004-06-18"), updatedPatient.getDateOfBirth());
	}

	@Test
	public void testDeletePatient() throws Exception {
		Patient patient = new Patient();
		patient.setId(1L);
		patient.setFirstName("John");
		patient.setLastName("Doe");
		patient.setGender(Gender.M);
		patient.setDateOfBirth(LocalDate.parse("2004-06-18"));
		patient.setPhoneNumber("1234567890");
		patient.setAddress("123 Main St");
		patientRepository.save(patient);

		mockMvc.perform(get("/patient/delete/" + patient.getId())).andExpect(status().isOk());

		List<Patient> patients = patientRepository.findAll();
		assertEquals(0, patients.size());
	}

}
