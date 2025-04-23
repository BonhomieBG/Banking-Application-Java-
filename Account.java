import java.util.concurrent.ThreadLocalRandom;
public class Account{
    protected String accountType;
    private int accountNumber, code;
    private double balance;
    private customer accountHolder;

    public static int totalAccounts = 0;

    public Account(){
        totalAccounts++;
    }

    public Account(customer name, double initialBalance, int accountCode) {
        this();
        accountHolder = name;
        accountNumber = GeneratedRandomAccountNumber();
        this.balance = initialBalance;
        code = accountCode;
    }
    // overloaded constructor for custom account number
    public Account(customer name, double initialBalance, int customAccountNumber, int accountCode) {
        this();
        accountHolder = name;
        accountNumber = customAccountNumber;
        this.balance = initialBalance;
        code = accountCode;
        // Add this account to the customer's account list
    }

    public final int GeneratedRandomAccountNumber(){
        return ThreadLocalRandom.current().nextInt(100000, 999999); // 6 digit account number
    }

    public void registerAccountWithCustomer(Account C){
        accountHolder.addAccount(C); // Add this account to the customer's list of accounts
    }

    // Getters
    public customer getAccountHolder() {
        return accountHolder;
    }

    public double getBalance(){
        return balance;
    }

    public int getAccountNumber(){
        return accountNumber;
    }
    // User pins is private thus not display in toString
    public int getLoginCode(){
        return code;
    }

    public String getAccountType(){
        return accountType;
    }

    public String getName(){
        return accountHolder.getCustomerName();
    }

    // Setters
    public void setAccountNumber(int customAccountNumber) {
        accountNumber = customAccountNumber;
    }
    public void setBalance(double amount) {
        balance = amount;
    }

    @Override
    public String toString() {
        return "Account Name: " + accountHolder + "\n" +
               "Account Type: " + accountType + "\n" +
               "Account Number: " + accountNumber + "\n" +
               "Balance: " + balance + "\n";
    }
}