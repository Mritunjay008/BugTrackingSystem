package com.bugtrackingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bugtrackingsystem.dto.DeveloperDTO;
import com.bugtrackingsystem.dto.ProjectDTO;
import com.bugtrackingsystem.dto.TestEngineerDTO;
import com.bugtrackingsystem.dto.TesterChangePasswordDTO;
import com.bugtrackingsystem.service.ITestEngineerService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/testEngineer")
@Validated
public class TestEngineerController {
	
	@Autowired
	ITestEngineerService testEngineerService;
	
	@PatchMapping("/addToProject/{id}")
	public ResponseEntity<TestEngineerDTO> addTestEngineerToProject(@PathVariable @Min(value=1, message="ID must be a positive number") Integer id, 
			@RequestParam @Min(value=1, message="ID must be a positive number") Integer projectId,
			@RequestParam @Min(value=1, message="ID must be a positive number") Integer userId
		) 
	{
		return new ResponseEntity<>(testEngineerService.addTestEngineerToProject(id, projectId, userId), HttpStatus.ACCEPTED);
	}
	
	@PatchMapping("/removeFromProject/{id}")
	public ResponseEntity<TestEngineerDTO> removeTestEngineerFromProject(@PathVariable @Min(value=1, message="ID must be a positive number") Integer id, 
			@RequestParam @Min(value=1, message="ID must be a positive number") Integer projectId,
			@RequestParam @Min(value=1, message="ID must be a positive number") Integer userId
			) 
	{
		return new ResponseEntity<>(testEngineerService.removeTestEngineerFromProject(id, projectId, userId), HttpStatus.ACCEPTED);
	}

	@PatchMapping("/updateName/{id}")
	public ResponseEntity<TestEngineerDTO> updateTestEngineerName(@PathVariable @Min(value=1, message="ID must be a positive number") Integer id, 
			@RequestParam @NotEmpty @NotBlank String name) 
	{
		return new ResponseEntity<>(testEngineerService.updateTestEngineerName(id, name), HttpStatus.OK);
	}
	
	@PatchMapping("/updateSkill/{id}")
	public ResponseEntity<TestEngineerDTO> updateTestEngineerSkill(@PathVariable @Min(value=1, message="ID must be a positive number") Integer id, 
			@RequestParam @NotEmpty @NotBlank String skill) 
	{
		return new ResponseEntity<>(testEngineerService.updateTestEngineerSkill(id, skill), HttpStatus.OK);
	}
	
	@PatchMapping("/updatePassword")
	public ResponseEntity<TestEngineerDTO> updateTestEngineerPassword(@Valid @RequestBody TesterChangePasswordDTO testerPasswordDto) {
		return new ResponseEntity<>(testEngineerService.updateTestEngineerPassword(testerPasswordDto), HttpStatus.OK);
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<TestEngineerDTO> getById(@PathVariable @Min(value=1, message="ID must be a positive number") Integer id) {
		return new ResponseEntity<>(testEngineerService.getById(id), HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<TestEngineerDTO>> getAllTestEngineers() {
		return new ResponseEntity<>(testEngineerService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/getProjectByTester/{id}")
	public ResponseEntity<ProjectDTO> getProjectByTester(@PathVariable @Min(value=1, message="ID must be a positive number") Integer id) {
		return new ResponseEntity<>(testEngineerService.getProjectByTester(id), HttpStatus.OK);
	}
	
	@PostMapping("/assignBugToDeveloper/{bugId}")
	public ResponseEntity<DeveloperDTO> assignBugToDeveloper(@PathVariable @Min(value=1, message="ID must be a positive number") Integer bugId, 
			@RequestParam @Min(value=1, message="ID must be a positive number") Integer devId, 
			@RequestParam @Min(value=1, message="ID must be a positive number") Integer userId) 
	{
		return new ResponseEntity<>(testEngineerService.assignBugToDeveloper(bugId, devId, userId), HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<String> deleteById(@PathVariable @Min(value=1, message="ID must be a positive number") Integer id) {
		return new ResponseEntity<>(testEngineerService.deleteById(id), HttpStatus.OK);
	}
}
