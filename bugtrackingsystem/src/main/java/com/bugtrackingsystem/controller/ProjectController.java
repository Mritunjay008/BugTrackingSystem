package com.bugtrackingsystem.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bugtrackingsystem.dto.BugDTO;
import com.bugtrackingsystem.dto.DeveloperDTO;
import com.bugtrackingsystem.dto.ProjectDTO;
import com.bugtrackingsystem.dto.TestEngineerDTO;
import com.bugtrackingsystem.serviceimpl.ProjectServiceImpl;
import com.bugtrackingsystem.util.ProjStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects")
public class ProjectController {
	@Autowired
	ProjectServiceImpl projService;

	@PostMapping("/create")
	public ResponseEntity<ProjectDTO> create(@Valid @RequestBody ProjectDTO dto) {
		ProjectDTO created = projService.createProject(dto,dto.getUserId());
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	@GetMapping("/viewAll")
	public ResponseEntity<List<ProjectDTO>> getAllProjects() {
		List<ProjectDTO> projects = projService.getAllProjects();
		return new ResponseEntity<>(projects, HttpStatus.OK);
	}

	@GetMapping("/view/{id}")
	public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Integer id) {
		return new ResponseEntity<>(projService.getProjectById(id), HttpStatus.OK);
	}

	@GetMapping("/{projId}/viewDeveloper")
	public ResponseEntity<List<DeveloperDTO>> getDevelopersById(@PathVariable Integer projId) {
		List<DeveloperDTO> developers = projService.getDevelopersByProjectId(projId);
		return new ResponseEntity<>(developers, HttpStatus.OK);
	}
	

	@GetMapping("/{projId}/viewTestEngineer")
	public ResponseEntity<List<TestEngineerDTO>> getTestEngineersById(@PathVariable Integer projId) {
		List<TestEngineerDTO> testEngineers = projService.getTestEngineersByProjectId(projId);
		return new ResponseEntity<>(testEngineers, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public String deleteProject(@PathVariable Integer id, @RequestParam Integer userId) {
		projService.deleteProject(id, userId);
		return "project deleted";
	}
	
	@GetMapping("/{projId}/viewAllBugs")
	public ResponseEntity<List<BugDTO>> getAllBugsByProjectId(@PathVariable Integer projId)
	{
		List<BugDTO>bugs=projService.getBugsByProjectId(projId);
		return new ResponseEntity<>(bugs,HttpStatus.OK);
	}
	
	@PatchMapping("/{projId}/updateProjStatus")
	public ResponseEntity<ProjectDTO>updateProjectStatus(@PathVariable Integer projId, @RequestParam ProjStatus projStatus)
	{
		return new ResponseEntity<>(( projService.updateStatus(projId, projStatus)),HttpStatus.OK);
	}
	
	@PatchMapping("/{projId}/updateName")
	public ResponseEntity<ProjectDTO>updateName(@PathVariable Integer projId, @RequestParam String name)
	{
		return new ResponseEntity<>(( projService.updateName(projId, name)),HttpStatus.OK);
	}
	
	@PatchMapping("/{projId}/updateDescription")
	public ResponseEntity<ProjectDTO>updateDescription(@PathVariable Integer projId, @RequestParam String description)
	{
		return new ResponseEntity<>(( projService.updateDescription(projId, description)),HttpStatus.OK);
	}
	
	@PatchMapping("/{projId}/updateDeadline")
	public ResponseEntity<ProjectDTO>updateDeadline(@PathVariable Integer projId, @RequestParam LocalDate date)
	{
		return new ResponseEntity<>(( projService.updateDeadline(projId, date)),HttpStatus.OK);
	}
	
}

