// Copyright (c) 2025 Tekhour Khov
// All rights reserved.
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation. 
//
/*
   * Program: Bank Management System Application
   * Filename:        client.java
   * Author:          Tekhour Khov
   * Date Created:    4/20/2025
   * Time Spent:     2 weeks
   * Description:     Bank Management System that allows user to create an account, deposit, withdraw, transfer money, check balance and request user details.
   *                The system is designed to be user-friendly and easy to use. This system is designed to be used by both new and existing customers.
   *                The Program was created on 4/20/2025 and finished implement on 5/5/2025.
   * Predicted Test Cases: 
   * New customer, create account, Enter informations, deposit initial balance, the program then save data to user_data.csv for later retrieval.
   * 
   * Existing customer->TK(Created with new user)->Create an account->Complete details->Deposit initial $25-> Mainmenu-> Check balance-> $25-> Mainmenu->Exit.
   * 
   * New customer->Yes->Enter details->Agree TOS-> Relaunch(exited)->Create an account->Complete account creation->Check balance -> $2500 (deposited).
   *
   * Every transaction should generate a receipt and data is saved to user_data.csv.
   * In all case, the program should be able to handle invalid inputs and exceptions gracefully. Most of the exceptions are handled and X (close button) will close the program directly.
   * 
   *    * Date:                   By:               Action:
   * ---------------------------------------------------
   * 04/20/2025         TK               Created
   * 05/05/2025         TK               Finalized implementation
   * 05/06/2025         TK               Added exception handling and validation
*/

// Main Implementation of the Project
import javax.swing.JOptionPane;

enum accountType {
    CHECKING, SAVING
};

enum transactionType {
    DEPOSIT, WITHDRAW, TRANFER
};

enum aggreement {
    YES, NO
};

public class client {
    public static void main(String[] args) {
        // Initialize objects
        boolean activeSession = true;

        bank bank = new bank("Republic bank of Cambodia", "855-763399966",
                "128 Packer St, Salum District, Cambodia", "rpb.com.kh");
        boolean existingUser = false;
        customer customer = new customer();

        // Special Variables
        customer newCustomer = null; // handle new customer creation
        customer Excustomer = null; // handle existing customer search
        checkingAccount newCheckingCreation = null;
        savingAccount newSavingCreation = null;
        Account selectedAccount = null; // handle account selection

        String HelpMessage = "Welcome to " + bank.getName() +
                ". Get in touch with us at " + bank.getAddress() + " or via our email " + bank.getEmail()
                + " Or our phone number, " + bank.getPhoneNumber() + "\n";
        String[] startup = { "Existing customer", "New customer", "Exit(X)" };
        String[] options = { "Create an account", "Deposit", "Withdraw", "Transfer", "Check balance",
                "Request user details", "Help", "Exit" };

        // Welcome messages
        boolean userChecking = true;
        int choice1 = JOptionPane.showOptionDialog(null, "Welcome! Are you an existing customer?", "Welcome",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, startup, startup[0]);
        if (choice1 == JOptionPane.CLOSED_OPTION) {
            JOptionPane.showMessageDialog(null, "Goodbye!", "Goodbye", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);// Exit the program
        }
        switch (choice1) {
            case 0: // Existing customer
                UserChecking: while (userChecking) {
                    String input = JOptionPane.showInputDialog(null,
                            "Please enter your account number or name to search for your account:",
                            "Account Search", JOptionPane.INFORMATION_MESSAGE);
                    if (input == null) { // Handle "X" button
                        JOptionPane.showMessageDialog(null, "Operation canceled.", "Info",
                                JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                    }
                    Excustomer = Utility.searchCustomer(input); // Search for customer in the file
                    try { // Try Block to handle unexpected errors
                        if (Excustomer != null) {
                            customer = Excustomer; // Set the customer to the found customer
                            JOptionPane.showMessageDialog(null, "Welcome back! " + customer.getCustomerName(),
                                    "Welcome", JOptionPane.INFORMATION_MESSAGE);
                            existingUser = true;
                            userChecking = false;
                            break UserChecking; // Exit loop if customer is found
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "No matching customer found. Returning to the main menu.", "Customer Not Found",
                                    JOptionPane.WARNING_MESSAGE);
                            break UserChecking;
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "An error occurred while searching for the customer.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        break UserChecking;
                    }
                }
                break;
            case 1: // New customer
                int opt = JOptionPane.showOptionDialog(null, "Would you like to create an account?", "New Customer",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, aggreement.values(),
                        aggreement.YES);
                if (opt == JOptionPane.CLOSED_OPTION) {
                    JOptionPane.showMessageDialog(null, "Operation canceled.", "Info",
                            JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0); // Exit the program
                } else if (opt == 0) { // Yes
                    newCustomer = AccountCreator.createNewUserAccount();
                    if (newCustomer != null) {
                        JOptionPane.showMessageDialog(null, "Welcome! Please launch the app again to login.",
                                "Register success",
                                JOptionPane.INFORMATION_MESSAGE);
                        Utility.saveCustomer(newCustomer); // Save the new customer to the file
                        System.exit(0);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "In order to use our service, you need an active account.",
                            "Account required", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                break;
            case 2: // Exit
                System.exit(0);
                break;
        }

        // Main Menu
        while (activeSession) {
            int choice2 = JOptionPane.showOptionDialog(null, "What would you like to do?", "Options",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (choice2 == JOptionPane.CLOSED_OPTION) {
                JOptionPane.showMessageDialog(null, "Goodbye!", "Goodbye", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0); // Exit
            }
            switch (choice2) {
                case 0: // Create an account
                    if (existingUser) {
                        // Ask for Account type
                        int accountTypeInput = JOptionPane.showOptionDialog(null,
                                "What type of account would you like to create?", "Account Type",
                                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                                accountType.values(), accountType.CHECKING);
                        if (accountTypeInput == JOptionPane.CLOSED_OPTION) {
                            JOptionPane.showMessageDialog(null, "Operation canceled.", "Info",
                                    JOptionPane.INFORMATION_MESSAGE);
                            break; // Exit to main menu
                        }

                        // Create the selected account type
                        if (accountTypeInput == 0) { // Checking account
                            newCheckingCreation = AccountCreator.createCheckingAccount(customer);
                        } else { // Saving account
                            newSavingCreation = AccountCreator.createSavingAccount(customer);
                        }

                        // Associate the new account with the customer
                        if (newCheckingCreation != null) {
                            customer.addAccount(newCheckingCreation); // Add the new account to the customer
                            Utility.saveCustomer(customer); // Save the updated customer data
                            Utility.printReceiptToFile(customer, newCheckingCreation); // Print receipt
                        } else if (newSavingCreation != null) {
                            customer.addAccount(newSavingCreation); // Add the new account to the customer
                            Utility.saveCustomer(customer); // Save the updated customer data
                            Utility.printReceiptToFile(customer, newSavingCreation); // Print receipt
                        } else {
                            JOptionPane.showMessageDialog(null, "Account creation failed. Please try again.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "You need to log in first before creating an account.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 1: // Deposit
                    if (existingUser) {
                        selectedAccount = Utility.selectAccount(customer);
                        if (selectedAccount != null && validatePin(selectedAccount)) {
                            Utility.deposit(customer, selectedAccount);
                            Utility.saveCustomer(customer); // Save updated balance
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "You need to log in first before making a deposit.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 2: // Withdraw
                    if (existingUser) {
                        try {
                            selectedAccount = Utility.selectAccount(customer);
                            if (selectedAccount != null && validatePin(selectedAccount)) {
                                Utility.withdraw(customer, selectedAccount);
                                Utility.saveCustomer(customer);
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null,
                                    "An error occurred while processing the withdrawal. Please try again.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "You need to log in first before making a withdrawal.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 3: // Transfer
                    if (existingUser) {
                        try {
                            Account fromAccount = Utility.selectAccount(customer);
                            if (fromAccount != null && validatePin(fromAccount)) {
                                // For simplicity, selecting another account that is recorded in csv file
                                Account toAccount = Utility.selectAccount(customer);
                                if (toAccount != null) {
                                    Utility.transfer(customer, fromAccount, toAccount);
                                    Utility.saveCustomer(customer);
                                }
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null,
                                    "An error occurred while processing the transfer. Please try again.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "You need to log in first before making a transfer.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 4: // Check Balance
                    if (existingUser) {
                        try {
                            selectedAccount = Utility.selectAccount(customer);
                            if (selectedAccount != null && validatePin(selectedAccount)) {
                                Utility.checkBalance(selectedAccount);
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null,
                                    "An error occurred while checking the balance. Please try again.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "You need to log in first before checking your balance.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 5: // Request User Details
                    if (existingUser) {
                        try {
                            // Validate user pin with any account's pin
                            if (customer != null && validatePin(customer.getAccounts().get(0))) {
                                Utility.requestUserDetails(customer);
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "You need to log in first before requesting your details.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null,
                                    "An error occurred while requesting user details. Please make sure you have an account.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;

                case 6: // Help
                    JOptionPane.showMessageDialog(null, HelpMessage, "Help", JOptionPane.INFORMATION_MESSAGE);
                    break;

                case 7: // Exit
                    activeSession = false;
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please try again.", "Error",
                            JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to validate PIN
    private static boolean validatePin(Account account) {
        while (true) {
            String pinInput = JOptionPane.showInputDialog(null,
                    "Please enter your account PIN:", "PIN Validation",
                    JOptionPane.INFORMATION_MESSAGE);
            if (pinInput == null) { // Handle "X" button
                JOptionPane.showMessageDialog(null, "Operation canceled.", "Info", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0); // Exit the program
            } else if (pinInput.equals("-1")) {
                return false;
            }

            try {
                int pin = Integer.parseInt(pinInput);
                if (pin == account.getPIN()) {
                    return true; // PIN is valid
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect PIN. Please try again.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid PIN format. Please enter numbers only.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}