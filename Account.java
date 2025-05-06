import java.util.concurrent.ThreadLocalRandom;

public class Account {
    protected String accountType;
    private int accountNumber, code;
    private double balance;
    private customer accountHolder;

    public static int totalAccounts = 0;

    // public Account(string name, double initialBalance, int accountCode) {
    // totalAccounts++;
    // accountHolder = name;
    // accountNumber = GeneratedRandomAccountNumber();
    // this.balance = Balance;
    // code = accountCode;
    // }

    public Account() {
        totalAccounts++;
    }

    public Account(customer name, double Balance, int accountCode) {
        this(); // call the defualt constructor with totalAccounts++
        accountHolder = name; // Associate the account with the customer
        accountNumber = GeneratedRandomAccountNumber();
        // "This" here use for setting balance but both of them are the same name so
        // "THIS" is for solving name conflict.
        this.balance = Balance;
        code = accountCode;
    }

    // overloaded constructor for custom account number
    public Account(customer name, double Balance, int customAccountNumber, int accountCode) {
        this();
        accountHolder = name; // Associate the account with the customer
        accountNumber = customAccountNumber;
        this.balance = Balance;
        code = accountCode;
        // Add this account to the customer's account list
    }

    public final int GeneratedRandomAccountNumber() {
        return ThreadLocalRandom.current().nextInt(100000, 999999); // 6 digit account number
    }

    public void registerAccountWithCustomer(Account C) {
        accountHolder.addAccount(C); // Add this account to the customer's list of accounts
    }

    // Getters
    public customer getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    // User pins is private thus not display in toString
    public int getPIN() {
        return code;
    }

    public String getAccountType() {
        return accountType != null ? accountType : "Account not exist"; // Return "Not Specified" if accountType is null
    }

    public String getName() {
        return accountHolder.getCustomerName();
    }

    // Setters
    public void setAccountNumber(int customAccountNumber) {
        accountNumber = customAccountNumber;
    }

    public void setAccountHolder(customer name) {
        accountHolder = name;
    }

    public void setBalance(double amount) {
        balance = amount;
    }

    public void setPin(int pin) {
        code = pin;
    }

    @Override
    public String toString() {
        return "Account Name: " + accountHolder + "\n" +
                "Account Type: " + accountType + "\n" +
                "Account Number: " + accountNumber + "\n" +
                "Balance: " + balance + "\n";
    }
}