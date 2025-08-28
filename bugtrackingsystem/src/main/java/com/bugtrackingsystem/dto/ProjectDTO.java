package com.bugtrackingsystem.dto;

import java.time.LocalDate;

import com.bugtrackingsystem.util.ProjStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProjectDTO {
	
	private Integer id;
	@NotNull
	@NotBlank(message = "name should not be blank")
	private String name;
	@NotNull
	@NotBlank(message = "description should not be blank")
	@Size(min = 5, max = 30)
	private String description;

	@NotNull(message = "Project status is required")
	private ProjStatus status;
	
	private LocalDate deadline;
	
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public ProjStatus getStatus() {
		return status;
	}

	public void setStatus(ProjStatus status) {
		this.status = status;
	}
}

