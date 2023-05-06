package com.bugtracking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bugtracking.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
	Optional<Application> findByName(String name);

	@Query(value = "Select app from Application app where name=:name")
	Optional<Application> findApplicationByNameCustom(@Param("name") String name);
}