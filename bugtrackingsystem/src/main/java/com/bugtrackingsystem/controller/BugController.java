package com.bugtrackingsystem.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bugtrackingsystem.dto.AddBugDTO;
import com.bugtrackingsystem.dto.BugDTO;
import com.bugtrackingsystem.service.IBugService;
import com.bugtrackingsystem.util.BugStatus;
import com.bugtrackingsystem.util.Severity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bugs")
public class BugController {
	@Autowired
	IBugService bugService;

	@PostMapping("/create")
	public ResponseEntity<BugDTO> createBug(@Valid @RequestBody AddBugDTO dto) {
		BugDTO created = bugService.createBug(dto);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	@PutMapping("/updateSeverity/{id}")
	public ResponseEntity<BugDTO> updateBugSeverity(@PathVariable int id, @RequestParam Severity severity, @RequestParam int userId) {
		BugDTO updated = bugService.updateBugSeverity(id, severity, userId);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<BugDTO> updateBugStatus(@PathVariable int id, @RequestParam int userId, BugStatus status) {
		BugDTO updated = bugService.updateBugStatus(id, userId, status);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<BugDTO> updateBug(@PathVariable int id, @RequestParam String title,
			@RequestParam String description) {
		BugDTO updatedBug = bugService.updateBug(id, title, description);
		return new ResponseEntity<>(updatedBug, HttpStatus.OK);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<BugDTO> findBugById(@PathVariable int id) {
		BugDTO bug = bugService.findBugById(id);
		return new ResponseEntity<>(bug, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<BugDTO>> findAllBugs() {
		List<BugDTO> bugDtoList = bugService.findAllBugs();
		return new ResponseEntity<>(bugDtoList, HttpStatus.OK);
	}

	@GetMapping("/getBugsByProjectId/{projectId}")
	public ResponseEntity<List<BugDTO>> findBugsByProjectId(@PathVariable int projectId) {
		List<BugDTO> bugInProject = bugService.findBugsByProjectId(projectId);
		return new ResponseEntity<>(bugInProject, HttpStatus.OK);
	}

	@GetMapping("/findBugsBydeveloper/{devId}")
	public ResponseEntity<List<BugDTO>> findBugsByDeveloperId(@PathVariable int devId) {
		List<BugDTO> bugsForDev = bugService.findBugsByDeveloperId(devId);
		return new ResponseEntity<>(bugsForDev, HttpStatus.OK);
	}

	@GetMapping("/developer/{devId}/status/{status}")
	public ResponseEntity<List<BugDTO>> findBugsByDeveloperIdAndStatus(@PathVariable int devId,
			@PathVariable BugStatus status) {
		List<BugDTO> bugsForDevByStatus = bugService.findBugsByDeveloperIdAndStatus(devId, status);
		return new ResponseEntity<>(bugsForDevByStatus, HttpStatus.OK);
	}

	@GetMapping("/status/{status}")
	public ResponseEntity<List<BugDTO>> findBugsByStatus(@PathVariable BugStatus status) {
		List<BugDTO> bugsByStatus = bugService.findBugsByStatus(status);
		return new ResponseEntity<>(bugsByStatus, HttpStatus.OK);
	}

	@GetMapping("/severity/{severity}")
	public ResponseEntity<List<BugDTO>> findBugsBySeverity(@PathVariable Severity severity) {
		List<BugDTO> bugsBySeverity = bugService.findBugsBySeverity(severity);
		return new ResponseEntity<>(bugsBySeverity, HttpStatus.OK);
	}

	@GetMapping("/date/{date}")
	public ResponseEntity<List<BugDTO>> findBugsByDate(@PathVariable LocalDate date) {
		List<BugDTO> bugsByDate = bugService.findBugsByDate(date);
		return new ResponseEntity<>(bugsByDate, HttpStatus.OK);
	}

	@GetMapping("/findBugsByTestEngineer/{testEngId}")
	public ResponseEntity<List<BugDTO>> findBugsByTestEngineerId(@PathVariable int testEngId) {
		List<BugDTO> bugsByTestEngineer = bugService.findBugsByTestEngineerId(testEngId);
		return new ResponseEntity<>(bugsByTestEngineer, HttpStatus.OK);
	}

	@GetMapping("/project/{projectId}/after/{date}")
	public ResponseEntity<List<BugDTO>> findBugsByProjectIdAfterDate(@PathVariable int projectId,
			@PathVariable LocalDate date) {
		List<BugDTO> bugsInProjectAfterDate = bugService.findBugsByProjectIdAfterDate(projectId, date);
		return new ResponseEntity<>(bugsInProjectAfterDate, HttpStatus.OK);
	}

	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<String> deleteBug(@PathVariable int id) {
		bugService.deleteBugById(id);
		return new ResponseEntity<>("Bug with ID " + id + " deleted successfully.", HttpStatus.OK);
	}

}

