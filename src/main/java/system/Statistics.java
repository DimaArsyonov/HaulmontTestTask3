package system;

public class Statistics {

	private int num;
	private Doctor doctor;
	
    public Statistics(int num, Doctor doctor) {
        this.num = num;
        this.doctor = doctor;
    }
    
    public void setNum(int num) {
        this.num = num;
    }
    
    public int getNum() {
        return num;
    }
    
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
    public Doctor getDoctor() {
        return doctor;
    }
    
    public String getFullDoctorName() {
        return doctor.getFullName();
    }
}
