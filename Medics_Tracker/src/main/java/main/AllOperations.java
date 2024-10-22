package main;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import service.AdminService;
import daoImplementation.*;

import static main.main.mainOps;
import entity.*;
public class AllOperations {
    static Scanner sc;
    public AllOperations(Scanner scanner){
        this.sc=scanner;
    }



    public static void adminLogin() {
        System.out.println("Enter admin username:");
        String username = sc.nextLine();
        System.out.println("Enter admin password:");
        String password = sc.nextLine();

        if (AdminService.authenticate(username, password)) {
            adminOperations();
        } else {
            System.out.println("Invalid credentials");
        }
    }

    public static void adminOperations() {
        while (true) {
            System.out.println("Admin Operations\n1. Create Doctor\n2. View Doctors\n3. Delete Doctor\n4. update Doctor \n5. Logout");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    createDoctor();
                    break;
                case 2:
                    viewDoctors();
                    break;
                case 3:
                    deleteDoctor();
                    break;
                case 4:
                    updateDoctor();
                    break;
                case 5:
                    System.out.println("logout successfully");
                    mainOps();

            }
        }
    }

    private static void createDoctor() {
        System.out.println("Enter doctor's id:");
        long id = sc.nextLong();
        sc.nextLine();

        System.out.println("Enter doctor's name:");
        String name = sc.nextLine();

        System.out.println("Enter doctor's specialization:");
        String specialization = sc.nextLine();

        // Create a new Doctor object
        Doctor newDoctor = new Doctor();
        newDoctor.setName(name);
        newDoctor.setId(id);
        newDoctor.setSpecialization(specialization);

        // Collect appointment slots
        List<AppointmentSlot> appointmentSlots = new ArrayList<>();
        String addMoreSlots;

        do {
            AppointmentSlot slot = new AppointmentSlot();

            System.out.println("Enter available date and time for the appointment slot ");

            String availableDate =  sc.nextLine();

            slot.setAvailableDate(availableDate);
            slot.setDoctor(newDoctor); // Set the doctor for this slot

            // Add the slot to the list
            appointmentSlots.add(slot);

            System.out.println("Do you want to add another appointment slot? (yes/no)");
            addMoreSlots = sc.nextLine();
        } while (addMoreSlots.equalsIgnoreCase("yes"));

        // Set appointment slots to the doctor
        newDoctor.setAppointmentSlots(appointmentSlots);

        // Save the doctor and slots to the database
        DoctorDaoImpl d=new DoctorDaoImpl();
        d.saveDoctor(newDoctor);

        System.out.println("Doctor created successfully with ID: " + newDoctor.getId());
    }

    private static void viewDoctors(){
        System.out.println("list of doctors are");
        DoctorDaoImpl d= new DoctorDaoImpl();
        List<Doctor> allDoctors=d.getAllDoctors();

        for(Doctor doc:allDoctors){
            System.out.println(doc.getId());
            System.out.println(doc.getName());
            System.out.println(doc.getSpecialization());

            List<AppointmentSlot> slots=doc.getAppointmentSlots();
            System.out.println("doctors appointment slots are:");
            for(AppointmentSlot slot:slots){
                System.out.println(slot);
            }
        }

    }

    private static void deleteDoctor(){
        System.out.println("delete doctor operation below are list of doctors ");
        viewDoctors();
        System.out.println("enter the doctor id you want to delete ");
        long id=sc.nextLong();
        sc.nextLine();

        DoctorDaoImpl d=new DoctorDaoImpl();
        d.deleteDoctor(id);

    }

    private static void updateDoctor(){
        System.out.println("update doctor ");
        System.out.println("input doctor id you want to delete ");

        long id = sc.nextLong();
        sc.nextLine();

        DoctorDaoImpl d= new DoctorDaoImpl();
        Doctor updatedDoctor;
        updatedDoctor=d.getDoctorById(id);

        updatedDoctor.setId(updatedDoctor.getId());

        System.out.println("Enter doctor's name:");
        String name = sc.nextLine();
        updatedDoctor.setName(name);

        System.out.println("Enter doctor's specialization:");
        String specialization = sc.nextLine();
        updatedDoctor.setSpecialization(specialization);

        // Collect appointment slots
        List<AppointmentSlot> appointmentSlots = new ArrayList<>();
        String addMoreSlots;
        do {
            AppointmentSlot slot = new AppointmentSlot();

            System.out.println("Enter available date and time for the appointment slot ");

            String availableDate =  sc.nextLine();

            slot.setAvailableDate(availableDate);
            slot.setDoctor(updatedDoctor); // Set the doctor for this slot

            // Add the slot to the list
            appointmentSlots.add(slot);

            System.out.println("Do you want to add another appointment slot? (yes/no)");
            addMoreSlots = sc.nextLine();
        } while (addMoreSlots.equalsIgnoreCase("yes"));

        updatedDoctor.setAppointmentSlots(appointmentSlots);

        d.updateDoctor(updatedDoctor);

    }
    static boolean confirmAccountDelete(String user,String pass){
        PatientDaoImpl patientDao= new PatientDaoImpl();
        Patient patient = patientDao.validateLogin(user,pass);
        if(patient !=null){return true;}
        return false;
    }
    static void loginAsPatient() {
        System.out.println("Enter your username: ");
        String username = sc.nextLine();

        System.out.println("Enter your password: ");
        String password = sc.nextLine();

        PatientDaoImpl patientDao= new PatientDaoImpl();
        Patient patient = patientDao.validateLogin(username,password);

        if (patient != null) {
            System.out.println("Login successful! Welcome, " + patient.getUsername());
            patient();
        } else {
            System.out.println("Invalid credentials, please try again.");
        }
    }

    //all patient operations
    public static void registerNewPatient(){

            System.out.println("enter patient first name ");
            String firstName = sc.nextLine();

            System.out.println("Enter Patient Last Name:");
            String lastName = sc.nextLine();

            System.out.println("Enter Patient username:");
            String username= sc.nextLine();

            System.out.println("Enter Patient password:");
            String password = sc.nextLine();

            Patient newPatient = new Patient();
            newPatient.setFirstName(firstName);
            newPatient.setLastName(lastName);
        newPatient.setUsername(username); // Correct for username
        newPatient.setPassword(password); // Correct for password

             // Persist patient and appointment to the database
             PatientDaoImpl patientDao = new PatientDaoImpl();
             if(patientDao.savePatient(newPatient)){
        System.out.println("new user register successfully");
        System.out.println("login as existing patient user");
        mainOps();}
             else{
                 System.out.println("new registration failed  try again ");
                 registerNewPatient();
             }
    }
    public static void patient() {
        System.out.println("login successfullly ");
        while (true) {
            System.out.println("Patient Dashboard");
            System.out.println("/*1.check prescription */ \n2. View all patient detail\n3. Delete ur account\n4. Update your details \n5. View ur details \n6. Back to Main Menu");

            int input = sc.nextInt();
            sc.nextLine();

            switch (input) {
                case 1:
                    //presciption()
                    break;
                case 2:
                    viewAllPatients();
                    break;
                case 3:
                    deletePatient();
                    break;
                case 4:
                    updatePatient();
                    break;
                case 5:
                    viewPatientById();
                    break;
                case 6:
                    System.out.println(" back to main menu ");
                    mainOps();// Go back to the main menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void  createPatient(){
        System.out.println("enter patient first name ");
        String firstName = sc.nextLine();

        System.out.println("Enter Patient Last Name:");
        String lastName = sc.nextLine();

        System.out.println("Enter Patient Email:");
        String email = sc.nextLine();

        System.out.println("Enter Patient Address:");
        String address = sc.nextLine();

        Patient newPatient = new Patient();
        newPatient.setFirstName(firstName);
        newPatient.setLastName(lastName);
        newPatient.setEmail(email);
        newPatient.setAddress(address);

        //show available appointment slots
        System.out.println("available appointment slots are");
        AppointmentSlotDaoImpl asdi=new AppointmentSlotDaoImpl();
        List<AppointmentSlot> doctoravaialbleSlots=asdi.getAvailableSlots();

        /*for(AppointmentSlot slot:doctoravaialbleSlots){
            System.out.println("doctor name:"+slot.getDoctor());
            System.out.println("doctor avaialability :"+ slot.getAvailableDate());
        }*/

        if (doctoravaialbleSlots.isEmpty()) {
            System.out.println("No available slots at the moment.");
        } else {
            for (int i = 0; i < doctoravaialbleSlots.size(); i++) {
                AppointmentSlot slot = doctoravaialbleSlots.get(i);
                System.out.println((i + 1) + ". " + slot.getAvailableDate());
            }
        }
        // Select an appointment slot for the patient
        System.out.println("Enter the number of the slot you'd like to book:");
        int slotIndex = sc.nextInt();
        sc.nextLine();
        if (slotIndex > 0 && slotIndex <= doctoravaialbleSlots.size()) {
            AppointmentSlot selectedSlot = doctoravaialbleSlots.get(slotIndex - 1);

            // Create an appointment for the patient
            Appointment newAppointment = new Appointment();
            newAppointment.setPatient(newPatient);
            newAppointment.setSlot(selectedSlot);

            // Add the appointment to the patient
            newPatient.setAppointments(List.of(newAppointment));

            // Persist patient and appointment to the database
            PatientDaoImpl patientDao = new PatientDaoImpl();
            patientDao.savePatient(newPatient);

            System.out.println("Patient and appointment saved successfully!");
        } else {
            System.out.println("Invalid slot selection.");
        }
    }

    public static void viewAllPatients(){
        System.out.println("============================================\nlist of all patients ");

        PatientDaoImpl p1=new PatientDaoImpl();
        List<Patient> allPatients=p1.getAllPatients();

        for(Patient p :allPatients){
            System.out.println("patient id : "+p.getId());
            System.out.println("patient name : "+p.getFirstName()+" "+p.getLastName());
            System.out.println("patient email : "+p.getEmail());
            System.out.println("patient address : "+p.getAddress());
            System.out.println("patients booked appointments are ");



            for (Appointment a : p.getAppointments()) {

                AppointmentSlot as=a.getSlot();

                System.out.println("doctor name :"+ as.getDoctor().getName()+" | doctor specialization :"+as.getDoctor().getSpecialization() +" | appointment date and time :"+as.getAvailableDate());

            }
            System.out.println("============================================================\n");
        }
    }

    private static void viewPatientById() {

        System.out.println("enter patient id to view individual patient ");

        long id = sc.nextLong();
        sc.nextLine();

        System.out.println("\npatient details are:");
        PatientDaoImpl p = new PatientDaoImpl();
        Patient patient;
        patient = p.getPatientById(id);

        if(patient !=null) {

            System.out.println("patient id : " + patient.getId());
            System.out.println("patient name : " + patient.getFirstName() + " " + patient.getLastName());
            System.out.println("patient email : " + patient.getEmail());
            System.out.println("patient address : " + patient.getAddress());
            System.out.println("patients booked appointments are ");


            for (Appointment a : patient.getAppointments()) {

                AppointmentSlot as = a.getSlot();

                System.out.println("doctor name :" + as.getDoctor().getName() + " | doctor specialization :" + as.getDoctor().getSpecialization() + " | appointment date and time :" + as.getAvailableDate());

            }
        } else {
            System.out.println("patient doesnt exist");
        }
    }

    private static void deletePatient(){
        System.out.println("=====================================");
        System.out.println("delete your account");
        System.out.println("enter patien id to confirm deletion");
        long id=sc.nextLong();
        sc.nextLine();

        PatientDaoImpl p=new PatientDaoImpl();
        Patient patient;
        patient=p.getPatientById(id);
        System.out.println("are you sure you want to delete enter yes or no");
        String s=sc.nextLine();
        System.out.println("eneter your username to confirm deletion ");
        String user=sc.nextLine();
        System.out.println("eneter your  password to confirm deletion ");
        String pass= sc.nextLine();
        if(s.equalsIgnoreCase("yes" )&& confirmAccountDelete(user,pass)){
        if(patient!=null){
            p.deletePatient(patient);
            System.out.println("patient deleted successfully");
        }else{
            System.out.println("invalid user not found enter correct details and try again");
        }
        }else {
            System.out.println("deletion cancelled ");
        }
    }

    private static void updatePatient(){
        System.out.println("================================================" +
                "\nupdate pateint \nenter patient id to update ");

        long id=sc.nextLong();
        sc.nextLine();

        PatientDaoImpl p=new PatientDaoImpl();
        Patient patient=p.getPatientById(id);

        if(patient!=null){
            System.out.println("enter patient first name ");
            String firstName = sc.nextLine();

            System.out.println("Enter Patient Last Name:");
            String lastName = sc.nextLine();

            System.out.println("Enter Patient Email:");
            String email = sc.nextLine();

            System.out.println("Enter Patient Address:");
            String address = sc.nextLine();

            patient.setFirstName(firstName);
            patient.setLastName(lastName);
            patient.setEmail(email);
            patient.setAddress(address);

            System.out.println("do u also want change the appointment for this patient enter yes or no");
            String str=sc.nextLine();
            if(str.equalsIgnoreCase("yes")) {

/*                //show available appointment slots
                System.out.println("available appointment slots are");
                AppointmentSlotDaoImpl asdi=new AppointmentSlotDaoImpl();

                List<AppointmentSlot> doctoravaialbleSlots=asdi.getAvailableSlots();


                if (doctoravaialbleSlots.isEmpty()) {
                    System.out.println("No available slots at the moment.");
                } else {
                    for (int i = 0; i < doctoravaialbleSlots.size(); i++) {
                        AppointmentSlot slot = doctoravaialbleSlots.get(i);
                        System.out.println((i + 1) + ". " + slot.getAvailableDate());
                    }
                }*/

                // Select an appointment slot for the patient
                AppointmentSlotDaoImpl asdi = new AppointmentSlotDaoImpl();
                List<AppointmentSlot> doctoravaialbleSlots = asdi.getAvailableSlots();

                System.out.println("Enter the number of the slot you'd like to book:");
                int slotIndex = sc.nextInt();
                sc.nextLine();
                if (slotIndex > 0 && slotIndex <= doctoravaialbleSlots.size()) {
                    AppointmentSlot selectedSlot = doctoravaialbleSlots.get(slotIndex - 1);

                    // Create an appointment for the patient
                    Appointment newAppointment = new Appointment();
                    newAppointment.setPatient(patient);
                    newAppointment.setSlot(selectedSlot);

                    // Add the appointment to the patient
                    patient.setAppointments(List.of(newAppointment));

                }
            }else {patient.setAppointments(patient.getAppointments());}
            p.updatePatient(patient);

        } else{
            System.out.println("patient not found input correct patient id and try again");
        }


    }
}



