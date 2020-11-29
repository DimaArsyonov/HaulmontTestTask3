package system;

public class Doctor extends Person {
	
	private String speciality;
	
	public Doctor(long id, String s, String n, String p, String spec) {
		setId(id);
		setName(n);
	    setSurname(s);
	    setPatronymic(p);
	    setSpeciality(spec);
	}
	
	public void setSpeciality(String spec) {
		this.speciality = spec;
	}
	
	public String getSpeciality() {
		return speciality;
	}
}
