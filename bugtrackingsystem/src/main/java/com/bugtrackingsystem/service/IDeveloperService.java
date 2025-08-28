package com.bugtrackingsystem.service;

import java.util.List;

import com.bugtrackingsystem.dto.DeveloperDTO;
import com.bugtrackingsystem.dto.ProjectDTO;

public interface IDeveloperService {
	
DeveloperDTO addDeveloperToProject(Integer id,Integer projectId,Integer userId);
	
	DeveloperDTO removeDeveloperFromProject(Integer id,Integer projectId, Integer userId);

	DeveloperDTO updateDeveloperByName(Integer id, String name);
	
	DeveloperDTO updateDeveloperBySkill(Integer id, String skill);

	DeveloperDTO getDeveloperById(Integer id);

	List<DeveloperDTO> getAllDevelopers();
	
	DeveloperDTO deleteDeveloper(Integer id);

	ProjectDTO getDeveloperProject(Integer id);
}

