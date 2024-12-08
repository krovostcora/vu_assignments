class Account {
    private float balance;

    public Account(float balance) {
        this.balance = balance;
    }

    public Account() {
        this(0);
    }

    public float getBalance() {
        return balance;
    }

    public void deposit(float amount) {
        balance += amount;
    }

    public void withdrawal(float amount) {
        if (amount <= balance) {
            balance -= amount;
        }
    }
}
