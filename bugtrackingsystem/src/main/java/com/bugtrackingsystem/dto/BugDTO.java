package com.bugtrackingsystem.dto;

import java.time.LocalDate;

import com.bugtrackingsystem.util.BugStatus;
import com.bugtrackingsystem.util.Severity;

public class BugDTO {
	private int id;
	
	private String title;
	
	private String description;

	private Severity severity;

	private BugStatus bugStatus;

	private LocalDate startDate;

	private LocalDate lastUpdatedDate;

	private ProjectDTO project;

	private TestEngineerDTO createdBy;

	private DeveloperDTO assignedTo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public ProjectDTO getProject() {
		return project;
	}

	public void setProject(ProjectDTO project) {
		this.project = project;
	}

	public TestEngineerDTO getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(TestEngineerDTO createdBy) {
		this.createdBy = createdBy;
	}

	public DeveloperDTO getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(DeveloperDTO assignedTo) {
		this.assignedTo = assignedTo;
	}
}

