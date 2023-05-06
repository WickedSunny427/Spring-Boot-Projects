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

import com.bankingmanagement.dto.BranchDTO;
import com.bankingmanagement.dto.BranchRequest;
import com.bankingmanagement.service.IBranchService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/branches")
public class BranchController {

	@Autowired
	private IBranchService branchService;

	@GetMapping
	public ResponseEntity<List<BranchDTO>> getAllBranches() {
		log.info("Inside BranchController and calling the getAllBranches method ...");
		List<BranchDTO> branchDTOs = null;

		try {
			branchDTOs = branchService.findAll();
			log.info("Branch response :{}", branchDTOs);

			if (CollectionUtils.isEmpty(branchDTOs)) {
				log.info("No branch details found !!!");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(" Error while fetching Branch details !!! ", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<BranchDTO>>(branchDTOs, HttpStatus.OK);
	}

	// Find a Branch by name.
	@GetMapping("/name")
	public ResponseEntity<BranchDTO> getBranchByName(@RequestParam("name") String name) {
		log.info("Inside the BranchController.getBranchByName");
		BranchDTO branchDTO = null;
		try {
			// branchDTO = branchService.findByName(name); // Find by Property.
			branchDTO = branchService.findBranchByNameCustom(name); // Find by custom Query at JPA Repository.
			log.info("Branch response:{}", branchDTO);
			if (branchDTO == null) {
				log.info("Branch details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			log.error("Exception while calling getBranchByName", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BranchDTO>(branchDTO, HttpStatus.OK);
	}

	// Find a Branch by ID.
	@GetMapping("/id")
	public ResponseEntity<BranchDTO> getBranchById(@RequestParam("id") int id) {
		log.info("Inside the BranchController.getBranchById");
		BranchDTO branchDTO = null;
		try {
			branchDTO = branchService.findById(id);

			log.info("Branch response:{}", branchDTO);
			if (branchDTO == null) {
				log.info("Branch details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			log.error("Exception while calling getBranchById", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BranchDTO>(branchDTO, HttpStatus.OK);
	}

	// Create a new Branch..
	@PostMapping
	public ResponseEntity<BranchDTO> save(@RequestBody BranchRequest branchRequest) {
		log.info("Inside BranchRequestController.save and branchRequest;{}", branchRequest);
		if (branchRequest == null) {
			log.info("Invalid Branch request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		BranchDTO branchDTO = null;
		try {
			branchDTO = branchService.save(branchRequest);
			if (branchDTO == null) {
				log.info("Branch details are not saved");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			log.error("Exception while saving branch");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<BranchDTO>(branchDTO, HttpStatus.OK);
	}

	// Update a new Branch..
	@PutMapping
	public ResponseEntity<BranchDTO> update(@RequestBody BranchRequest branchRequest) {
		log.info("Inside BranchRequestController.update and branchRequest;{}", branchRequest);
		if (branchRequest == null) {
			log.info("Invalid Branch request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		BranchDTO branchDTO = null;
		try {
			branchDTO = branchService.save(branchRequest);
			if (branchDTO == null) {
				log.info("Branch details are not updated.");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			log.error("Exception while updating branch");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<BranchDTO>(branchDTO, HttpStatus.OK);
	}

	// Delete an existing branch.
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam("id") int id) {
		log.info("Input to BranchController.delete, id:{}", id);
		String response = null;
		try {
			response = branchService.delete(id);
		} catch (Exception ex) {
			log.error("Exception while deleting branch");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	// Delete an existing branch by name..
	@DeleteMapping("/deletebyname")
	public ResponseEntity<String> deleteByName(@RequestParam("name") String name) {
		log.info("Input to BranchController.deleteByName, name:{}", name);
		String response = null;
		try {
			response = branchService.deleteByBranchName(name);
		} catch (Exception ex) {
			log.error("Exception while deleting branch by name.");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}