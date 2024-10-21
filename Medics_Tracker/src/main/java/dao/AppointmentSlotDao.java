package dao;

import entity.Appointment;
import entity.AppointmentSlot;

import java.util.List;

public interface AppointmentSlotDao {

    void saveAppointmentSlot(AppointmentSlot slot);

    AppointmentSlot getAppointmentSlotById(Long id);

    List<AppointmentSlot> getAllAppointmentSlots();

    void updateAppointmentSlot(AppointmentSlot slot);

    void deleteAppointmentSlot(AppointmentSlot slot);

    List<AppointmentSlot> getAvailableSlots();
}
