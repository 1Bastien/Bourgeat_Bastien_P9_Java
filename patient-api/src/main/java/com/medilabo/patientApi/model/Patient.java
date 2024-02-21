package com.medilabo.patientApi.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 2, max = 50, message = "First name should be between {min} and {max} characters")
	@Pattern(regexp = "[a-zA-Z]+", message = "First name should contain only letters")
	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;

	@NotNull
	@Size(min = 2, max = 50, message = "Last name should be between {min} and {max} characters")
	@Pattern(regexp = "[a-zA-Z]+", message = "Last name should contain only letters")
	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;

	@NotNull
	@Column(name = "date_of_birth", nullable = false)
	@PastOrPresent(message = "Date of birth should be in the past or present")
	private LocalDate dateOfBirth;

	@NotNull
	@Column(nullable = false)
	private Gender gender;

	@Size(max = 150, message = "Address should be at most {max} characters")
	@Column(nullable = true, length = 150)
	private String address;

	@Size(max = 15, message = "Phone number should be at most {max} characters")
	@Column(name = "phone_number", nullable = true, length = 15)
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
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
