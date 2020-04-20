package Prescriber;

import Info.Allergy;
import Info.Drug;
import Info.Patient;

import java.util.List;

/**
 * Performance testing all optimizations we have made in out API
 * <p>
 * There are three main optimizations we have mode in our final API: finding drug interactions in parallel, manipulating
 * relational algebra in all queries, and paginating the querying of drugs
 * <p>
 * These tests consist of timing implementations with or without these implementations
 * <p>
 * Note that not all possible combinations of optimizations are tested here. The reason for this is the methods that we
 * apply optimizations to are different for some optimization techniques. For example, we paginate the querying of drugs
 * only because thats the only method that could result is a huge result, and we only parallize the finding of drug
 * interactions because that is the only place that consists of multiple independent queries at the same time. Thus it
 * does not make sense to test both of them together
 */
public class PerformanceTest {

    private static final Prescriber UNOPTIMIZED_PRESCRIBER = Prescriber.createFdbPrescriberUnoptimized();
    private static final Prescriber PARALLEL_PRESCRIBER = Prescriber.createFdbPrescriberParallel();
    private static final Prescriber PAGE_PRESCRIBER = Prescriber.createFdbPrescriberPage();
    private static final Prescriber RELATIONAL_PRESCRIBER = Prescriber.createFdbPrescriberRelational();
    private static final Prescriber PARALLEL_RELATIONAL_PRESCRIBER = Prescriber.createFdbPrescriberParallelRelation();
    private static final Prescriber PAGE_RELATIONAL_PRESCRIBER = Prescriber.createFdbPrescriberPageRelation();

    public static void main(String[] args) {
        timeQueryDrugsWithoutPage("can");
        timeQueryDrugsWithPage("can",2);
        timeFindDrugInteractions();
    }

    /**
     * A method that test all meaningful combinations of optimizations for querying drugs without pages
     * <p>
     * Note that pagination is not tested here because we arnt testing pages
     * <p>
     * Parallel is not tested here because we only parallelize durg interactions
     *
     * @param pattern pattern to to search for
     */
    private static void timeQueryDrugsWithoutPage(String pattern) {
        System.out.println("Test Query All Drugs");
        System.out.println();
        time("Unoptimized", () -> UNOPTIMIZED_PRESCRIBER.queryDrugs(pattern));
    }

    /**
     * A method that test all meaningful combinations of optimizations for querying drugs with pages
     * <p>
     * Note we do not test parallelism here because we only parallelize drug interactions
     *
     * @param pattern pattern to to search for
     * @param page    to consider
     */
    private static void timeQueryDrugsWithPage(String pattern, int page) {
        System.out.println();
        System.out.println("Test Query Page of Drugs");
        System.out.println();
        time("Unoptimized", () -> UNOPTIMIZED_PRESCRIBER.queryDrugs(pattern, page));
        time("Pagination", () -> PAGE_PRESCRIBER.queryDrugs(pattern, page));
        time("Relational", () -> RELATIONAL_PRESCRIBER.queryDrugs(pattern, page));
        time("Pagination and relational", () -> PAGE_RELATIONAL_PRESCRIBER.queryDrugs(pattern, page));
    }

    /**
     * Tests finding drug interactions with a dummy patient (see createTestPatient) with a dummy drug that I took from
     * other tests
     * <p>
     * Note we do not test pagination here because we only paginate querrying of drugs; we do this because the results
     * from querying interactions is usually very small
     */
    private static void timeFindDrugInteractions() {
        System.out.println();
        System.out.println("Test Drug Interaction Time");
        System.out.println();
        List<Drug> queryDrugsResult = UNOPTIMIZED_PRESCRIBER.queryDrugs("ALERT TABLET");
        Drug drug = queryDrugsResult.get(0);
        Patient dummyPatient = createTestPatient();
        time("Unoptimized", () -> UNOPTIMIZED_PRESCRIBER.findInteractions(drug, dummyPatient));
        time("Parallel", () -> PARALLEL_PRESCRIBER.findInteractions(drug, dummyPatient));
        time("Relational", () -> RELATIONAL_PRESCRIBER.findInteractions(drug, dummyPatient));
        time("Relational and Parallel", () -> PARALLEL_RELATIONAL_PRESCRIBER.findInteractions(drug, dummyPatient));
    }

    /**
     * Times an action
     *
     * @param nameOfTest name of performance test to display to stdin
     * @param operation  operation to run
     */

    private static void time(String nameOfTest, Runnable operation) {
        try {
            long start = System.nanoTime();
            operation.run();
            long end = System.nanoTime();
            double time = (end - start) / Math.pow(10, 9);
            System.out.printf("%-20s: %f\n", nameOfTest, time);
        } catch (RuntimeException e) {
            System.out.printf("%-20s: %s\n", nameOfTest, "NA");
        }
    }

    /**
     * Creates a dummy patient (which i took from other tests)
     *
     * @return a dummy patient
     */
    private static Patient createTestPatient() {
        Patient patient = new Patient();
        patient.addAllergy(Allergy.createFdbAllergy(140, "Xanthines"));
        patient.addAllergy(Allergy.createFdbAllergy(53, "Haemophilus influenzae Vaccines"));
        patient.addAllergy(Allergy.createFdbAllergy(900654, null));
        
        List<Drug> queryCurrentDrugResult = UNOPTIMIZED_PRESCRIBER.queryDrugs("APO-QUIN-G 325 MG TABLET");
        queryCurrentDrugResult.add(UNOPTIMIZED_PRESCRIBER.queryDrugs("BIO BALANCED CALC/MAG TAB").get(0));
        patient.addDrug(queryCurrentDrugResult.get(0));
        patient.addDrug(queryCurrentDrugResult.get(1));

        return patient;
    }
}
