package com.mediscreen.entities;


public class Note {

	private String id;
	
	private String practitionnerNotes;
	
	private String patientId;
	
	public Note() {}

	
	public String getPractitionnerNotes() {
		return practitionnerNotes;
	}

	public void setPractitionnerNotes(String practitionnerNotes) {
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
