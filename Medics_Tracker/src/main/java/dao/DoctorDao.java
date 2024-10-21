package dao;


import entity.Doctor;

import java.util.List;

public interface DoctorDao {

        public void saveDoctor(Doctor doctor);


        public Doctor getDoctorById(Long id);

        public List<Doctor> getAllDoctors();

        public void updateDoctor(Doctor doctor);

        void deleteDoctor(long id);
}