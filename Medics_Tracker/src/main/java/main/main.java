package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.AllOperations;

import static main.AllOperations.adminLogin;
import static main.AllOperations.patient;

public class main {
    private static Scanner sc = new Scanner(System.in); // Single scanner instance

    public static void main(String[] args) {
        AllOperations allOperations = new AllOperations(sc); // Pass scanner to AllOperations
        mainOps();
    }

    static void mainOps() {
        while (true) {
            System.out.println("Press 1. Admin \n2. Patient \n3. Quit");
            int choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    patient();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Wrong input.");
            }
        }
    }
}
