package com.medilabo.assessmentservice.services;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medilabo.assessmentservice.Dto.AssessmentType;
import com.medilabo.assessmentservice.Dto.GenderType;
import com.medilabo.assessmentservice.Dto.Keywords;
import com.medilabo.assessmentservice.Dto.PatientDto;
import com.medilabo.assessmentservice.proxies.FeedbackProxy;
import com.medilabo.assessmentservice.proxies.PatientProxy;
import com.medilabo.assessmentservice.services.impl.AssessmentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AssessmentServiceTest {

	@InjectMocks
	private AssessmentServiceImpl assessmentService;

	@Mock
	private PatientProxy patientProxy;

	@Mock
	private FeedbackProxy feedbackProxy;

	@Test
	void testGetAssessmentNONE() {
		Long patientId = 1L;
		int age = 31;
		int keywordsOccurences = 0;
		PatientDto patient = new PatientDto();
		patient.setGender(GenderType.M);
		patient.setDateOfBirth(LocalDate.now().minusYears(age));

		when(patientProxy.getPatient(patientId)).thenReturn(patient);
		when(feedbackProxy.countWordOccurrences(patientId,
				Arrays.asList(Keywords.values()).stream().map(Keywords::getKeyword).collect(Collectors.toList())))
				.thenReturn(keywordsOccurences);

		String assessmentType = assessmentService.getAssessment(patientId);

		assertEquals(AssessmentType.NONE.getType(), assessmentType);
	}
	
	@Test
	void testGetAssessmentINDANGER() {
		Long patientId = 1L;
		int age = 30;
		int keywordsOccurences = 4;
		PatientDto patient = new PatientDto();
		patient.setGender(GenderType.M);
		patient.setDateOfBirth(LocalDate.now().minusYears(age));

		when(patientProxy.getPatient(patientId)).thenReturn(patient);
		when(feedbackProxy.countWordOccurrences(patientId,
				Arrays.asList(Keywords.values()).stream().map(Keywords::getKeyword).collect(Collectors.toList())))
				.thenReturn(keywordsOccurences);

		String assessmentType = assessmentService.getAssessment(patientId);

		assertEquals(AssessmentType.IN_DANGER.getType(), assessmentType);
	}
	
	@Test
	void testGetAssessmentEARLYONSET() {
		Long patientId = 1L;
		int age = 30;
		int keywordsOccurences = 5;
		PatientDto patient = new PatientDto();
		patient.setGender(GenderType.M);
		patient.setDateOfBirth(LocalDate.now().minusYears(age));

		when(patientProxy.getPatient(patientId)).thenReturn(patient);
		when(feedbackProxy.countWordOccurrences(patientId,
				Arrays.asList(Keywords.values()).stream().map(Keywords::getKeyword).collect(Collectors.toList())))
				.thenReturn(keywordsOccurences);

		String assessmentType = assessmentService.getAssessment(patientId);

		assertEquals(AssessmentType.EARLY_ONSET.getType(), assessmentType);
	}
	
	@Test
	void testGetAssessmentBORDERLINE() {
		Long patientId = 1L;
		int age = 31;
		int keywordsOccurences = 2;
		PatientDto patient = new PatientDto();
		patient.setGender(GenderType.M);
		patient.setDateOfBirth(LocalDate.now().minusYears(age));

		when(patientProxy.getPatient(patientId)).thenReturn(patient);
		when(feedbackProxy.countWordOccurrences(patientId,
				Arrays.asList(Keywords.values()).stream().map(Keywords::getKeyword).collect(Collectors.toList())))
				.thenReturn(keywordsOccurences);

		String assessmentType = assessmentService.getAssessment(patientId);

		assertEquals(AssessmentType.BORDERLINE.getType(), assessmentType);
	}
	
	@Test
	void testGetAssessmentINDANGERAndMore30() {
		Long patientId = 1L;
		int age = 31;
		int keywordsOccurences = 6;
		PatientDto patient = new PatientDto();
		patient.setGender(GenderType.M);
		patient.setDateOfBirth(LocalDate.now().minusYears(age));

		when(patientProxy.getPatient(patientId)).thenReturn(patient);
		when(feedbackProxy.countWordOccurrences(patientId,
				Arrays.asList(Keywords.values()).stream().map(Keywords::getKeyword).collect(Collectors.toList())))
				.thenReturn(keywordsOccurences);

		String assessmentType = assessmentService.getAssessment(patientId);

		assertEquals(AssessmentType.IN_DANGER.getType(), assessmentType);
	}
	
	@Test
	void testGetAssessmentEARLYONSETAndMore30() {
		Long patientId = 1L;
		int age = 31;
		int keywordsOccurences = 8;
		PatientDto patient = new PatientDto();
		patient.setGender(GenderType.M);
		patient.setDateOfBirth(LocalDate.now().minusYears(age));

		when(patientProxy.getPatient(patientId)).thenReturn(patient);
		when(feedbackProxy.countWordOccurrences(patientId,
				Arrays.asList(Keywords.values()).stream().map(Keywords::getKeyword).collect(Collectors.toList())))
				.thenReturn(keywordsOccurences);

		String assessmentType = assessmentService.getAssessment(patientId);

		assertEquals(AssessmentType.EARLY_ONSET.getType(), assessmentType);
	}
	
	@Test
	void testGetAssessmentINDANGER_F() {
		Long patientId = 1L;
		int age = 30;
		int keywordsOccurences = 4;
		PatientDto patient = new PatientDto();
		patient.setGender(GenderType.F);
		patient.setDateOfBirth(LocalDate.now().minusYears(age));

		when(patientProxy.getPatient(patientId)).thenReturn(patient);
		when(feedbackProxy.countWordOccurrences(patientId,
				Arrays.asList(Keywords.values()).stream().map(Keywords::getKeyword).collect(Collectors.toList())))
				.thenReturn(keywordsOccurences);

		String assessmentType = assessmentService.getAssessment(patientId);

		assertEquals(AssessmentType.IN_DANGER.getType(), assessmentType);
	}
	
	@Test
	void testGetAssessmentEARLYONSET_F() {
		Long patientId = 1L;
		int age = 30;
		int keywordsOccurences = 7;
		PatientDto patient = new PatientDto();
		patient.setGender(GenderType.F);
		patient.setDateOfBirth(LocalDate.now().minusYears(age));

		when(patientProxy.getPatient(patientId)).thenReturn(patient);
		when(feedbackProxy.countWordOccurrences(patientId,
				Arrays.asList(Keywords.values()).stream().map(Keywords::getKeyword).collect(Collectors.toList())))
				.thenReturn(keywordsOccurences);

		String assessmentType = assessmentService.getAssessment(patientId);

		assertEquals(AssessmentType.EARLY_ONSET.getType(), assessmentType);
	}
	
	@Test
	void testGetAssessmentINDANGERAndMore30_F() {
		Long patientId = 1L;
		int age = 31;
		int keywordsOccurences = 6;
		PatientDto patient = new PatientDto();
		patient.setGender(GenderType.F);
		patient.setDateOfBirth(LocalDate.now().minusYears(age));

		when(patientProxy.getPatient(patientId)).thenReturn(patient);
		when(feedbackProxy.countWordOccurrences(patientId,
				Arrays.asList(Keywords.values()).stream().map(Keywords::getKeyword).collect(Collectors.toList())))
				.thenReturn(keywordsOccurences);

		String assessmentType = assessmentService.getAssessment(patientId);

		assertEquals(AssessmentType.IN_DANGER.getType(), assessmentType);
	}
	
	@Test
	void testGetAssessmentEARLYONSETAndMore30_F() {
		Long patientId = 1L;
		int age = 31;
		int keywordsOccurences = 8;
		PatientDto patient = new PatientDto();
		patient.setGender(GenderType.F);
		patient.setDateOfBirth(LocalDate.now().minusYears(age));

		when(patientProxy.getPatient(patientId)).thenReturn(patient);
		when(feedbackProxy.countWordOccurrences(patientId,
				Arrays.asList(Keywords.values()).stream().map(Keywords::getKeyword).collect(Collectors.toList())))
				.thenReturn(keywordsOccurences);

		String assessmentType = assessmentService.getAssessment(patientId);

		assertEquals(AssessmentType.EARLY_ONSET.getType(), assessmentType);
	}
}
