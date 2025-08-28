package com.bugtrackingsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class TesterChangePasswordDTO {
	@NotNull(message = "ID is required")
	@Positive(message = "ID must be greater than zero")
	private Integer id;

	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", 
			message = "Password must be 8–20 characters, include uppercase, lowercase, digit, and special character")
	private String oldPassword;

	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", 
			message = "Password must be 8–20 characters, include uppercase, lowercase, digit, and special character")
	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}

