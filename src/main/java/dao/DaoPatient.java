package dao;

import system.Patient;
import service.DBManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoPatient implements DaoController<Patient> {

	private DBManager manager;

    private final String TABLE_NAME = "Patient";

    @Override
    public void setDBManager(DBManager manager) {
        this.manager = manager;
    }

    public DaoPatient(DBManager manager){
        this.manager = manager;
    }

    @Override
    public List<Patient> getAll() {
        PreparedStatement statement;
        List<Patient> resultList = new ArrayList<Patient>();
        try {
            statement = manager.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                resultList.add(new Patient(
                        resultSet.getLong("Patient_Id"),
                        resultSet.getString("Surname"),
                        resultSet.getString("Name"),
                        resultSet.getString("Patronymic"),
                        resultSet.getString("PhoneNumber")
                ));
            }
            System.out.println("All patients selected successfully");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Patient getById(long id) {
        PreparedStatement statement;
        Patient result;
        try {
            statement = manager.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME
                    + " WHERE Patient_Id=" + id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                result = new Patient(
                        resultSet.getLong("Patient_Id"),
                        resultSet.getString("Surname"),
                        resultSet.getString("Name"),
                        resultSet.getString("Patronymic"),
                        resultSet.getString("PhoneNumber")
                );
                System.out.println("Patient" + id + " selected successfully");
                return result;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void add(Patient patient) {
        PreparedStatement statement;
        try{
            String s = "INSERT INTO " + TABLE_NAME
                    + " (Surname, Name, Patronymic, PhoneNumber) VALUES ('" + patient.getSurname()
                    + "', '" + patient.getName()
                    + "', '" + patient.getPatronymic()
                    + "', '" + patient.getPhoneNumber()
                    + "');";
            System.out.println(s);
            statement = manager.getConnection().prepareStatement(s);
            statement.execute();
            System.out.println("Patient " + patient.getId() + " added successfully");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int delete(long id) {
        PreparedStatement statement;
        try{
            String s = "DELETE FROM " + TABLE_NAME
                    + " WHERE Patient_Id = " + id;
            statement = manager.getConnection().prepareStatement(s);
            statement.execute();
            System.out.println("Patient " + id + " deleted successfully");
        }
        catch(SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            return 1;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public void update(Patient patient) {
        PreparedStatement statement;
        try{
            String s = "UPDATE " + TABLE_NAME
                    + " SET Surname='" + patient.getSurname()
                    + "', Name='" + patient.getName()
                    + "', Patronymic='" + patient.getPatronymic()
                    + "', PhoneNumber='" + patient.getPhoneNumber()
                    + "' WHERE Patient_Id=" + patient.getId();
            System.out.println(s);
            statement = manager.getConnection().prepareStatement(s);
            statement.execute();
            System.out.println("Patient " + patient.getId() + " updated successfully");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	
}
