// Class for saving account information

public class savingAccount extends Account { //inherits from Account class
    public savingAccount(customer accountName, double balance, int accountCode) {
        super(accountName, balance, accountCode); // Calls the constructor of the parent class
        this.accountType = "Checking Account"; // Set the account type to "Checking Account"
    }

    public savingAccount(customer accountName, double balance, int customAccountNumber, int accountCode) {
        super(accountName, balance, customAccountNumber, accountCode); // Calls the overloaded constructor of the parent class
        this.accountType = "Checking Account"; // Set the account type to "Checking Account"
    }

    public savingAccount(){
        super(); // Calls the default constructor of the parent class
        this.accountType = "Checking Account"; // Set the account type to "Checking Account"
    }

    public String toString(){
        return "Saving Account Details:\n"+super.toString();
    }
}
