package com.bugtrackingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrackingsystem.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

}

