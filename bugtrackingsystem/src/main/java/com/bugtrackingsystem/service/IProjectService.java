package com.bugtrackingsystem.service;

import java.time.LocalDate;
import java.util.List;

import com.bugtrackingsystem.dto.BugDTO;
import com.bugtrackingsystem.dto.DeveloperDTO;
import com.bugtrackingsystem.dto.ProjectDTO;
import com.bugtrackingsystem.dto.TestEngineerDTO;
import com.bugtrackingsystem.util.ProjStatus;

public interface IProjectService {
	ProjectDTO createProject(ProjectDTO projDTO, Integer userId);

	ProjectDTO getProjectById(Integer projId);

	List<ProjectDTO> getAllProjects();

	ProjectDTO updateName(Integer projId, String name);

	ProjectDTO updateDescription(Integer projId, String description);
	
	ProjectDTO updateDeadline(Integer projId, LocalDate date);

	ProjectDTO updateStatus(Integer projId, ProjStatus projStatus);

	String deleteProject(Integer projId, Integer userId);

	List<DeveloperDTO> getDevelopersByProjectId(Integer projId);

	List<TestEngineerDTO> getTestEngineersByProjectId(Integer projId);

	List<BugDTO> getBugsByProjectId(Integer projId);

}

