package com.bankingmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bankingmanagement.dto.AccountDTO;
import com.bankingmanagement.dto.BankDTO;
import com.bankingmanagement.dto.BranchDTO;
import com.bankingmanagement.dto.BranchRequest;
import com.bankingmanagement.dto.CustomerDTO;
import com.bankingmanagement.dto.LoanDTO;
import com.bankingmanagement.entity.Account;
import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.entity.Customer;
import com.bankingmanagement.entity.Loan;
import com.bankingmanagement.exception.BranchNotFoundException;
import com.bankingmanagement.repository.BranchRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BranchServiceImpl implements IBranchService {

	@Autowired
	private BranchRepository branchRepository;

	@Override
	public List<BranchDTO> findAll() throws BranchNotFoundException {
		log.info("Inside BranchServiceImpl.findAll method.");

		List<Branch> branches = branchRepository.findAll();
		log.info("Fetching all branch details : branches {}", branches);

		if (CollectionUtils.isEmpty(branches)) {
			log.info("No branch details available !!!");
			throw new BranchNotFoundException("No branch details available !!!");
		}
		List<BranchDTO> branchDTOs = branches.parallelStream().map(branch -> {

			BranchDTO branchDTO = new BranchDTO();

			branchDTO.setBranchName(branch.getBranchName());
			branchDTO.setBranchAddress(branch.getBranchAddress());

			Set<Account> accounts = branch.getAccounts();
			Set<AccountDTO> accountDTOs = accounts.stream().map(account -> {
				AccountDTO accountDTO = new AccountDTO();
				accountDTO.setAccountNumber(account.getAccountNumber());
				accountDTO.setAccountType(account.getAccountType());
				accountDTO.setAccountBalance(account.getAccountBalance());

				Customer customer = account.getCustomer();
				CustomerDTO customerDTO = new CustomerDTO();

				customerDTO.setCustId(customer.getCustId());
				customerDTO.setCustName(customer.getCustName());
				customerDTO.setCustPhone(customer.getCustPhone());
				customerDTO.setCustAddress(customer.getCustAddress());

				accountDTO.setCustomer(customerDTO);

				return accountDTO;
			}).collect(Collectors.toSet());

			branchDTO.setAccountsDTOs(accountDTOs);

			Set<Loan> loans = branch.getLoans();
			Set<LoanDTO> loandDTOs = loans.stream().map(loan -> {

				LoanDTO loanDTO = new LoanDTO();

				loanDTO.setLoanId(loan.getLoanId());
				loanDTO.setLoanType(loan.getLoanType());
				loanDTO.setLoanAmount(loan.getLoanAmount());

				Customer customer = loan.getCustomer();
				CustomerDTO customerDTO = new CustomerDTO();

				customerDTO.setCustId(customer.getCustId());
				customerDTO.setCustName(customer.getCustName());
				customerDTO.setCustPhone(customer.getCustPhone());
				customerDTO.setCustAddress(customer.getCustAddress());

				loanDTO.setCustomer(customerDTO);

				return loanDTO;

			}).collect(Collectors.toSet());

			branchDTO.setLoansDTOs(loandDTOs);// to be set

			Bank bank = branch.getBank();
			BankDTO bankDTO = new BankDTO();

			bankDTO.setBankName(bank.getBankName());
			bankDTO.setBankAddress(bank.getBankAddress());

			branchDTO.setBankDTO(bankDTO);

			return branchDTO;
		}).collect(Collectors.toList());

		return branchDTOs;
	}

	@Override
	public BranchDTO findByName(String name) throws BranchNotFoundException {
		log.info("Inside BranchServiceImpl.findByName and name: {}", name);
		BranchDTO branchDTO = null;
		if (name == null) {
			log.info("Invalid branch name:{}", name);
			throw new BranchNotFoundException("Branch name is not valid");
		}
		Optional<Branch> branch = branchRepository.findByBranchName(name);
		log.info("Default findByBranchName invoked Successfully !!!");

		if (branch.isPresent()) {
			log.info("Branch details for the name {} and the values :{}", name, branch.get());

			branchDTO = new BranchDTO();

			branchDTO.setBranchName(branch.get().getBranchName());
			branchDTO.setBranchAddress(branch.get().getBranchAddress());

			Set<Account> accounts = branch.get().getAccounts();
			Set<AccountDTO> accountDTOs = accounts.stream().map(account -> {
				AccountDTO accountDTO = new AccountDTO();
				accountDTO.setAccountNumber(account.getAccountNumber());
				accountDTO.setAccountType(account.getAccountType());
				accountDTO.setAccountBalance(account.getAccountBalance());

				Customer customer = account.getCustomer();
				CustomerDTO customerDTO = new CustomerDTO();

				customerDTO.setCustId(customer.getCustId());
				customerDTO.setCustName(customer.getCustName());
				customerDTO.setCustPhone(customer.getCustPhone());
				customerDTO.setCustAddress(customer.getCustAddress());

				accountDTO.setCustomer(customerDTO);

				return accountDTO;
			}).collect(Collectors.toSet());

			branchDTO.setAccountsDTOs(accountDTOs);

			Set<Loan> loans = branch.get().getLoans();
			Set<LoanDTO> loandDTOs = loans.stream().map(loan -> {

				LoanDTO loanDTO = new LoanDTO();

				loanDTO.setLoanId(loan.getLoanId());
				loanDTO.setLoanType(loan.getLoanType());
				loanDTO.setLoanAmount(loan.getLoanAmount());

				Customer customer = loan.getCustomer();
				CustomerDTO customerDTO = new CustomerDTO();

				customerDTO.setCustId(customer.getCustId());
				customerDTO.setCustName(customer.getCustName());
				customerDTO.setCustPhone(customer.getCustPhone());
				customerDTO.setCustAddress(customer.getCustAddress());

				loanDTO.setCustomer(customerDTO);

				return loanDTO;

			}).collect(Collectors.toSet());

			branchDTO.setLoansDTOs(loandDTOs);// to be set

			Bank bank = branch.get().getBank();
			BankDTO bankDTO = new BankDTO();

			bankDTO.setBankName(bank.getBankName());
			bankDTO.setBankAddress(bank.getBankAddress());

			branchDTO.setBankDTO(bankDTO);
		}
		return branchDTO;
	}

	@Override
	public BranchDTO findBranchByNameCustom(String name) throws BranchNotFoundException {
		log.info("Inside BranchServiceImpl.findBranchByNameCustom and name: {}", name);
		BranchDTO branchDTO = null;
		if (name == null) {
			log.info("Invalid branch name:{}", name);
			throw new BranchNotFoundException("Branch name is not valid");
		}
		Optional<Branch> branch = branchRepository.findBranchByNameCustom(name);// Custom Method using @Query
		log.info("Custom findBranchByNameCustom invoked Successfully !!!");

		if (branch.isPresent()) {
			log.info("Branch details for the name {} and the values :{}", name, branch.get());
			branchDTO = new BranchDTO();

			branchDTO.setBranchName(branch.get().getBranchName());
			branchDTO.setBranchAddress(branch.get().getBranchAddress());

			Set<Account> accounts = branch.get().getAccounts();
			Set<AccountDTO> accountDTOs = accounts.stream().map(account -> {
				AccountDTO accountDTO = new AccountDTO();
				accountDTO.setAccountNumber(account.getAccountNumber());
				accountDTO.setAccountType(account.getAccountType());
				accountDTO.setAccountBalance(account.getAccountBalance());

				Customer customer = account.getCustomer();
				CustomerDTO customerDTO = new CustomerDTO();

				customerDTO.setCustId(customer.getCustId());
				customerDTO.setCustName(customer.getCustName());
				customerDTO.setCustPhone(customer.getCustPhone());
				customerDTO.setCustAddress(customer.getCustAddress());

				accountDTO.setCustomer(customerDTO);

				return accountDTO;
			}).collect(Collectors.toSet());

			branchDTO.setAccountsDTOs(accountDTOs);

			Set<Loan> loans = branch.get().getLoans();
			Set<LoanDTO> loandDTOs = loans.stream().map(loan -> {

				LoanDTO loanDTO = new LoanDTO();

				loanDTO.setLoanId(loan.getLoanId());
				loanDTO.setLoanType(loan.getLoanType());
				loanDTO.setLoanAmount(loan.getLoanAmount());

				Customer customer = loan.getCustomer();
				CustomerDTO customerDTO = new CustomerDTO();

				customerDTO.setCustId(customer.getCustId());
				customerDTO.setCustName(customer.getCustName());
				customerDTO.setCustPhone(customer.getCustPhone());
				customerDTO.setCustAddress(customer.getCustAddress());

				loanDTO.setCustomer(customerDTO);

				return loanDTO;

			}).collect(Collectors.toSet());

			branchDTO.setLoansDTOs(loandDTOs);// to be set

			Bank bank = branch.get().getBank();
			BankDTO bankDTO = new BankDTO();

			bankDTO.setBankName(bank.getBankName());
			bankDTO.setBankAddress(bank.getBankAddress());

			branchDTO.setBankDTO(bankDTO);
		}
		return branchDTO;
	}

	@Override
	public BranchDTO findById(int id) throws BranchNotFoundException {
		log.info("Inside BranchServiceImpl.findBranchById and id: {}", id);
		BranchDTO branchDTO = null;
		if (id < 0) {
			log.info("Invalid branch id:{}", id);
			throw new BranchNotFoundException("Branch id is not valid");
		}
		Optional<Branch> branch = branchRepository.findById(id);// Find branch by ID default JPA available method.

		if (branch.isPresent()) {
			log.info("Branch details for the name {} and the values :{}", id, branch.get());
			branchDTO = new BranchDTO();

			branchDTO.setBranchName(branch.get().getBranchName());
			branchDTO.setBranchAddress(branch.get().getBranchAddress());

			Set<Account> accounts = branch.get().getAccounts();
			Set<AccountDTO> accountDTOs = accounts.stream().map(account -> {
				AccountDTO accountDTO = new AccountDTO();
				accountDTO.setAccountNumber(account.getAccountNumber());
				accountDTO.setAccountType(account.getAccountType());
				accountDTO.setAccountBalance(account.getAccountBalance());

				Customer customer = account.getCustomer();
				CustomerDTO customerDTO = new CustomerDTO();

				customerDTO.setCustId(customer.getCustId());
				customerDTO.setCustName(customer.getCustName());
				customerDTO.setCustPhone(customer.getCustPhone());
				customerDTO.setCustAddress(customer.getCustAddress());

				accountDTO.setCustomer(customerDTO);

				return accountDTO;
			}).collect(Collectors.toSet());

			branchDTO.setAccountsDTOs(accountDTOs);

			Set<Loan> loans = branch.get().getLoans();
			Set<LoanDTO> loandDTOs = loans.stream().map(loan -> {

				LoanDTO loanDTO = new LoanDTO();

				loanDTO.setLoanId(loan.getLoanId());
				loanDTO.setLoanType(loan.getLoanType());
				loanDTO.setLoanAmount(loan.getLoanAmount());

				Customer customer = loan.getCustomer();
				CustomerDTO customerDTO = new CustomerDTO();

				customerDTO.setCustId(customer.getCustId());
				customerDTO.setCustName(customer.getCustName());
				customerDTO.setCustPhone(customer.getCustPhone());
				customerDTO.setCustAddress(customer.getCustAddress());

				loanDTO.setCustomer(customerDTO);

				return loanDTO;

			}).collect(Collectors.toSet());

			branchDTO.setLoansDTOs(loandDTOs);// to be set

			Bank bank = branch.get().getBank();
			BankDTO bankDTO = new BankDTO();

			bankDTO.setBankName(bank.getBankName());
			bankDTO.setBankAddress(bank.getBankAddress());

			branchDTO.setBankDTO(bankDTO);
		}
		return branchDTO;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public BranchDTO save(BranchRequest branchRequest) throws BranchNotFoundException {
		log.info("Inside the BranchServiceImpl.save method and params, branchRequest:{}", branchRequest);

		if (branchRequest == null) {
			log.info("Invalid branch request");
			throw new BranchNotFoundException("Invalid branch request");
		}

		Branch branch = new Branch();
		if (branchRequest.getBranchName() != null) {
			branch.setBranchName(branchRequest.getBranchName());
		}

		branch.setBranchId(branchRequest.getBranchId());
		branch.setBranchAddress(branchRequest.getBranchAddress());

		Bank bank = new Bank();
		bank.setBankCode(branchRequest.getBankDTO().getBankCode());
		branch.setBank(bank);

		Branch branchResponse = branchRepository.save(branch);
		BranchDTO branchDTO = null;
		if (branchResponse != null) {
			log.info("Branch Response, branchResponse:{}", branchResponse);

			branchDTO = new BranchDTO();

			BankDTO bankDTO = new BankDTO();
			bankDTO.setBankCode(branchResponse.getBank().getBankCode());
			bankDTO.setBankAddress(branchResponse.getBank().getBankAddress());
			bankDTO.setBankName(branchResponse.getBranchName());

			branchDTO.setBranchName(branchResponse.getBranchName());
			branchDTO.setBranchAddress(branchResponse.getBranchAddress());
			branchDTO.setBankDTO(bankDTO);

		}
		return branchDTO;
	}

	@Override
	public String delete(int id) throws BranchNotFoundException {
		log.info("Input to BranchServiceImpl.delete, id:{}", id);
		if (id < 0) {
			log.info("Invalid branch id");
			throw new BranchNotFoundException("Invalid branch id");
		}
		try {
			branchRepository.deleteById(id);
		} catch (Exception ex) {
			log.error("Exception while deleting branch");
			throw new BranchNotFoundException("Exception while deleting branch");
		}
		return "Branch has been deleted";
	}

	@Transactional
	@Override
	public String deleteByBranchName(String name) throws BranchNotFoundException {
		log.info("Input to BranchServiceImpl.deleteByBranchName, name:{}", name);
		if (name == null) {
			log.info("Invalid branch name");
			throw new BranchNotFoundException("Invalid branch name");
		}
		try {
			branchRepository.deleteByBranchName(name);
		} catch (Exception ex) {
			log.error("Exception while deleting branch by name." + ex.getMessage());
			ex.printStackTrace();
			throw new BranchNotFoundException("Exception while deleting branch by name.");
		}
		return "Branch has been deleted by name.";
	}
}