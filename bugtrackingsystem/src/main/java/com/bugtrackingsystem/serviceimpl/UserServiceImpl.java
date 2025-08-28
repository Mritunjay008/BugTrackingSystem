package com.bugtrackingsystem.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugtrackingsystem.dto.UserDTO;
import com.bugtrackingsystem.dto.UserSignInDTO;
import com.bugtrackingsystem.exception.BugTrackingException;
import com.bugtrackingsystem.model.Developer;
import com.bugtrackingsystem.model.TestEngineer;
import com.bugtrackingsystem.model.User;
import com.bugtrackingsystem.repository.DeveloperRepository;
import com.bugtrackingsystem.repository.TestEngineerRepository;
import com.bugtrackingsystem.repository.UserRepository;
import com.bugtrackingsystem.service.IUserService;
import com.bugtrackingsystem.util.UserRole;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	DeveloperRepository developerRepo;
	
	@Autowired
	TestEngineerRepository testEngineerRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	@Transactional
	public UserDTO register(UserDTO userDTO) throws BugTrackingException {
		if (userDTO.getUserRole().equals(UserRole.ADMIN)) {
			throw new BugTrackingException("Not authorized to be a Admin");
		}
		if (userRepo.existsByEmail(userDTO.getEmail())) {
			throw new BugTrackingException("User with Email Already Exists");
		}
		User user;
		if (userDTO.getUserRole().equals(UserRole.DEVELOPER)) {
			Developer dev = new Developer();
		    dev.setEmail(userDTO.getEmail());
		    dev.setName(userDTO.getName());
		    dev.setPassword(userDTO.getPassword());
		    dev.setUserRole(userDTO.getUserRole());
		    dev.setBugs(null);
		    dev.setProject(null);
		    dev.setSkill(null);
		    developerRepo.save(dev);
		    return modelMapper.map(dev, UserDTO.class);
		} else {
			user = new TestEngineer(userDTO);
			userRepo.save(user);
			return modelMapper.map(user, UserDTO.class);
		}
	}
	
	@Override
	public UserDTO signIn(UserSignInDTO userDTO) throws BugTrackingException {
		User user = userRepo.findByEmail(userDTO.getEmail());
		if (user==null) {
			throw new BugTrackingException("User with email doesn't exist");
		}
		if (!user.getPassword().equals(userDTO.getPassword())) {
			throw new BugTrackingException("Invalid Password");
		}
		return modelMapper.map(user, UserDTO.class);
	}
}


