package Prescriber;

import Info.*;

import java.util.List;

/**
 * An object that can gather the necessary information in order to make a prescription
 */
public interface Prescriber {

    /**
     * Creates a {@code Prescriber} for the FDB database using localhost as the connection to the database
     * <p>
     * Note this method will probably change significantly, but the main idea will stay the same. We want our end users
     * to only look at what our system will do (aka the interface) and not at our specific implementation
     * (FdbPresciber), so we hide our implementation and only allow our end user to create objects through here
     */
    static Prescriber createFdbPrescriber() {
        return new FdbPrescriber();
    }


    /**
     * Creates a {@code Prescriber} for the FDB database
     *
     * @param jdbcUrl  url you would pass to {@link java.sql.DriverManager#getConnection(String)}
     * @param username username in fdb server
     * @param password password in fdb server
     */
    static Prescriber createFdbPrescriber(String jdbcUrl, String username, String password) {
        return new FdbPrescriber(jdbcUrl, username, password);
    }

      /**
     * Creates a {@code Prescriber} for the FDB database
     *
     * @param username username in fdb server
     * @param password password in fdb server
     */
    static Prescriber createFdbPrescriber(String username, String password) {
        return new FdbPrescriber(username, password);
    }

    /**
     * Returns all drugs in FDB database which have a name that starts with the given prefix
     * <p>
     * Note that a drug will be compared on multiple names such as a Brand Name, Label Name, etc. See {@link
     * ManufacturedDrug}
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
     * @return list of patient allergies that would be provoked by {@code drug}
     */
    List<AllergyInteraction> queryAllergyInteractionsOfDrug(Drug drug);

    /**
     * Finds all foods in FDB database that interact badly with a given drug in FDB database
     *
     * @param drug the drug being checked
     * @return a list of foods that would interact badly with {@code drug}
     */
    List<FoodInteraction> queryFoodInteractionsOfDrug(Drug drug);

    /**
     * Finds all drugs in FDB database that would interact badly with a given drug in FDB database
     *
     * @param newDrug the drug being checked
     * @param currentDrug the drugs currently being taken
     * @return a list of drug that interact badly with {@code drug}
     */
    List<DrugInteraction> queryDrugInteractionsWithOtherDrugs(Drug newDrug, Drug currentDrug);


    /**
     * Closes any connection to any server/database that the presciber is connected to
     */
    void destroyPrescriber();

}