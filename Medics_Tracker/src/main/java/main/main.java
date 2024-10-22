package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.AllOperations;

import static main.AllOperations.*;

public class main {
    private static Scanner sc = new Scanner(System.in); // Single scanner instance

    public static void main(String[] args) {
        AllOperations allOperations = new AllOperations(sc); // Pass scanner to AllOperations
        mainOps();
    }

    static void mainOps() {
        while (true) {
            System.out.println("welcome to medics application \n1. Doctor login  \n2.existing Patient user login \n3.register new patient user \n4. Quit");
            int choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    loginAsPatient();
                    break;
                case 3:
                    registerNewPatient();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Wrong input.");
            }
        }
    }
}
