// Copyright (c) 2025 Tekhour Khov
// All rights reserved.
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation. 
//
// Subclass for saving account information

public class savingAccount extends Account { // inherits from Account class
    public savingAccount(customer accountName, double balance, int accountCode) {
        super(accountName, balance, accountCode); // Calls the constructor of the parent class
        this.accountType = "Checking Account"; // Set the account type to "Checking Account"
    }

    public savingAccount(customer accountName, double balance, int customAccountNumber, int accountCode) {
        super(accountName, balance, customAccountNumber, accountCode); // Calls the overloaded constructor of the parent
                                                                       // class
        this.accountType = "Checking Account"; // Set the account type to "Checking Account"
    }

    public savingAccount() {
        super(); // Calls the default constructor of the parent class
        this.accountType = "Checking Account"; // Set the account type to "Checking Account"
    }

    public String toString() {
        return "Saving Account Details:\n" + super.toString();
    }
}
