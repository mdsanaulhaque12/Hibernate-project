package daoImplementation;
import dao.AppointmentSlotDao;
import entity.AppointmentSlot;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.util.List;
public class AppointmentSlotDaoImpl implements AppointmentSlotDao {
    @Override
    public void saveAppointmentSlot(AppointmentSlot slot) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(slot);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public AppointmentSlot getAppointmentSlotById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(AppointmentSlot.class, id);
        }
    }

    @Override
    public List<AppointmentSlot> getAllAppointmentSlots() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from AppointmentSlot", AppointmentSlot.class).list();
        }
    }

    @Override
    public void updateAppointmentSlot(AppointmentSlot slot) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(slot);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAppointmentSlot(AppointmentSlot slot) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(slot);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<AppointmentSlot> getAvailableSlots() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<AppointmentSlot> availableSlots = null;

        try {
            session.beginTransaction();
            availableSlots = session.createQuery("SELECT s FROM AppointmentSlot s WHERE s.id NOT IN (SELECT a.slot.id FROM Appointment a)", AppointmentSlot.class).list();
//            availableSlots = session.createQuery("FROM AppointmentSlot s WHERE s.appointment IS NULL", AppointmentSlot.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            session.close();
        }
        return availableSlots;
    }

}
