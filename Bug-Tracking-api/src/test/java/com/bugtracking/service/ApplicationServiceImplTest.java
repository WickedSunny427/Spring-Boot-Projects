package com.bugtracking.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.bugtracking.entity.Application;
import com.bugtracking.exception.ApplicationNotFoundException;
import com.bugtracking.model.ApplicationRequest;
import com.bugtracking.model.ApplicationVO;
import com.bugtracking.repository.ApplicationRepository;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceImplTest {

	@Mock // only used for mocking data cannot call methods through this.
	ApplicationRepository applicationRepository;

	@InjectMocks
	ApplicationServiceImpl applicationService;

	@Test()
	public void save() throws ApplicationNotFoundException {

		Application application = new Application();
		application.setId(1);
		application.setOwner("Regu");
		application.setDescription("A new application.");
		application.setCreatedOn(null);

		when(applicationRepository.save(any())).thenReturn(application);

		ApplicationRequest applicationRequest = new ApplicationRequest();
		applicationRequest.setId(1);
		applicationRequest.setOwner("Regu");
		applicationRequest.setDescription("A new application.");
		applicationRequest.setCreatedOn(null);

		ApplicationVO applicationVO = applicationService.save(applicationRequest);
		assertEquals(1, applicationVO.getId());

	}
}
