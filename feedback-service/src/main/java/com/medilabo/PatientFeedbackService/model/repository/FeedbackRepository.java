package com.medilabo.PatientFeedbackService.model.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.medilabo.PatientFeedbackService.model.Feedback;

@Repository
public interface FeedbackRepository extends MongoRepository<Feedback, String> {

	List<Feedback> findByPatientId(Long patientId);
	
	Boolean existsByPatientId(Long patientId);
}
