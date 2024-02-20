package com.medilabo.client.UI.proxies;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medilabo.client.UI.Dto.PatientDto;

@Service
public interface PatientProxy {

	List<PatientDto> getPatientsList();
	
	PatientDto getPatient(Long id);
	
	void updatePatient(Long id, PatientDto newPatient);
	
	void addPatient(PatientDto patient);
	
	void deletePatient(Long id);
}
