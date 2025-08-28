package com.bugtrackingsystem.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.bugtrackingsystem.dto.DeveloperDTO;
import com.bugtrackingsystem.dto.ProjectDTO;
import com.bugtrackingsystem.dto.TestEngineerDTO;
import com.bugtrackingsystem.dto.TesterChangePasswordDTO;
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
import com.bugtrackingsystem.service.ITestEngineerService;
import com.bugtrackingsystem.util.BugStatus;
import com.bugtrackingsystem.util.UserRole;

import jakarta.transaction.Transactional;

@Service
public class TestEngineerServiceImpl implements ITestEngineerService {
private final TestEngineerRepository testEngineerRepo;
	
	private final ProjectRepository projectRepo;
	
	private final DeveloperRepository developerRepo;
	
	private final BugRepository bugRepo;
	
	private final ModelMapper modelMapper;
	
	private final UserRepository userRepository;
	
	public TestEngineerServiceImpl(TestEngineerRepository testEngineerRepo, ProjectRepository projectRepo,
			DeveloperRepository developerRepo, BugRepository bugRepo, UserRepository userRepository,
			ModelMapper modelMapper) {
		this.testEngineerRepo = testEngineerRepo;
		this.projectRepo = projectRepo;
		this.developerRepo = developerRepo;
		this.bugRepo = bugRepo;
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
	}
	
	private static final String USEREXCEPTIONMESSAGE = "User Doesn't exist";
	private static final String TESTEREXCEPTIONMESSAGE = "Test Engineer Doesn't exist";
	private static final String PROJECTEXCEPTIONMESSAGE = "Project Doesn't exist";
	
	@Override
	@Transactional
	public TestEngineerDTO addTestEngineerToProject(Integer testEngId, Integer projectId, Integer userId) throws BugTrackingException {
		User user = userRepository.findById(userId).orElseThrow(() -> new BugTrackingException(USEREXCEPTIONMESSAGE));
		if (!user.getUserRole().equals(UserRole.ADMIN)) {
			throw new BugTrackingException("Only Admin can access");
		}
		TestEngineer testEngineer = testEngineerRepo.findById(testEngId).orElseThrow(() -> new BugTrackingException(TESTEREXCEPTIONMESSAGE));
		Project project = projectRepo.findById(projectId).orElseThrow(() -> new BugTrackingException(PROJECTEXCEPTIONMESSAGE));
		if (!project.getCreatedBy().equals(user)) {
			throw new BugTrackingException("User is not authorized");
		}
		if (project.getTestEngineers().contains(testEngineer)) {
			throw new BugTrackingException("TestEngineer has already joined that project");
		}
		if (testEngineer.getProject()!=null) {
			throw new BugTrackingException("TestEngineer has joined other project");
		}
		project.getTestEngineers().add(testEngineer);
		testEngineer.setProject(project);
		return modelMapper.map(testEngineer, TestEngineerDTO.class);
	}
	
	@Override
	@Transactional
	public TestEngineerDTO removeTestEngineerFromProject(Integer testEngId, Integer projectId, Integer userId) throws BugTrackingException {
		User user = userRepository.findById(userId).orElseThrow(() -> new BugTrackingException(USEREXCEPTIONMESSAGE));
		if (!user.getUserRole().equals(UserRole.ADMIN)) {
			throw new BugTrackingException("Only Admin can access");
		}
		TestEngineer testEngineer = testEngineerRepo.findById(testEngId).orElseThrow(() -> new BugTrackingException(TESTEREXCEPTIONMESSAGE));
		Project project = projectRepo.findById(projectId).orElseThrow(() -> new BugTrackingException(PROJECTEXCEPTIONMESSAGE));
		if (!project.getCreatedBy().equals(user)) {
			throw new BugTrackingException("User is not authorized");
		}
		if (testEngineer.getProject()==null) {
			throw new BugTrackingException("TestEngineer has not joined any project");
		}
		if (!project.getTestEngineers().contains(testEngineer)) {
			throw new BugTrackingException("TestEngineer has not joined this project");
		}
		project.getTestEngineers().remove(testEngineer);
		testEngineer.setProject(null);
		return modelMapper.map(testEngineer, TestEngineerDTO.class);
	}

	@Override
	@Transactional
	public TestEngineerDTO updateTestEngineerName(Integer id, String name) throws BugTrackingException {
		TestEngineer testEngineer = testEngineerRepo.findById(id).orElseThrow(() -> new BugTrackingException(TESTEREXCEPTIONMESSAGE));
		testEngineer.setName(name);
		return modelMapper.map(testEngineer, TestEngineerDTO.class);
	}
	
	@Override
	@Transactional
	public TestEngineerDTO updateTestEngineerSkill(Integer id, String skill) throws BugTrackingException {
		TestEngineer testEngineer = testEngineerRepo.findById(id).orElseThrow(() -> new BugTrackingException(TESTEREXCEPTIONMESSAGE));
		testEngineer.setSkill(skill);
		return modelMapper.map(testEngineer, TestEngineerDTO.class);
	}
	
	@Override
	@Transactional
	public TestEngineerDTO updateTestEngineerPassword(TesterChangePasswordDTO testerPasswordDTO) throws BugTrackingException {
		TestEngineer testEngineer = testEngineerRepo.findById(testerPasswordDTO.getId()).orElseThrow(() -> new BugTrackingException(TESTEREXCEPTIONMESSAGE));
		
		if (!testEngineer.getPassword().equals(testerPasswordDTO.getOldPassword())) {
			throw new BugTrackingException("Wrong Password");
		}
		testEngineer.setPassword(testerPasswordDTO.getNewPassword());
		return modelMapper.map(testEngineer, TestEngineerDTO.class);
	}

	@Override
	public TestEngineerDTO getById(Integer testEngineerId) throws BugTrackingException {
		TestEngineer testEngineer = testEngineerRepo.findById(testEngineerId).orElseThrow(() -> new BugTrackingException(TESTEREXCEPTIONMESSAGE));
		return modelMapper.map(testEngineer, TestEngineerDTO.class);
	}

	@Override
	@Transactional
	public List<TestEngineerDTO> getAll() throws BugTrackingException {
		List<TestEngineer> testEngineers = testEngineerRepo.findAll();
		if (testEngineers.isEmpty()) {
			throw new BugTrackingException("No Test Engineer is present");
		}
		List<TestEngineerDTO> resTestEngineers = new ArrayList<>();
		for(TestEngineer tester: testEngineers) {
			TestEngineerDTO resTester = modelMapper.map(tester, TestEngineerDTO.class);
			resTestEngineers.add(resTester);
		}
		return resTestEngineers;
	}

	@Override
	public ProjectDTO getProjectByTester(Integer id) throws BugTrackingException {
		TestEngineer testEngineer = testEngineerRepo.findById(id).orElseThrow(() -> new BugTrackingException(TESTEREXCEPTIONMESSAGE));
		Project project = testEngineer.getProject();
		if (project==null) {
			throw new BugTrackingException("Test Engineer Has not Assigned a project yet");
		}
		return modelMapper.map(project, ProjectDTO.class);
	}
	
	@Override
	@Transactional
	public DeveloperDTO assignBugToDeveloper(Integer bugId, Integer devId, Integer userId) throws BugTrackingException {
		User user = userRepository.findById(userId).orElseThrow(() -> new BugTrackingException(USEREXCEPTIONMESSAGE));
		if (user.getUserRole().equals(UserRole.DEVELOPER)) {
			throw new BugTrackingException("Developer can't assign a bug");
		}
		Bug bug = bugRepo.findById(bugId).orElseThrow(() -> new BugTrackingException("Bug Doesn't exist"));
		Project proj = bug.getProject();
		if (user.getUserRole().equals(UserRole.ADMIN) && !((Admin)user).getProjects().contains(proj)) {
			throw new BugTrackingException("Admin is not authorized to assign bug for this Developer");
		} else if(user.getUserRole().equals(UserRole.TESTENGINEER) && !((TestEngineer)user).getBugs().contains(bug)){
			throw new BugTrackingException("TestEngineer is not authorized to assign bug for this Developer");
		}
		Developer dev = developerRepo.findById(devId).orElseThrow(() -> new BugTrackingException("Developer Doesn't exist"));
		if (!proj.getDevelopers().contains(dev)) {
			throw new BugTrackingException("Developer is not assiged the project");
		}
		bug.setAssignedTo(dev);
		dev.getBugs().add(bug);
		bug.setBugStatus(BugStatus.IN_PROGRESS);
		return modelMapper.map(dev, DeveloperDTO.class);
	}
	
	@Override
	@Transactional
	public String deleteById(Integer id) throws BugTrackingException {
		TestEngineer tester = testEngineerRepo.findById(id).orElseThrow(() -> new BugTrackingException(TESTEREXCEPTIONMESSAGE));
		Set<Bug> bugs = tester.getBugs();
		for(Bug bug: bugs) {
			bug.setCreatedBy(null);
		}
		Project project = tester.getProject();
		if (project!=null) {
			project.getTestEngineers().remove(tester);
		}
		testEngineerRepo.delete(tester);
		return "Test Engineer Removed";
	}
}

