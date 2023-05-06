package com.bankingmanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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

import com.bankingmanagement.aysnservice.ICustomerAsyncService;
import com.bankingmanagement.dto.CustomerDTO;
import com.bankingmanagement.dto.CustomerRequest;
import com.bankingmanagement.service.ICustomerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

	@Autowired
	private ICustomerService customerService;

	@Autowired
	private ICustomerAsyncService customerAsyncService;

	@GetMapping
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
		log.info("Inside CustomerController and calling the getAllCustomers method ...");
		List<CustomerDTO> customerDTOs = null;

		try {
			customerDTOs = customerService.findAll();
			log.info("Customer response :{}", customerDTOs);

			if (CollectionUtils.isEmpty(customerDTOs)) {
				log.info("No customer details found !!!");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(" Error while fetching Customer details !!! ", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<CustomerDTO>>(customerDTOs, HttpStatus.OK);
	}

	// Find a Customer by name.
	@GetMapping("/name")
	public ResponseEntity<CustomerDTO> getCustomerByName(@RequestParam("name") String name) {
		log.info("Inside the CustomerController.getCustomerByName");
		CustomerDTO customerDTO = null;
		try {
			// customerDTO = customerService.findByName(name); // Find by Property.
			customerDTO = customerService.findCustomerByNameCustom(name); // Find by custom Query at
			// JPA Repository.

			log.info("Customer response:{}", customerDTO);
			if (customerDTO == null) {
				log.info("Customer details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			log.error("Exception while calling getCustomerByName", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CustomerDTO>(customerDTO, HttpStatus.OK);
	}

	// Find a Customer by ID.
	@GetMapping("/id")
	public ResponseEntity<CustomerDTO> getCustomerById(@RequestParam("id") int id) {
		log.info("Inside the CustomerController.getCustomerById");
		CustomerDTO customerDTO = null;
		try {
			customerDTO = customerService.findById(id);

			log.info("Customer response:{}", customerDTO);
			if (customerDTO == null) {
				log.info("Customer details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			log.error("Exception while calling getCustomerById", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CustomerDTO>(customerDTO, HttpStatus.OK);
	}

	// Find a Customer by name and id without Asynchronous feature.
	// It takes 662 ms

	@GetMapping("/idandnamewithoutasync")
	public ResponseEntity<List<CustomerDTO>> getCustomerByNameAndIdSynchronous(@RequestParam("name") String name,
			@RequestParam("id") int id) {
		log.info("Inside the CustomerController.getCustomerByNameAndIdSynchronous");
		List<CustomerDTO> customers = null;
		CustomerDTO customerDTO1 = null;
		CustomerDTO customerDTO2 = null;
		try {
			customerDTO1 = customerService.findByName(name); // Find by name.
			log.info("Customer by name response:{}", customerDTO1);

			customerDTO2 = customerService.findById(id); // Find by Id..
			log.info("Customer by id response:{}", customerDTO1);

			if (customerDTO1 == null || customerDTO2 == null) {
				log.info("Customer details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			customers = new ArrayList<>();

			customers.add(customerDTO1);
			customers.add(customerDTO2);
		} catch (Exception ex) {
			log.error("Exception while calling getCustomerByNameAndIdSynchronous", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<CustomerDTO>>(customers, HttpStatus.OK);
	}

	// Find a Customer by name and id WITH ASYNCHRONOUS feature.
	// It takes id and name both and returns the joined/combined result.

	@GetMapping("/idandnamewithasync")
	public ResponseEntity<List<CustomerDTO>> getCustomerByNameAndIdWithAsynchronous(@RequestParam("name") String name,
			@RequestParam("id") int id) {
		log.info("Inside the CustomerController.getCustomerByNameAndIdWithAsynchronous");

		CompletableFuture<CustomerDTO> customerDTObyId = null;
		CompletableFuture<CustomerDTO> customerDTObyName = null;
		List<CustomerDTO> customers = new ArrayList<>();
		try {
			customerDTObyId = customerAsyncService.findById(id); // Find by id..
			log.info("Customer by id response:{}", customerDTObyId);

			customerDTObyName = customerAsyncService.findByName(name); // Find by name.
			log.info("Customer by name response:{}", customerDTObyName);

			if (customerDTObyId == null || customerDTObyName == null) {
				log.info("Customer details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			CompletableFuture.allOf(customerDTObyId, customerDTObyName).join();

			customers.add(customerDTObyId.get());
			customers.add(customerDTObyName.get());
		} catch (Exception ex) {
			log.error("Exception while calling getCustomerByNameAndIdWithAsynchronous", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<CustomerDTO>>(customers, HttpStatus.OK);
	}
	// Find a Customer by list of id's WITH ASYNCHRONOUS feature.
	// It takes multiple id's and returns the joined/combined result.

	@GetMapping("/idmultiplewithasync")
	public ResponseEntity<List<CustomerDTO>> getCustomerByMultipleIdsWithAsynchronous(
			@RequestParam("customerids") List<Integer> customerids) {
		log.info("Inside the CustomerController.getCustomerByMultipleIdsWithAsynchronous");

		CompletableFuture<CustomerDTO> customerDTObyId = null;

		List<CustomerDTO> customers = new ArrayList<>();
		List<Integer> ids = customerids;

		for (Integer id : ids) {
			try {
				customerDTObyId = customerAsyncService.findById(id); // Find by id..
				log.info("Customer by id response:{}", customerDTObyId);

				if (customerDTObyId == null) {
					log.info("Customer details are not found");
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}

				CompletableFuture.allOf(customerDTObyId).join();

				customers.add(customerDTObyId.get());

			} catch (Exception e) {
				log.error("Exception while calling getCustomerByNameAndIdWithAsynchronous", e);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<List<CustomerDTO>>(customers, HttpStatus.OK);
	}

	// Create a new Customer.
	@PostMapping
	public ResponseEntity<CustomerDTO> save(@RequestBody CustomerRequest customerRequest) {
		log.info("Inside CustomerController.save and customerRequest;{}", customerRequest);
		if (customerRequest == null) {
			log.info("Invalid Customer request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		CustomerDTO customerDTO = null;
		try {
			customerDTO = customerService.save(customerRequest);
			if (customerDTO == null) {
				log.info("Customer details are not saved");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			log.error("Exception while saving customer.");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<CustomerDTO>(customerDTO, HttpStatus.OK);
	}

	// Update an existing Customer.
	@PutMapping
	public ResponseEntity<CustomerDTO> update(@RequestBody CustomerRequest customerRequest) {
		log.info("Inside CustomerController.update and customerRequest;{}", customerRequest);
		if (customerRequest == null) {
			log.info("Invalid Customer request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		CustomerDTO customerDTO = null;
		try {
			customerDTO = customerService.save(customerRequest);
			if (customerDTO == null) {
				log.info("Customer details are not updated.");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			log.error("Exception while updating customer.");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<CustomerDTO>(customerDTO, HttpStatus.OK);
	}

	// Delete an existing customer..
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam("id") int id) {
		log.info("Input to CustomerController.delete, id:{}", id);
		String response = null;
		try {
			response = customerService.delete(id);
		} catch (Exception ex) {
			log.error("Exception while deleting customer.");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	// Delete an existing customer by name..
	@DeleteMapping("/deletebyname")
	public ResponseEntity<String> deleteByName(@RequestParam("name") String name) {
		log.info("Input to CustomerController.deleteByName, name:{}", name);
		String response = null;
		try {
			response = customerService.deleteByCustomerName(name);
		} catch (Exception ex) {
			log.error("Exception while deleting customer by name.");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

}