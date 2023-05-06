package com.bankingmanagement.service;

import java.util.List;

import com.bankingmanagement.dto.CustomerDTO;
import com.bankingmanagement.dto.CustomerRequest;
import com.bankingmanagement.exception.CustomerNotFoundException;

public interface ICustomerService {

	List<CustomerDTO> findAll() throws CustomerNotFoundException;

	CustomerDTO findByName(String name) throws CustomerNotFoundException;

	CustomerDTO findCustomerByNameCustom(String name) throws CustomerNotFoundException;

	CustomerDTO findById(int id) throws CustomerNotFoundException;

	CustomerDTO save(CustomerRequest customerRequest) throws CustomerNotFoundException;

	String delete(int id) throws CustomerNotFoundException;

	String deleteByCustomerName(String name) throws CustomerNotFoundException;

}