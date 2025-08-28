package com.bugtrackingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrackingsystem.model.TestEngineer;

public interface TestEngineerRepository extends JpaRepository<TestEngineer, Integer>{
	boolean existsByEmail(String email);
}

