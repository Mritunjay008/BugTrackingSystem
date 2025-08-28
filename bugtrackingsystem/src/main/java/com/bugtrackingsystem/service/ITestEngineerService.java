package com.bugtrackingsystem.service;

import java.util.List;

import com.bugtrackingsystem.dto.DeveloperDTO;
import com.bugtrackingsystem.dto.ProjectDTO;
import com.bugtrackingsystem.dto.TestEngineerDTO;
import com.bugtrackingsystem.dto.TesterChangePasswordDTO;

public interface ITestEngineerService {
TestEngineerDTO addTestEngineerToProject(Integer testEngId, Integer projectId, Integer userId);
	
	TestEngineerDTO removeTestEngineerFromProject(Integer testEngId, Integer projectId, Integer userId);

	TestEngineerDTO updateTestEngineerName(Integer id, String name);
	
	TestEngineerDTO updateTestEngineerSkill(Integer id, String skill);
	
	TestEngineerDTO updateTestEngineerPassword(TesterChangePasswordDTO testerPasswordDTO);

	TestEngineerDTO getById(Integer testEngineerId);

	List<TestEngineerDTO> getAll();
	
	ProjectDTO getProjectByTester(Integer id);
	
	DeveloperDTO assignBugToDeveloper(Integer id, Integer devId, Integer userId);
	
	String deleteById(Integer id);
}
