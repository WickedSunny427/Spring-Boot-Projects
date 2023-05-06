package com.bankingmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bankingmanagement.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	Optional<Account> findByAccountType(String accountType);

	@Query(value = "Select account from Account account where accountType=:accountType")
	Optional<Account> findByAccountTypeCustom(@Param("accountType") String accountType);

    Optional<Account> findByAccountNumber(int accountNumber);

}