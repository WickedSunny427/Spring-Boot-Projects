package com.bankingmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bankingmanagement.dto.AccountDTO;
import com.bankingmanagement.dto.AccountRequest;
import com.bankingmanagement.dto.BankDTO;
import com.bankingmanagement.dto.BranchDTO;
import com.bankingmanagement.dto.CustomerDTO;
import com.bankingmanagement.dto.LoanDTO;
import com.bankingmanagement.entity.Account;
import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.entity.Customer;
import com.bankingmanagement.entity.Loan;
import com.bankingmanagement.exception.AccountNotFoundException;
import com.bankingmanagement.repository.AccountRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public List<AccountDTO> findAll() throws AccountNotFoundException {
		log.info("Inside AccountServiceImpl.findAll method.");

		List<Account> accounts = accountRepository.findAll();
		log.info("Fetching all account details : accounts {}", accounts);

		if (CollectionUtils.isEmpty(accounts)) {
			log.info("No accounts details available !!!");
			throw new AccountNotFoundException("No account details available !!!");
		}
		List<AccountDTO> accountDTOs = accounts.parallelStream().map(account -> {

			AccountDTO accountDTO = new AccountDTO();

			accountDTO.setAccountNumber(account.getAccountNumber());
			accountDTO.setAccountType(account.getAccountType());
			accountDTO.setAccountBalance(account.getAccountBalance());

			BranchDTO branchDTO = new BranchDTO();

			branchDTO.setBranchName(account.getBranch().getBranchName());
			branchDTO.setBranchAddress(account.getBranch().getBranchAddress());

			Bank bank = account.getBranch().getBank();
			BankDTO bankDTO = new BankDTO();

			bankDTO.setBankName(bank.getBankName());
			bankDTO.setBankAddress(bank.getBankAddress());

			branchDTO.setBankDTO(bankDTO);

			CustomerDTO customerDTO = new CustomerDTO();

			customerDTO.setCustName(account.getCustomer().getCustName());
			customerDTO.setCustId(account.getCustomer().getCustId());
			customerDTO.setCustPhone(account.getCustomer().getCustPhone());
			customerDTO.setCustAddress(account.getCustomer().getCustAddress());

			Set<Loan> loans = account.getCustomer().getLoans();
			Set<LoanDTO> loanDTOs = loans.stream().map(loan -> {

				LoanDTO loanDTO = new LoanDTO();

				loanDTO.setLoanId(loan.getLoanId());
				loanDTO.setLoanType(loan.getLoanType());
				loanDTO.setLoanAmount(loan.getLoanAmount());

				return loanDTO;

			}).collect(Collectors.toSet());

			customerDTO.setLoansDTOs(loanDTOs);

			accountDTO.setBranchDTO(branchDTO);
			accountDTO.setCustomer(customerDTO);

			return accountDTO;
		}).collect(Collectors.toList());

		return accountDTOs;
	}

	@Override
	public AccountDTO findByAccountType(String accountType) throws AccountNotFoundException {

		log.info("Inside AccountServiceImpl.findByAccountType and accountType: {}", accountType);
		AccountDTO accountDTO = null;
		if (accountType == null) {
			log.info("Invalid account type :{}", accountType);
			throw new AccountNotFoundException("Account type is not valid");
		}
		Optional<Account> account = accountRepository.findByAccountType(accountType);
		log.info("Default findByAccountType invoked Successfully !!!");

		if (account.isPresent()) {
			log.info("Account details for the account type {} and the values :{}", accountType, account.get());

			accountDTO = new AccountDTO();

			accountDTO.setAccountNumber(account.get().getAccountNumber());
			accountDTO.setAccountType(account.get().getAccountType());
			accountDTO.setAccountBalance(account.get().getAccountBalance());

			BranchDTO branchDTO = new BranchDTO();

			branchDTO.setBranchName(account.get().getBranch().getBranchName());
			branchDTO.setBranchAddress(account.get().getBranch().getBranchAddress());

			Bank bank = account.get().getBranch().getBank();
			BankDTO bankDTO = new BankDTO();

			bankDTO.setBankName(bank.getBankName());
			bankDTO.setBankAddress(bank.getBankAddress());

			branchDTO.setBankDTO(bankDTO);

			CustomerDTO customerDTO = new CustomerDTO();

			customerDTO.setCustName(account.get().getCustomer().getCustName());
			customerDTO.setCustId(account.get().getCustomer().getCustId());
			customerDTO.setCustPhone(account.get().getCustomer().getCustPhone());
			customerDTO.setCustAddress(account.get().getCustomer().getCustAddress());

			Set<Loan> loans = account.get().getCustomer().getLoans();
			Set<LoanDTO> loanDTOs = loans.stream().map(loan -> {

				LoanDTO loanDTO = new LoanDTO();

				loanDTO.setLoanId(loan.getLoanId());
				loanDTO.setLoanType(loan.getLoanType());
				loanDTO.setLoanAmount(loan.getLoanAmount());

				return loanDTO;

			}).collect(Collectors.toSet());

			customerDTO.setLoansDTOs(loanDTOs);

			accountDTO.setBranchDTO(branchDTO);
			accountDTO.setCustomer(customerDTO);
		}
		return accountDTO;
	}

	@Override
	public AccountDTO findByAccountTypeCustom(String accountType) throws AccountNotFoundException {

		log.info("Inside AccountServiceImpl.findByAccountTypeCustom and accountType: {}", accountType);
		AccountDTO accountDTO = null;
		if (accountType == null) {
			log.info("Invalid account type :{}", accountType);
			throw new AccountNotFoundException("Account type is not valid");
		}
		Optional<Account> account = accountRepository.findByAccountTypeCustom(accountType);
		log.info("Account findByAccountTypeCustom invoked Successfully !!!");

		if (account.isPresent()) {
			log.info("Account details for the account type {} and the values :{}", accountType, account.get());

			accountDTO = new AccountDTO();

			accountDTO.setAccountNumber(account.get().getAccountNumber());
			accountDTO.setAccountType(account.get().getAccountType());
			accountDTO.setAccountBalance(account.get().getAccountBalance());

			BranchDTO branchDTO = new BranchDTO();

			branchDTO.setBranchName(account.get().getBranch().getBranchName());
			branchDTO.setBranchAddress(account.get().getBranch().getBranchAddress());

			Bank bank = account.get().getBranch().getBank();
			BankDTO bankDTO = new BankDTO();

			bankDTO.setBankName(bank.getBankName());
			bankDTO.setBankAddress(bank.getBankAddress());

			branchDTO.setBankDTO(bankDTO);

			CustomerDTO customerDTO = new CustomerDTO();

			customerDTO.setCustName(account.get().getCustomer().getCustName());
			customerDTO.setCustId(account.get().getCustomer().getCustId());
			customerDTO.setCustPhone(account.get().getCustomer().getCustPhone());
			customerDTO.setCustAddress(account.get().getCustomer().getCustAddress());

			Set<Loan> loans = account.get().getCustomer().getLoans();
			Set<LoanDTO> loanDTOs = loans.stream().map(loan -> {

				LoanDTO loanDTO = new LoanDTO();

				loanDTO.setLoanId(loan.getLoanId());
				loanDTO.setLoanType(loan.getLoanType());
				loanDTO.setLoanAmount(loan.getLoanAmount());

				return loanDTO;

			}).collect(Collectors.toSet());

			customerDTO.setLoansDTOs(loanDTOs);

			accountDTO.setBranchDTO(branchDTO);
			accountDTO.setCustomer(customerDTO);
		}
		return accountDTO;
	}

	@Override
	public AccountDTO findByAccountNumber(int accountNumber) throws AccountNotFoundException {

		log.info("Inside AccountServiceImpl.findByAccountNumber and accountNumber: {}", accountNumber);
		AccountDTO accountDTO = null;
		if (accountNumber < 0) {
			log.info("Invalid account Number :{}", accountNumber);
			throw new AccountNotFoundException("Account number is not valid");
		}
		Optional<Account> account = accountRepository.findById(accountNumber);
		log.info("Account findByAccountNumber invoked Successfully !!!");

		if (account.isPresent()) {
			log.info("Account details for the account number {} and the values :{}", accountNumber, account.get());

			accountDTO = new AccountDTO();

			accountDTO.setAccountNumber(account.get().getAccountNumber());
			accountDTO.setAccountType(account.get().getAccountType());
			accountDTO.setAccountBalance(account.get().getAccountBalance());

			BranchDTO branchDTO = new BranchDTO();

			branchDTO.setBranchName(account.get().getBranch().getBranchName());
			branchDTO.setBranchAddress(account.get().getBranch().getBranchAddress());

			Bank bank = account.get().getBranch().getBank();
			BankDTO bankDTO = new BankDTO();

			bankDTO.setBankName(bank.getBankName());
			bankDTO.setBankAddress(bank.getBankAddress());

			branchDTO.setBankDTO(bankDTO);

			CustomerDTO customerDTO = new CustomerDTO();

			customerDTO.setCustName(account.get().getCustomer().getCustName());
			customerDTO.setCustId(account.get().getCustomer().getCustId());
			customerDTO.setCustPhone(account.get().getCustomer().getCustPhone());
			customerDTO.setCustAddress(account.get().getCustomer().getCustAddress());

			Set<Loan> loans = account.get().getCustomer().getLoans();
			Set<LoanDTO> loanDTOs = loans.stream().map(loan -> {

				LoanDTO loanDTO = new LoanDTO();

				loanDTO.setLoanId(loan.getLoanId());
				loanDTO.setLoanType(loan.getLoanType());
				loanDTO.setLoanAmount(loan.getLoanAmount());

				return loanDTO;

			}).collect(Collectors.toSet());

			customerDTO.setLoansDTOs(loanDTOs);

			accountDTO.setBranchDTO(branchDTO);
			accountDTO.setCustomer(customerDTO);
		}
		return accountDTO;
	}

	@Override
	public AccountDTO save(AccountRequest accountRequest) throws AccountNotFoundException {
		log.info("Inside the AccountServiceImpl.save method and params, accountRequest:{}", accountRequest);

		if (accountRequest == null) {
			log.info("Invalid account request");
			throw new AccountNotFoundException("Invalid account request");
		}

		Account account = new Account();

		if (accountRequest.getAccountType() != null) {
			account.setAccountType(accountRequest.getAccountType());
		}

		account.setAccountNumber(accountRequest.getAccountNumber());
		account.setAccountBalance(accountRequest.getAccountBalance());

		Branch branch = new Branch();

		branch.setBranchId(accountRequest.getBranchDTO().getBranchId());
		account.setBranch(branch);

		Customer customer = new Customer();

		customer.setCustId(accountRequest.getCustomer().getCustId());
		account.setCustomer(customer);

		Account accountResponse = accountRepository.save(account);
		AccountDTO accountDTO = null;
		if (accountResponse != null) {
			log.info("Account Response, accountResponse:{}", accountResponse);

			accountDTO = new AccountDTO();

			accountDTO.setAccountNumber(accountResponse.getAccountNumber());
			accountDTO.setAccountType(accountResponse.getAccountType());

			BranchDTO branchDTO = new BranchDTO();
			branchDTO.setBranchId(accountResponse.getBranch().getBranchId());
			branchDTO.setBranchName(accountResponse.getBranch().getBranchName());
			branchDTO.setBranchAddress(accountResponse.getBranch().getBranchAddress());

			accountDTO.setBranchDTO(branchDTO);

			CustomerDTO customerDTO = new CustomerDTO();
			customerDTO.setCustId(accountResponse.getCustomer().getCustId());
			customerDTO.setCustName(accountResponse.getCustomer().getCustName());
			customerDTO.setCustPhone(accountResponse.getCustomer().getCustPhone());
			customerDTO.setCustAddress(accountResponse.getCustomer().getCustAddress());

			accountDTO.setCustomer(customerDTO);

			accountDTO.setAccountBalance(accountResponse.getAccountBalance());

		}
		return accountDTO;
	}

	@Override
	public String delete(int id) throws AccountNotFoundException {
		log.info("Input to AccountServiceImpl.delete, id:{}", id);
		if (id < 0) {
			log.info("Invalid account id");
			throw new AccountNotFoundException("Invalid account id");
		}
		try {
			accountRepository.deleteById(id);
		} catch (Exception ex) {
			log.error("Exception while deleting account");
			throw new AccountNotFoundException("Exception while deleting account.");
		}
		return "Account has been deleted.";
	}

}
