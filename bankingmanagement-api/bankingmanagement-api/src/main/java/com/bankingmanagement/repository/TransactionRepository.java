package com.bankingmanagement.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bankingmanagement.entity.Account;
import com.bankingmanagement.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    // Predefined JPA method to find transactions by account
    List<Transaction> findByAccount(Account account);

    // Predefined JPA method to find transactions by account and transaction date between two dates
    List<Transaction> findByAccountAndTransactionDateBetween(Account account, LocalDateTime startDate, LocalDateTime endDate);

    // Custom query to find transactions by account and transaction date between two dates using JPQL
    @Query("SELECT t FROM Transaction t WHERE t.account = :account AND t.transactionDate BETWEEN :startDate AND :endDate")
    List<Transaction> findByAccountAndDates(@Param("account") Account account, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Custom query to find transactions by transaction type using native SQL
    @Query(value = "SELECT * FROM Transaction t WHERE t.transaction_type = :transactionType", nativeQuery = true)
    List<Transaction> findByTransactionType(@Param("transactionType") String transactionType);

}
