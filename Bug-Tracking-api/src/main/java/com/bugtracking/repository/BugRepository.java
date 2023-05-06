package com.bugtracking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bugtracking.entity.Bug;

public interface BugRepository extends JpaRepository<Bug, Integer> {

	Optional<Bug> findByStatus(String status);

	@Query(value = "Select bugDetail from Bug bugDetail where status=:status")
	Optional<Bug> findBugByStatusCustom(@Param("status") String status);
}