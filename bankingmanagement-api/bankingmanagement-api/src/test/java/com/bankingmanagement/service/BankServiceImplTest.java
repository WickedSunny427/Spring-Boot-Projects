package com.bankingmanagement.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.bankingmanagement.dto.BankDTO;
import com.bankingmanagement.entity.Account;
import com.bankingmanagement.entity.Bank;
import com.bankingmanagement.entity.Branch;
import com.bankingmanagement.entity.Customer;
import com.bankingmanagement.entity.Loan;
import com.bankingmanagement.exception.BankNotFoundException;
import com.bankingmanagement.repository.BankRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BankServiceImplTest {
	@Mock
	private BankRepository bankRepository;

	@InjectMocks
	private BankServiceImpl bankService;

	@Test
	public void testfindAllPositive() throws BankNotFoundException {

		Bank bank = new Bank();
		bank.setBankCode(101);
		bank.setBankName("AXIS");
		bank.setBankAddress("WHITEFIELD");

		List<Bank> banks = new ArrayList<>();
		banks.add(bank);

		when(bankRepository.findAll()).thenReturn(banks);

		List<BankDTO> bankDTOList = bankService.findAll();
		assertEquals(1, bankDTOList.size());
	}

	@Test(expected = BankNotFoundException.class)
	public void testFindAllNegativeBankEmpty() throws BankNotFoundException {
		List<Bank> banks = null;

		when(bankRepository.findAll()).thenReturn(banks);

		List<BankDTO> bankDTOList = bankService.findAll();
		assertNull(bankDTOList);// to check if null.

	}

	@Test
	public void testfindAllWithBranchesPositive() throws BankNotFoundException {

		Bank bank = saveBank();

		List<Bank> banks = new ArrayList<>();
		banks.add(bank);

		when(bankRepository.findAll()).thenReturn(banks);

		List<BankDTO> bankDTOList = bankService.findAll();
		assertEquals(1, bankDTOList.size());
	}

	@Test
	public void testFindBankByNameCustomPositive() throws BankNotFoundException, InterruptedException {

		Bank bank = saveBank();

		List<Bank> banks = new ArrayList<>();
		banks.add(bank);

		when(bankRepository.findBankByNameCustom(anyString())).thenReturn(Optional.of(bank));

		BankDTO bankDTO = bankService.findBankByNameCustom("AXIS");

		assertEquals("KARNATAKA", bankDTO.getBankAddress());
	}

	@Test(expected = BankNotFoundException.class)
	public void testFindBankByNameCustomNegativeNullUserInput() throws BankNotFoundException, InterruptedException {

		Bank bank = new Bank();
		bank.setBankName("AXIS");

		List<Bank> banks = new ArrayList<>();
		banks.add(bank);

		when(bankRepository.findBankByNameCustom(anyString())).thenReturn(Optional.of(bank));

		BankDTO bankDTO = bankService.findBankByNameCustom(null);

		assertNull(bankDTO);
	}

	@Test(expected = BankNotFoundException.class)
	public void testFindBankByNameCustomNegativeBankEmpty() throws BankNotFoundException, InterruptedException {

		when(bankRepository.findBankByNameCustom(anyString())).thenReturn(Optional.empty());

		BankDTO bankDTO = bankService.findBankByNameCustom("AXIS");
		assertNull(bankDTO);
	}

	@Test
	public void testFindBankByBankNamePositive() throws BankNotFoundException, InterruptedException {

		Bank bank = saveBank();

		List<Bank> banks = new ArrayList<>();
		banks.add(bank);

		when(bankRepository.findByBankName(anyString())).thenReturn(Optional.of(bank));

		BankDTO bankDTO = bankService.findByName("AXIS");

		assertEquals("KARNATAKA", bankDTO.getBankAddress());
	}

	@Test(expected = BankNotFoundException.class)
	public void testFindBankByNameNegativeNullUserInput() throws BankNotFoundException, InterruptedException {

		Bank bank = new Bank();
		bank.setBankName("AXIS");

		List<Bank> banks = new ArrayList<>();
		banks.add(bank);

		when(bankRepository.findByBankName(anyString())).thenReturn(Optional.of(bank));

		BankDTO bankDTO = bankService.findByName(null);

		assertNull(bankDTO);
	}

	@Test
	public void testFindBankByBankNameNegativeBankEmpty() throws BankNotFoundException, InterruptedException {

		when(bankRepository.findByBankName(anyString())).thenReturn(Optional.empty());

		BankDTO bankDTO = bankService.findByName("AXIS");

		assertNull(bankDTO);
	}

	@Test
	public void testFindByIdPositive() throws BankNotFoundException {

		Bank bank = saveBank();

		when(bankRepository.findById(anyInt())).thenReturn(Optional.of(bank));

		BankDTO bankDTO = bankService.findById(101);

		assertEquals("AXIS", bankDTO.getBankName());

	}

	@Test(expected = BankNotFoundException.class)
	public void testFindByIdNegativeInvalidInput() throws BankNotFoundException {

		Bank bank = saveBank();

		when(bankRepository.findById(anyInt())).thenReturn(Optional.of(bank));

		BankDTO bankDTO = bankService.findById(-20);

		assertNull(bankDTO);

	}

	/*
	 * @Test(expected = BankNotFoundException.class) public void
	 * testFindByIdNegativeBankEmpty() throws BankNotFoundException {
	 * 
	 * when(bankRepository.findById(anyInt())).thenReturn(Optional.empty());
	 * 
	 * BankDTO bankDTO = bankService.findById(112);
	 * 
	 * assertNull(bankDTO);
	 * 
	 * }
	 */

	public Bank saveBank() {
		Bank bank = new Bank();
		bank.setBankCode(101);
		bank.setBankName("AXIS");
		bank.setBankAddress("KARNATAKA");

		Branch branch = new Branch();
		branch.setBranchId(201);
		branch.setBranchName("WHITEFIELD");

		Customer customer = new Customer();
		customer.setCustId(301);
		customer.setCustName("ALLU ARJUN");

		Account account = new Account();
		account.setAccountNumber(401);
		account.setAccountType("CURRENT");
		account.setCustomer(customer);

		Set<Account> accounts = new HashSet<>();
		accounts.add(account);

		Loan loan = new Loan();
		loan.setLoanId(501);
		loan.setLoanType("HOME");
		loan.setLoanAmount(42.35);
		loan.setCustomer(customer);

		Set<Loan> loans = new HashSet<>();
		loans.add(loan);

		Set<Branch> branches = new HashSet<>();
		branches.add(branch);

		Set<Customer> customers = new HashSet<>();
		customers.add(customer);

		branch.setLoans(loans);
		branch.setAccounts(accounts);

		bank.setBranches(branches);
		return bank;

	}
}
