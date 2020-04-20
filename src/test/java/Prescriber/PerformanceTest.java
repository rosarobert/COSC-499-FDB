package Prescriber;

public class PerformanceTest {
    public static void main(String[] args) {
        time("Unoptimized", () -> Prescriber.createFdbPrescriberUnoptimized());
        time("Parallel", () -> Prescriber.createFdbPrescriberParallel());
    }

    private static void time(String nameOfTest, Runnable operation) {
        long start = System.nanoTime();
        operation.run();
        long end = System.nanoTime();
        double time = (end - start) / Math.pow(10, 9);
        System.out.printf("%-20s: %f\n", time);
    }
}
