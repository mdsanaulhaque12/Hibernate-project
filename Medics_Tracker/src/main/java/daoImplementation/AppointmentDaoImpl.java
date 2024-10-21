package daoImplementation;

import dao.AppointmentDao;
import entity.Appointment;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class AppointmentDaoImpl implements AppointmentDao {

    private static SessionFactory sessionFactory;
    static{
        sessionFactory= new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }
    @Override
    public Appointment saveAppointment(Appointment appointment) {
        Transaction transaction=null;
        try(Session session = sessionFactory.openSession()){
            transaction=session.beginTransaction();
            session.save(appointment);
            transaction.commit();
            return appointment;
        }catch (HibernateException e ){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Appointment getAppointment(long id) {
        Transaction transaction=null;
        Appointment appointment;
        try(Session session =sessionFactory.openSession()){
            transaction= session.beginTransaction();
            appointment=session.get(Appointment.class,id);
            transaction.commit();
            return appointment;
        }catch(HibernateException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Appointment> getAllAppointment() {

        Transaction transaction;
        List<Appointment> allAppointment;

        try(Session session = sessionFactory.openSession()){
            transaction=session.beginTransaction();
            Query<Appointment> query= session.createQuery("from Appointment");

            allAppointment=query.list();
            transaction.commit();
            return allAppointment;
        }catch (HibernateException e){e.printStackTrace();}
        return null;
    }

    @Override
    public Appointment updateAppointment(long id,Appointment appointment) {
        Transaction transaction;
        Appointment updatedAppointment;

        try(Session session = sessionFactory.openSession()){
            transaction=session.beginTransaction();
            updatedAppointment=session.get(Appointment.class,id);

            updatedAppointment.setId(appointment.getId());
            updatedAppointment.setPatient(appointment.getPatient());
            updatedAppointment.setSlot(appointment.getSlot());

            session.saveOrUpdate(updatedAppointment);
            transaction.commit();

            return updatedAppointment;
        }catch (HibernateException e){e.printStackTrace();}

        return null;
    }

    @Override
    public String deleteAppointment(long id) {
        System.out.println("are you sure to delete appointment enter yes or no");
        Scanner sc=new Scanner(System.in);
        String status=sc.nextLine();

        Transaction transaction;
        try(Session session = sessionFactory.openSession()){
            if(status.equalsIgnoreCase("no")){
                return "deletion of appointment aborted ";
            }else{
                transaction=session.beginTransaction();
                session.delete(session.get(Appointment.class,id));
                transaction.commit();
                return "succesfully deleted appointment";
            }
        }catch(HibernateException e){e.printStackTrace();}
        return "appointment deletion cancelled";
    }
}
