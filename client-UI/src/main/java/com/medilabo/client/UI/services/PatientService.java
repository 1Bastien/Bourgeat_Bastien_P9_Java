package com.medilabo.client.UI.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.medilabo.client.UI.Dto.PatientDto;

@Service
public interface PatientService {

	String getPatientsList(Model model);

	String getPatient(Long id, Model model);

	String updatePatient(Long id, PatientDto newPatient);

	String getFormAddPatient(Model model);

	String addPatient(PatientDto patient);
	
	String deletePatient(Long id);
}
