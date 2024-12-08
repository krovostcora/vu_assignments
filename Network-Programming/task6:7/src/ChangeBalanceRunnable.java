import java.util.Random;

class ChangeBalanceRunnable implements Runnable {
    private final Account account;
    private final Random random = new Random();

    public ChangeBalanceRunnable(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            int choice = random.nextInt(11);
            float amount = random.nextFloat() * 2000;

            if (choice % 2 == 0) {
                account.deposit(amount);
                System.out.println("Deposited: " + amount + ", Balance: " + account.getBalance());
            } else {
                account.withdrawal(amount);
                System.out.println("Withdrew: " + amount + ", Balance: " + account.getBalance());
            }

            try {
                Thread.sleep(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
