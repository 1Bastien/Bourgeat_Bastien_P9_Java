package com.medilabo.assessmentservice.services.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.medilabo.assessmentservice.Dto.AssessmentType;
import com.medilabo.assessmentservice.Dto.GenderType;
import com.medilabo.assessmentservice.Dto.Keywords;
import com.medilabo.assessmentservice.Dto.PatientDto;
import com.medilabo.assessmentservice.proxies.FeedbackProxy;
import com.medilabo.assessmentservice.proxies.PatientProxy;
import com.medilabo.assessmentservice.services.AssessmentService;

@Service
public class AssessmentServiceImpl implements AssessmentService {

	private static final Logger logger = LogManager.getLogger(AssessmentServiceImpl.class);

	private PatientProxy patientProxy;
	private FeedbackProxy feedbackProxy;

	public AssessmentServiceImpl(PatientProxy patientProxy, FeedbackProxy feedbackProxy) {
		this.patientProxy = patientProxy;
		this.feedbackProxy = feedbackProxy;
	}

	@Override
	public String getAssessment(Long patientId) {
		try {
			List<String> keywords = Arrays.asList(Keywords.values()).stream().map(Keywords::getKeyword)
					.collect(Collectors.toList());

			Integer keywordsOccurences = feedbackProxy.countWordOccurrences(patientId, keywords);
			PatientDto patient = patientProxy.getPatient(patientId);

			int age = Period.between(patient.getDateOfBirth(), LocalDate.now()).getYears();
			
			AssessmentType assessmentType = AssessmentType.NONE;

			if (keywordsOccurences == 0) {
				return assessmentType.getType();
			}

			switch (patient.getGender()) {
			case GenderType.M:
				if (age <= 30) {
					if (keywordsOccurences >= 3 && keywordsOccurences <= 4) {
						assessmentType = AssessmentType.IN_DANGER;
					} else if (keywordsOccurences >= 5) {
						assessmentType = AssessmentType.EARLY_ONSET;
					}
				} else if (age > 30) {
					if (keywordsOccurences >= 2 && keywordsOccurences <= 5) {
						assessmentType = AssessmentType.BORDERLINE;
					} else if (keywordsOccurences >= 6 && keywordsOccurences <= 7) {
						assessmentType = AssessmentType.IN_DANGER;
					} else if (keywordsOccurences >= 8) {
						assessmentType = AssessmentType.EARLY_ONSET;
					}
				}
				break;
			case GenderType.F:
				if (age <= 30) {
					if (keywordsOccurences >= 4 && keywordsOccurences <= 6) {
						assessmentType = AssessmentType.IN_DANGER;
					} else if (keywordsOccurences >= 7) {
						assessmentType = AssessmentType.EARLY_ONSET;
					}
				} else if (age > 30) {
					if (keywordsOccurences >= 6 && keywordsOccurences <= 7) {
						assessmentType = AssessmentType.IN_DANGER;
					} else if (keywordsOccurences >= 8) {
						assessmentType = AssessmentType.EARLY_ONSET;
					}
				}
				break;
			}

			return assessmentType.getType();

		} catch (Exception e) {
			logger.error("Error while getting assessment", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while getting assessment");
		}
	}
}
