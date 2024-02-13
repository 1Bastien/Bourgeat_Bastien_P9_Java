package com.medilabo.client.UI.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.medilabo.client.UI.Dto.PatientDto;

import jakarta.servlet.http.HttpServletRequest;

@Service
public interface PatientService {

	String getPatientsList(Model model, HttpServletRequest response);

	String getPatient(Long id, Model model, HttpServletRequest response);

	String updatePatient(Long id, PatientDto newPatient, Model model, HttpServletRequest response);

	String getFormAddPatient(Model model, HttpServletRequest response);

	String addPatient(PatientDto patient, Model model, HttpServletRequest response);
	
	String deletePatient(Long id, Model model, HttpServletRequest response);
}
