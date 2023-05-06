package com.bankingmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bankingmanagement.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
	Optional<Loan> findByLoanType(String type);

	@Query(value = "Select loan from Loan loan where loanType=:type")
	Optional<Loan> findLoanByTypeCustom(@Param("type") String type);

}