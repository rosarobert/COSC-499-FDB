package Utils;

import Info.Drug;
import Info.FoodInteraction;
import Prescriber.Prescriber;

import java.util.List;
import java.util.Scanner;

public class PrescriberMinUi {

    public static void main(String[] args) {
        Prescriber fdbPresriber = Prescriber.createFdbPrescriber();
        fdbPresriber.initializePrescriber("jdbc:sqlserver://localhost:1433; databaseName=FDB;integratedSecurity=true");
        String prefix = "";
        List<Drug> drugResults;
        List<FoodInteraction> foodInteractionList;
        Scanner input = new Scanner(System.in);
        printInitialMenu();
        String code = input.nextLine();
        while (code.equals("1")) {
            System.out.println("Enter a prefix: ");
            prefix = input.nextLine();
            drugResults = fdbPresriber.queryDrugs(prefix);
            for(Drug drug : drugResults)
                System.out.println(drug.getDisplayName());
            System.out.println("Choose a drug: ");
            String drugIndex = input.nextLine();
            Drug drugChosen = drugResults.get(Integer.parseInt(drugIndex));
            List<FoodInteraction> foodInteractions = fdbPresriber.queryFoodInteractionsOfDrug(drugChosen);
            for (FoodInteraction foodInteraction : foodInteractions)
                System.out.println(foodInteraction.getDrugFoodInteractionResult());

            System.out.println("Program Finished");
        }
    }


    private static void printInitialMenu() {
        System.out.println("Choose an option by entering the corresponding number:");
        System.out.println("(1): Query drug:");
        System.out.println("(2): Exit");
    }


    private static void printExit() {
        System.out.println("Program Ended");
    }

    private static int enter1Or2() {
        Scanner input = new Scanner(System.in);
        int returnedValue = Integer.parseInt(input.nextLine());
        input.close();
        return returnedValue;
    }

    private static String enteryPrefix() {
        Scanner input = new Scanner(System.in);
        String result = input.nextLine();
        input.close();
        return result;
    }

    private static int chooseADrug(List<Drug> listOfDrugs) {
        Scanner input = new Scanner(System.in);
        int result = Integer.parseInt(input.nextLine());
        input.close();
        return result;
    }

    private static void printDrugs(List<Drug> drugsToPrint) {
        System.out.println("Results:");
        for (int i = 0; i < drugsToPrint.size(); i++) {
            System.out.printf("(%d): %30s\n", i, drugsToPrint.get(i).getDisplayName());
        }
        System.out.println();
    }
}


