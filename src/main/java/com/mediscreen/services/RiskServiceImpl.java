package com.mediscreen.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mediscreen.entities.Note;
import com.mediscreen.entities.Patient;
import com.mediscreen.proxies.RiskNoteProxy;
import com.mediscreen.proxies.RiskPatientProxy;

@Service
public class RiskServiceImpl implements RiskService {
	
	private final RiskPatientProxy riskPatientProxy;
	private final RiskNoteProxy riskNoteProxy;
	
	public RiskServiceImpl(RiskPatientProxy riskPatientProxy, RiskNoteProxy riskNoteProxy) {
		this.riskPatientProxy = riskPatientProxy;
		this.riskNoteProxy = riskNoteProxy;
	}
	

	@Override
	public String evaluateRisk(Long patientId) {	
		
		return riskLevel(patientId);
	}
	
	
	@Override
	public Patient findByPatientId(Long id) {
		
		return riskPatientProxy.findPatient(id);
	}
	
	
	private List<Note> getPatientNotes(String patientId) {
			
		return riskNoteProxy.findNoteByPatientId(patientId);
	}
	
	
	private int termsOccurrence(String patientId) {
		int danger = 0;
		String[] dangerTerms = {"Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur", "Anormal", "Cholestérol", "Vertige", "Rechute", "Réaction", "Anticorps"};
		List<String> dangerTermsList = Arrays.asList(dangerTerms);
		List<Note> patientNotes = getPatientNotes(patientId);
		
		for(int i = 0; i < patientNotes.size(); i++) {
			for(int j = 0; j < dangerTermsList.size(); j++) {
				if( patientNotes.get(i).getPractitionnerNote().toLowerCase().contains(dangerTermsList.get(j).toLowerCase()) ) {
					danger++;
				}
			}
		}
		
		return danger;
	}
	
	
	
	
	private String riskLevel(Long patientId) {
		String riskLevel = "not determined";
		Patient patient = findByPatientId(patientId);
		LocalDate dateNow = LocalDate.now();
		int age = Period.between(patient.getBirthDate(), dateNow).getYears();
		int danger = termsOccurrence(patientId.toString());
		
		
		if( (age < 30 && danger >= 5 && patient.getGender().equals("M")) || (age < 30 && danger >= 7 && patient.getGender().equals("F")) || (danger >= 8 && age >= 30) ) {
			riskLevel = "Early onset";
		} else if( (age < 30 && danger >= 3 && patient.getGender().equals("M")) || (age < 30 && danger >= 4 && patient.getGender().equals("F")) || (danger >= 6 && age >= 30) ) {
			riskLevel = "In Danger";
		} else if( (age >= 30 && danger >= 2) ) {
			riskLevel = "Borderline";
		} else if(danger == 2) {
			riskLevel = "None";
		}
		

		return riskLevel + " (risks triggers: "+ danger + ")";
	}

	
}
