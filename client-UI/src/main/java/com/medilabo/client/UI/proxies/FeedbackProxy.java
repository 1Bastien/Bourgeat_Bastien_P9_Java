package com.medilabo.client.UI.proxies;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medilabo.client.UI.Dto.FeedbackDto;

@Service
public interface FeedbackProxy {

	List<FeedbackDto> getFeedbacks(Long patientId);
	
	void addFeedback(FeedbackDto feedback);
}
