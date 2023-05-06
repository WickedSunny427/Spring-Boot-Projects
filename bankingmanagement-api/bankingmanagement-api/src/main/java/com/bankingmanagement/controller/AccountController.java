package com.bankingmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingmanagement.dto.AccountDTO;
import com.bankingmanagement.dto.AccountRequest;
import com.bankingmanagement.service.IAccountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {

	@Autowired
	private IAccountService accountService;

	@GetMapping
	public ResponseEntity<List<AccountDTO>> getAllAccounts() {
		log.info("Inside AccountController and calling the getAllAccounts method ...");
		List<AccountDTO> accountDTOs = null;

		try {
			accountDTOs = accountService.findAll();
			log.info("Account response : {}", accountDTOs);

			if (CollectionUtils.isEmpty(accountDTOs)) {
				log.info("No account details found !!!");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(" Error while fetching Account details !!! ", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<AccountDTO>>(accountDTOs, HttpStatus.OK);
	}

	// Find an Account by name.
	@GetMapping("/type")
	public ResponseEntity<AccountDTO> getAccountByType(@RequestParam("type") String accountType) {
		log.info("Inside the AccountController.getAccountByType");
		AccountDTO accountDTO = null;
		try {
			// accountDTO = accountService.findByAccountType(accountType); // Find by
			// Account Type.
			accountDTO = accountService.findByAccountTypeCustom(accountType); // Find by Account Type Custom Query

			log.info("Account response:{}", accountDTO);
			if (accountDTO == null) {
				log.info("Account details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			log.error("Exception while calling findByAccountType", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<AccountDTO>(accountDTO, HttpStatus.OK);
	}

	// Find an Account by account number.
	@GetMapping("/accountnumber")
	public ResponseEntity<AccountDTO> getAccountByAccountNumber(@RequestParam("accountnumber") int accountNumber) {
		log.info("Inside the AccountController.getAccountByAccountNumber");
		AccountDTO accountDTO = null;
		try {
			accountDTO = accountService.findByAccountNumber(accountNumber);

			log.info("Account response:{}", accountDTO);
			if (accountDTO == null) {
				log.info("Account details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			log.error("Exception while calling getAccountByAccountNumber", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<AccountDTO>(accountDTO, HttpStatus.OK);
	}

	// Create a new Account...
	@PostMapping
	public ResponseEntity<AccountDTO> save(@RequestBody AccountRequest accountRequest) {
		log.info("Inside AccountController.save and accountRequest;{}", accountRequest);
		if (accountRequest == null) {
			log.info("Invalid Account request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		AccountDTO accountDTO = null;
		try {
			accountDTO = accountService.save(accountRequest);
			if (accountDTO == null) {
				log.info("Account details are not saved");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			log.error("Exception while saving account." + ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<AccountDTO>(accountDTO, HttpStatus.OK);
	}

	// Update an existing Account...
	@PutMapping
	public ResponseEntity<AccountDTO> update(@RequestBody AccountRequest accountRequest) {
		log.info("Inside AccountController.update and accountRequest;{}", accountRequest);
		if (accountRequest == null) {
			log.info("Invalid Account request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		AccountDTO accountDTO = null;
		try {
			accountDTO = accountService.save(accountRequest);
			if (accountDTO == null) {
				log.info("Account details are not updated.");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			log.error("Exception while updating account." + ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<AccountDTO>(accountDTO, HttpStatus.OK);
	}

	// Delete an existing account..
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam("id") int id) {
		log.info("Input to AccountController.delete, id:{}", id);
		String response = null;
		try {
			response = accountService.delete(id);
		} catch (Exception ex) {
			log.error("Exception while deleting account.");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

}