package com.bugtrackingsystem.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bugtrackingsystem.dto.DeveloperDTO;
import com.bugtrackingsystem.dto.ProjectDTO;
import com.bugtrackingsystem.exception.BugTrackingException;
import com.bugtrackingsystem.model.Bug;
import com.bugtrackingsystem.model.Developer;
import com.bugtrackingsystem.model.Project;
import com.bugtrackingsystem.model.User;
import com.bugtrackingsystem.repository.DeveloperRepository;
import com.bugtrackingsystem.repository.ProjectRepository;
import com.bugtrackingsystem.repository.UserRepository;
import com.bugtrackingsystem.service.IDeveloperService;
import com.bugtrackingsystem.util.UserRole;

@Service
public class DeveloperServiceImpl implements IDeveloperService {
	
	private DeveloperRepository developerRepository;
	private ProjectRepository projectRepo;
	private ModelMapper modelMapper;
	private UserRepository userRepository;
	
	@Autowired
	public DeveloperServiceImpl(DeveloperRepository developerRepository, ProjectRepository projectRepo, ModelMapper modelMapper, UserRepository userRepository) {
		this.developerRepository = developerRepository;
		this.projectRepo=projectRepo;
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
	}
	
	@Override
	@Transactional
	public DeveloperDTO addDeveloperToProject(Integer id, Integer projectId,Integer userId) {
		
		User user = userRepository.findById(userId).orElseThrow(() -> new BugTrackingException("User Doesn't exist"));
		if (!user.getUserRole().equals(UserRole.ADMIN)) {
			throw new BugTrackingException("Only Admin can access");
		}
		
	    Developer developer = developerRepository.findById(id)
	        .orElseThrow(() -> new BugTrackingException("Developer is not found with ID: "+id ));

	    Project project = projectRepo.findById(projectId)
	        .orElseThrow(() -> new BugTrackingException("Project not found with ID: "+id ));

	    // Optional: Prevent duplicate assignment
	    if (!project.getCreatedBy().equals(user)) {
			throw new BugTrackingException("User is not authorized");
		}
	    if (project.getDevelopers().contains(developer)) {
	        throw new BugTrackingException("Developer has already joined the project");
	    }
	    if (developer.getProject()!=null) {
	    	throw new BugTrackingException("Developer has already joined the project.");
	    }

	    project.getDevelopers().add(developer);
	    developer.setProject(project);
	    developerRepository.save(developer);
	    projectRepo.save(project);
	    
	    return modelMapper.map(developer, DeveloperDTO.class);
	}
	
	@Override
	@Transactional
	public DeveloperDTO removeDeveloperFromProject(Integer id,Integer projectId,Integer userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new BugTrackingException("User Doesn't exist"));
		if (!user.getUserRole().equals(UserRole.ADMIN)) {
			throw new BugTrackingException("Only Admin can access");
		}
		Developer developer = developerRepository.findById(id).orElseThrow(() -> new BugTrackingException("Developer Doesn't exist"));
		Project project = projectRepo.findById(projectId).orElseThrow(() -> new BugTrackingException("Project Doesn't exist"));
		
		
		if (!project.getCreatedBy().equals(user)) {
			throw new BugTrackingException("User is not authorized");
		}
		if (developer.getProject()==null) {
			throw new BugTrackingException("Developer has not joined any project");
		}
		if (!project.getDevelopers().contains(developer)) {
	        throw new BugTrackingException("Developer has not joined the project");
	    }
		project.getDevelopers().remove(developer);
		developer.setProject(null);
		return modelMapper.map(developer, DeveloperDTO.class);
	}
	
	@Override
	@Transactional
	public DeveloperDTO updateDeveloperByName(Integer id, String name) {
		if (name.isEmpty() || name.isBlank()) {
			throw new BugTrackingException("Name should be valid");
		}
		
		Developer developer=developerRepository.findById(id).orElseThrow(() -> new BugTrackingException("Developer doesn't exist"));
		developer.setName(name);
		
		Developer updatedDeveloper=developerRepository.save(developer);
		return modelMapper.map(updatedDeveloper, DeveloperDTO.class);
	}
	
	@Override
	@Transactional
	public DeveloperDTO updateDeveloperBySkill(Integer id, String skill) {
		if (skill.isEmpty() || skill.isBlank()) {
			throw new BugTrackingException("Skill should be valid");
		}
		Developer developer=developerRepository.findById(id).orElseThrow(() -> new BugTrackingException("Developer doesn't exist"));
		developer.setSkill(skill);
		
		Developer updatedSkill=developerRepository.save(developer);
		
		return modelMapper.map(updatedSkill, DeveloperDTO.class);
	}
	
	@Override
	public DeveloperDTO getDeveloperById(Integer id) {
		Developer developer=developerRepository.findById(id).orElseThrow(() -> new BugTrackingException("Developer not found with ID: "+id));
		return modelMapper.map(developer, DeveloperDTO.class);
	}
	
	
	@Override
	@Transactional
	public List<DeveloperDTO> getAllDevelopers() {
		List<Developer> developers=developerRepository.findAll();
		if(developers.isEmpty()) {
			throw new BugTrackingException("No developers in the system");
		}
		List<DeveloperDTO> resultDevelopers = new ArrayList<>();
		for(Developer dev: developers) {
			DeveloperDTO resultDev = modelMapper.map(dev, DeveloperDTO.class);
			resultDevelopers.add(resultDev);
		}
		return resultDevelopers;
		
	}
	
	@Override
	@Transactional
	public DeveloperDTO deleteDeveloper(Integer id) {
		Developer developer=developerRepository.findById(id).orElseThrow( () -> new BugTrackingException("Developer has not found with ID: "+id));
		Set<Bug> bugs=developer.getBugs();
		for(Bug bug:bugs) {
			bug.setAssignedTo(null);
		}
		Project project = developer.getProject();
		if (project!=null) {
			project.getDevelopers().remove(developer);
		}
		developerRepository.delete(developer);
		return modelMapper.map(developer, DeveloperDTO.class);
	}
	
	@Override
	public ProjectDTO getDeveloperProject(Integer id) {
		Developer developer=developerRepository.findById(id).orElseThrow( () -> new BugTrackingException("Developer doesn't exists."));
		Project project = developer.getProject();
		if (project==null) {
			throw new BugTrackingException("User Has not Assigned a project yet");
		}
		return modelMapper.map(project, ProjectDTO.class);
	}
	
}

