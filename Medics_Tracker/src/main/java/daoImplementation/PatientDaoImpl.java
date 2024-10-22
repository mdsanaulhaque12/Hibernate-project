package daoImplementation;

import dao.PatientDao;
import entity.Patient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class PatientDaoImpl implements PatientDao {

    public boolean savePatient(Patient patient) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(patient);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("patient could not saved");
        }
        return false;
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

    @Override
    public Patient validateLogin(String username, String password) {
        Transaction transaction = null;
        Patient patient = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Query to validate login
            String hql = "FROM Patient WHERE username = :username AND password = :password";
            Query query = session.createQuery(hql);
            query.setParameter("username", username);
            query.setParameter("password", password);

            patient = (Patient) query.getSingleResult();

            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
        return patient;
    }
}
