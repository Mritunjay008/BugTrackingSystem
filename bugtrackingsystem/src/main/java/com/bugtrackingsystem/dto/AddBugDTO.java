package com.bugtrackingsystem.dto;

import com.bugtrackingsystem.util.Severity;

import jakarta.validation.constraints.NotNull;

public class AddBugDTO {
	@NotNull(message = "Title cannot be null")
	private String title;
	@NotNull(message = "Description cannot be null")
	private String description;

	private Severity severity;
	@NotNull
	private Integer projectId;
	@NotNull
	private Integer testEngineerId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getTestEngineerId() {
		return testEngineerId;
	}

	public void setTestEngineerId(Integer testEngineerId) {
		this.testEngineerId = testEngineerId;
	}
}

