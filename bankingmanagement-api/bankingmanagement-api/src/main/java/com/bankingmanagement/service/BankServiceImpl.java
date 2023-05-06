package com.bankingmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bankingmanagement.dto.AccountDTO;
import com.bankingmanagement.dto.BankDTO;
import com.bankingmanagement.dto.BankRequest;
import com.bankingmanagement.dto.BranchDTO;
import com.bankingmanagement.dto.CustomerDTO;
import com.bankingmanagement.dto.LoanDTO;
import com.bankingmanagement.entity.Account;
import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.entity.Customer;
import com.bankingmanagement.entity.Loan;
import com.bankingmanagement.exception.BankNotFoundException;
import com.bankingmanagement.repository.BankRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BankServiceImpl implements IBankService {

	@Autowired
	private BankRepository bankRepository;

	@Override
	public List<BankDTO> findAll() throws BankNotFoundException {
		log.info("Inside BankServiceImpl.findAll method.");

		List<Bank> banks = bankRepository.findAll();
		log.info("Fetching all bank details : banks {}", banks);

		if (CollectionUtils.isEmpty(banks)) {
			log.info("No bank details available !!!");
			throw new BankNotFoundException("No bank details available !!!");
		}
		List<BankDTO> bankDTOs = banks.parallelStream().map(bank -> {

			BankDTO bankDTO = new BankDTO();

			bankDTO.setBankName(bank.getBankName());
			bankDTO.setBankAddress(bank.getBankAddress());

			if (!CollectionUtils.isEmpty(bank.getBranches())) {

				Set<Branch> branches = bank.getBranches();
				Set<BranchDTO> branchDTOs = branches.stream().map(branch -> {

					BranchDTO branchDTO = new BranchDTO();

					branchDTO.setBranchName(branch.getBranchName());
					branchDTO.setBranchAddress(branch.getBranchAddress());

					Set<Loan> loans = branch.getLoans();
					Set<LoanDTO> loanDTOs = loans.stream().map(loan -> {
						LoanDTO loanDTO = new LoanDTO();

						loanDTO.setLoanId(loan.getLoanId());
						loanDTO.setLoanType(loan.getLoanType());
						loanDTO.setLoanAmount(loan.getLoanAmount());
						loanDTO.setBranchDTO(null);// to be set.

						Customer customer = loan.getCustomer();
						CustomerDTO customerDTO = new CustomerDTO();

						customerDTO.setCustId(customer.getCustId());
						customerDTO.setCustName(customer.getCustName());
						customerDTO.setCustPhone(customer.getCustPhone());
						customerDTO.setCustAddress(customer.getCustAddress());

						loanDTO.setCustomer(customerDTO);
						return loanDTO;

					}).collect(Collectors.toSet());

					branchDTO.setLoansDTOs(loanDTOs);

					Set<Account> accounts = branch.getAccounts();
					Set<AccountDTO> accountDTOs = accounts.stream().map(account -> {

						AccountDTO accountDTO = new AccountDTO();

						accountDTO.setAccountNumber(account.getAccountNumber());
						accountDTO.setAccountType(account.getAccountType());
						accountDTO.setBranchDTO(null);// to be set.

						Customer customer = account.getCustomer();
						CustomerDTO customerDTO = new CustomerDTO();

						customerDTO.setCustId(customer.getCustId());
						customerDTO.setCustName(customer.getCustName());
						customerDTO.setCustPhone(customer.getCustPhone());
						customerDTO.setCustAddress(customer.getCustAddress());

						accountDTO.setCustomer(customerDTO);
						accountDTO.setAccountBalance(account.getAccountBalance());

						return accountDTO;

					}).collect(Collectors.toSet());

					branchDTO.setAccountsDTOs(accountDTOs);

					return branchDTO;
				}).collect(Collectors.toSet());

				bankDTO.setBranches(branchDTOs);
			}
			return bankDTO;

		}).collect(Collectors.toList());
		return bankDTOs;
	}

	@Override
	public BankDTO findByName(String name) throws BankNotFoundException, InterruptedException {
		log.info("Inside BankServiceImpl.findByName and name: {}", name);
		BankDTO bankDTO = null;
		if (name == null) {
			log.info("Invalid bank name:{}", name);
			throw new BankNotFoundException("Bank name is not valid");
		}

		Optional<Bank> bank = bankRepository.findByBankName(name);
		log.info("Default findByName invoked Successfully !!!");

		// Using sleep to test Caching mechanism.
		Thread.sleep(10000);

		if (bank.isPresent()) {
			log.info("Bank details for the name {} and the values :{}", name, bank.get());

			bankDTO = new BankDTO();

			bankDTO.setBankName(bank.get().getBankName());
			bankDTO.setBankAddress(bank.get().getBankAddress());
			Set<Branch> branches = bank.get().getBranches();
			Set<BranchDTO> branchDTOs = branches.stream().map(branch -> {

				BranchDTO branchDTO = new BranchDTO();

				branchDTO.setBranchName(branch.getBranchName());
				branchDTO.setBranchAddress(branch.getBranchAddress());

				Set<Loan> loans = branch.getLoans();
				Set<LoanDTO> loanDTOs = loans.stream().map(loan -> {
					LoanDTO loanDTO = new LoanDTO();

					loanDTO.setLoanId(loan.getLoanId());
					loanDTO.setLoanType(loan.getLoanType());
					loanDTO.setLoanAmount(loan.getLoanAmount());
					loanDTO.setBranchDTO(null);// to be set.

					Customer customer = loan.getCustomer();
					CustomerDTO customerDTO = new CustomerDTO();

					customerDTO.setCustId(customer.getCustId());
					customerDTO.setCustName(customer.getCustName());
					customerDTO.setCustPhone(customer.getCustPhone());
					customerDTO.setCustAddress(customer.getCustAddress());

					loanDTO.setCustomer(customerDTO);
					return loanDTO;

				}).collect(Collectors.toSet());

				branchDTO.setLoansDTOs(loanDTOs);

				Set<Account> accounts = branch.getAccounts();
				Set<AccountDTO> accountDTOs = accounts.stream().map(account -> {

					AccountDTO accountDTO = new AccountDTO();

					accountDTO.setAccountNumber(account.getAccountNumber());
					accountDTO.setAccountType(account.getAccountType());
					accountDTO.setBranchDTO(null);// to be set.

					Customer customer = account.getCustomer();
					CustomerDTO customerDTO = new CustomerDTO();

					customerDTO.setCustId(customer.getCustId());
					customerDTO.setCustName(customer.getCustName());
					customerDTO.setCustPhone(customer.getCustPhone());
					customerDTO.setCustAddress(customer.getCustAddress());

					accountDTO.setCustomer(customerDTO);
					accountDTO.setAccountBalance(account.getAccountBalance());

					return accountDTO;

				}).collect(Collectors.toSet());

				branchDTO.setAccountsDTOs(accountDTOs);

				return branchDTO;
			}).collect(Collectors.toSet());

			bankDTO.setBranches(branchDTOs);

		}
		return bankDTO;
	}

	@Override
	@Cacheable("cacheOnFindBankByNameCustom")
	public BankDTO findBankByNameCustom(String name) throws BankNotFoundException, InterruptedException {
		log.info("Inside BankServiceImpl.findBankByNameCustom and name: {}", name);
		BankDTO bankDTO = null;
		if (name == null) {
			log.info("Invalid bank name:{}", name);
			throw new BankNotFoundException("Bank name is not valid");
		}
		Optional<Bank> bank = bankRepository.findBankByNameCustom(name);// Custom Method using @Query
		log.info("Custom findBankByNameCustom invoked Successfully !!!");

		// Using sleep to test Caching mechanism.
		Thread.sleep(10000);

		if (!bank.isPresent()) {
			log.info("Bank details not found !!!");
			throw new BankNotFoundException("Bank details not found !!!");
		}

		log.info("Bank details for the name {} and the values :{}", name, bank.get());

		bankDTO = new BankDTO();

		bankDTO.setBankName(bank.get().getBankName());
		bankDTO.setBankAddress(bank.get().getBankAddress());
		Set<Branch> branches = bank.get().getBranches();
		Set<BranchDTO> branchDTOs = branches.stream().map(branch -> {

			BranchDTO branchDTO = new BranchDTO();

			branchDTO.setBranchName(branch.getBranchName());
			branchDTO.setBranchAddress(branch.getBranchAddress());

			Set<Loan> loans = branch.getLoans();
			Set<LoanDTO> loanDTOs = loans.stream().map(loan -> {
				LoanDTO loanDTO = new LoanDTO();

				loanDTO.setLoanId(loan.getLoanId());
				loanDTO.setLoanType(loan.getLoanType());
				loanDTO.setLoanAmount(loan.getLoanAmount());
				loanDTO.setBranchDTO(null);// to be set.

				Customer customer = loan.getCustomer();
				CustomerDTO customerDTO = new CustomerDTO();

				customerDTO.setCustId(customer.getCustId());
				customerDTO.setCustName(customer.getCustName());
				customerDTO.setCustPhone(customer.getCustPhone());
				customerDTO.setCustAddress(customer.getCustAddress());

				loanDTO.setCustomer(customerDTO);
				return loanDTO;

			}).collect(Collectors.toSet());

			branchDTO.setLoansDTOs(loanDTOs);

			Set<Account> accounts = branch.getAccounts();
			Set<AccountDTO> accountDTOs = accounts.stream().map(account -> {

				AccountDTO accountDTO = new AccountDTO();

				accountDTO.setAccountNumber(account.getAccountNumber());
				accountDTO.setAccountType(account.getAccountType());
				accountDTO.setBranchDTO(null);// to be set.

				Customer customer = account.getCustomer();
				CustomerDTO customerDTO = new CustomerDTO();

				customerDTO.setCustId(customer.getCustId());
				customerDTO.setCustName(customer.getCustName());
				customerDTO.setCustPhone(customer.getCustPhone());
				customerDTO.setCustAddress(customer.getCustAddress());

				accountDTO.setCustomer(customerDTO);
				accountDTO.setAccountBalance(account.getAccountBalance());

				return accountDTO;

			}).collect(Collectors.toSet());

			branchDTO.setAccountsDTOs(accountDTOs);

			return branchDTO;
		}).collect(Collectors.toSet());

		bankDTO.setBranches(branchDTOs);

		return bankDTO;
	}

	@Override
	public BankDTO findById(int id) throws BankNotFoundException {
		log.info("Inside BankServiceImpl.findBankById and id: {}", id);
		BankDTO bankDTO = null;
		if (id < 0) {
			log.info("Invalid bank id:{}", id);
			throw new BankNotFoundException("Bank id is not valid");
		}
		Optional<Bank> bank = bankRepository.findById(id);// Find bank by ID default JPA available method.

		if (bank.isPresent()) {
			log.info("Bank details for the id {} and the values :{}", id, bank.get());


			bankDTO = new BankDTO();

			bankDTO.setBankName(bank.get().getBankName());
			bankDTO.setBankAddress(bank.get().getBankAddress());
			Set<Branch> branches = bank.get().getBranches();
			Set<BranchDTO> branchDTOs = branches.stream().map(branch -> {

				BranchDTO branchDTO = new BranchDTO();

				branchDTO.setBranchName(branch.getBranchName());
				branchDTO.setBranchAddress(branch.getBranchAddress());

				Set<Loan> loans = branch.getLoans();
				Set<LoanDTO> loanDTOs = loans.stream().map(loan -> {
					LoanDTO loanDTO = new LoanDTO();

					loanDTO.setLoanId(loan.getLoanId());
					loanDTO.setLoanType(loan.getLoanType());
					loanDTO.setLoanAmount(loan.getLoanAmount());
					loanDTO.setBranchDTO(null);// to be set.

					Customer customer = loan.getCustomer();
					CustomerDTO customerDTO = new CustomerDTO();

					customerDTO.setCustId(customer.getCustId());
					customerDTO.setCustName(customer.getCustName());
					customerDTO.setCustPhone(customer.getCustPhone());
					customerDTO.setCustAddress(customer.getCustAddress());

					loanDTO.setCustomer(customerDTO);
					return loanDTO;

				}).collect(Collectors.toSet());

				branchDTO.setLoansDTOs(loanDTOs);

				Set<Account> accounts = branch.getAccounts();
				Set<AccountDTO> accountDTOs = accounts.stream().map(account -> {

					AccountDTO accountDTO = new AccountDTO();

					accountDTO.setAccountNumber(account.getAccountNumber());
					accountDTO.setAccountType(account.getAccountType());
					accountDTO.setBranchDTO(null);// to be set.

					Customer customer = account.getCustomer();
					CustomerDTO customerDTO = new CustomerDTO();

					customerDTO.setCustId(customer.getCustId());
					customerDTO.setCustName(customer.getCustName());
					customerDTO.setCustPhone(customer.getCustPhone());
					customerDTO.setCustAddress(customer.getCustAddress());

					accountDTO.setCustomer(customerDTO);
					accountDTO.setAccountBalance(account.getAccountBalance());

					return accountDTO;

				}).collect(Collectors.toSet());

				branchDTO.setAccountsDTOs(accountDTOs);

				return branchDTO;
			}).collect(Collectors.toSet());

			bankDTO.setBranches(branchDTOs);
		}
		return bankDTO;
	}

	@Override
	public BankDTO save(BankRequest bankRequest) throws BankNotFoundException {
		log.info("Inside the BankServiceImpl.save method and params, bankRequest:{}", bankRequest);

		if (bankRequest == null) {
			log.info("Invalid bank request");
			throw new BankNotFoundException("Invalid bank request");
		}

		Bank bank = new Bank();
		if (bankRequest.getBankName() != null) {
			bank.setBankName(bankRequest.getBankName());
		}

		bank.setBankCode(bankRequest.getBankCode());
		bank.setBankAddress(bankRequest.getBankAddress());
		bank.setBankAddress(bankRequest.getBankAddress());

		Bank bankResponse = bankRepository.save(bank);
		BankDTO bankDTO = null;
		if (bankResponse != null) {
			log.info("Bank Response, bankResponse:{}", bankResponse);

			bankDTO = new BankDTO();

			bankDTO.setBankName(bankResponse.getBankName());
			bankDTO.setBankAddress(bankResponse.getBankAddress());
		}
		return bankDTO;
	}

	@Override
	public String delete(int id) throws BankNotFoundException {
		log.info("Input to BankServiceImpl.delete, id:{}", id);
		if (id < 0) {
			log.info("Invalid bank id");
			throw new BankNotFoundException("Invalid bank id");
		}
		try {
			bankRepository.deleteById(id);
		} catch (Exception ex) {
			log.error("Exception while deleting bank");
			throw new BankNotFoundException("Exception while deleting bank");
		}
		return "Bank has been deleted";
	}

	@Transactional
	@Override
	public String deleteByBankName(String name) throws BankNotFoundException {
		log.info("Input to BankServiceImpl.deleteByBankName, name:{}", name);
		if (name == null) {
			log.info("Invalid bank name");
			throw new BankNotFoundException("Invalid bank name");
		}
		try {
			bankRepository.deleteByBankName(name);
		} catch (Exception ex) {
			log.error("Exception while deleting bank by name." + ex.getMessage());
			ex.printStackTrace();
			throw new BankNotFoundException("Exception while deleting bank by name.");
		}
		return "Bank has been deleted by name.";
	}

	@Override
	@CacheEvict(value = "cacheOnFindBankByNameCustom", allEntries = true)
	public void clearCacheForFindByNameCustom() {
		log.info("Inside BankServiceImpl.clearCacheForFindByNameCustom");
	}
}