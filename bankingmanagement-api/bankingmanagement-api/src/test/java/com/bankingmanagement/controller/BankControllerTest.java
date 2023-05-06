package com.bankingmanagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.bankingmanagement.dto.BankDTO;
import com.bankingmanagement.dto.BranchDTO;
import com.bankingmanagement.service.IBankService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BankControllerTest {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	private IBankService bankService;

	@Test
	public void testGetBanks() throws Exception {

		List<BankDTO> bankDTOList = new ArrayList<>();

		BankDTO bankDTO = new BankDTO();

		bankDTO.setBankName("CITI");
		bankDTO.setBankAddress("Bangalore");

		Set<BranchDTO> branchDTOList = new HashSet<>();

		BranchDTO branchDTO = new BranchDTO();

		branchDTO.setBranchName("Electronic City");
		branchDTO.setBranchAddress("Neeladri Nagar");

		branchDTOList.add(branchDTO);

		bankDTOList.add(bankDTO);

		when(bankService.findAll()).thenReturn(bankDTOList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/banks")
				.contentType(MediaType.APPLICATION_JSON);

		// Approach 1
		/*
		 * MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		 * MockHttpServletResponse response = result.getResponse();
		 * assertEquals(HttpStatus.OK.value(), response.getStatus());
		 */

		// Approach 2
		// compare the body
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$", Matchers.hasSize(1))).andDo(print());// test if size is
																										// 1.

		// compare the status code
		mockMvc.perform(requestBuilder).andExpect(status().isOk());// checks with status code.

	}

	@Test
	public void testGetBanksWithException() throws Exception {

		when(bankService.findAll()).thenThrow(new NullPointerException());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/banks")
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError());

	}

	@Test
	public void testGetBanksWithEmptyData() throws Exception {
		List<BankDTO> bankDTOList = new ArrayList<>();

		when(bankService.findAll()).thenReturn(bankDTOList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/banks")
				.contentType(MediaType.APPLICATION_JSON);

		// compare the status code
		mockMvc.perform(requestBuilder).andExpect(status().isNotFound());

	}

	@Test
	public void testGetBankById() throws Exception {
	    BankDTO bankDTO = new BankDTO();
	    bankDTO.setBankName("SBI");
	    bankDTO.setBankAddress("Bangalore");

	    BranchDTO branchDTO = new BranchDTO();
	    branchDTO.setBranchName("Electronic City");
	    branchDTO.setBranchAddress("Electronic City");

	    Set<BranchDTO> branchDTOSet = new HashSet<>();
	    branchDTOSet.add(branchDTO);

	    bankDTO.setBranches(branchDTOSet);
	    when(bankService.findById(anyInt())).thenReturn(bankDTO);

	    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/banks/id")
	            .param("id", "99")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk());
	}

	 @Test
	    public void testSave() throws Exception {
	        BankDTO bankDTO = new BankDTO();
	        bankDTO.setBankName("SBI");
	        bankDTO.setBankAddress("Bangalore");
	     
	        BranchDTO branchDTO = new BranchDTO();
	        branchDTO.setBranchName("Electronic City");
	        branchDTO.setBranchAddress("Electronic City");
	        
	        Set<BranchDTO> branchDTOSet = new HashSet<>();
	        branchDTOSet.add(branchDTO);
	        bankDTO.setBranches(branchDTOSet);

	        when(bankService.save(any())).thenReturn(bankDTO);

	        RequestBuilder requestBuilder = MockMvcRequestBuilders
	                .post("/api/v1/banks")
	                .content(asJsonString(bankDTO))
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON);

	        mockMvc.perform(requestBuilder).andExpect(status().isOk());

	    }
	 
	    @Test
	    public void testUpdate() throws Exception {
	    	  BankDTO bankDTO = new BankDTO();
		        bankDTO.setBankName("SBI");
		        bankDTO.setBankAddress("Bangalore");
		     
		        BranchDTO branchDTO = new BranchDTO();
		        branchDTO.setBranchName("Electronic City");
		        branchDTO.setBranchAddress("Electronic City");
		        
		        Set<BranchDTO> branchDTOSet = new HashSet<>();
		        branchDTOSet.add(branchDTO);
		        bankDTO.setBranches(branchDTOSet);

	        when(bankService.save(any())).thenReturn(bankDTO);

	        RequestBuilder requestBuilder = MockMvcRequestBuilders
	                .put("/api/v1/banks")
	                .content(asJsonString(bankDTO))
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON);

	        mockMvc.perform(requestBuilder).andExpect(status().isOk());

	    }

	    @Test
	    public void testDelete() throws Exception {
	        when(bankService.delete(anyInt())).thenReturn("Bank has been deleted");
	        RequestBuilder requestBuilder = MockMvcRequestBuilders
	                .delete("/api/v1/banks?id=99")
	                .contentType(MediaType.APPLICATION_JSON);

	        mockMvc.perform(requestBuilder).andExpect(status().isOk());
	    }
	 public String asJsonString(final Object obj) {
	        try {
	            final ObjectMapper mapper = new ObjectMapper();
	            final String jsonContent = mapper.writeValueAsString(obj);
	            return jsonContent;
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
}