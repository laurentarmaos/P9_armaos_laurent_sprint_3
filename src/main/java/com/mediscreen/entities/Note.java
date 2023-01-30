package com.mediscreen.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="notes")
public class Note {

	@Id
	private String id;
	
	private String practitionnerNotes;
	
	private String patientId;
	
	public Note() {}

	
	public String getPractitionnerNote() {
		return practitionnerNotes;
	}

	public void setPractitionnerNote(String practitionnerNotes) {
		this.practitionnerNotes = practitionnerNotes;
	}
	

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}


	public String getId() {
		return id;
	}
	
	
}
