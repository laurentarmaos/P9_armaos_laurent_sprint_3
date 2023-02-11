package com.mediscreen.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mediscreen.entities.Patient;

@FeignClient(name="microservice-patients", url="localhost:8081")
public interface RiskPatientProxy {
	
	@GetMapping("/patient/{id}")
	Patient findPatient(@PathVariable Long id);

}
