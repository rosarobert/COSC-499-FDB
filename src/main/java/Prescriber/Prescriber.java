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
     * Creates a prescriber using the FDB database with a page size of 20
     * <p>
     * * Note that this method uses all the optimizations that we implemented. This includes parallelizing the finding
     * of * drug interactions, using pagination to query drugs, and manipulating relational algebra in an optimal way
     */
    static Prescriber createFdbPrescriber() {
        return new FdbPrescriberOptimized();
    }

    /**
     * Creates a prescriber with a given page size
     * <p>
     * Note that this method uses all the optimizations that we implemented. This includes parallelizing the finding of
     * drug interactions, using pagination to query drugs, and manipulating relational algebra in an optimal way
     *
     * @param pageSize the size of a page
     */
    static Prescriber createFdbPrescriber(int pageSize) {
        return new FdbPrescriberOptimized(pageSize);
    }


    /**
     * Creates an presciber with a page size of 20 that is not optimized at all. That is, not parallel programming, relation algebra
     * manipulation, or pagination
     */
    static Prescriber createFdbPrescriberUnoptimized() {
        return new FdbPrescriberUnoptimized();
    }

    /**
     * Creates an presciber with a page size of 20 that is only optimized by adding parallel programming to {@link #findInteractions(Drug,
     * Patient)}. The rest of the implementation is not parallelizable
     */
    static Prescriber createFdbPrescriberParallel() {
        return new FdbPrescriberParallel();
    }

    /**
     * Creates an presciber with a page size of 20 that is only optimized by manipulating the relational algebra of all queries  in a optimal
     * way.
     * <p>
     * By optimal way, we mean that way described in our COSC 404 textbook, which is the way many DBMSs do it
     */
    static Prescriber createFdbPrescriberRelational() {
        return new FdbPrescriberRelational();
    }

    /**
     * Creates an presciber with a page size of 20 that is only optimized by adding pagination to {@link #queryDrugs(String, int)}
     * <p>
     * No other queries use pagination because their expected results are very very small
     */
    static Prescriber createFdbPrescriberPage() {
        return new FdbPrescriberPage();
    }

    /**
     * Creates an presciber with a page size of 20 that is optimized by using pagination on {@link #queryDrugs(String, int)} and manipulating
     * relational algebra on all queries
     * <p>
     * No other queries use pagination because their expected results are very very small
     */
    static Prescriber createFdbPrescriberPageRelation() {
        return new FdbPrescriberPageRelational();
    }

    /**
     * Creates an presciber with a page size of 20 that is optimized by using parallel programming on {@link #findInteractions(Drug, Patient)}
     * and manipulating relational algebra on all queries
     * <p>
     * No other queries use pagination because their expected results are very very small
     */
    static Prescriber createFdbPrescriberParallelRelation() {
        return new FdbPrescriberParallelRelational();
    }


    /**
     * Returns all drugs in FDB database which have a name that contains the pattern
     * <p>
     * The pattern does not support any regex operators; it only supports checking if the given patterns is exactly in
     * the drug name
     *
     * @param pattern string that all drug names should contain
     * @return a list of all drugs that contain the pattern
     */
    List<Drug> queryDrugs(String pattern);

    /**
     * Same as {@link #queryDrugs(String, int)} but only the drugs on a particular page are shown
     * <p>
     * The number of drugs per page is implementation specific
     *
     * @param pattern string that all drug names should contain
     * @param page    the page that all drugs returned should be on
     * @return a list of all drugs that contain the pattern
     */
    List<Drug> queryDrugs(String pattern, int page);

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