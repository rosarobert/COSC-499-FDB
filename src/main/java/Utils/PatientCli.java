package Utils;

import Info.Allergy;
import Info.Drug;
import Info.DrugInteraction;
import Info.Patient;
import Prescriber.Prescriber;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class PatientCli {

    public static void main(String[] args) throws InterruptedException {
        List<Patient> patients = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        Prescriber fdbPrescriber = Prescriber.createFdbPrescriber();
        // While user enters "queries drugs" in initial menu
        while (initialMenu(input)) {
            int choice = patientMenu(input);
            while(choice != 3) {
                if (choice == 1) {
                    Patient patient = createPatientMenu(input);
                    addToPatient(fdbPrescriber, input, patient);
                    patients.add(patient);
                } else {
                    int patientIndex = chooseAPatient(patients, input);
                    if (patientIndex == -1) {
                        System.out.println("Sorry, there are no patients.");
                    } else if (patientIndex != patients.size()) {
                        addToPatient(fdbPrescriber, input, patients.get(patientIndex));
                    }
                }
                choice = patientMenu(input);
            }
        }
    }

    /**
     * Prints the inital menu and prompts user for input
     *
     * @return true if user wants to query drugs
     */
    private static boolean initialMenu(Scanner input) {
        System.out.println("(1): Enter Prescription CLI");
        System.out.println("(2): Exit");
        System.out.println("Choose an option by entering the corresponding number:");

        int choice = chooseAnInteger(input, 1, 3);
        if (choice == 2)
            System.out.println("Program Ended");
        return choice == 1;
    }

    private static int patientMenu(Scanner input) {
        System.out.println("(1): Create A New Patient");
        System.out.println("(2): Use Existing Patient");
        System.out.println("(3): Exit");
        System.out.println("Choose an option by entering the corresponding number:");

        return chooseAnInteger(input, 1, 4);
    }

    private static Patient createPatientMenu(Scanner input) {
        Patient patient = new Patient();
        System.out.println("Enter patient name:");
        String name = input.nextLine();
        patient.setName(name);
        return patient;
    }

    private static int addToPatientMenu(Scanner input, Patient patient) {
        System.out.println("(1): Prescribe a drug to " + patient.getName());
        System.out.println("(2): Add an allergy to " + patient.getName());
        System.out.println("(3): View " + patient.getName() + "'s past prescriptions");
        System.out.println("(4): Remove drug");
        System.out.println("(5): View " + patient.getName() + "'s allergies");
        System.out.println("(6): Remove allergy");
        System.out.println("(7): Exit Patient");
        System.out.println("Choose an option by entering the corresponding number:");

        return chooseAnInteger(input, 1, 8);
    }

    private static void addToPatient(Prescriber fdbPrescriber, Scanner input, Patient patient) {
        int patientOption = addToPatientMenu(input, patient);
        while(patientOption != 7){ //while patient doesn't choose exit
            if( patientOption == 1){ //add a drug to the current patient
                addDrug(fdbPrescriber, input, patient);
            }else if (patientOption == 2){ //add a allergy to the current patient
                List<Allergy> queriedAllergies = queryAllergies(fdbPrescriber, input);
                if(queriedAllergies.isEmpty()){
                    System.out.println("No allergies found");
                }else{
                    Allergy allergyChosen = chooseAAllergy(queriedAllergies, input);
                    if(allergyChosen != null)
                        patient.addAllergy(allergyChosen);
                }
            }else if(patientOption == 3){ //Get prescribed drugs of current patient
                if(!patient.getDrugsPrescribed().isEmpty()){
                    System.out.println(patient.getName()+" is taking:");
                    Iterator<Drug> drugIterator = patient.getDrugsPrescribed().iterator();
                    while(drugIterator.hasNext())
                        System.out.println(drugIterator.next().getDisplayName());
                }else{
                    System.out.println(patient.getName() + " is not currently taking any drugs!");
                }
            }else if(patientOption == 4){
                if(!patient.getDrugsPrescribed().isEmpty()){
                    // create an empty list
                    List<Drug> list = new ArrayList<>();

                    // push each element in the set into the list
                    for (Drug a : patient.getDrugsPrescribed())
                        list.add(a);
                    Drug drugChosen = chooseADrug(list,input);
                    if(drugChosen != null) {
                        patient.getDrugsPrescribed().remove(drugChosen);
                        System.out.println(drugChosen.getDisplayName() + " removed from " + patient.getName());
                    }
                }else{
                    System.out.println(patient.getName() + " isn't taking any drugs!");
                }
            }else if(patientOption == 5){ //Get patient allergies
                if(!patient.getPatientAllergies().isEmpty()){
                    System.out.println(patient.getName()+"'s allergies:");
                    Iterator<Allergy> allergyIterator = patient.getPatientAllergies().iterator();
                    while(allergyIterator.hasNext())
                        System.out.println(allergyIterator.next().getName());
                }else{
                    System.out.println(patient.getName() + " has no allergies!");
                }
            }else{
                if(!patient.getPatientAllergies().isEmpty()){
                    // create an empty list
                    List<Allergy> list = new ArrayList<>();

                    // push each element in the set into the list
                    for (Allergy a : patient.getPatientAllergies())
                        list.add(a);
                    Allergy allergyChosen = chooseAAllergy(list,input);
                    if(allergyChosen != null) {
                        patient.getPatientAllergies().remove(allergyChosen);
                        System.out.println(allergyChosen.getName() + "removed from " + patient.getName());
                    }
                }else{
                    System.out.println(patient.getName() + " has no allergies!");
                }
            }
            patientOption = addToPatientMenu(input, patient);
        }
    }

    private static int acceptMenu(Scanner input) {
        System.out.println("(1): Prescribe Drug");
        System.out.println("(2): Cancel Prescribing");
        System.out.println("Choose an option by entering the corresponding number:");

        int choice = chooseAnInteger(input, 1, 3);
        return choice;
    }

    private static List<Drug> queryDrugs(Prescriber prescriber, Scanner input) {
        System.out.println("Enter a prefix:");
        String prefix = input.nextLine();
        return prescriber.queryDrugs(prefix);
    }

    private static Drug chooseADrug(List<Drug> drugs, Scanner input) {
        for (int i = 0; i < drugs.size(); i++)
            System.out.printf("(%d): %30s\n", i, drugs.get(i).getDisplayName());
        System.out.printf("Choose a drug by entering an index: (0 to Exit)\n",drugs.size());
        int index = chooseAnInteger(input, 0, drugs.size()+1);
        if(index == drugs.size()+1)
            return null;
        return drugs.get(index);
    }

    private static List<Allergy> queryAllergies(Prescriber prescriber, Scanner input) {
        System.out.println("Enter a prefix:");
        String prefix = input.nextLine();
        return prescriber.queryAllergies(prefix);
    }

    private static Allergy chooseAAllergy(List<Allergy> allergies, Scanner input) {
        for (int i = 0; i < allergies.size(); i++)
            System.out.printf("(%d): %30s\n", i, allergies.get(i).getName());
        System.out.printf("Choose a allergy by entering an index: (0 to Exit)\n",allergies.size());
        int index = chooseAnInteger(input, 0, allergies.size()+1);
        if(index == allergies.size()+1)
            return null;
        return allergies.get(index);
    }

    private static int chooseAPatient(List<Patient> patients, Scanner input) {
        if(patients.isEmpty())
            return -1;
        for (int i = 0; i < patients.size(); i++)
            System.out.printf("(%d): %30s\n", i, patients.get(i).getName());
        System.out.printf("Choose a allergy by entering an index: (%d to Exit)\n",patients.size());
        return chooseAnInteger(input, 0, patients.size()+1);
    }

    private static void addDrug(Prescriber fdbPrescriber, Scanner input, Patient patient){
        List<Drug> queriedDrugs = queryDrugs(fdbPrescriber, input);
        if (queriedDrugs.isEmpty()) {
            System.out.println("No drugs found :(");
        }else{
            Drug drugChosen = chooseADrug(queriedDrugs, input);
            if(drugChosen != null){
                List<DrugInteraction> drugInteractions = fdbPrescriber.findInteractions(drugChosen, patient);
                if(drugInteractions.isEmpty()){
                    System.out.println("No drug interactions!");
                    int choice = acceptMenu(input);
                    if (choice == 1) {
                        System.out.println("Prescribing drug " + drugChosen.getDisplayName() + " to " + patient.getName());
                        patient.addDrug(drugChosen);
                    } else {
                        System.out.println(drugChosen.getDisplayName() + " not added to " + patient.getName());
                    }
                }else {
                    //break up into food, allergy and drug later
                    System.out.println("Prescribing " + drugChosen.getDisplayName() + " will cause these interactions:");
                    for (int i = 0; i < drugInteractions.size(); i++) {
                        System.out.println(drugInteractions.get(i).getInteractionDescription());
                    }
                    int choice = acceptMenu(input);
                    if (choice == 1) {
                        System.out.println("Prescribing drug " + drugChosen.getDisplayName() + " to " + patient.getName());
                        patient.addDrug(drugChosen);
                    } else {
                        System.out.println(drugChosen.getDisplayName() + " not added to " + patient.getName());
                    }
                }
            }
        }
    }

    /**
     * Returns an integer from the user that satisfies the given predicate
     */
    private static int chooseAnInteger(Scanner input, int low, int high) {

        int result = low - 1;
        while (result < low || result >= high) {
            try {
                result = Integer.parseInt(input.nextLine());
                if (result >= high || result < low)
                    System.out.println("Number is not in range. Try again: ");
            } catch (NumberFormatException e) {
                System.out.println("Not a number. Try again");
            }
        }
        return result;
    }

}
