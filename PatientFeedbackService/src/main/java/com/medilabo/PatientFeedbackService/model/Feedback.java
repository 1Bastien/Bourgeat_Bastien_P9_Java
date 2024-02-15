package com.medilabo.PatientFeedbackService.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Document(collection = "feedbacks")
public class Feedback {

	@Id
	private String id;

	@NotNull(message = "L'identifiant du patient ne peut pas être vide")
	@Indexed
	private Long patientId;

	@NotNull(message = "Le nom du patient ne peut pas être vide")
	@Pattern(regexp = "[a-zA-Z]+", message = "Le prénom ne doit contenir que des lettres")
	private String patientName;

	@NotNull(message = "La date ne peut pas être vide")
	private LocalDateTime date;

	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
