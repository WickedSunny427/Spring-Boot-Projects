package com.bankingmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bankingmanagement.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Optional<Customer> findByCustName(String name);

	@Query(value = "Select customer from Customer customer where custName=:name")
	Optional<Customer> findCustomerByNameCustom(@Param("name") String name);

	@Modifying
	@Query(value = "Delete from Customer customer where custName=:name")
	Optional<Void> deleteByCustomerName(@Param("name") String name);
}