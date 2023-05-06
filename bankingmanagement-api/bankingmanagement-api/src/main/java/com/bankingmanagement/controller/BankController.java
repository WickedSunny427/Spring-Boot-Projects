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

import com.bankingmanagement.dto.BankDTO;
import com.bankingmanagement.dto.BankRequest;
import com.bankingmanagement.exception.BankNotFoundException;
import com.bankingmanagement.service.IBankService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/banks")
public class BankController {

	@Autowired
	private IBankService bankService;

	@GetMapping
	public ResponseEntity<List<BankDTO>> getAllBanks() {
		log.info("Inside BankController and calling the getApplications method ...");
		List<BankDTO> bankDTOs = null;

		try {
			bankDTOs = bankService.findAll();
			log.info("Bank response :{}", bankDTOs);

			if (CollectionUtils.isEmpty(bankDTOs)) {
				log.info("No bank details found !!!");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(" Error while fetching Bank details !!! ", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<BankDTO>>(bankDTOs, HttpStatus.OK);
	}

	/*
	 * Using Try Catch
	// Find a Bank by name.
	@GetMapping("/name")
	public ResponseEntity<BankDTO> getBankByName(@RequestParam("name") String name) {
		log.info("Inside the BankController.getBankByName");
		BankDTO bankDTO = null;
		try {
			// bankDTO = bankService.findByName(name); //Find by Property.
			bankDTO = bankService.findBankByNameCustom(name);// Find by custom Query at JPA Repository.

			log.info("Bank response:{}", bankDTO);
			if (bankDTO == null) {
				log.info("Bank details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			log.error("Exception while calling getBankByName", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BankDTO>(bankDTO, HttpStatus.OK);
	}
*/
	
	
	//Using Exception Handler the controller, don't handle the exception, throw exception from service layer if Bank is not found.
	
	// Find a Bank by name.
	@GetMapping("/name")
	public ResponseEntity<BankDTO> getBankByName(@RequestParam("name") String name) throws BankNotFoundException, InterruptedException {
		log.info("Inside the BankController.getBankByName");
		BankDTO bankDTO = null;
	
			// bankDTO = bankService.findByName(name); //Find by Property.
			bankDTO = bankService.findBankByNameCustom(name);// Find by custom Query at JPA Repository.

			log.info("Bank response:{}", bankDTO);
			if (bankDTO == null) {
				log.info("Bank details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}		
		return new ResponseEntity<BankDTO>(bankDTO, HttpStatus.OK);
	}

	// Find a Bank by ID.
	@GetMapping("/id")
	public ResponseEntity<BankDTO> getBankById(@RequestParam("id") int id) {
		log.info("Inside the BankController.getBankById");
		BankDTO bankDTO = null;
		try {
			bankDTO = bankService.findById(id);

			log.info("Bank response:{}", bankDTO);
			if (bankDTO == null) {
				log.info("Bank details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			log.error("Exception while calling getBankById", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BankDTO>(bankDTO, HttpStatus.OK);
	}

	// Create a new Bank..
	@PostMapping
	public ResponseEntity<BankDTO> save(@RequestBody BankRequest bankRequest) {
		log.info("Inside BankController.save and bankRequest;{}", bankRequest);
		if (bankRequest == null) {
			log.info("Invalid Bank request.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		BankDTO bankDTO = null;
		try {
			bankDTO = bankService.save(bankRequest);
			if (bankDTO == null) {
				log.info("Bank details are not saved.");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			log.error("Exception while saving bank.");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<BankDTO>(bankDTO, HttpStatus.OK);
	}

	// Update an new Bank..
	@PutMapping
	public ResponseEntity<BankDTO> update(@RequestBody BankRequest bankRequest) {
		log.info("Inside BankController.update and bankRequest;{}", bankRequest);
		if (bankRequest == null) {
			log.info("Invalid Bank request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		BankDTO bankDTO = null;
		try {
			bankDTO = bankService.save(bankRequest);
			if (bankDTO == null) {
				log.info("Bank details are not updated.");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			log.error("Exception while updating bank");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<BankDTO>(bankDTO, HttpStatus.OK);
	}

	// Delete an existing bank.
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam("id") int id) {
		log.info("Input to BankController.delete, id:{}", id);
		String response = null;
		try {
			response = bankService.delete(id);
		} catch (Exception ex) {
			log.error("Exception while deleting bank");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	// Delete an existing bank by name..
	@DeleteMapping("/deletebyname")
	public ResponseEntity<String> deleteByName(@RequestParam("name") String name) {
		log.info("Input to BankController.deleteByName, name:{}", name);
		String response = null;
		try {
			response = bankService.deleteByBankName(name);
		} catch (Exception ex) {
			log.error("Exception while deleting bank by name.");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	//End point for calling the caching mechanism.
	@GetMapping("clearcache")
	public ResponseEntity<String> clearByNameCache(){
		try {
			bankService.clearCacheForFindByNameCustom();
			log.info("Cached Cleared !!!");
		} catch (Exception e) {
			log.error("Exception while clearing Cache !!!",e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("Cache cleared for Find By Name Custom Method !!! ", HttpStatus.OK);

	}
}