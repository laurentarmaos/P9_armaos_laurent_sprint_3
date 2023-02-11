package com.mediscreen.risks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mediscreen.entities.Note;
import com.mediscreen.entities.Patient;
import com.mediscreen.proxies.RiskNoteProxy;
import com.mediscreen.proxies.RiskPatientProxy;
import com.mediscreen.services.RiskService;
import com.mediscreen.services.RiskServiceImpl;

@SpringBootTest
public class RiskServiceTests {
	
	@MockBean
	private RiskNoteProxy riskNoteProxy;
	
	@MockBean
	private RiskPatientProxy riskPatientProxy;
	
	private RiskService riskService;
	
	@BeforeEach
	public void setUp() {
		riskService = new RiskServiceImpl(riskPatientProxy, riskNoteProxy);
	}
	
	
	
	@Test
	public void evaluateRiskTest() {
		
		//create a first patient with 3 risk triggers
		Patient patient1 = new Patient();
		patient1.setFirstName("firstname");
		patient1.setPatientId((long) 1);
		patient1.setBirthDate(LocalDate.now().minusYears(20));
		patient1.setGender("M");
		
		Note note1 = new Note();
		note1.setPatientId("1");
		note1.setPractitionnerNote("fumeur, Hémoglobine A1C, Microalbumine");
		List<Note> notesPatient1 = new ArrayList<>();
		notesPatient1.add(note1);
		
		//create a second patient with 5 risk triggers
		Patient patient2 = new Patient();
		patient2.setFirstName("firstname");
		patient2.setPatientId((long) 2);
		patient2.setBirthDate(LocalDate.now().minusYears(40));
		patient2.setGender("F");
		
		Note note2 = new Note();
		note2.setPatientId("2");
		note2.setPractitionnerNote("fumeur, Hémoglobine A1C, Microalbumine, Rechute, vertige");
		List<Note> notesPatient2 = new ArrayList<>();
		notesPatient2.add(note2);
		
		
		when(riskPatientProxy.findPatient((long) 1)).thenReturn(patient1);
		when(riskPatientProxy.findPatient((long) 2)).thenReturn(patient2);
		when(riskNoteProxy.findNoteByPatientId("1")).thenReturn(notesPatient1);
		when(riskNoteProxy.findNoteByPatientId("2")).thenReturn(notesPatient2);
		
		String riskPatient1 = riskService.evaluateRisk((long) 1);
		String riskPatient2 = riskService.evaluateRisk((long) 2);
		
		assertEquals("In Danger (risks triggers: 3)", riskPatient1);
		assertEquals("Borderline (risks triggers: 5)", riskPatient2);
		
	}
	
	
	@Test
	public void testFindPatientById() {
		Patient patient = new Patient();
		patient.setFirstName("firstname");
		patient.setPatientId((long) 1);
		
		when(riskPatientProxy.findPatient(Mockito.anyLong())).thenReturn(patient);
		
		Patient foundPatient = riskService.findByPatientId((long) 1);
		
		assertNotNull(foundPatient);
		assertEquals(foundPatient.getFirstName(), "firstname");
		verify(riskPatientProxy).findPatient(Mockito.anyLong());
	}
	

}
