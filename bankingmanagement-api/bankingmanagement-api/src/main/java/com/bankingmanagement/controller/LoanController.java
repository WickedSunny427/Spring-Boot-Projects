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

import com.bankingmanagement.dto.LoanDTO;
import com.bankingmanagement.dto.LoanRequest;
import com.bankingmanagement.service.ILoanService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/loans")
public class LoanController {

	@Autowired
	private ILoanService loanService;

	@GetMapping
	public ResponseEntity<List<LoanDTO>> getAllLoans() {
		log.info("Inside LoanController and calling the getAllLoans method ...");
		List<LoanDTO> loanDTOs = null;

		try {
			loanDTOs = loanService.findAll();
			log.info("Loan response :{}", loanDTOs);

			if (CollectionUtils.isEmpty(loanDTOs)) {
				log.info("No loan details found !!!");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(" Error while fetching Loan details !!! ", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<LoanDTO>>(loanDTOs, HttpStatus.OK);
	}

	// Find a Customer by name.
	@GetMapping("/type")
	public ResponseEntity<LoanDTO> getLoanByType(@RequestParam("type") String type) {
		log.info("Inside the LoanController.getLoanByType");
		LoanDTO loanDTO = null;
		try {
			// loanDTO = loanService.findByType(type);
			loanDTO = loanService.findLoanByTypeCustom(type);

			log.info("Loan response:{}", loanDTO);
			if (loanDTO == null) {
				log.info("Loan details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			log.error("Exception while calling getLoanByType", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<LoanDTO>(loanDTO, HttpStatus.OK);
	}

	// Find a Loan by ID.
	@GetMapping("/id")
	public ResponseEntity<LoanDTO> getLoanById(@RequestParam("id") int id) {
		log.info("Inside the LoanController.getLoanById");
		LoanDTO loanDTO = null;
		try {
			loanDTO = loanService.findById(id);

			log.info("Loan response:{}", loanDTO);
			if (loanDTO == null) {
				log.info("Loan details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			log.error("Exception while calling getLoanById", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<LoanDTO>(loanDTO, HttpStatus.OK);
	}

	// Create a new Loan..
	@PostMapping
	public ResponseEntity<LoanDTO> save(@RequestBody LoanRequest loanRequest) {
		log.info("Inside LoanRequestController.save and loanRequest;{}", loanRequest);
		if (loanRequest == null) {
			log.info("Invalid Loan request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		LoanDTO loanDTO = null;
		try {
			loanDTO = loanService.save(loanRequest);
			if (loanDTO == null) {
				log.info("Loan details are not saved");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			log.error("Exception while saving loan");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<LoanDTO>(loanDTO, HttpStatus.OK);
	}

	// Update an existing Loan..
	@PutMapping
	public ResponseEntity<LoanDTO> update(@RequestBody LoanRequest loanRequest) {
		log.info("Inside LoanRequestController.update and loanRequest;{}", loanRequest);
		if (loanRequest == null) {
			log.info("Invalid Loan request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		LoanDTO loanDTO = null;
		try {
			loanDTO = loanService.save(loanRequest);
			if (loanDTO == null) {
				log.info("Loan details are not updated");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			log.error("Exception while updating loan");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<LoanDTO>(loanDTO, HttpStatus.OK);
	}

	// Delete an existing loan.
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam("id") int id) {
		log.info("Input to LoanController.delete, id:{}", id);
		String response = null;
		try {
			response = loanService.delete(id);
		} catch (Exception ex) {
			log.error("Exception while deleting loan");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}