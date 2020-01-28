package Utils;

import Info.Drug;
import Info.DrugInteraction;
import Info.DrugToDrugInteraction;
import Prescriber.Prescriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrescriberCli {

    public static void main(String[] args) throws InterruptedException {
        Scanner input = new Scanner(System.in);
        Prescriber fdbPrescriber = createPrescriber(input);
        // While user enters "queries drugs" in initial menu
        while (initialMenu(input)) {

            List<Drug> queriedDrugs = queryDrugs(fdbPrescriber, input);
            if (queriedDrugs.isEmpty()) {
                System.out.println("No drugs found :(");
            } else {
                Drug drugChosen = chooseADrug(queriedDrugs, input);
                int choice = interactionMenu(input);
                switch (choice) {
                    case 1:
                        queryFoodInteractions(fdbPrescriber, drugChosen);
                        break;
                    case 2:
                        queryAllergyInteractions(fdbPrescriber, drugChosen);
                        break;
                    case 3:
                        queryADrugInteractions(fdbPrescriber, drugChosen, input);
                }

            }
        }
    }

    private static Prescriber createPrescriber(Scanner input) {
        System.out.println("Who are you?:");
        System.out.println("(1): A normal person");
        System.out.println("(2): Ethan");
        int choice = chooseAnInteger(input, 1, 3);
        if (choice == 1) {
            return Prescriber.createFdbPrescriber();
        } else {
            return Prescriber.createFdbPrescriber("sa", "linuxSucks123");
        }
    }

    private static void queryFoodInteractions(Prescriber prescriber, Drug drugChosen) throws InterruptedException {
        List<DrugInteraction> foodInteractions = prescriber.queryFoodInteractionsOfDrug(drugChosen);
        if (foodInteractions.isEmpty()) {
            System.out.println("There are no harmful food interactions known :)");
            System.out.println();
            System.out.println();
        } else {
            System.out.println("Results:");
            for (DrugInteraction foodInteraction : foodInteractions)
                System.out.println(foodInteraction.getInteractionDescription());
        }
    }

    private static void queryAllergyInteractions(Prescriber prescriber, Drug drugChosen) throws InterruptedException {
        List<DrugInteraction> allergyInteractions = prescriber.queryAllergyInteractionsOfDrug(drugChosen);
        if (allergyInteractions.isEmpty()) {
            System.out.println("There are no harmful allergy interactions known :)");
            System.out.println();
            System.out.println();
        } else {
            System.out.println("Results:");
            for (DrugInteraction allergyInteraction : allergyInteractions)
                System.out.println(allergyInteraction.getInteractionDescription());
        }
    }


    private static void queryADrugInteractions(Prescriber prescriber, Drug newDrug, Scanner input) throws InterruptedException {
        List<Drug> drugsToCheck = new ArrayList<>();
        int chosenNumber = 1;
        do {
          List<Drug> queriedDrugs = queryDrugs(prescriber, input);
          Drug drugChosen = chooseADrug(queriedDrugs, input);
          drugsToCheck.add(drugChosen);
            System.out.println("Do you want to check more drugs?");
            System.out.println("(1): Yes");
            System.out.println("(2): No");
            chosenNumber = chooseAnInteger(input, 1, 3);
        } while(chosenNumber != 2);
        List<DrugToDrugInteraction> drugInteractions = prescriber.queryDrugInteractionsWithOtherDrugs(newDrug, drugsToCheck.toArray(new Drug[drugsToCheck.size()]));
        if (drugInteractions.isEmpty()) {
            System.out.println("There are no harmful allergy interactions known :)");
            System.out.println();
            System.out.println();
        } else {
            System.out.println("Results:");
            System.out.println(newDrug.getDisplayName());
            for (DrugInteraction drugInteraction : drugInteractions) {
                System.out.println(drugInteraction.getDrugBeingPrescribed().getDisplayName());
                System.out.println(drugsToCheck.get(0));
                System.out.println(drugInteraction.getInteractionDescription());
            }
        }
    }

    private static List<Drug> queryDrugs(Prescriber prescriber, Scanner input) {
        System.out.println("Enter a prefix:");
        String prefix = input.nextLine();
        return prescriber.queryDrugs(prefix);
    }

    private static Drug chooseADrug(List<Drug> drugs, Scanner input) {
        for (int i = 0; i < drugs.size(); i++)
            System.out.printf("(%d): %30s\n", i, drugs.get(i).getDisplayName());
        System.out.println("Choose a drug by entering an index:");
        int index = chooseAnInteger(input, 0, drugs.size());
        return drugs.get(index);
    }

    /**
     * Prints the inital menu and prompts user for input
     *
     * @return true if user wants to query drugs
     */
    private static boolean initialMenu(Scanner input) {
        System.out.println("(1): Query drug:");
        System.out.println("(2): Exit");
        System.out.println("Choose an option by entering the corresponding number:");

        int choice = chooseAnInteger(input, 1, 3);
        if (choice == 2)
            System.out.println("Program Ended");
        return choice == 1;
    }

    /**
     * Returns an integer from the user that satisfies the given predicate
     */
    private static int chooseAnInteger(Scanner input, int low, int high) {

        int result = low - 1;
        while (result < low || result >= high) {
            try {
                result = input.nextInt();
                input.nextLine();
                if (result < high && result >= low)
                    break;
                else
                    System.out.print("Number is not in range. Try again: ");
            } catch (NumberFormatException e) {
                System.out.println("Not a number. Try again");
                input.nextLine();
            }
        }
        return result;
    }


    private static int interactionMenu(Scanner input) {
        System.out.println("(1): Food Interactions:");
        System.out.println("(2): Allergy Interactions");
        System.out.println("(3): Drug Interactions");
        System.out.println("Choose an option by entering the corresponding number:");

        return chooseAnInteger(input, 1, 4);

    }

}
