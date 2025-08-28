package com.bugtrackingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrackingsystem.model.Developer;

public interface DeveloperRepository extends JpaRepository<Developer, Integer>{

}

