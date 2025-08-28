package com.bugtrackingsystem.serviceimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugtrackingsystem.dto.AddBugDTO;
import com.bugtrackingsystem.dto.BugDTO;
import com.bugtrackingsystem.exception.BugTrackingException;
import com.bugtrackingsystem.model.Admin;
import com.bugtrackingsystem.model.Bug;
import com.bugtrackingsystem.model.Developer;
import com.bugtrackingsystem.model.Project;
import com.bugtrackingsystem.model.TestEngineer;
import com.bugtrackingsystem.model.User;
import com.bugtrackingsystem.repository.BugRepository;
import com.bugtrackingsystem.repository.DeveloperRepository;
import com.bugtrackingsystem.repository.ProjectRepository;
import com.bugtrackingsystem.repository.TestEngineerRepository;
import com.bugtrackingsystem.repository.UserRepository;
import com.bugtrackingsystem.service.IBugService;
import com.bugtrackingsystem.util.BugStatus;
import com.bugtrackingsystem.util.Severity;
import com.bugtrackingsystem.util.UserRole;

import jakarta.transaction.Transactional;

@Service
public class BugServiceImpl implements IBugService {

	BugRepository bugRepo;

	ModelMapper modelMap;

	ProjectRepository projectRepo;
	
	DeveloperRepository devRepo;
	
	TestEngineerRepository testEngRepo;
	
	UserRepository userRepo;
	
	@Autowired
	public BugServiceImpl(BugRepository bugRepo, ModelMapper modelMap, ProjectRepository projectRepo,
			DeveloperRepository devRepo, TestEngineerRepository testEngRepo, UserRepository userRepo) {
		super();
		this.bugRepo = bugRepo;
		this.modelMap = modelMap;
		this.projectRepo = projectRepo;
		this.devRepo = devRepo;
		this.testEngRepo = testEngRepo;
		this.userRepo = userRepo;
	}
	
	@Override
	@Transactional
	public BugDTO createBug(AddBugDTO bugDto) {
		Project project = projectRepo.findById(bugDto.getProjectId()).orElseThrow(() -> new BugTrackingException("Project doesn't exist"));
		TestEngineer tester = testEngRepo.findById(bugDto.getTestEngineerId()).orElseThrow(() -> new BugTrackingException("TestEngineer doesn't exist"));
		if (!project.getTestEngineers().contains(tester)) {
			throw new BugTrackingException("tester is not authorized to created this bug");
		}
		Bug bug = modelMap.map(bugDto, Bug.class);
		bug.setBugStatus(BugStatus.NEW);
		bug.setProject(project);
		project.getBugs().add(bug);
		bug.setCreatedBy(tester);
		tester.getBugs().add(bug);
		bug.setStartDate(LocalDate.now());
		bug.setLastUpdatedDate(LocalDate.now());
		bug = bugRepo.save(bug);
		return modelMap.map(bug, BugDTO.class);
	}
	
	private static final String BUGWITHID = "Bug with id : ";
	private static final String DOESNOTEXIST = " does not exist";

	@Override
	@Transactional
	public BugDTO updateBugStatus(int bugId, int userId, BugStatus status) {
	    Bug bug = bugRepo.findById(bugId)
	            .orElseThrow(() -> new BugTrackingException(BUGWITHID + bugId + DOESNOTEXIST));

	    User user = userRepo.findById(userId)
	            .orElseThrow(() -> new BugTrackingException("User Doesn't exist"));

	    if (user.getUserRole() == UserRole.TESTENGINEER) {
	        handleTestEngineerStatusUpdate(bug, user, status);
	    } else if (user.getUserRole() == UserRole.DEVELOPER) {
	        handleDeveloperStatusUpdate(bug, user, status);
	    }

	    bug.setBugStatus(status);
	    return modelMap.map(bugRepo.save(bug), BugDTO.class);
	}



	private void handleTestEngineerStatusUpdate(Bug bug, User user, BugStatus status) {
	    if (!bug.getCreatedBy().getId().equals(user.getId())) {
	        throw new BugTrackingException("Test Engineer is not authorized");
	    }
	    if (status == BugStatus.NEW || status == BugStatus.IN_PROGRESS) {
	        throw new BugTrackingException("These status can't be filled by TestEngineer");
	    }
	    if (bug.getBugStatus() == BugStatus.IN_PROGRESS) {
	        throw new BugTrackingException("Developer is currently working on that bug");
	    }
	}

	private void handleDeveloperStatusUpdate(Bug bug, User user, BugStatus status) {
	    if (!bug.getAssignedTo().getId().equals(user.getId())) {
	        throw new BugTrackingException("Developer is not authorized");
	    }
	    if (status == BugStatus.NEW || status == BugStatus.CLOSED || status == BugStatus.RE_OPEN) {
	        throw new BugTrackingException("These status can't be filled by Developer");
	    }
	    if (bug.getBugStatus() != BugStatus.RE_OPEN && bug.getBugStatus() != BugStatus.IN_PROGRESS) {
	        throw new BugTrackingException("Developer is not working on that bug");
	    }
	}

	@Override
	@Transactional
	public BugDTO updateBugSeverity(int bugId, Severity severity, int userId) {
	    Bug bug = bugRepo.findById(bugId)
	            .orElseThrow(() -> new BugTrackingException(BUGWITHID + bugId + DOESNOTEXIST));

	    User user = userRepo.findById(userId)
	            .orElseThrow(() -> new BugTrackingException("user Doesn't exist"));

	    if (user.getUserRole() == UserRole.TESTENGINEER) {
	        validateTestEngineerProjectAccess((TestEngineer) user, bug);
	    } else if (user.getUserRole() == UserRole.DEVELOPER) {
	        validateDeveloperProjectAccess((Developer) user, bug);
	    } else if (user.getUserRole() == UserRole.ADMIN) {
	        validateAdminProjectAccess((Admin) user, bug);
	    }

	    bug.setSeverity(severity);
	    return modelMap.map(bugRepo.save(bug), BugDTO.class);
	}


	private void validateTestEngineerProjectAccess(TestEngineer user, Bug bug) {
	    Project project = user.getProject();
	    if (project == null) {
	        throw new BugTrackingException("TestEngineer has not assigned a project");
	    }
	    if (!project.equals(bug.getProject())) {
	        throw new BugTrackingException("TestEngineer has not joined this project");
	    }
	}

	private void validateDeveloperProjectAccess(Developer user, Bug bug) {
	    Project project = user.getProject();
	    if (project == null) {
	        throw new BugTrackingException("Developer has not assigned a project");
	    }
	    if (!project.equals(bug.getProject())) {
	        throw new BugTrackingException("Developer has not joined this project");
	    }
	}

	private void validateAdminProjectAccess(Admin user, Bug bug) {
	    if (!user.getProjects().contains(bug.getProject())) {
	        throw new BugTrackingException("Admin is not authorized");
	    }
	}



	@Override
	@Transactional
	public BugDTO updateBug(int bugId, String title, String description)
	{
		Bug existingBug = bugRepo.findById(bugId)
				.orElseThrow(() -> new BugTrackingException(BUGWITHID + bugId + DOESNOTEXIST));
		existingBug.setTitle(title);
		existingBug.setDescription(description);
		Bug updatedBug = bugRepo.save(existingBug);
		return modelMap.map(updatedBug, BugDTO.class);
	}
	
	@Override
	public BugDTO findBugById(Integer bugId) {
		Bug bug = bugRepo.findById(bugId)
				.orElseThrow(() -> new BugTrackingException(BUGWITHID + bugId + DOESNOTEXIST));
		return modelMap.map(bug, BugDTO.class);
	}

	@Override
	public List<BugDTO> findAllBugs() {
		List<Bug> bugList = bugRepo.findAll();
		List<BugDTO> bugDtoList = new ArrayList<>();
		for (Bug bug : bugList) {
			bugDtoList.add(modelMap.map(bug, BugDTO.class));
		}
		return bugDtoList;
	}

	

	@Override
	public List<BugDTO> findBugsByProjectId(Integer projId) {
		Project project = projectRepo.findById(projId)
				.orElseThrow(() -> new BugTrackingException("Project with id : " + projId + DOESNOTEXIST));
		Set<Bug> bugs = project.getBugs();
		List<BugDTO> bugDtoList = new ArrayList<>();
		for (Bug bug : bugs) {
			bugDtoList.add(modelMap.map(bug, BugDTO.class));
		}
		return bugDtoList;
	}

	@Override
	public List<BugDTO> findBugsByDeveloperId(Integer devId) {
		Developer dev = devRepo.findById(devId)
				.orElseThrow(() -> new BugTrackingException("Developer with id : " + devId + DOESNOTEXIST));
		Set<Bug> bugs = dev.getBugs();
		List<BugDTO> bugDtoList = new ArrayList<>();
		for (Bug bug : bugs) {
			bugDtoList.add(modelMap.map(bug, BugDTO.class));
		}
		return bugDtoList;
	}

	@Override
	public List<BugDTO> findBugsByDeveloperIdAndStatus(Integer devId, BugStatus status) {
		Developer dev = devRepo.findById(devId)
				.orElseThrow(() -> new BugTrackingException("Developer with id : " + devId + DOESNOTEXIST));
		Set<Bug> bugs = dev.getBugs();
		List<BugDTO> bugDtoList = new ArrayList<>();
		for (Bug bug : bugs) {
			if (bug.getBugStatus().equals(status))
				bugDtoList.add(modelMap.map(bug, BugDTO.class));
		}
		return bugDtoList;
	}

	@Override
	public List<BugDTO> findBugsByStatus(BugStatus bugStatus) {
		List<Bug> bugList = bugRepo.findByBugStatus(bugStatus);
		List<BugDTO> bugDtoList = new ArrayList<>();
		for (Bug bug : bugList) {
			bugDtoList.add(modelMap.map(bug, BugDTO.class));
		}
		return bugDtoList;
	}

	@Override
	public List<BugDTO> findBugsBySeverity(Severity severity) {
		List<Bug> bugList = bugRepo.findBugsBySeverity(severity);
		List<BugDTO> bugDtoList = new ArrayList<>();
		for (Bug bug : bugList) {
			bugDtoList.add(modelMap.map(bug, BugDTO.class));
		}
		return bugDtoList;
	}

	@Override
	public List<BugDTO> findBugsByDate(LocalDate date) {
		List<Bug> bugList = bugRepo.findBugsByStartDate(date);
		List<BugDTO> bugDtoList = new ArrayList<>();
		for (Bug bug : bugList) {
			bugDtoList.add(modelMap.map(bug, BugDTO.class));
		}
		return bugDtoList;
	}

	@Override
	public List<BugDTO> findBugsByTestEngineerId(Integer testEngId) {
		TestEngineer tester = testEngRepo.findById(testEngId)
				.orElseThrow(() -> new BugTrackingException("Tester with id : " + testEngId + DOESNOTEXIST));
		Set<Bug> bugs = tester.getBugs();
		List<BugDTO> bugDtoList = new ArrayList<>();
		for (Bug b : bugs) {
			bugDtoList.add(modelMap.map(b, BugDTO.class));
		}
		return bugDtoList;
	}

	@Override
	public List<BugDTO> findBugsByProjectIdAfterDate(Integer projId, LocalDate date) {
		Project project = projectRepo.findById(projId)
				.orElseThrow(() -> new BugTrackingException("Project with id : " + projId + DOESNOTEXIST));
		Set<Bug> bugList = project.getBugs();
		List<BugDTO> bugDtoList = new ArrayList<>();
		for (Bug bug : bugList) {
			if (bug.getLastUpdatedDate().isAfter(date))
				bugDtoList.add(modelMap.map(bug, BugDTO.class));
		}
		return bugDtoList;
	}

	@Override
	@Transactional
	public void deleteBugById(Integer bugId) throws BugTrackingException {
		Bug bug = bugRepo.findById(bugId)
				.orElseThrow(() -> new BugTrackingException(BUGWITHID + bugId + DOESNOTEXIST));
		TestEngineer tester = bug.getCreatedBy();
		if(tester != null)
			tester.getBugs().remove(bug);
		Developer dev = bug.getAssignedTo();
		if (dev != null)
			dev.getBugs().remove(bug);
		Project project = bug.getProject();
		project.getBugs().remove(bug);
		bugRepo.delete(bug);
	}
}
