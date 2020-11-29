package system;

import java.time.LocalDate;

public class Prescription {
	
	private enum valuesOfPriority {
		NORMAL,
		CITO,
		STATIM
	}
	
	private long id;
    private String description;
    private Patient patient;
    private Doctor doctor;
    private LocalDate dateOfCreation;
    private LocalDate validityPeriod;
    private valuesOfPriority priority;

    public Prescription(long id, String desc, Patient pat, Doctor doc, LocalDate date, LocalDate val, String prio) {
        setId(id);
        setDescription(desc);
        setPatient(pat);
        setDoctor(doc);
        setDateOfCreation(date);
        setValidityPeriod(val);
        setPriority(prio);
    }
    
    public void setDescription(String desc) {
        this.description = desc;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setPatient(Patient pat) {
        this.patient = pat;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public void setDoctor(Doctor doc) {
        this.doctor = doc;
    }
    
    public Doctor getDoctor() {
        return doctor;
    }
    
    public void setDateOfCreation(LocalDate date) {
        this.dateOfCreation = date;
    }
    
    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }
    
    public void setValidityPeriod(LocalDate val) {
        this.validityPeriod = val;
    }

    public LocalDate getValidityPeriod() {
        return validityPeriod;
    }
    
    public void setPriority(String prio) {
        if(prio.toLowerCase().equals("нормальный"))
        	this.priority = valuesOfPriority.NORMAL;
        if(prio.toLowerCase().equals("срочный")) 
        	this.priority = valuesOfPriority.CITO;
        if(prio.toLowerCase().equals("немедленный")) 
        	this.priority = valuesOfPriority.STATIM;
    }

    public String getPriority() {
    	String str = null;
        if (priority == valuesOfPriority.NORMAL) 
        	str = "Нормальный";
        if (priority == valuesOfPriority.CITO) 
        	str = "Срочный";
        if (priority == valuesOfPriority.STATIM) 
        	str = "Немедленный";
        return str;
    }

    public String getFullPatientName() {
        return patient.getFullName();
    }
    
    public String getFullDoctorName() {
        return doctor.getFullName();
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public long getId() {
        return id;
    }
    
}
