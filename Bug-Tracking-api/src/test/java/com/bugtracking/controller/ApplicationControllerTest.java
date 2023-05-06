package com.bugtracking.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PathVariable;

import com.bugtracking.exception.ApplicationNotFoundException;
import com.bugtracking.model.ApplicationVO;
import com.bugtracking.service.IApplicationService;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ApplicationControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	IApplicationService applicationService;

	@Test
	public void testGetApplicationsWithValidData() throws Exception {
		List<ApplicationVO> applicationVOS = new ArrayList<>();
		ApplicationVO applicationVO = new ApplicationVO();
		applicationVO.setId(102);
		applicationVO.setName("Banking Application");
		applicationVO.setOwner("Dilip");
		applicationVO.setDescription("Anew application.");
		applicationVO.setCreatedOn(null);
		applicationVO.setBugs(null);

		applicationVOS.add(applicationVO);

		when(applicationService.findAll()).thenReturn(applicationVOS);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/applications")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	public void testGetApplicationsWithNoData() throws Exception {
		List<ApplicationVO> applicationVOS = new ArrayList<>();
		ApplicationVO applicationVO = new ApplicationVO();
		applicationVO.setId(102);
		applicationVO.setName("Banking Application");
		applicationVO.setOwner("Dilip");
		applicationVO.setDescription("Anew application.");
		applicationVO.setCreatedOn(null);
		applicationVO.setBugs(null);

		applicationVOS.add(applicationVO);

		when(applicationService.findAll()).thenReturn(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/applications")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}

	@Test
	public void testGetApplicationsWithException() throws Exception {
		List<ApplicationVO> applicationVOS = new ArrayList<>();
		ApplicationVO applicationVO = new ApplicationVO();
		applicationVO.setId(102);
		applicationVO.setName("Banking Application");
		applicationVO.setOwner("Dilip");
		applicationVO.setDescription("Anew application.");
		applicationVO.setCreatedOn(null);
		applicationVO.setBugs(null);

		applicationVOS.add(applicationVO);

		when(applicationService.findAll()).thenThrow(new NullPointerException());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/applications")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
	}

	@Test()
	public void getApplicationById() throws Exception {
		ApplicationVO applicationVO = null;

		applicationVO.setId(105);
		applicationVO.setName("Banking Application");
		applicationVO.setOwner("Dilip");
		applicationVO.setDescription("A new application.");
		when(applicationService.findAll()).thenThrow(new NullPointerException());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/applications/105")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());

	}
}