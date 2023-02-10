package com.mediscreen.risks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mediscreen.controllers.RiskController;
import com.mediscreen.entities.Patient;
import com.mediscreen.services.RiskService;

@SpringBootTest
public class RiskControllerTests {
	
	@MockBean
	private RiskService riskService;
	
	private RiskController riskController;
	
	private MockMvc mockMvc;
	
	
	@BeforeEach
	public void setUp() {
		riskController = new RiskController(riskService);
		mockMvc = MockMvcBuilders.standaloneSetup(riskController).build();
	}
	
	
	@Test
	public void evaluateRiskTest() throws Exception {
		Patient patient = new Patient();
		patient.setPatientId((long) 1);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/assess/{patientId}", patient.getPatientId())).andExpect(status().isOk());
	}
	
	
	@Test
	public void testFindPatient() throws Exception {
		Patient patient = new Patient();
		patient.setFirstName("firstname");
		patient.setPatientId((long) 1);
		
		when(riskService.findByPatientId((long) 1)).thenReturn(patient);
		
		
		mockMvc.perform(MockMvcRequestBuilders.get("/patient/{id}", patient.getPatientId())).andExpect(status().isOk());
	}

}
