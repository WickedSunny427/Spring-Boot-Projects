package com.bankingmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bankingmanagement.dto.AccountDTO;
import com.bankingmanagement.dto.BankDTO;
import com.bankingmanagement.dto.BranchDTO;
import com.bankingmanagement.dto.CustomerDTO;
import com.bankingmanagement.dto.CustomerRequest;
import com.bankingmanagement.dto.LoanDTO;
import com.bankingmanagement.entity.Account;
import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.entity.Customer;
import com.bankingmanagement.entity.Loan;
import com.bankingmanagement.exception.CustomerNotFoundException;
import com.bankingmanagement.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<CustomerDTO> findAll() throws CustomerNotFoundException {
		log.info("Inside CustomerServiceImpl.findAll method.");

		List<Customer> customers = customerRepository.findAll();
		log.info("Fetching all customer details : customers {}", customers);

		if (CollectionUtils.isEmpty(customers)) {
			log.info("No customer details available !!!");
			throw new CustomerNotFoundException("No customer details available !!!");
		}
		List<CustomerDTO> customerDTOs = customers.parallelStream().map(customer -> {

			CustomerDTO customerDTO = new CustomerDTO();

			customerDTO.setCustId(customer.getCustId());
			customerDTO.setCustName(customer.getCustName());
			customerDTO.setCustPhone(customer.getCustPhone());
			customerDTO.setCustAddress(customer.getCustAddress());

			Set<Account> accounts = customer.getAccounts();
			Set<AccountDTO> accountDTOs = accounts.stream().map(account -> {

				AccountDTO accountDTO = new AccountDTO();

				accountDTO.setAccountNumber(account.getAccountNumber());
				accountDTO.setAccountType(account.getAccountType());

				Branch branch = account.getBranch();
				BranchDTO branchDTO = new BranchDTO();

				branchDTO.setBranchName(branch.getBranchName());
				branchDTO.setBranchAddress(branch.getBranchAddress());

				Bank bank = account.getBranch().getBank();
				BankDTO bankDTO = new BankDTO();

				bankDTO.setBankName(bank.getBankName());
				bankDTO.setBankAddress(bank.getBankAddress());

				branchDTO.setBankDTO(bankDTO);

				accountDTO.setBranchDTO(branchDTO);
				accountDTO.setAccountBalance(account.getAccountBalance());

				return accountDTO;

			}).collect(Collectors.toSet());

			Set<Loan> loans = customer.getLoans();
			Set<LoanDTO> loanDTOs = loans.stream().map(loan -> {
				LoanDTO loanDTO = new LoanDTO();

				loanDTO.setLoanId(loan.getLoanId());
				loanDTO.setLoanType(loan.getLoanType());

				Branch branch = loan.getBranch();
				BranchDTO branchDTO = new BranchDTO();

				branchDTO.setBranchName(branch.getBranchName());
				branchDTO.setBranchAddress(branch.getBranchAddress());

				Bank bank = loan.getBranch().getBank();
				BankDTO bankDTO = new BankDTO();

				bankDTO.setBankName(bank.getBankName());
				bankDTO.setBankAddress(bank.getBankAddress());

				branchDTO.setBankDTO(bankDTO);
				loanDTO.setBranchDTO(branchDTO);

				loanDTO.setLoanAmount(loan.getLoanAmount());
				return loanDTO;

			}).collect(Collectors.toSet());

			customerDTO.setAccountsDTOs(accountDTOs);
			customerDTO.setLoansDTOs(loanDTOs);

			return customerDTO;
		}).collect(Collectors.toList());

		return customerDTOs;
	}

	@Override
	public CustomerDTO findByName(String name) throws CustomerNotFoundException {
		log.info("Inside CustomerServiceImpl.findByName and name: {}", name);
		CustomerDTO customerDTO = null;
		if (name == null) {
			log.info("Invalid customer name:{}", name);
			throw new CustomerNotFoundException("Customer name is not valid");
		}
		Optional<Customer> customer = customerRepository.findByCustName(name);
		log.info("Default findByCustomerName invoked Successfully !!!");

		if (customer.isPresent()) {
			log.info("Customer details for the name {} and the values :{}", name, customer.get());

			customerDTO = new CustomerDTO();

			customerDTO.setCustId(customer.get().getCustId());
			customerDTO.setCustName(customer.get().getCustName());
			customerDTO.setCustPhone(customer.get().getCustPhone());
			customerDTO.setCustAddress(customer.get().getCustAddress());

			Set<Account> accounts = customer.get().getAccounts();
			Set<AccountDTO> accountDTOs = accounts.stream().map(account -> {

				AccountDTO accountDTO = new AccountDTO();

				accountDTO.setAccountNumber(account.getAccountNumber());
				accountDTO.setAccountType(account.getAccountType());

				Branch branch = account.getBranch();
				BranchDTO branchDTO = new BranchDTO();

				branchDTO.setBranchName(branch.getBranchName());
				branchDTO.setBranchAddress(branch.getBranchAddress());

				Bank bank = account.getBranch().getBank();
				BankDTO bankDTO = new BankDTO();

				bankDTO.setBankName(bank.getBankName());
				bankDTO.setBankAddress(bank.getBankAddress());

				branchDTO.setBankDTO(bankDTO);

				accountDTO.setBranchDTO(branchDTO);
				accountDTO.setAccountBalance(account.getAccountBalance());

				return accountDTO;

			}).collect(Collectors.toSet());

			Set<Loan> loans = customer.get().getLoans();
			Set<LoanDTO> loanDTOs = loans.stream().map(loan -> {
				LoanDTO loanDTO = new LoanDTO();

				loanDTO.setLoanId(loan.getLoanId());
				loanDTO.setLoanType(loan.getLoanType());

				Branch branch = loan.getBranch();
				BranchDTO branchDTO = new BranchDTO();

				branchDTO.setBranchName(branch.getBranchName());
				branchDTO.setBranchAddress(branch.getBranchAddress());

				Bank bank = loan.getBranch().getBank();
				BankDTO bankDTO = new BankDTO();

				bankDTO.setBankName(bank.getBankName());
				bankDTO.setBankAddress(bank.getBankAddress());

				branchDTO.setBankDTO(bankDTO);
				loanDTO.setBranchDTO(branchDTO);

				loanDTO.setLoanAmount(loan.getLoanAmount());
				return loanDTO;

			}).collect(Collectors.toSet());

			customerDTO.setAccountsDTOs(accountDTOs);
			customerDTO.setLoansDTOs(loanDTOs);
		}
		return customerDTO;
	}

	@Override
	public CustomerDTO findCustomerByNameCustom(String name) throws CustomerNotFoundException {
		log.info("Inside CustomerServiceImpl.findCustomerByNameCustom and name: {}", name);
		CustomerDTO customerDTO = null;
		if (name == null) {
			log.info("Invalid customer name:{}", name);
			throw new CustomerNotFoundException("Customer name is not valid");
		}
		Optional<Customer> customer = customerRepository.findCustomerByNameCustom(name);
		log.info("Custom findCustomerByNameCustom invoked Successfully !!!");

		if (customer.isPresent()) {
			log.info("Customer details for the name {} and the values :{}", name, customer.get());

			customerDTO = new CustomerDTO();

			customerDTO.setCustId(customer.get().getCustId());
			customerDTO.setCustName(customer.get().getCustName());
			customerDTO.setCustPhone(customer.get().getCustPhone());
			customerDTO.setCustAddress(customer.get().getCustAddress());

			Set<Account> accounts = customer.get().getAccounts();
			Set<AccountDTO> accountDTOs = accounts.stream().map(account -> {

				AccountDTO accountDTO = new AccountDTO();

				accountDTO.setAccountNumber(account.getAccountNumber());
				accountDTO.setAccountType(account.getAccountType());

				Branch branch = account.getBranch();
				BranchDTO branchDTO = new BranchDTO();

				branchDTO.setBranchName(branch.getBranchName());
				branchDTO.setBranchAddress(branch.getBranchAddress());

				Bank bank = account.getBranch().getBank();
				BankDTO bankDTO = new BankDTO();

				bankDTO.setBankName(bank.getBankName());
				bankDTO.setBankAddress(bank.getBankAddress());

				branchDTO.setBankDTO(bankDTO);

				accountDTO.setBranchDTO(branchDTO);
				accountDTO.setAccountBalance(account.getAccountBalance());

				return accountDTO;

			}).collect(Collectors.toSet());

			Set<Loan> loans = customer.get().getLoans();
			Set<LoanDTO> loanDTOs = loans.stream().map(loan -> {
				LoanDTO loanDTO = new LoanDTO();

				loanDTO.setLoanId(loan.getLoanId());
				loanDTO.setLoanType(loan.getLoanType());

				Branch branch = loan.getBranch();
				BranchDTO branchDTO = new BranchDTO();

				branchDTO.setBranchName(branch.getBranchName());
				branchDTO.setBranchAddress(branch.getBranchAddress());

				Bank bank = loan.getBranch().getBank();
				BankDTO bankDTO = new BankDTO();

				bankDTO.setBankName(bank.getBankName());
				bankDTO.setBankAddress(bank.getBankAddress());

				branchDTO.setBankDTO(bankDTO);
				loanDTO.setBranchDTO(branchDTO);

				loanDTO.setLoanAmount(loan.getLoanAmount());
				return loanDTO;

			}).collect(Collectors.toSet());

			customerDTO.setAccountsDTOs(accountDTOs);
			customerDTO.setLoansDTOs(loanDTOs);
		}
		return customerDTO;
	}

	@Override
	public CustomerDTO findById(int id) throws CustomerNotFoundException {
		log.info("Inside CustomerServiceImpl.findCustomerById and id: {}", id);
		CustomerDTO customerDTO = null;
		if (id < 0) {
			log.info("Invalid customer id:{}", id);
			throw new CustomerNotFoundException("Customer id is not valid");
		}
		Optional<Customer> customer = customerRepository.findById(id);// Find customer by ID default JPA available
																		// method.

		if (customer.isPresent()) {
			log.info("Customer details for the id {} and the values :{}", id, customer.get());

			customerDTO = new CustomerDTO();

			customerDTO.setCustId(customer.get().getCustId());
			customerDTO.setCustName(customer.get().getCustName());
			customerDTO.setCustPhone(customer.get().getCustPhone());
			customerDTO.setCustAddress(customer.get().getCustAddress());

			Set<Account> accounts = customer.get().getAccounts();
			Set<AccountDTO> accountDTOs = accounts.stream().map(account -> {

				AccountDTO accountDTO = new AccountDTO();

				accountDTO.setAccountNumber(account.getAccountNumber());
				accountDTO.setAccountType(account.getAccountType());

				Branch branch = account.getBranch();
				BranchDTO branchDTO = new BranchDTO();

				branchDTO.setBranchName(branch.getBranchName());
				branchDTO.setBranchAddress(branch.getBranchAddress());

				Bank bank = account.getBranch().getBank();
				BankDTO bankDTO = new BankDTO();

				bankDTO.setBankName(bank.getBankName());
				bankDTO.setBankAddress(bank.getBankAddress());

				branchDTO.setBankDTO(bankDTO);

				accountDTO.setBranchDTO(branchDTO);
				accountDTO.setAccountBalance(account.getAccountBalance());

				return accountDTO;

			}).collect(Collectors.toSet());

			Set<Loan> loans = customer.get().getLoans();
			Set<LoanDTO> loanDTOs = loans.stream().map(loan -> {
				LoanDTO loanDTO = new LoanDTO();

				loanDTO.setLoanId(loan.getLoanId());
				loanDTO.setLoanType(loan.getLoanType());

				Branch branch = loan.getBranch();
				BranchDTO branchDTO = new BranchDTO();

				branchDTO.setBranchName(branch.getBranchName());
				branchDTO.setBranchAddress(branch.getBranchAddress());

				Bank bank = loan.getBranch().getBank();
				BankDTO bankDTO = new BankDTO();

				bankDTO.setBankName(bank.getBankName());
				bankDTO.setBankAddress(bank.getBankAddress());

				branchDTO.setBankDTO(bankDTO);
				loanDTO.setBranchDTO(branchDTO);

				loanDTO.setLoanAmount(loan.getLoanAmount());
				return loanDTO;

			}).collect(Collectors.toSet());

			customerDTO.setAccountsDTOs(accountDTOs);
			customerDTO.setLoansDTOs(loanDTOs);
		}
		return customerDTO;
	}

	@Override
	public CustomerDTO save(CustomerRequest customerRequest) throws CustomerNotFoundException {
		log.info("Inside the CustomerServiceImpl.save method and params, customerRequest:{}", customerRequest);

		if (customerRequest == null) {
			log.info("Invalid customer request");
			throw new CustomerNotFoundException("Invalid customer request");
		}

		Customer customer = new Customer();
		if (customerRequest.getCustName() != null) {
			customer.setCustName(customerRequest.getCustName());
		}

		customer.setCustId(customerRequest.getCustId());
		customer.setCustPhone(customerRequest.getCustPhone());
		customer.setCustAddress(customerRequest.getCustAddress());

		Customer customerResponse = customerRepository.save(customer);
		CustomerDTO customerDTO = null;
		if (customerResponse != null) {
			log.info("Customer Response, customerResponse:{}", customerResponse);

			customerDTO = new CustomerDTO();

			customerDTO.setCustId(customerResponse.getCustId());
			customerDTO.setCustName(customerResponse.getCustName());
			customerDTO.setCustPhone(customerResponse.getCustPhone());
			customerDTO.setCustAddress(customerResponse.getCustAddress());

		}
		return customerDTO;
	}

	@Override
	public String delete(int id) throws CustomerNotFoundException {
		log.info("Input to CustomerServiceImpl.delete, id:{}", id);
		if (id < 0) {
			log.info("Invalid customer id");
			throw new CustomerNotFoundException("Invalid customer id");
		}
		try {
			customerRepository.deleteById(id);
		} catch (Exception ex) {
			log.error("Exception while deleting customer");
			throw new CustomerNotFoundException("Exception while deleting customer.");
		}
		return "Customer has been deleted.";
	}

	@Transactional
	@Override
	public String deleteByCustomerName(String name) throws CustomerNotFoundException {
		log.info("Input to CustomerServiceImpl.deleteByCustomerName, name:{}", name);
		if (name == null) {
			log.info("Invalid customer name");
			throw new CustomerNotFoundException("Invalid customer name");
		}
		try {
			customerRepository.deleteByCustomerName(name);
		} catch (Exception ex) {
			log.error("Exception while deleting customer by name." + ex.getMessage());
			ex.printStackTrace();
			throw new CustomerNotFoundException("Exception while deleting customer by name.");
		}
		return "Customer has been deleted by name.";
	}
}