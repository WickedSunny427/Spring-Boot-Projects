package com.bugtracking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bugtracking.entity.Release;

public interface ReleaseRepository extends JpaRepository<Release, Integer> {
	Optional<Release> findByName(String name);

	@Query(value = "Select rel from Release rel where name=:name")
	Optional<Release> findReleaseByNameCustom(@Param("name") String name);
}