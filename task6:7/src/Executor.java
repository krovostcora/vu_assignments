import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor {
    public static void main(String[] args) {
        Account account = new Account(1234);
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            executor.submit(new ChangeBalanceRunnable(account));
        }

        executor.shutdown();
    }
}
