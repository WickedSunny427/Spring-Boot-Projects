package com.bankingmanagement.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bankingmanagement.entity.Bank;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BankRepositoryTest {

	@Autowired
	public BankRepository bankRepository;

	@Test
	public void testFindBankByNameCustom() {
		saveBank();
		Optional<Bank> bankOptional = bankRepository.findBankByNameCustom("CITI");
		assertEquals("WHITEFIELD", bankOptional.get().getBankAddress());
	}

	@Test
	public void testDeleteByBankName()
	{
		saveBank();
		bankRepository.deleteByBankName("CITI");
	}
	
	
	public void saveBank() {
		Bank bank = new Bank();
		bank.setBankCode(101);
		bank.setBankName("CITI");
		bank.setBankAddress("WHITEFIELD");

		bankRepository.save(bank);
	}
}