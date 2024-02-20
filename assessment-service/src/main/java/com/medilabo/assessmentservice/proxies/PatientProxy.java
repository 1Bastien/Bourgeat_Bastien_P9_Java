package com.medilabo.assessmentservice.proxies;

import org.springframework.stereotype.Service;

import com.medilabo.assessmentservice.Dto.PatientDto;

@Service
public interface PatientProxy {

	PatientDto getPatient(Long id);
}
