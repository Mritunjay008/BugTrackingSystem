package com.bugtrackingsystem.model;

import java.time.LocalDate;

import com.bugtrackingsystem.util.BugStatus;
import com.bugtrackingsystem.util.Severity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Bug {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private int id;

	@Column(name = "Title", nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Severity severity;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BugStatus bugStatus;

	private LocalDate startDate;

	private LocalDate lastUpdatedDate;

	@ManyToOne()
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	@ManyToOne
	@JoinColumn(name = "created_by_id", nullable = true)
	private TestEngineer createdBy;

	@ManyToOne
	@JoinColumn(name = "assigned_to_id", nullable = true)
	private Developer assignedTo;

	public int getId() {
		return id;
	}

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

	public BugStatus getBugStatus() {
		return bugStatus;
	}

	public void setBugStatus(BugStatus bugStatus) {
		this.bugStatus = bugStatus;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public TestEngineer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(TestEngineer createdBy) {
		this.createdBy = createdBy;
	}

	public Developer getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Developer assignedTo) {
		this.assignedTo = assignedTo;
	}
}