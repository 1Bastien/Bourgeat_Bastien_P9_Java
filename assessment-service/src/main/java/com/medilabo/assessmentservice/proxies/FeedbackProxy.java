package com.medilabo.assessmentservice.proxies;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface FeedbackProxy {
	
	int countWordOccurrences(Long patientId, List<String> keywords);
}
