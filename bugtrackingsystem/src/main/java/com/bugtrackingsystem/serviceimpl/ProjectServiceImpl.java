package com.bugtrackingsystem.serviceimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugtrackingsystem.dto.BugDTO;
import com.bugtrackingsystem.dto.DeveloperDTO;
import com.bugtrackingsystem.dto.ProjectDTO;
import com.bugtrackingsystem.dto.TestEngineerDTO;
import com.bugtrackingsystem.exception.BugTrackingException;
import com.bugtrackingsystem.model.Admin;
import com.bugtrackingsystem.model.Bug;
import com.bugtrackingsystem.model.Developer;
import com.bugtrackingsystem.model.Project;
import com.bugtrackingsystem.model.TestEngineer;
import com.bugtrackingsystem.repository.AdminRepository;
import com.bugtrackingsystem.repository.ProjectRepository;
import com.bugtrackingsystem.service.IProjectService;
import com.bugtrackingsystem.util.ProjStatus;

import jakarta.transaction.Transactional;

@Service
public class ProjectServiceImpl implements IProjectService {
	
	private ProjectRepository projectRepo;
	private ModelMapper modelMap;
	private AdminRepository adminRepo;
	
	@Autowired
	public ProjectServiceImpl(ProjectRepository projectRepo, ModelMapper modelMap, AdminRepository adminRepo) {
		this.projectRepo = projectRepo;
		this.modelMap = modelMap;
		this.adminRepo = adminRepo;
	}

	@Override // dto entity to
	public ProjectDTO createProject(ProjectDTO projDTO, Integer userId) {
		Project proj = new Project();
		Admin admin = adminRepo.findById(userId)
				.orElseThrow(() -> new BugTrackingException("you are not admin so you can't create project"));
		modelMap.map(projDTO, proj);
		proj.setCreatedBy(admin);
		proj.setStartDate(LocalDate.now());
		Project savedProject = projectRepo.save(proj);
		return modelMap.map(savedProject, ProjectDTO.class);
	}

	@Override
	public ProjectDTO getProjectById(Integer projId) {
		Project project = projectRepo.findById(projId)
				.orElseThrow(() -> new BugTrackingException("no project id is present"));
		return modelMap.map(project, ProjectDTO.class);
	}

	@Override // entity to dto
	public List<ProjectDTO> getAllProjects() {
		List<Project> projectList = projectRepo.findAll();
		if (projectList.isEmpty()) {
			throw new BugTrackingException("project is not present");
		}
		List<ProjectDTO> allProjects = new ArrayList<>();
		for (Project p : projectList) {
			ProjectDTO projectDTO = modelMap.map(p, ProjectDTO.class);
			allProjects.add(projectDTO);
		}
		return allProjects;
	}

	@Override // entity to dto
	public List<DeveloperDTO> getDevelopersByProjectId(Integer projId) {
		Project p = projectRepo.findById(projId).orElseThrow(() -> new BugTrackingException("proj id is not present"));
		if (p.getDevelopers().isEmpty()) {
			throw new BugTrackingException("no developers are found in project");
		}
		List<DeveloperDTO> developerDTOList = new ArrayList<>();

		for (Developer dev : p.getDevelopers()) {
			developerDTOList.add(modelMap.map(dev, DeveloperDTO.class));
		}
		return developerDTOList;
	}
	
	@Override
	public List<TestEngineerDTO> getTestEngineersByProjectId(Integer projId) {
		Project p = projectRepo.findById(projId).orElseThrow(() -> new BugTrackingException("no project id is found in database"));
		List<TestEngineerDTO> testEngineerDTOList=new ArrayList<>();
		if(p.getTestEngineers().isEmpty())
		{
			throw new BugTrackingException("no test engineers are found in project");
		}
		for(TestEngineer engi:p.getTestEngineers())
		{
			testEngineerDTOList.add(modelMap.map(engi,TestEngineerDTO.class ));
		}
		return testEngineerDTOList;
	}

	@Override
	public String deleteProject(Integer projId, Integer userId) {
		Project p = projectRepo.findById(projId).orElseThrow(() -> new BugTrackingException("Project Doesn't exist"));
		Admin admin = adminRepo.findById(userId).orElseThrow(() -> new BugTrackingException("Admin doesn't exist"));
		Admin projAdmin = p.getCreatedBy();
		if (!admin.equals(projAdmin)) {
			throw new BugTrackingException("Admin is not autorized");
		}
		for(TestEngineer tester: p.getTestEngineers()) {
			tester.setProject(null);
		}
		for(Developer dev: p.getDevelopers()) {
			dev.setProject(null);
		}
		projAdmin.getProjects().remove(p);
		projectRepo.delete(p);
		return "project deleted";
	}

	@Override
	public List<BugDTO> getBugsByProjectId(Integer projId) {
		Project p = projectRepo.findById(projId).orElseThrow(() -> new BugTrackingException("project id is not present in database"));
		if (p.getBugs().isEmpty()) {
			throw new BugTrackingException("no bugs are created in this project");
		}
		List<BugDTO> bugList = new ArrayList<>();
		for (Bug b : p.getBugs()) {
			bugList.add(modelMap.map(b, BugDTO.class));
		}
		return bugList;
	}

	@Override
	@Transactional
	public ProjectDTO updateStatus(Integer projId, ProjStatus projStatus) {
		Project p = projectRepo.findById(projId).orElseThrow(() -> new BugTrackingException("no project id is found in the database"));
		p.setStatus(projStatus);
		return modelMap.map(p, ProjectDTO.class);
	}

	@Override
	@Transactional
	public ProjectDTO updateName(Integer projId, String name) {
		Project p = projectRepo.findById(projId).orElseThrow(() -> new BugTrackingException("project id is not found in the database"));
		p.setName(name);
		return modelMap.map(p, ProjectDTO.class);
	}

	@Override
	@Transactional
	public ProjectDTO updateDescription(Integer projId, String description) {
		Project p = projectRepo.findById(projId).orElseThrow(() -> new BugTrackingException("no project id is present in the database"));
		p.setDescription(description);
		return modelMap.map(p, ProjectDTO.class);
	}

	@Override
	@Transactional
	public ProjectDTO updateDeadline(Integer projId, LocalDate date) {
		Project p = projectRepo.findById(projId).orElseThrow(() -> new BugTrackingException("no project id is found in database"));
		p.setDeadline(date);
		return modelMap.map(p, ProjectDTO.class);
	}


}
