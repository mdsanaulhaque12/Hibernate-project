package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppointmentSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String availableDate;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne(mappedBy = "slot",cascade = CascadeType.ALL)
    private Appointment appointment;

    @Override
    public String toString() {
        return "AppointmentSlot{" +
                "id=" + id +
                ", availableDate=" + availableDate +
                '}';
    }


}
