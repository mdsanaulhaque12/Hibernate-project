package dao;

import entity.Patient;

import java.util.List;

public interface PatientDao {

    public void savePatient(Patient patient);
    public Patient getPatientById(Long id) ;

    public void updatePatient(Patient patient) ;
    public void deletePatient(Patient patient) ;
    public List<Patient> getAllPatients() ;

}
