package dao;

import entity.Appointment;
import entity.AppointmentSlot;

import java.util.List;

public interface AppointmentDao {


    Appointment saveAppointment(Appointment appointment);

    Appointment getAppointment(long id);

    List<Appointment> getAllAppointment();

    Appointment updateAppointment(long id, Appointment appointment);

    String deleteAppointment(long id);
}
