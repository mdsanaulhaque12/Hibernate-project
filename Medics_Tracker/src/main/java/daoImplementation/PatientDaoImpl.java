package daoImplementation;

import dao.PatientDao;
import entity.Patient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.util.List;
import java.util.Scanner;

public class PatientDaoImpl implements PatientDao {

    public void savePatient(Patient patient) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(patient);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }



    public Patient getPatientById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Patient.class, id);
        }catch (HibernateException e){
            System.out.println("invalid patient id or patient doesnt exist try again");
        }
        return  null;
    }

    public List<Patient> getAllPatients() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Patient", Patient.class).list();
        }
    }

    public void updatePatient(Patient patient) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(patient);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePatient(Patient patient) {
        Transaction transaction = null;
        System.out.println("are you sure you want to delete this ");
        Scanner sc= new Scanner(System.in);
        String input=sc.nextLine();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if(input.equalsIgnoreCase("yes")) {
                transaction = session.beginTransaction();

                session.delete(patient);
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
