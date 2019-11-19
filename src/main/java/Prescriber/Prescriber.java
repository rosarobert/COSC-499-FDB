package Prescriber;

import java.util.List;

/**
 * An object that can gather the necessary information in order to make a prescription
 */
public interface Prescriber {
    /**
     * Returns all drugs in FDB database which have a name that starts with the given prefix
     * <p>
     * Note that a drug will be compared on multiple names such as a Brand Name, Label Name, etc. See {@link Drug}
     *
     * @param prefix string that all drug names should start with
     * @return a list of all drugs that start with {@code prefix}
     */
    List<Drug> queryDrugs(String prefix);

    /**
     * Determines which patient allergies will be triggered from a given drug based on allergy and drug information in
     * FDB database
     *
     * @param drug    the drug being checked for provacations
     * @param patient the patient whose allergies are being checked
     * @return list of patient allergies that would be provoked by {@code drug}
     */
    List<Allergy> queryAllergyInteractionsOfDrug(Drug drug, Patient patient);

    /**
     * Finds all foods in FDB database that interact badly with a given drug in FDB database
     *
     * @param drug the drug being checked
     * @return a list of foods that would interact badly with {@code drug}
     */
    List<Food> queryFoodInteractionsOfDrug(Drug drug);

    /**
     * Finds all drugs in FDB database that would interact badly with a given drug in FDB database
     *
     * @param drug the drug being checked
     * @return a list of drug that interact badly with {@code drug}
     */
    List<Drug> queryDrugInteractionsWithOtherDrugs(Drug drug);

}