package dao;

import system.Prescription;
import system.Statistics;
import service.DBManager;
import service.ServiceController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoPrescription implements DaoController<Prescription> {

	private DBManager manager;

    private final String TABLE_NAME = "Prescription";

    public DaoPrescription(DBManager manager){
        this.manager = manager;
    }

    @Override
    public void setDBManager(DBManager manager) {
        this.manager = manager;
    }

    @Override
    public List<Prescription> getAll() {
        PreparedStatement statement;
        List<Prescription> resultList = new ArrayList<Prescription>();
        try {
            statement = manager.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                resultList.add(new Prescription(
                        resultSet.getLong("Prescription_Id"),
                        resultSet.getString("Description"),
                        ServiceController.getPatientById(resultSet.getLong("Patient")),
                        ServiceController.getDoctorById(resultSet.getLong("Doctor")),
                        resultSet.getDate("Creation_Date").toLocalDate(),
                        resultSet.getDate("Validity").toLocalDate(),
                        resultSet.getString("Priority")
                ));
            }
            System.out.println("All prescriptions selected successfully");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public List<Statistics> getPrescriptionStatistics(){
        PreparedStatement statement;
        List<Statistics> result = new ArrayList<Statistics>();
        try {
            statement = manager.getConnection().prepareStatement("SELECT Doctor, COUNT(*) as PrescNum FROM " + TABLE_NAME +
                    " GROUP BY Doctor");
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                result.add(new Statistics(
                        resultSet.getInt("PrescNum"),
                        ServiceController.getDoctorById(resultSet.getLong("Doctor"))
                ));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Prescription getById(long id) {
        PreparedStatement statement;
        Prescription result;
        try {
            statement = manager.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            result = new Prescription(
                        resultSet.getLong("Prescription_Id"),
                        resultSet.getString("Description"),
                        ServiceController.getPatientById(resultSet.getLong("Patient")),
                        ServiceController.getDoctorById(resultSet.getLong("Doctor")),
                        resultSet.getDate("Creation_Date").toLocalDate(),
                        resultSet.getDate("Validity").toLocalDate(),
                        resultSet.getString("Priority")
                );
            System.out.println("Prescription " + id + " selected successfully");
            return result;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void add(Prescription prescription) {
        PreparedStatement statement;
        try{
            String s = "INSERT INTO " + TABLE_NAME
                    + " (Description, Patient, Doctor, Creation_date, Validity, Priority) VALUES ('" + prescription.getDescription()
                    + "', " + prescription.getPatient().getId()
                    + ", " + prescription.getDoctor().getId()
                    + ", '" + Date.valueOf(prescription.getDateOfCreation())
                    + "', '" + Date.valueOf(prescription.getValidityPeriod())
                    + "', '" + prescription.getPriority()
                    + "');";
            System.out.println(s);
            statement = manager.getConnection().prepareStatement(s);
            statement.execute();
            System.out.println("Prescription " + prescription.getId() + " added successfully");
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
                    + " WHERE Prescription_Id = " + id;
            statement = manager.getConnection().prepareStatement(s);
            statement.execute();
            System.out.println("Prescription " + id + " deleted successfully");
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
    public void update(Prescription prescription) {
        PreparedStatement statement;
        try{
            String s = "UPDATE " + TABLE_NAME
                    + " SET Description='" + prescription.getDescription()
                    + "', Patient=" + prescription.getPatient().getId()
                    + ", Doctor=" + prescription.getDoctor().getId()
                    + ", Creation_Date='" + Date.valueOf(prescription.getDateOfCreation())
                    + "', Validity='" + Date.valueOf(prescription.getValidityPeriod())
                    + "', Priority='" + prescription.getPriority()
                    + "' WHERE Prescription_Id=" + prescription.getId();
            System.out.println(s);
            statement = manager.getConnection().prepareStatement(s);
            statement.execute();
            System.out.println("Prescription " + prescription.getId() + " updated successfully");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
