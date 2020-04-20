package Prescriber;

public class PerformanceTest {

    private static final Prescriber UNOPTIMIZED_PRESCRIBER = Prescriber.createFdbPrescriberUnoptimized();
    private static final Prescriber PARALLEL_PRESCRIBER = Prescriber.createFdbPrescriberParallel();
    private static final Prescriber PAGE_PRESCRIBER = Prescriber.createFdbPrescriberPage();
    private static final Prescriber RELATIONAL_PRESCRIBER = Prescriber.createFdbPrescriberRelational();
    private static final Prescriber PARALLEL_RELATIONAL_PRESCRIBER = Prescriber.createFdbPrescriberParallelRelation();
    private static final Prescriber PAGE_RELATIONAL_PRESCRIBER = Prescriber.createFdbPrescriberPageRelation();

    public static void main(String[] args) {

    }

    private static void timeQueryDrugsWithoutPage(String pattern) {
        System.out.println("Test Query All Drugs");
        System.out.println();
        time("Unoptimized", () -> UNOPTIMIZED_PRESCRIBER.queryDrugs(pattern));
        time("Pagination", () -> PAGE_PRESCRIBER.queryDrugs(pattern));
        time("Relational", () -> RELATIONAL_PRESCRIBER.queryDrugs(pattern));
        time("Relational and Pagination", () -> PAGE_RELATIONAL_PRESCRIBER.queryDrugs(pattern));
    }

    private static void timeQueryDrugsWithPage(String pattern, int page) {
        System.out.println("Test Query Page of Drugs");
        System.out.println();
        time("Unoptimized", () -> UNOPTIMIZED_PRESCRIBER.queryDrugs(pattern, page));
        time("Pagination", () -> PAGE_PRESCRIBER.queryDrugs(pattern, page));
    }



    private static void time(String nameOfTest, Runnable operation) {
        try {
            long start = System.nanoTime();
            operation.run();
            long end = System.nanoTime();
            double time = (end - start) / Math.pow(10, 9);
            System.out.printf("%-20s: %f\n", time);
        } catch (NullPointerException e) {
            System.out.printf("%-20s: %f\n", "NA");
        }
    }
}
