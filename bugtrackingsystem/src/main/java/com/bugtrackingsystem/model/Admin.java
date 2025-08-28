package com.bugtrackingsystem.model;


import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Admin extends User {
	@OneToMany(mappedBy = "createdBy")
	private Set<Project> projects;

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
}

