//// Copyright (c) 2025 Tekhour Khov
// All rights reserved.
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by 
//
//  Class for checking account information
//  Created as a subclass of Account class
// 
//  Use for handle checking account information in banking system
//

public class checkingAccount extends Account { // inherits from Account class
    public checkingAccount(customer accountName, double balance, int accountCode) {
        super(accountName, balance, accountCode); // Calls the constructor of the parent class
        this.accountType = "Checking Account"; // Set the account type to "Checking Account"
    }

    public checkingAccount(customer accountName, double balance, int customAccountNumber, int accountCode) {
        super(accountName, balance, customAccountNumber, accountCode); // Calls the overloaded constructor of the parent
                                                                       // class
        this.accountType = "Checking Account"; // Set the account type to "Checking Account"
    }

    public checkingAccount() {
        super(); // Calls the default constructor of the parent class
        this.accountType = "Checking Account"; // Set the account type to "Checking Account"
    }

    public String toString() {
        return "Checking Account Details:\n" + super.toString();
    }
}