package com.bugtrackingsystem.model; 
 
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="developer")
public class Developer extends User{
	
	@Column(name="developer_skill")
	private String skill;
	
	@ManyToOne
	@JoinColumn(name="project_id")
	private Project project;

	@OneToMany(mappedBy = "assignedTo")
	private Set<Bug> bugs;

	
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

