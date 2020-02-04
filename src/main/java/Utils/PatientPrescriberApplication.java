package Utils;

import java.util.Scanner;

import Prescriber.Prescriber;

final class PatientPresciber {
    // Change with your info
    private static final Scanner INPUT = new Scanner(System.in);
    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "linuxSucks123";
    private static Prescriber prescriber;

    public static void main(String[] args) {
        createPrescriber();
        do {
            int menuChoice = getMainMenuChoice();
            switch (menuChoice) {
            case 0:
                break;
            case 4:
                INPUT.close();
                return;
            }
        } while (true);

    }

    private static void createPrescriber() {
        System.out.println("Who are you?:");
        System.out.println("(0): A normal person");
        System.out.println("(1): Ethan");
        int choice = chooseAnIntegerInInclusiveRange(0, 1);
        if (choice == 0) {
            prescriber = Prescriber.createFdbPrescriber();
        } else {
            prescriber = Prescriber.createFdbPrescriber(USER_NAME, PASSWORD);
        }
    }

    private static final int getMainMenuChoice() {
        System.out.println("(0): Print patient info");
        System.out.println("(1): Prescribe drug");
        System.out.println("(2): Switch patient");
        System.out.println("(3): Add patient");
        System.out.println("(4): Exit program");
        return chooseAnIntegerInInclusiveRange(0, 3);
    }

    /**
     * Returns an integer from the user that is between the two numbers inclusive
     */
    private static int chooseAnIntegerInInclusiveRange(int low, int high) {
        boolean isNotFirstIteration = false;
        int result = -1;
        do {
            if (isNotFirstIteration)
                System.out.print("\nInvalid input. Try again.");
            System.out.print("\nEnter an integer between " + low + " and " + high + " inclusive:");
            try {
                result = INPUT.nextInt();
            } catch (NumberFormatException e) {
                INPUT.nextLine();
            }
            isNotFirstIteration = true;
        } while (result < low || result > high);
        return result;
    }
}