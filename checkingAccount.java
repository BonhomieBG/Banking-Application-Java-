// Class for checking account information

public class checkingAccount extends Account { //inherits from Account class
    public checkingAccount(customer accountName, double balance, int accountCode) {
        super(accountName, balance, accountCode); // Calls the constructor of the parent class
        this.accountType = "Checking Account"; // Set the account type to "Checking Account"
    }

    public checkingAccount(customer accountName, double balance, int customAccountNumber, int accountCode) {
        super(accountName, balance, customAccountNumber, accountCode); // Calls the overloaded constructor of the parent class
        this.accountType = "Checking Account"; // Set the account type to "Checking Account"
    }

    public checkingAccount(){
        super(); // Calls the default constructor of the parent class
        this.accountType = "Checking Account"; // Set the account type to "Checking Account"
    }

    public String toString(){
        return "Checking Account Details:\n"+super.toString();
    }
}