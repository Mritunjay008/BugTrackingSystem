package com.bugtrackingsystem.model;

import java.time.LocalDate;
import java.util.Set;

import com.bugtrackingsystem.util.ProjStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String description;

	private LocalDate startDate;

	private LocalDate deadline;
	
	@ManyToOne
	private Admin createdBy;

	@Enumerated(EnumType.STRING)
	private ProjStatus status;

	public Admin getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Admin createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getId() {
		return id;
	}

	@OneToMany(mappedBy = "project")
	private Set<TestEngineer> testEngineers;

	@OneToMany(mappedBy = "project")
	private Set<Developer> developers;

	@OneToMany(mappedBy = "project")
	private Set<Bug> bugs;

	public Set<TestEngineer> getTestEngineers() {
		return testEngineers;
	}

	public void setTestEngineers(Set<TestEngineer> testEngineers) {
		this.testEngineers = testEngineers;
	}

	public Set<Developer> getDevelopers() {
		return developers;
	}

	public void setDevelopers(Set<Developer> developers) {
		this.developers = developers;
	}

	public Set<Bug> getBugs() {
		return bugs;
	}

	public void setBugs(Set<Bug> bugs) {
		this.bugs = bugs;
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
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

