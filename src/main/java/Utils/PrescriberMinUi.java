package Utils;

import Info.Drug;
import Info.FoodInteraction;
import Prescriber.Prescriber;

import java.util.List;
import java.util.Scanner;

public class PrescriberMinUi {

    public static void main(String[] args) throws InterruptedException {
        Scanner input = new Scanner(System.in);
        Prescriber fdbPrescriber = Prescriber.createFdbPrescriber();
        //While user enters "queries drugs" in initial menu
        while (initialMenu(input)) {

            List<Drug> queriedDrugs = queryDrugs(fdbPrescriber, input);
            if (queriedDrugs.isEmpty()) {
                System.out.println("No drugs found :(");
            } else {
                Drug drugChosen = chooseADrug(queriedDrugs, input);
                queryFoodInteractions(fdbPrescriber, drugChosen);
            }
        }
    }

    private static void queryFoodInteractions(Prescriber prescriber, Drug drugChosen) throws InterruptedException {
        List<FoodInteraction> foodInteractions = prescriber.queryFoodInteractionsOfDrug(drugChosen);
        if (foodInteractions.isEmpty()) {
            System.out.println("There are no harmful food interactions known :)");
            System.out.println();
            System.out.println();
        }else {
            System.out.println("Results:");
            for (FoodInteraction foodInteraction : foodInteractions)
                System.out.println(foodInteraction.getDrugFoodInteractionResult());
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
}


