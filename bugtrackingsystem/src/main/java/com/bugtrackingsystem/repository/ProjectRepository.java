package com.bugtrackingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrackingsystem.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{

}

