package com.bankingmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bankingmanagement.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
	Optional<Branch> findByBranchName(String name);

	@Query(value = "Select branch from Branch branch where branchName=:name")
	Optional<Branch> findBranchByNameCustom(@Param("name") String name);
	
	@Modifying
	@Query(value = "Delete from Branch branch where branchName=:name")
	Optional<Void> deleteByBranchName(@Param("name") String name);

}