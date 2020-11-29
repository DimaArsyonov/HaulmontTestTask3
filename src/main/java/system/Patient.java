package system;

public class Patient extends Person {
	
    private String phone_num;
    
    public Patient(long id, String s, String n, String p, String num) {
        setId(id);
        setName(n);
        setSurname(s);
        setPatronymic(p);
        setPhoneNumber(num);
    }
    
    public void setPhoneNumber(String num) {
        this.phone_num = num;
    }
    
    public String getPhoneNumber() {
        return phone_num;
    }
}