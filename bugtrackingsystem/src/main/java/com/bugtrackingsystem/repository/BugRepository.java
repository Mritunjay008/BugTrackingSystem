package com.bugtrackingsystem.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bugtrackingsystem.model.Bug;
import com.bugtrackingsystem.util.BugStatus;
import com.bugtrackingsystem.util.Severity;
@Repository
public interface BugRepository extends JpaRepository<Bug, Integer>{
	List<Bug> findByBugStatus(BugStatus bugStatus);

	List<Bug> findBugsBySeverity(Severity severity);

	List<Bug> findBugsByStartDate(LocalDate date);
}

