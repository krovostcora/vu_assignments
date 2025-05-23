import java.util.Scanner;
import java.util.concurrent.*;

public class ParallelSum {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Scanner scanner = new Scanner(System.in);

        // Get array size and number of threads from user
        System.out.print("Enter the number of elements in the array: ");
        int size = scanner.nextInt();
        System.out.print("Enter the number of threads: ");
        int numThreads = scanner.nextInt();
        scanner.close();

        // Generate an array with random values
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 100);
        }

        // Compute the sum in parallel
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        Future<Integer>[] futures = new Future[numThreads];
        int chunkSize = (int) Math.ceil((double) size / numThreads);

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, size);
            futures[i] = executor.submit(new SumTask(array, start, end));
        }

        int totalSum = 0;
        for (Future<Integer> future : futures) {
            totalSum += future.get();
        }

        executor.shutdown();
        System.out.println("Total sum: " + totalSum);
    }
}

class SumTask implements Callable<Integer> {
    private final int[] array;
    private final int start, end;

    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() {
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += array[i];
        }
        return sum;
    }
}
