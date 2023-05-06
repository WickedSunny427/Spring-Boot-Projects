package com.bankingmanagement.aysnservice;

import java.util.concurrent.CompletableFuture;

import com.bankingmanagement.dto.CustomerDTO;
import com.bankingmanagement.exception.CustomerNotFoundException;

public interface ICustomerAsyncService {


	CompletableFuture<CustomerDTO>  findByName(String name) throws CustomerNotFoundException;

	CompletableFuture<CustomerDTO> findById(int id) throws CustomerNotFoundException;
}