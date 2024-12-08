class SynchronizedAccount {
    private float balance;

    public SynchronizedAccount(float balance) {
        this.balance = balance;
    }

    public SynchronizedAccount() {
        this(0);
    }

    public synchronized float getBalance() {
        return balance;
    }

    public synchronized void deposit(float amount) {
        balance += amount;
    }

    public synchronized void withdrawal(float amount) {
        if (amount <= balance) {
            balance -= amount;
        }
    }
}
