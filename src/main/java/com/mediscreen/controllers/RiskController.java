package com.mediscreen.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mediscreen.entities.Patient;
import com.mediscreen.services.RiskService;

@RestController
public class RiskController {
	
	private final RiskService riskService;
	
	public RiskController(RiskService riskService) {
		this.riskService = riskService;
	}

	
	@GetMapping("/assess/{patientId}")
	public String evaluateRisk(@PathVariable Long patientId) {
		
		return riskService.evaluateRisk(patientId);
	}
	
	
	@GetMapping("/patient/{id}")
	public Patient findPatient(@PathVariable Long id) {	
		return riskService.findByPatientId(id);
	}
}
