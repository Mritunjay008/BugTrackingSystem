package com.bugtrackingsystem.dto;

import java.util.Set;

import org.springframework.stereotype.Component;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Component
public class DeveloperDTO extends UserDTO{
	@NotBlank(message="Developer skill is required.")
	@Size(max=50, message="Title cannot exceed 50 characters.")
	private String skill;

	@Valid
	private ProjectDTO project;
	
	@Valid
	private Set<BugDTO> assignedTo;
	
	public Set<BugDTO> getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Set<BugDTO> assignedTo) {
		this.assignedTo = assignedTo;
	}

	public ProjectDTO getProject() {
		return project;
	}

	public void setProject(ProjectDTO project) {
		this.project = project;
	}
	
	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}
	
}

