package com.bugtrackingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bugtrackingsystem.dto.UserDTO;
import com.bugtrackingsystem.dto.UserSignInDTO;
import com.bugtrackingsystem.exception.BugTrackingException;
import com.bugtrackingsystem.service.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	IUserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO userDTO) throws BugTrackingException {
		return new ResponseEntity<>(userService.register(userDTO), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<UserDTO> signIn(@Valid @RequestBody UserSignInDTO userSginInDTO) throws BugTrackingException {
		return new ResponseEntity<>(userService.signIn(userSginInDTO), HttpStatus.OK);
	}
}

