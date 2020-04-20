package Prescriber;

public class PerformanceTest {

    Prescriber unoptimizedPrescriber = Prescriber.createFdbPrescriberUnoptimized();
    Prescriber parallelPrescriber = Prescriber.createFdbPrescriberParallel();
    Prescriber pagePrescriber = Prescriber.createFdbPrescriberPage();

    public static void main(String[] args) {
        time("Unoptimized Quer", () -> Prescriber.createFdbPrescriberUnoptimized());
        time("Parallel", () -> Prescriber.createFdbPrescriberParallel());
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
