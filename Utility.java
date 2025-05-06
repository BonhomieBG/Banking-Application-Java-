// Copyright (c) 2025 Tekhour Khov
// All rights reserved.
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation. 
//
// Contain all nesscessary methods for current implementation of banking system in client class
//

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class Utility {

    public enum TransactionType {
        DEPOSIT, WITHDRAW, TRANSFER
    }

    // Static method to search for a customer in the CSV file
    public static customer searchCustomer(String input) {
        File file = new File("user_data.csv");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                customer tempCustomer = null;
                Account tempAccount = null;

                while ((line = reader.readLine()) != null) {
                    // Skip separator lines or empty lines
                    if (line.trim().equals("-------------------------") || line.trim().isEmpty()) {
                        if (tempCustomer != null) {
                            // Check if the current customer matches the input by name or account number
                            if (tempCustomer.getCustomerName().equalsIgnoreCase(input) ||
                                    tempCustomer.getAccountByNumberOrName(input) != null) {
                                return tempCustomer;
                            }
                            tempCustomer = null; // Reset for the next customer
                        }
                        continue;
                    }

                    // Process valid data lines
                    String[] data = line.split(": ");
                    if (data.length == 2) {
                        String key = data[0].trim();
                        String value = data[1].trim();

                        if (tempCustomer == null) {
                            tempCustomer = new customer(); // Create a new customer object
                        }

                        switch (key) {
                            case "Name":
                                tempCustomer.setCustomerName(value);
                                break;
                            case "Phone Number":
                                tempCustomer.setPhoneNumber(value);
                                break;
                            case "Address":
                                tempCustomer.setAddress(value);
                                break;
                            case "Email":
                                tempCustomer.setEmail(value);
                                break;
                            case "Date of Birth":
                                tempCustomer.setCustomerDoB(LocalDate.parse(value));
                                break;
                            case "Account Number":
                                tempAccount = new Account();
                                tempAccount.setAccountNumber(Integer.parseInt(value));
                                tempAccount.setAccountHolder(tempCustomer);// Associate the account with the customer
                                tempCustomer.addAccount(tempAccount); // Add the account to the customer
                                break;
                            case "Account Type":
                                if (tempAccount != null) {
                                    tempAccount.accountType = value;
                                }
                                break;
                            case "Balance":
                                if (tempAccount != null) {
                                    tempAccount.setBalance(Double.parseDouble(value));
                                }
                                break;
                            case "PIN":
                                if (tempAccount != null) {
                                    tempAccount.setPin(Integer.parseInt(value));
                                }
                        }
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error reading file 'user_data.csv': " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return null; // Return null if no match is found
    }

    public static Account selectAccount(customer customer) {
        if (customer == null || customer.getAccounts() == null || customer.getAccounts().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No accounts found for this customer.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Display account options to the user
        String[] accountOptions = customer.getAccounts().stream()
                .map(account -> "Account Number: " + account.getAccountNumber() + " (" + account.getAccountType() + ")")
                .toArray(String[]::new);

        int selectedAccountIndex = JOptionPane.showOptionDialog(null,
                "Select an account:", "Select Account",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                accountOptions, accountOptions[0]);

        if (selectedAccountIndex == -1) {
            JOptionPane.showMessageDialog(null, "No account selected. Operation canceled.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return customer.getAccounts().get(selectedAccountIndex);
    }

    // Deposit Method
    public static void deposit(customer customer, Account account) {
        if (account == null) {
            JOptionPane.showMessageDialog(null, "Invalid account. Operation canceled.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ask for deposit amount
        String depositAmountInput = JOptionPane.showInputDialog(null,
                "Please enter the amount you want to deposit:", "Deposit",
                JOptionPane.INFORMATION_MESSAGE);
        if (depositAmountInput == null || depositAmountInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Deposit amount is required. Operation canceled.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double depositAmount = Double.parseDouble(depositAmountInput);
            if (depositAmount <= 0) {
                JOptionPane.showMessageDialog(null, "Deposit amount must be greater than 0.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update account balance
            account.setBalance(account.getBalance() + depositAmount);

            // Save updated customer data
            saveCustomer(customer);

            // Print receipt
            printReceiptToFile(account, depositAmount, TransactionType.DEPOSIT);

            JOptionPane.showMessageDialog(null,
                    "Deposit successful! New balance: $" + account.getBalance(), "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a valid number.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Withdraw Method
    public static void withdraw(customer customer, Account account) {
        if (account == null) {
            JOptionPane.showMessageDialog(null, "Invalid account. Operation canceled.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ask for withdrawal amount
        String withdrawAmountInput = JOptionPane.showInputDialog(null,
                "Please enter the amount you want to withdraw:", "Withdraw",
                JOptionPane.INFORMATION_MESSAGE);
        if (withdrawAmountInput == null || withdrawAmountInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Withdrawal amount is required. Operation canceled.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double withdrawAmount = Double.parseDouble(withdrawAmountInput);
            if (withdrawAmount <= 0) {
                JOptionPane.showMessageDialog(null, "Withdrawal amount must be greater than 0.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (withdrawAmount > account.getBalance()) {
                JOptionPane.showMessageDialog(null, "Insufficient balance.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update account balance
            account.setBalance(account.getBalance() - withdrawAmount);

            // Save updated customer data
            saveCustomer(customer);

            // Print receipt
            printReceiptToFile(account, withdrawAmount, TransactionType.WITHDRAW);

            JOptionPane.showMessageDialog(null,
                    "Withdrawal successful! New balance: $" + account.getBalance(), "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a valid number.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Transfer Method
    public static void transfer(customer customer, Account fromAccount, Account toAccount) {
        if (fromAccount == null || toAccount == null) {
            JOptionPane.showMessageDialog(null, "Invalid accounts. Operation canceled.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ask for transfer amount
        String transferAmountInput = JOptionPane.showInputDialog(null,
                "Please enter the amount you want to transfer:", "Transfer",
                JOptionPane.INFORMATION_MESSAGE);
        if (transferAmountInput == null || transferAmountInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Transfer amount is required. Operation canceled.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double transferAmount = Double.parseDouble(transferAmountInput);
            if (transferAmount <= 0) {
                JOptionPane.showMessageDialog(null, "Transfer amount must be greater than 0.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (transferAmount > fromAccount.getBalance()) {
                JOptionPane.showMessageDialog(null, "Insufficient balance in the source account.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update account balances
            fromAccount.setBalance(fromAccount.getBalance() - transferAmount);
            toAccount.setBalance(toAccount.getBalance() + transferAmount);

            // Save updated customer data
            saveCustomer(customer);

            // Print receipt
            printReceiptToFile(fromAccount, transferAmount, TransactionType.TRANSFER);

            JOptionPane.showMessageDialog(null,
                    "Transfer successful! New balance in source account: $" + fromAccount.getBalance(), "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a valid number.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Check Balance Method
    public static void checkBalance(Account account) {
        if (account == null) {
            JOptionPane.showMessageDialog(null, "Invalid account. Operation canceled.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null,
                "Your current balance is: $" + account.getBalance(), "Balance",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Request User Details Method
    public static void requestUserDetails(customer customer) {
        if (customer == null) {
            JOptionPane.showMessageDialog(null, "Invalid customer. Operation canceled.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String userDetails = "Name: " + customer.getCustomerName() + "\n" +
                "Phone Number: " + customer.getPhoneNumber() + "\n" +
                "Address: " + customer.getAddress() + "\n" +
                "Email: " + customer.getEmail() + "\n" +
                "Date of Birth: " + customer.getCustomerDoB() + "\n";

        JOptionPane.showMessageDialog(null, userDetails, "User Details", JOptionPane.INFORMATION_MESSAGE);
    }

    // Save updated customer data to user_data.csv
    public static boolean saveCustomer(customer customer) {
        if (customer == null || customer.getCustomerName() == null || customer.getAccounts() == null) {
            JOptionPane.showMessageDialog(null, "Invalid customer data. Cannot save to file.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        File file = new File("user_data.csv");
        File tempFile = new File("user_data_temp.csv");
        boolean isCustomerFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("-------------------------")) {
                    writer.write(line);
                    writer.newLine();
                    continue;
                }

                // Check if the current customer matches
                if (line.startsWith("Name: ") && line.substring(6).equalsIgnoreCase(customer.getCustomerName())) {
                    isCustomerFound = true;

                    // Write updated customer details
                    writer.write("Name: " + customer.getCustomerName());
                    writer.newLine();
                    writer.write("Phone Number: " + customer.getPhoneNumber());
                    writer.newLine();
                    writer.write("Address: " + customer.getAddress());
                    writer.newLine();
                    writer.write("Email: " + customer.getEmail());
                    writer.newLine();
                    writer.write("Date of Birth: " + customer.getCustomerDoB());
                    writer.newLine();

                    // Write all accounts
                    for (Account account : customer.getAccounts()) {
                        writer.write("Account Number: " + account.getAccountNumber());
                        writer.newLine();
                        writer.write("Account Type: " + account.getAccountType());
                        writer.newLine();
                        writer.write("Balance: " + account.getBalance());
                        writer.newLine();
                        writer.write("PIN: " + account.getPIN());
                        writer.newLine();
                    }

                    writer.write("-------------------------");
                    writer.newLine();

                    // Skip the old customer data
                    while ((line = reader.readLine()) != null && !line.trim().equals("-------------------------")) {
                        // Skip lines until the next separator
                    }
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }

            // If the customer is not found, add them as a new entry
            if (!isCustomerFound) {
                writer.write("Name: " + customer.getCustomerName());
                writer.newLine();
                writer.write("Phone Number: " + customer.getPhoneNumber());
                writer.newLine();
                writer.write("Address: " + customer.getAddress());
                writer.newLine();
                writer.write("Email: " + customer.getEmail());
                writer.newLine();
                writer.write("Date of Birth: " + customer.getCustomerDoB());
                writer.newLine();

                for (Account account : customer.getAccounts()) {
                    writer.write("Account Number: " + account.getAccountNumber());
                    writer.newLine();
                    writer.write("Account Type: " + account.getAccountType());
                    writer.newLine();
                    writer.write("Balance: " + account.getBalance());
                    writer.newLine();
                    writer.write("PIN: " + account.getPIN());
                    writer.newLine();
                }

                writer.write("-------------------------");
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error processing file 'user_data.csv': " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Replace the original file with the temporary file
        if (!file.delete() || !tempFile.renameTo(file)) {
            JOptionPane.showMessageDialog(null, "Error replacing the original file with updated data.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public static boolean printReceiptToFile(String content) {
        String fileName = "Receipt_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
                + ".csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            writer.newLine();
            writer.flush(); // Ensure all data is written to the file
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error printing receipt: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Overloaded for account and customer
    public static boolean printReceiptToFile(customer customer, Account account) {
        String content = "\t\tNew Account Creation\t\t\n" +
                "Customer Name: " + customer.getCustomerName() + "\n" +
                "Account Type: " + account.getAccountType() + "\n" +
                "Account Number: " + account.getAccountNumber() + "\n" +
                "Date Created: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                + "\n";
        return printReceiptToFile(content);
    }

    // Print receipt after every interaction
    public static boolean printReceiptToFile(Account account, double amount, TransactionType transactionType) {
        String content = "";
        switch (transactionType) {
            case DEPOSIT:
                content = "Transaction Type: Deposit\n" +
                        "Account Holder: " + account.getName() + "\n" +
                        "Deposited Amount: $" + amount + "\n" +
                        "New Balance: $" + account.getBalance() + "\n" +
                        "Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        + "\n";
                break;
            case WITHDRAW:
                content = "Transaction Type: Withdraw\n" +
                        "Account Holder: " + account.getName() + "\n" +
                        "Withdrawn Amount: $" + amount + "\n" +
                        "New Balance: $" + account.getBalance() + "\n" +
                        "Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        + "\n";
                break;
            case TRANSFER:
                content = "Transaction Type: Transfer\n" +
                        "Account Holder: " + account.getName() + "\n" +
                        "Transferred Amount: $" + amount + "\n" +
                        "New Balance: $" + account.getBalance() + "\n" +
                        "Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        + "\n";
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid transaction type.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
        }
        return printReceiptToFile(content);
    }
}
