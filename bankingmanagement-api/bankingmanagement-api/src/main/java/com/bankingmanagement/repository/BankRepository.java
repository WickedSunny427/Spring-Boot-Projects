package com.bankingmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bankingmanagement.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Integer> {
	Optional<Bank> findByBankName(String name);

	@Query(value = "Select bank from Bank bank where bankName=:name")
	Optional<Bank> findBankByNameCustom(@Param("name") String name);

	@Modifying
	@Query(value = "Delete from Bank bank where bankName=:name")
	Optional<Void> deleteByBankName(@Param("name") String name);

}