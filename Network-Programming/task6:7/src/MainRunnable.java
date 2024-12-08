public class MainRunnable {
    public static void main(String[] args) {
        Account account = new Account(2024);
        for (int i = 0; i < 5; i++) {
            new Thread(new ChangeBalanceRunnable(account)).start();
        }
    }
}
