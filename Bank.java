abstract class BankAccount {
    private static int accountCounter = 1000;
    private int accountNumber;
    private String accountHolderName;
    private String mobileNumber;
    protected double balance;
    private boolean isActive;

    public BankAccount(String name, String mobile, double initialDeposit) {
        this.accountNumber = ++accountCounter;
        this.accountHolderName = name;
        this.mobileNumber = mobile;
        this.balance = initialDeposit;
        this.isActive = false;
    }

    public abstract void openAccount();
    public abstract double calculateInterest();
    public abstract void withdraw(double amount);

    protected void activateAccount() {
        this.isActive = true;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void displayAccountDetails() {
        System.out.println("\nAccount Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Mobile Number: " + mobileNumber);
        System.out.println("Balance: ₹" + balance);
        System.out.println("Account Active: " + (isActive ? "Yes" : "No"));
    }
}

class SavingAccount extends BankAccount {
    private int withdrawalCount = 0;
    private static final double INTEREST_RATE = 0.04;

    public SavingAccount(String name, String mobile, double deposit) {
        super(name, mobile, deposit);
    }

    @Override
    public void openAccount() {
        if (balance >= 1000) {
            activateAccount();
            System.out.println("Saving account opened successfully.");
        } else {
            System.out.println("Minimum deposit for Saving Account is ₹1000.");
        }
    }

    @Override
    public double calculateInterest() {
        double interest = balance * INTEREST_RATE;
        System.out.println("Annual Interest: ₹" + interest);
        return interest;
    }

    @Override
    public void withdraw(double amount) {
        if (!isActive()) {
            System.out.println("Account is not active.");
            return;
        }
        if (withdrawalCount >= 3) {
            System.out.println("Monthly withdrawal limit reached.");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds.");
            return;
        }
        balance -= amount;
        withdrawalCount++;
        System.out.println("₹" + amount + " withdrawn. New balance: ₹" + balance);
    }
}

class CurrentAccount extends BankAccount {
    private boolean isBusiness;
    private static final double MIN_BALANCE = 2000;
    private static final double PENALTY = 500;

    public CurrentAccount(String name, String mobile, double deposit, boolean isBusiness) {
        super(name, mobile, deposit);
        this.isBusiness = isBusiness;
    }

    @Override
    public void openAccount() {
        if (balance >= 5000) {
            activateAccount();
            System.out.println("Current account opened successfully.");
        } else {
            System.out.println("Minimum deposit for Current Account is ₹5000.");
        }
    }

    @Override
    public double calculateInterest() {
        System.out.println("No interest for Current Account.");
        return 0;
    }

    @Override
    public void withdraw(double amount) {
        if (!isActive()) {
            System.out.println("Account is not active.");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds.");
            return;
        }
        balance -= amount;
        System.out.println("₹" + amount + " withdrawn. New balance: ₹" + balance);
        if (balance < MIN_BALANCE) {
            balance -= PENALTY;
            System.out.println("Penalty of ₹500 applied for low balance.");
            System.out.println("New balance after penalty: ₹" + balance);
        }
    }
}

public class BankSystem {
    public static void main(String[] args) {
        System.out.println("=== Saving Account ===");
        SavingAccount sa = new SavingAccount("John Doe", "9876543210", 1200);
        sa.openAccount();
        sa.displayAccountDetails();
        sa.calculateInterest();
        sa.withdraw(200);
        sa.withdraw(300);
        sa.withdraw(400);
        sa.withdraw(100); 

        System.out.println("\n=== Current Account ===");
        CurrentAccount ca = new CurrentAccount("ABC Pvt Ltd", "9123456780", 6000, true);
        ca.openAccount();
        ca.displayAccountDetails();
        ca.calculateInterest();
        ca.withdraw(4500);
        ca.withdraw(1000);
    }
}
