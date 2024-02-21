package com.medilabo.patientApi.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.medilabo.patientApi.model.Gender;
import com.medilabo.patientApi.model.Patient;
import com.medilabo.patientApi.model.repository.PatientRepository;
import com.medilabo.patientApi.service.impl.PatientServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

	@InjectMocks
	private PatientServiceImpl patientService;

	@Mock
	private PatientRepository patientRepository;

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
	public void testAddPatient() {
		when(patientRepository.existsByFirstNameAndLastNameAndDateOfBirth(patient.getFirstName(), patient.getLastName(),
				patient.getDateOfBirth())).thenReturn(false);
		when(patientRepository.save(patient)).thenReturn(patient);

		Patient addedPatient = patientService.addPatient(patient);

		verify(patientRepository, times(1)).existsByFirstNameAndLastNameAndDateOfBirth(anyString(), anyString(), any());
		verify(patientRepository, times(1)).save(any());

		assertNotNull(addedPatient);
		assertEquals(patient.getId(), addedPatient.getId());
		assertEquals(patient.getFirstName(), addedPatient.getFirstName());
		assertEquals(patient.getLastName(), addedPatient.getLastName());
		assertEquals(patient.getGender(), addedPatient.getGender());
		assertEquals(patient.getPhoneNumber(), addedPatient.getPhoneNumber());
		assertEquals(patient.getAddress(), addedPatient.getAddress());
		assertEquals(patient.getDateOfBirth(), addedPatient.getDateOfBirth());
	}

	@Test
	public void testAddPatient_AlreadyExist() {
		when(patientRepository.existsByFirstNameAndLastNameAndDateOfBirth(patient.getFirstName(), patient.getLastName(),
				patient.getDateOfBirth())).thenReturn(true);

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			patientService.addPatient(patient);
		});

		assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());

		verify(patientRepository, times(1)).existsByFirstNameAndLastNameAndDateOfBirth(anyString(), anyString(), any());
		verify(patientRepository, times(0)).save(any());
	}

	@Test
	public void testGetPatients() {
		List<Patient> patients = new ArrayList<>();
		patients.add(patient);

		when(patientRepository.findAll()).thenReturn(patients);

		List<Patient> fetchedPatients = patientService.getPatients();

		verify(patientRepository, times(1)).findAll();

		assertEquals(patients.size(), fetchedPatients.size());
		assertEquals(patients.get(0).getId(), fetchedPatients.get(0).getId());
		assertEquals(patients.get(0).getFirstName(), fetchedPatients.get(0).getFirstName());
		assertEquals(patients.get(0).getLastName(), fetchedPatients.get(0).getLastName());
		assertEquals(patients.get(0).getGender(), fetchedPatients.get(0).getGender());
		assertEquals(patients.get(0).getPhoneNumber(), fetchedPatients.get(0).getPhoneNumber());
		assertEquals(patients.get(0).getAddress(), fetchedPatients.get(0).getAddress());
		assertEquals(patients.get(0).getDateOfBirth(), fetchedPatients.get(0).getDateOfBirth());
	}

	@Test
	public void testGetPatientById() {
		when(patientRepository.existsById(patient.getId())).thenReturn(true);
		when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));

		Patient fetchedPatient = patientService.getPatientById(patient.getId());

		verify(patientRepository, times(1)).existsById(patient.getId());
		verify(patientRepository, times(1)).findById(patient.getId());

		assertEquals(patient.getId(), fetchedPatient.getId());
		assertEquals(patient.getFirstName(), fetchedPatient.getFirstName());
		assertEquals(patient.getLastName(), fetchedPatient.getLastName());
		assertEquals(patient.getGender(), fetchedPatient.getGender());
		assertEquals(patient.getPhoneNumber(), fetchedPatient.getPhoneNumber());
		assertEquals(patient.getAddress(), fetchedPatient.getAddress());
		assertEquals(patient.getDateOfBirth(), fetchedPatient.getDateOfBirth());
	}

	@Test
	public void testGetPatientById_NotFound() {
		when(patientRepository.existsById(patient.getId())).thenReturn(false);

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			patientService.getPatientById(patient.getId());
		});

		assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

		verify(patientRepository, times(1)).existsById(patient.getId());
		verify(patientRepository, times(0)).findById(patient.getId());
	}

	@Test
	public void testUpdatePatient() {
		when(patientRepository.existsById(patient.getId())).thenReturn(true);
		when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));
		when(patientRepository.save(patient)).thenReturn(patient);

		Patient updatedPatient = patientService.updatePatient(patient.getId(), patient);

		verify(patientRepository, times(1)).existsById(patient.getId());
		verify(patientRepository, times(1)).findById(patient.getId());
		verify(patientRepository, times(1)).save(patient);

		assertEquals(patient.getId(), updatedPatient.getId());
		assertEquals(patient.getFirstName(), updatedPatient.getFirstName());
		assertEquals(patient.getLastName(), updatedPatient.getLastName());
		assertEquals(patient.getGender(), updatedPatient.getGender());
		assertEquals(patient.getPhoneNumber(), updatedPatient.getPhoneNumber());
		assertEquals(patient.getAddress(), updatedPatient.getAddress());
		assertEquals(patient.getDateOfBirth(), updatedPatient.getDateOfBirth());
	}
	
	@Test
	public void testUpdatePatient_NotFound() {
		when(patientRepository.existsById(patient.getId())).thenReturn(false);

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			patientService.updatePatient(patient.getId(), patient);
		});

		assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

		verify(patientRepository, times(1)).existsById(patient.getId());
		verify(patientRepository, times(0)).findById(patient.getId());
		verify(patientRepository, times(0)).save(patient);
	}
	
	@Test
	public void testDeletePatient() {
		when(patientRepository.existsById(patient.getId())).thenReturn(true);

		patientService.deletePatient(patient.getId());

		verify(patientRepository, times(1)).existsById(patient.getId());
	}

}
