package dao;

import system.Doctor;
import service.DBManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoDoctor implements DaoController<Doctor> {
	
	private DBManager manager;

    private final String TABLE_NAME = "Doctor";

    public  DaoDoctor(DBManager manager){
        this.manager = manager;
    }

    @Override
    public void setDBManager(DBManager manager) {
        this.manager = manager;
    }

    @Override
    public List<Doctor> getAll() {
        PreparedStatement statement;
        List<Doctor> resultList = new ArrayList<Doctor>();
        try {
            statement = manager.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                resultList.add(new Doctor(
                        resultSet.getLong("Doctor_Id"),
                        resultSet.getString("Surname"),
                        resultSet.getString("Name"),
                        resultSet.getString("Patronymic"),
                        resultSet.getString("Speciality")
                ));
            }
            System.out.println("All doctors selected successfully");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Doctor getById(long id) {
        PreparedStatement statement;
        Doctor result;
        try {
            statement = manager.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME
                                                                    + " WHERE Doctor_Id=" + id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                result = new Doctor(
                        resultSet.getLong("Doctor_Id"),
                        resultSet.getString("Surname"),
                        resultSet.getString("Name"),
                        resultSet.getString("Patronymic"),
                        resultSet.getString("Speciality")
                );
                System.out.println("Doctor" + id + " selected successfully");
                return result;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void add(Doctor doctor) {
        PreparedStatement statement;
        try{
            String s = "INSERT INTO " + TABLE_NAME
                    + " (Surname, Name, Patronymic, Speciality) VALUES ('" + doctor.getSurname()
                    + "', '" + doctor.getName()
                    + "', '" + doctor.getPatronymic()
                    + "', '" + doctor.getSpeciality()
                    + "');";
            System.out.println(s);
            statement = manager.getConnection().prepareStatement(s);
            statement.execute();
            System.out.println("Doctor " + doctor.getId() + " added successfully");
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
                    + " WHERE Doctor_Id = " + id;
            statement = manager.getConnection().prepareStatement(s);
            statement.execute();
            System.out.println("Doctor " + id + " deleted successfully");
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
    public void update(Doctor doctor) {
        PreparedStatement statement;
        try{
            String s = "UPDATE " + TABLE_NAME
                    + " SET Surname='" + doctor.getSurname()
                    + "', Name='" + doctor.getName()
                    + "', Patronymic='" + doctor.getPatronymic()
                    + "', Specialization='" + doctor.getSpeciality()
                    + "' WHERE Doctor_Id=" + doctor.getId();
            System.out.println(s);
            statement = manager.getConnection().prepareStatement(s);
            statement.execute();
            System.out.println("Doctor " + doctor.getId() + " updated successfully");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
}
