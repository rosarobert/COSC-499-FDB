package PrescriberParallel;

import Info.*;

import java.util.List;

/**
 * An object that can gather the necessary information in order to make a
 * prescription
 */
public interface Prescriber {

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

    List<Allergy> queryAllergies(String prefix);

    List<DrugInteraction> findInteractions(Drug drugBeingPrescribed, Patient patient);

    void prescribeDrug(Drug drug, Patient patient);

    /**
     * Creates an FdbPrescriber object based on the static properties defined in
     * ConnectionConfiguration.java
     */
    static Prescriber createFdbPrescriber() {
        return new FdbPrescriber();
    }

    /**
     * Closes any connection to any server/database that the presciber is connected
     * to
     */
    boolean closePrescriber();

}