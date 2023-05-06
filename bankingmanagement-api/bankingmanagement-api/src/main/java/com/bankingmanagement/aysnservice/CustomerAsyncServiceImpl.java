package com.bankingmanagement.aysnservice;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bankingmanagement.dto.AccountDTO;
import com.bankingmanagement.dto.BankDTO;
import com.bankingmanagement.dto.BranchDTO;
import com.bankingmanagement.dto.CustomerDTO;
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
public class CustomerAsyncServiceImpl implements ICustomerAsyncService {

	@Autowired
	private CustomerRepository customerRepository;

	@Async("asyncBean")
	@Override
	public CompletableFuture<CustomerDTO> findByName(String name) throws CustomerNotFoundException {
		log.info("Inside CustomerAsyncServiceImpl.findByName and name: {}", name);
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
		return CompletableFuture.completedFuture(customerDTO);
	}

	@Async("asyncBean")
	@Override
	public CompletableFuture<CustomerDTO> findById(int id) throws CustomerNotFoundException {
		log.info("Inside CustomerAsyncServiceImpl.findCustomerById and id: {}", id);
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
		return CompletableFuture.completedFuture(customerDTO);
	}
}