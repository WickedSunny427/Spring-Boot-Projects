package com.bugtracking.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracking.model.ApplicationVO;
import com.bugtracking.model.ReleaseRequest;
import com.bugtracking.model.ReleaseVO;
import com.bugtracking.service.IReleaseService;

@RestController
@RequestMapping("api/v1/releases")
public class ReleaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseController.class);

	@Autowired
	private IReleaseService releaseService;

	// Fetching all Releases.
	@GetMapping
	public ResponseEntity<List<ReleaseVO>> getReleases() {
		LOGGER.info("Inside ReleaseController and calling the getReleasess method ...");
		List<ReleaseVO> releaseVOS = null;

		try {
			releaseVOS = releaseService.findAll();
			LOGGER.info("Release response :{}", releaseVOS);

			if (CollectionUtils.isEmpty(releaseVOS)) {
				LOGGER.info("No Releases found !!!");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOGGER.error(" Error while fetching Release details !!! ", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<ReleaseVO>>(releaseVOS, HttpStatus.OK);
	}

	// Get a release by ID.
	// http://localhost:9090/api/v1/releases/1
	// http://localhost:9090/api/v1/releases?id=1&name=releasename
	@GetMapping("/{id}")
	public ResponseEntity<ReleaseVO> getReleaseById(@PathVariable("id") int id) {
		LOGGER.info("Inside ReleaseController and calling the getReleaseById method ...");
		ReleaseVO releaseVO = null;
		try {
			releaseVO = releaseService.findById(id);
			LOGGER.info("Release response:{}", releaseVO);
			if (releaseVO == null) {
				LOGGER.info("Release details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error("Exception while calling getReleases", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ReleaseVO>(releaseVO, HttpStatus.OK);
	}

	// Create a new release.
	@PostMapping
	public ResponseEntity<ReleaseVO> save(@RequestBody ReleaseRequest releaseRequest) {
		LOGGER.info("Inside ReleaseController.save and releaseVO;{}", releaseRequest);
		if (releaseRequest == null) {
			LOGGER.info("Invalid Release request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ReleaseVO releaseVO = null;
		try {
			releaseVO = releaseService.save(releaseRequest);
			if (releaseVO == null) {
				LOGGER.info("Release details are not saved");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			LOGGER.error("Exception while saving release");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ReleaseVO>(releaseVO, HttpStatus.OK);
	}

	// Update an existing Release.
	@PutMapping
	public ResponseEntity<ReleaseVO> update(@RequestBody ReleaseRequest releaseRequest) {
		LOGGER.info("Inside ReleaseController.update and releaseVO;{}", releaseRequest);
		if (releaseRequest == null) {
			LOGGER.info("Invalid Release request");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ReleaseVO releaseVO = null;
		try {
			releaseVO = releaseService.save(releaseRequest);
			if (releaseVO == null) {
				LOGGER.info("Release details are not updated");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			LOGGER.error("Exception while updating the release");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ReleaseVO>(releaseVO, HttpStatus.OK);
	}

	// Delete an existing release.
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam("id") int id) {
		LOGGER.info("Input to ReleaseController.delete, id:{}", id);
		String response = null;
		try {
			response = releaseService.delete(id);
		} catch (Exception ex) {
			LOGGER.error("Exception while deleting release");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	// Find a Release by name.
	@GetMapping("/name")
	public ResponseEntity<ReleaseVO> getReleaseByName(@RequestParam("name") String name) {
		LOGGER.info("Inside the ReleaseController.getReleaseByName");
		ReleaseVO releaseVO = null;
		try {
			//releaseVO = releaseService.findByName(name);
			 releaseVO =releaseService.findMyReleaseByName(name); //My custom method.

			LOGGER.info("Release response:{}", releaseVO);
			if (releaseVO == null) {
				LOGGER.info("Release details are not found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error("Exception while calling getReleases", ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ReleaseVO>(releaseVO, HttpStatus.OK);
	}
}