package service;

import dao.DaoDoctor;
import dao.DaoPatient;
import dao.DaoPrescription;
import system.Doctor;
import system.Patient;
import system.Prescription;
import system.Statistics;

import java.util.List;

public class ServiceController {

	private static DBManager manager = new DBManager();
	
	private static DaoPatient daoPatient = new DaoPatient(manager);
	private static DaoDoctor daoDoctor = new DaoDoctor(manager);
	private static DaoPrescription daoPrescription = new DaoPrescription(manager);
	
	public static List<Patient> getPatientList() {
        return daoPatient.getAll();
    }

	public static Patient getPatientById(long id) {
        return daoPatient.getById(id);
    }

	public static int deletePatient(long id) {
        return daoPatient.delete(id);
    }

	public static void updatePatient(Patient patient) {
        daoPatient.update(patient);
    }

	public static void addPatient(Patient patient) {
        daoPatient.add(patient);
    }
	
	public static List<Doctor> getDoctorList() {
        return daoDoctor.getAll();
    }
	
	public static int deleteDoctor(long id){
        return daoDoctor.delete(id);
    }

    public static Doctor getDoctorById(long id){
        return daoDoctor.getById(id);
    }

    public static void updateDoctor(Doctor doctor){
    	daoDoctor.update(doctor);
    }

    public static void addDoctor(Doctor doctor){
    	daoDoctor.add(doctor);
    }

    public static List<Prescription> getPrescriptionList(){
        return daoPrescription.getAll();
    }

    public static void deletePrescription(long id){
    	daoPrescription.delete(id);
    }

    public static void updatePrescription(Prescription pres){
    	daoPrescription.update(pres);
    }

    public static void addPrescription(Prescription pres){
    	daoPrescription.add(pres);
    }

    public static List<Statistics> getDoctorPrescriptionStat(){
        return daoPrescription.getPrescriptionStatistics();
    }
	
}
