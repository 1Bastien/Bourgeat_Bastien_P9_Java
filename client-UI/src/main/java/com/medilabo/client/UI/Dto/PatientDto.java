package com.medilabo.client.UI.Dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PatientDto {

	private Long id;

	@NotNull(message = "Le prénom ne peut pas être vide")
	@Size(min = 2, max = 50, message = "Le prénom doit contenir entre {min} et {max} caractères")
	@Pattern(regexp = "[a-zA-Z]+", message = "Le prénom ne doit contenir que des lettres")
	private String firstName;

	@NotNull(message = "Le nom de famille ne peut pas être vide")
	@Size(min = 2, max = 50, message = "Le nom de famille doit contenir entre {min} et {max} caractères")
	@Pattern(regexp = "[a-zA-Z]+", message = "Le nom de famille ne doit contenir que des lettres")
	private String lastName;

	@NotNull(message = "La date de naissance ne peut pas être vide")
	@PastOrPresent(message = "La date de naissance doit être dans le passé ou le présent")
	private LocalDate dateOfBirth;

	@NotNull(message = "Le genre ne peut pas être vide")
	@Pattern(regexp = "M|F", message = "Le genre doit être 'M' ou 'F'")
	private String gender;

	@Size(max = 150, message = "L'adresse doit contenir au maximum {max} caractères")
	private String address;

	@Size(max = 15, message = "Le numéro de téléphone doit contenir au maximum {max} caractères")
	private String phoneNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
