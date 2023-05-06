package com.bugtracking.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugtracking.entity.Application;
import com.bugtracking.exception.ApplicationNotFoundException;
import com.bugtracking.model.ApplicationRequest;
import com.bugtracking.model.ApplicationVO;
import com.bugtracking.repository.ApplicationRepository;

@Service
public class ApplicationServiceImpl implements IApplicationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationServiceImpl.class);

	@Autowired
	ApplicationRepository applicationRepository;

	@Override
	public List<ApplicationVO> findAll() {

		LOGGER.info("Inside ApplicationServiceImpl findAll method ...");
		List<Application> applications = applicationRepository.findAll();
		LOGGER.info("Fetching all applications response : {}", applications);

		List<ApplicationVO> applicationVOS = applications.parallelStream().map(application -> {

			ApplicationVO applicationVO = new ApplicationVO();

			applicationVO.setId(application.getId());
			applicationVO.setName(application.getName());
			applicationVO.setDescription(application.getDescription());
			applicationVO.setOwner(application.getOwner());
			applicationVO.setCreatedOn(application.getCreatedOn());
			// applicationVO.setBugs(application.getBugs());

			return applicationVO;
		}).collect(Collectors.toList());
		return applicationVOS;
	}

	@Override
	public ApplicationVO findById(int applicationId) throws ApplicationNotFoundException {
		LOGGER.info("Inside ApplicationServiceImpl findById method ...");
		Optional<Application> application = applicationRepository.findById(applicationId);
		LOGGER.info("Fetching an application response : {}", application);
		if (!application.isPresent()) {
			LOGGER.error("No such application present !!!");
			throw new ApplicationNotFoundException("No such application present !!!");
		} else {
			ApplicationVO applicationVO = new ApplicationVO();
			applicationVO.setId(application.get().getId());
			applicationVO.setName(application.get().getName());
			applicationVO.setDescription(application.get().getDescription());
			applicationVO.setOwner(application.get().getOwner());
			applicationVO.setCreatedOn(application.get().getCreatedOn());
			// applicationVO.setBugs(application.get().getBugs());
			return applicationVO;
		}
	}

	@Override
	public ApplicationVO save(ApplicationRequest applicationRequest) throws ApplicationNotFoundException {
		LOGGER.info("Inside the ApplicationServiceImpl.save method and params, applicationRequest:{}",
				applicationRequest);

		if (applicationRequest == null) {
			LOGGER.info("Invalid application request");
			throw new ApplicationNotFoundException("Invalid application request");
		}

		Application application = new Application();
		if (applicationRequest.getId() > 0) {
			application.setId(applicationRequest.getId());
		}
		application.setName(applicationRequest.getName());
		application.setDescription(applicationRequest.getDescription());
		application.setOwner(applicationRequest.getOwner());

		Application applicationResponse = applicationRepository.save(application);
		ApplicationVO applicationVO = null;
		if (applicationResponse != null) {
			LOGGER.info("Application Response, applicationResponse:{}", applicationResponse);
			applicationVO = new ApplicationVO();
			applicationVO.setId(application.getId());
			applicationVO.setId(application.getId());
			applicationVO.setName(application.getName());
			applicationVO.setOwner(application.getOwner());
			applicationVO.setDescription(application.getDescription());
		}
		return applicationVO;
	}

	@Override
	public String delete(int id) throws ApplicationNotFoundException {
		LOGGER.info("Input to AppplicationServiceImpl.delete, id:{}", id);
		if (id < 0) {
			LOGGER.info("Invalid application id");
			throw new ApplicationNotFoundException("Invalid application id");
		}
		try {
			applicationRepository.deleteById(id);
		} catch (Exception ex) {
			LOGGER.error("Exception while deleting application");
			throw new ApplicationNotFoundException("Exception while deleting application");
		}
		return "Application has been deleted";
	}

	@Override
	public ApplicationVO findByName(String name) throws ApplicationNotFoundException {
		LOGGER.info("Inside ApplicationServiceImpl.findByName and name: {}", name);
		ApplicationVO applicationVO = null;
		if (name == null) {
			LOGGER.info("Invalid application name:{}", name);
			throw new ApplicationNotFoundException("Application name is not valid");
		}
		Optional<Application> application = applicationRepository.findByName(name);
		LOGGER.info("Default findByName invoked Successfully !!!");

		if (application.isPresent()) {
			LOGGER.info("Application details for the name {} and the values :{}", name, application.get());
			applicationVO = new ApplicationVO();

			applicationVO = new ApplicationVO();
			applicationVO.setId(application.get().getId());
			applicationVO.setId(application.get().getId());
			applicationVO.setName(application.get().getName());
			applicationVO.setOwner(application.get().getOwner());
			applicationVO.setDescription(application.get().getDescription());
		}
		return applicationVO;
	}

	@Override
	public ApplicationVO findMyApplicationByName(String name) throws ApplicationNotFoundException {
		LOGGER.info("Inside ApplicationServiceImpl.findMyApplicationByName and name: {}", name);
		ApplicationVO applicationVO = null;
		if (name == null) {
			LOGGER.info("Invalid application name:{}", name);
			throw new ApplicationNotFoundException("Application name is not valid");
		}
		Optional<Application> application = applicationRepository.findApplicationByNameCustom(name);
		LOGGER.info("Custom findApplicationByNameCustom invoked Successfully !!!");
		if (application.isPresent()) {
			LOGGER.info("Application details for the name {} and the values :{}", name, application.get());
			applicationVO = new ApplicationVO();

			applicationVO = new ApplicationVO();
			applicationVO.setId(application.get().getId());
			applicationVO.setId(application.get().getId());
			applicationVO.setName(application.get().getName());
			applicationVO.setOwner(application.get().getOwner());
			applicationVO.setDescription(application.get().getDescription());
		}
		return applicationVO;
	}
}