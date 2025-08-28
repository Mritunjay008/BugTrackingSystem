package com.bugtrackingsystem.model;

import java.util.Set;

import com.bugtrackingsystem.dto.UserDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class TestEngineer extends User {
	private String skill;

	@ManyToOne
	private Project project;

	@OneToMany(mappedBy = "createdBy", cascade = CascadeType.PERSIST)
	private Set<Bug> bugs;
	
	public TestEngineer() {}
	
	public TestEngineer(UserDTO userDTO) {
		super(userDTO);
		this.skill = null;
		this.bugs = null;
		this.project = null;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Set<Bug> getBugs() {
		return bugs;
	}

	public void setBugs(Set<Bug> bugs) {
		this.bugs = bugs;
	}
}

