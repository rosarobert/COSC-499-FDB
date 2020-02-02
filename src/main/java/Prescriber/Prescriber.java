package Prescriber;

import Info.*;

import java.util.List;

/**
 * An object that can gather the necessary information in order to make a
 * prescription
 */
public interface Prescriber {

    /**
     * Creates an FdbPrescriber object based on the static properties defined in
     * ConnectionConfiguration.java
     */
    static Prescriber createFdbPrescriber() {
        return new FdbPrescriber();
    }

    /**
     * Returns all drugs in FDB database which have a name that starts with the
     * given prefix
     * <p>
     * Note that a drug will be compared on multiple names such as a Brand Name,
     * Label Name, etc. See {@link ManufacturedDrug}
     *
     * @param prefix string that all drug names should start with
     * @return a list of all drugs that start with {@code prefix}
     */
    List<Drug> queryDrugs(String prefix);

    /**
     * Determines which patient allergies will be triggered from a given drug based
     * on allergy and drug information in FDB database
     *
     * @param drug the drug being checked for provocations
     * @return list of patient allergies that would be provoked by {@code drug}
     */
    List<DrugInteraction> queryAllergyInteractionsOfDrug(Drug drug, Iterable<Integer> allergyCodes);

    /**
     * Finds all foods in FDB database that interact badly with a given drug in FDB
     * database
     *
     * @param drug the drug being checked
     * @return a list of foods that would interact badly with {@code drug}
     */
    List<DrugInteraction> queryFoodInteractionsOfDrug(Drug drug);

    /**
     * Finds all drugs in FDB database that would interact badly with a given drug
     * in FDB database
     *
     * @param newDrug     the drug being checked
     * @param currentDrug the drugs currently being taken
     * @return a list of drug that interact badly with {@code drug}
     */
    List<DrugToDrugInteraction> queryDrugInteractionsWithOtherDrugs(Drug newDrug, Iterable<Drug> otherDrugs);

    /**
     * Closes any connection to any server/database that the presciber is connected
     * to
     */
    void destroyPrescriber();

}