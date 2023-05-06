package com.bankingmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bankingmanagement.dto.AccountDTO;
import com.bankingmanagement.dto.BankDTO;
import com.bankingmanagement.dto.BranchDTO;
import com.bankingmanagement.dto.CustomerDTO;
import com.bankingmanagement.dto.LoanDTO;
import com.bankingmanagement.dto.LoanRequest;
import com.bankingmanagement.entity.Account;
import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.entity.Customer;
import com.bankingmanagement.entity.Loan;
import com.bankingmanagement.exception.LoanNotFoundException;
import com.bankingmanagement.repository.LoanRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoanServiceImpl implements ILoanService {

	@Autowired
	private LoanRepository loanRepository;

	@Override
	public List<LoanDTO> findAll() throws LoanNotFoundException {
		log.info("Inside LoanServiceImpl.findAll method.");

		List<Loan> loans = loanRepository.findAll();
		log.info("Fetching all loan details : loans {}", loans);

		if (CollectionUtils.isEmpty(loans)) {
			log.info("No loan details available !!!");
			throw new LoanNotFoundException("No loan details available !!!");
		}
		List<LoanDTO> loanDTOs = loans.parallelStream().map(loan -> {

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

			CustomerDTO customerDTO = new CustomerDTO();

			customerDTO.setCustName(loan.getCustomer().getCustName());
			customerDTO.setCustId(loan.getCustomer().getCustId());
			customerDTO.setCustPhone(loan.getCustomer().getCustPhone());
			customerDTO.setCustAddress(loan.getCustomer().getCustAddress());

			Set<Account> accounts = loan.getCustomer().getAccounts();
			Set<AccountDTO> accountDTOs = accounts.stream().map(account -> {

				AccountDTO accountDTO = new AccountDTO();

				accountDTO.setAccountNumber(account.getAccountNumber());
				accountDTO.setAccountType(account.getAccountType());
				accountDTO.setAccountBalance(account.getAccountBalance());

				return accountDTO;

			}).collect(Collectors.toSet());

			customerDTO.setAccountsDTOs(accountDTOs);

			loanDTO.setCustomer(customerDTO);

			loanDTO.setLoanAmount(loan.getLoanAmount());

			return loanDTO;
		}).collect(Collectors.toList());
		return loanDTOs;
	}

	@Override
	public LoanDTO findByType(String type) throws LoanNotFoundException {
		log.info("Inside LoanServiceImpl.findByType and name: {}", type);
		LoanDTO loanDTO = null;
		if (type == null) {
			log.info("Invalid loan type:{}", type);
			throw new LoanNotFoundException("Loan type is not valid");
		}
		Optional<Loan> loan = loanRepository.findByLoanType(type);
		log.info("Default findByLoanType invoked Successfully !!!");

		if (loan.isPresent()) {
			log.info("Loan details for the type {} and the values :{}", type, loan.get());

			loanDTO = new LoanDTO();

			loanDTO.setLoanId(loan.get().getLoanId());
			loanDTO.setLoanType(loan.get().getLoanType());

			Branch branch = loan.get().getBranch();
			BranchDTO branchDTO = new BranchDTO();

			branchDTO.setBranchName(branch.getBranchName());
			branchDTO.setBranchAddress(branch.getBranchAddress());

			Bank bank = loan.get().getBranch().getBank();
			BankDTO bankDTO = new BankDTO();

			bankDTO.setBankName(bank.getBankName());
			bankDTO.setBankAddress(bank.getBankAddress());

			branchDTO.setBankDTO(bankDTO);
			loanDTO.setBranchDTO(branchDTO);

			CustomerDTO customerDTO = new CustomerDTO();

			customerDTO.setCustName(loan.get().getCustomer().getCustName());
			customerDTO.setCustId(loan.get().getCustomer().getCustId());
			customerDTO.setCustPhone(loan.get().getCustomer().getCustPhone());
			customerDTO.setCustAddress(loan.get().getCustomer().getCustAddress());

			Set<Account> accounts = loan.get().getCustomer().getAccounts();
			Set<AccountDTO> accountDTOs = accounts.stream().map(account -> {

				AccountDTO accountDTO = new AccountDTO();

				accountDTO.setAccountNumber(account.getAccountNumber());
				accountDTO.setAccountType(account.getAccountType());
				accountDTO.setAccountBalance(account.getAccountBalance());

				return accountDTO;

			}).collect(Collectors.toSet());

			customerDTO.setAccountsDTOs(accountDTOs);

			loanDTO.setCustomer(customerDTO);

			loanDTO.setLoanAmount(loan.get().getLoanAmount());
		}
		return loanDTO;
	}

	@Override
	public LoanDTO findLoanByTypeCustom(String type) throws LoanNotFoundException {
		log.info("Inside LoanServiceImpl.findLoanByTypeCustom and name: {}", type);
		LoanDTO loanDTO = null;
		if (type == null) {
			log.info("Invalid loan type:{}", type);
			throw new LoanNotFoundException("Loan type is not valid");
		}
		Optional<Loan> loan = loanRepository.findLoanByTypeCustom(type);
		log.info("Custom findLoanByTypeCustom invoked Successfully !!!");

		if (loan.isPresent()) {
			log.info("Loan details for the type {} and the values :{}", type, loan.get());

			loanDTO = new LoanDTO();

			loanDTO.setLoanId(loan.get().getLoanId());
			loanDTO.setLoanType(loan.get().getLoanType());

			Branch branch = loan.get().getBranch();
			BranchDTO branchDTO = new BranchDTO();

			branchDTO.setBranchName(branch.getBranchName());
			branchDTO.setBranchAddress(branch.getBranchAddress());

			Bank bank = loan.get().getBranch().getBank();
			BankDTO bankDTO = new BankDTO();

			bankDTO.setBankName(bank.getBankName());
			bankDTO.setBankAddress(bank.getBankAddress());

			branchDTO.setBankDTO(bankDTO);
			loanDTO.setBranchDTO(branchDTO);

			CustomerDTO customerDTO = new CustomerDTO();

			customerDTO.setCustName(loan.get().getCustomer().getCustName());
			customerDTO.setCustId(loan.get().getCustomer().getCustId());
			customerDTO.setCustPhone(loan.get().getCustomer().getCustPhone());
			customerDTO.setCustAddress(loan.get().getCustomer().getCustAddress());

			Set<Account> accounts = loan.get().getCustomer().getAccounts();
			Set<AccountDTO> accountDTOs = accounts.stream().map(account -> {

				AccountDTO accountDTO = new AccountDTO();

				accountDTO.setAccountNumber(account.getAccountNumber());
				accountDTO.setAccountType(account.getAccountType());
				accountDTO.setAccountBalance(account.getAccountBalance());

				return accountDTO;

			}).collect(Collectors.toSet());

			customerDTO.setAccountsDTOs(accountDTOs);

			loanDTO.setCustomer(customerDTO);

			loanDTO.setLoanAmount(loan.get().getLoanAmount());
		}
		return loanDTO;
	}

	@Override
	public LoanDTO findById(int id) throws LoanNotFoundException {
		log.info("Inside LoanServiceImpl.findById and id: {}", id);
		LoanDTO loanDTO = null;
		if (id < 0) {
			log.info("Invalid loan id:{}", id);
			throw new LoanNotFoundException("Loan id is not valid");
		}
		Optional<Loan> loan = loanRepository.findById(id);// Find loan by ID default JPA available
															// method.

		if (loan.isPresent()) {
			log.info("Loan details for the id {} and the values :{}", id, loan.get());

			loanDTO = new LoanDTO();

			loanDTO.setLoanId(loan.get().getLoanId());
			loanDTO.setLoanType(loan.get().getLoanType());

			Branch branch = loan.get().getBranch();
			BranchDTO branchDTO = new BranchDTO();

			branchDTO.setBranchName(branch.getBranchName());
			branchDTO.setBranchAddress(branch.getBranchAddress());

			Bank bank = loan.get().getBranch().getBank();
			BankDTO bankDTO = new BankDTO();

			bankDTO.setBankName(bank.getBankName());
			bankDTO.setBankAddress(bank.getBankAddress());

			branchDTO.setBankDTO(bankDTO);
			loanDTO.setBranchDTO(branchDTO);

			CustomerDTO customerDTO = new CustomerDTO();

			customerDTO.setCustName(loan.get().getCustomer().getCustName());
			customerDTO.setCustId(loan.get().getCustomer().getCustId());
			customerDTO.setCustPhone(loan.get().getCustomer().getCustPhone());
			customerDTO.setCustAddress(loan.get().getCustomer().getCustAddress());

			Set<Account> accounts = loan.get().getCustomer().getAccounts();
			Set<AccountDTO> accountDTOs = accounts.stream().map(account -> {

				AccountDTO accountDTO = new AccountDTO();

				accountDTO.setAccountNumber(account.getAccountNumber());
				accountDTO.setAccountType(account.getAccountType());
				accountDTO.setAccountBalance(account.getAccountBalance());

				return accountDTO;

			}).collect(Collectors.toSet());

			customerDTO.setAccountsDTOs(accountDTOs);

			loanDTO.setCustomer(customerDTO);

			loanDTO.setLoanAmount(loan.get().getLoanAmount());
		}
		return loanDTO;

	}

	@Override
	public LoanDTO save(LoanRequest loanRequest) throws LoanNotFoundException {
		log.info("Inside the LoanServiceImpl.save method and params, loanRequest:{}", loanRequest);

		if (loanRequest == null) {
			log.info("Invalid loan request");
			throw new LoanNotFoundException("Invalid loan request");
		}

		Loan loan = new Loan();
		if (loanRequest.getLoanAmount()>0) {
			loan.setLoanAmount(loanRequest.getLoanAmount());
		}

		loan.setLoanId(loanRequest.getLoanId());
		loan.setLoanType(loanRequest.getLoanType());
		
		Branch branch= new Branch();
		branch.setBranchId(loanRequest.getBranchDTO().getBranchId());
		
		loan.setBranch(branch);
		
		Customer customer= new Customer();
		customer.setCustId(loanRequest.getCustomerDTO().getCustId());
		
		loan.setCustomer(customer);
	
		Loan loanResponse = loanRepository.save(loan);
		LoanDTO loanDTO = null;
		if (loanResponse != null) {
			log.info("Loan Response, loanResponse:{}", loanResponse);

			loanDTO = new LoanDTO();
			
			loanDTO.setLoanId(loanResponse.getLoanId());
			loanDTO.setLoanType(loanResponse.getLoanType());
			loanDTO.setLoanAmount(loanResponse.getLoanAmount());
			loanDTO.setBranchDTO(null);//to be set.
			loanDTO.setCustomer(null);//to be set.

			}
		return loanDTO;
	}

	@Override
	public String delete(int id) throws LoanNotFoundException {
		log.info("Input to LoanServiceImpl.delete, id:{}", id);
		if (id < 0) {
			log.info("Invalid loan id");
			throw new LoanNotFoundException("Invalid loan id");
		}
		try {
			loanRepository.deleteById(id);
		} catch (Exception ex) {
			log.error("Exception while deleting loan");
			throw new LoanNotFoundException("Exception while deleting loan");
		}
		return "Loan has been deleted";
	}
}