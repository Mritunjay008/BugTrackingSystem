package com.bugtrackingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrackingsystem.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	boolean existsByEmail(String email);
	User findByEmail(String email);
}

