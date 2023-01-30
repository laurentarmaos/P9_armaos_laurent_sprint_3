package com.mediscreen.services;

import com.mediscreen.entities.Patient;

public interface RiskService {

	public String evaluateRisk(Long PatientId);
	
	Patient findByPatientId(Long id);
}
