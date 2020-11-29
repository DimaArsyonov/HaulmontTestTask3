package system;

public abstract class Person {
	
	private long id;
    private String name;
    private String surname;
    private String patronymic;
    
    public void setName(String str) {
        this.name = str;
    }
    
    public String getName() {
        return name;
    }

    public void setSurname(String str) {
        this.surname = str;
    }

    public String getSurname() {
        return surname;
    }

    public void setPatronymic(String str) {
        this.patronymic = str;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getFullName() {
        return surname + " " +  name + " " +  patronymic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
