package com.bugtrackingsystem.service;

import java.time.LocalDate;
import java.util.List;

import com.bugtrackingsystem.dto.AddBugDTO;
import com.bugtrackingsystem.dto.BugDTO;
import com.bugtrackingsystem.exception.BugTrackingException;
import com.bugtrackingsystem.util.BugStatus;
import com.bugtrackingsystem.util.Severity;

public interface IBugService {
	BugDTO createBug(AddBugDTO addBugDTO);
	BugDTO findBugById(Integer bugId);
	List<BugDTO> findAllBugs();
	
	// Filters by entity relationships
	List<BugDTO> findBugsByProjectId (Integer projId);
	List<BugDTO> findBugsByDeveloperId(Integer devId);
	List<BugDTO> findBugsByTestEngineerId(Integer testEngId);
	
	// Filters by bug attributes
	List<BugDTO> findBugsByDeveloperIdAndStatus(Integer devId, BugStatus status);
	List<BugDTO> findBugsByStatus(BugStatus status);
	List<BugDTO> findBugsBySeverity(Severity severity);
	List<BugDTO> findBugsByDate(LocalDate date);
	List<BugDTO> findBugsByProjectIdAfterDate(Integer projId, LocalDate date);
	
	void deleteBugById(Integer bugId) throws BugTrackingException;
	BugDTO updateBugStatus(int bugId, int testerId, BugStatus status);
	BugDTO updateBugSeverity(int bugId, Severity severity, int userId);
	BugDTO updateBug(int bugId, String title, String description);
	
}

