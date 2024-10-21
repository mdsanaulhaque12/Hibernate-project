package daoImplementation;

import dao.DoctorDao;
import entity.Doctor;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import org.hibernate.Transaction;
import utils.HibernateUtil;


import java.util.ArrayList;
import java.util.List;


public class DoctorDaoImpl implements DoctorDao {

    @Override
    public void saveDoctor(Doctor doctor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(doctor);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Doctor getDoctorById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Doctor.class, id);
        }
    }

    @Override
    public List<Doctor> getAllDoctors(){
        List<Doctor> alldoctor = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            alldoctor= session.createQuery("from Doctor", Doctor.class).list();
            return alldoctor;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateDoctor(Doctor doctor) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(doctor);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDoctor(long id) {
        Transaction transaction = null;
        Doctor doctor;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            doctor= session.get(Doctor.class,id);
            session.delete(doctor);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}