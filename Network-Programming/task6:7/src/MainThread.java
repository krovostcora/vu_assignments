public class MainThread {
    public static void main(String[] args) {
        Account account = new Account(4444);
        for (int i = 0; i < 5; i++) {
            new ChangeBalance(account).start();
        }
    }
}

