package com.medilabo.client.UI.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserLoginDto {

	@Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
	@NotNull(message = "Username cannot be null")
	private String username;
	
	@Size(min = 3, max = 20, message = "Password must be between 3 and 20 characters")
	@NotNull(message = "Password cannot be null")
	private String password;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
