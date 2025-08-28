package com.bugtrackingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bugtrackingsystem.dto.DeveloperDTO;
import com.bugtrackingsystem.dto.ProjectDTO;
import com.bugtrackingsystem.service.IDeveloperService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/developer")
@Validated
public class DeveloperController {
	
	@Autowired
	private IDeveloperService developerService;
	
	@PostMapping("/addDeveloperToProject/{id}")
	public ResponseEntity<DeveloperDTO> addDeveloperToProject(@PathVariable @Min(value=1, message="ID must be a positive number") Integer id, 
			@RequestParam @Min(value=1, message="ID must be a positive number") Integer projectId,
			@RequestParam @Min(value=1, message="ID must be a positive number") Integer userId)
	{
		DeveloperDTO developer=developerService.addDeveloperToProject(id,projectId,userId);
		return ResponseEntity.ok(developer);
	}
	
	@PostMapping("/removeFromProject/{id}")
	public ResponseEntity<DeveloperDTO> removeDeveloperFromProject(@PathVariable @Min(value=1, message="ID must be a positive number") Integer id, 
			@RequestParam @Min(value=1, message="ID must be a positive number") Integer projectId,
			@RequestParam @Min(value=1, message="ID must be a positive number") Integer userId) 
	{
		DeveloperDTO developer=developerService.removeDeveloperFromProject(id,projectId,userId);
		return ResponseEntity.ok(developer);
	}
	
	@PutMapping("updateByName/{id}")
	public ResponseEntity<DeveloperDTO> updateDeveloperByName(@PathVariable @Min(value=1, message="ID must be a positive number") Integer id, @RequestParam @NotEmpty @NotBlank String name){
		DeveloperDTO developer=developerService.updateDeveloperByName(id,name);
		return ResponseEntity.ok(developer);
	}
	
	@PutMapping("updateBySkill/{id}")
	public ResponseEntity<DeveloperDTO> updateDeveloperBySkill(@PathVariable @Min(value=1, message="ID must be a positive number") Integer id, @RequestParam @NotEmpty @NotBlank String skill){
		DeveloperDTO developer=developerService.updateDeveloperBySkill(id,skill);
		return ResponseEntity.ok(developer);
	}
	
	//get developer by id
	@GetMapping("/getDeveloperById/{id}")
	public ResponseEntity<DeveloperDTO> getDeveloperById(@PathVariable @Min(value=1, message="ID must be a positive number") Integer id){
		DeveloperDTO developer=developerService.getDeveloperById(id);
		return ResponseEntity.ok(developer);
	}
	
	//get all developers
	@GetMapping("/getAllDevelopers")
	public ResponseEntity<List<DeveloperDTO>> getAllDevelopers(){
		List<DeveloperDTO> developers=developerService.getAllDevelopers();
		return ResponseEntity.ok(developers);
	}
	
	
//	delete
	@DeleteMapping("/deleteDeveloper/{id}")
	public ResponseEntity<DeveloperDTO> deleteDeveloper(@PathVariable @Min(value=1, message="ID must be a positive number") Integer id){
		DeveloperDTO developerDTO=developerService.deleteDeveloper(id);
		return ResponseEntity.ok(developerDTO);
	}
	
	// get project
	@GetMapping("/getDeveloperProject/{id}")
	public ResponseEntity<ProjectDTO> getDeveloperProject(@PathVariable @NotNull Integer id){
		ProjectDTO project=developerService.getDeveloperProject(id);
		return ResponseEntity.ok(project);
	}
}

