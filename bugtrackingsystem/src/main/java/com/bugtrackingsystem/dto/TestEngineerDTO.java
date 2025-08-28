package com.bugtrackingsystem.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TestEngineerDTO extends UserDTO {	
	private String skill;
	
	@JsonIgnore
	private ProjectDTO project;
	
	@JsonIgnore
	private Set<BugDTO> bugs;

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public ProjectDTO getProject() {
		return project;
	}

	public void setProject(ProjectDTO project) {
		this.project = project;
	}

	public Set<BugDTO> getBugs() {
		return bugs;
	}

	public void setBugs(Set<BugDTO> bugs) {
		this.bugs = bugs;
	}
}

