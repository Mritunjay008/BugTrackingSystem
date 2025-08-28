package com.bugtrackingsystem.service;

import com.bugtrackingsystem.dto.UserDTO;
import com.bugtrackingsystem.dto.UserSignInDTO;
import com.bugtrackingsystem.exception.BugTrackingException;

public interface IUserService {
	UserDTO register(UserDTO userDTO) throws BugTrackingException;
	UserDTO signIn(UserSignInDTO userSignInDTO) throws BugTrackingException;
}

