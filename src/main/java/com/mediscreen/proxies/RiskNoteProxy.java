package com.mediscreen.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mediscreen.entities.Note;

@FeignClient(name="microservice-notes", url="localhost:8082")
public interface RiskNoteProxy {

	@GetMapping("/patient/{patientId}/findnotes")
	public List<Note> findNoteByPatientId(@PathVariable String patientId);
}
