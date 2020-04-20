package Prescriber;

import Info.Allergy;
import Info.Drug;
import Info.DrugInteraction;
import Info.Patient;

import java.util.List;

/**
 * An object that can gather the necessary information in order to make a prescription
 */
public interface Prescriber {

    /**
     * Creates an FdbPrescriber object based on the static properties defined in ConnectionConfiguration.java
     */
    static Prescriber createFdbPrescriber() {
        return new FdbPrescriberUnoptimized();
    }

    /**
     * Returns all drugs in FDB database which have a name that starts with the given prefix
     * <p>
     *
     * @param prefix string that all drug names should start with
     * @param page   the page of drugs that should be shown
     * @return a list of all drugs that should be shown on a given page with the given prefix
     */
    List<Drug> queryDrugs(String prefix, int page);

    /**
     * Finds all allergies that start with a given prefix
     *
     * @param prefix string that all the allergies should start with
     * @return a list of allergies whose name starts with the given prefix
     */
    List<Allergy> queryAllergies(String prefix);

    /**
     * Finds all harmful drug interactions that could happen if a patient takes a given drug. This includes interactions
     * with drugs currently prescribed to the patient, interactions with patient allergies, and interactions with foods
     *
     * @param drugBeingPrescribed the drug we are attempting to prescribe to a patient
     * @param patient             patient we are prescribing to
     * @return a list of harmful interactions that could occur if the patient is prescribe takes the given drug
     */
    List<DrugInteraction> findInteractions(Drug drugBeingPrescribed, Patient patient);

    /**
     * Adds a drug to the prescribed drugs of the patient
     *
     * @param drug    drug being added
     * @param patient patient being added to
     */
    void prescribeDrug(Drug drug, Patient patient);

    /**
     * Closes any connection to any server/database that the presciber is connected to
     * <p>
     * Note, we realize this could be replaced by {@link #finalize()}, but we did not know that before the due date of
     * the presentation, and we did not want to risk changing it
     * <p>
     * This method also has the added benefit of forcing any implementation to use this method rather than hope the
     * implementation overrides {@link #finalize()}
     */
    boolean closePrescriber();

}