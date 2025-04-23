import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class Utility {

    // Static method to search for a customer in the CSV file
    public customer searchCustomer(String input) {
        File file = new File("user_data.csv");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                customer tempCustomer = null;

                while ((line = reader.readLine()) != null) {
                    // Skip separator lines
                    if (line.trim().equals("-------------------------") || line.trim().isEmpty()) {
                        if (tempCustomer != null) {
                            // Check if the current customer matches the input
                            if (tempCustomer.getCustomerName().equalsIgnoreCase(input)) {
                                return tempCustomer; // Return the matching customer by name
                            }
    
                            // Check if the input matches any account number for this customer
                            try {
                                int accountNumber = Integer.parseInt(input);
                                if (tempCustomer.getAccountByNumber(accountNumber) != null) {
                                    return tempCustomer; // Return the matching customer by account number
                                }
                            } catch (NumberFormatException e) {
                                // Ignore if input is not a valid account number
                            }
    
                            tempCustomer = null; // Reset for the next customer
                        }
                        continue;
                    }

                    // Process valid data lines
                    String[] data = line.split(": ");
                    if (data.length == 2) {
                        if (tempCustomer == null) {
                            tempCustomer = new customer(); // Create a new customer object
                        }
                        switch (data[0].trim()) {
                            case "Name":
                                tempCustomer.setCustomerName(data[1].trim());
                                break;
                            case "Phone Number":
                                tempCustomer.setPhoneNumber(data[1].trim());
                                break;
                            case "Address":
                                tempCustomer.setAddress(data[1].trim());
                                break;
                            case "Email":
                                tempCustomer.setEmail(data[1].trim());
                                break;
                            case "Date of Birth":
                                tempCustomer.setCustomerDoB(LocalDate.parse(data[1].trim()));
                                break;
                            case "Account Number":
                                // Create a new account and add it to the customer
                                try {
                                    int accountNumber = Integer.parseInt(data[1].trim());
                                    Account account = new Account();
                                    account.setAccountNumber(accountNumber);
                                    tempCustomer.addAccount(account);
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid account number format: " + data[1].trim());
                                }
                                break;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading file 'user_data.csv': " + e.getMessage());
            }
        }
        return null; // Return null if no match is found
    }

    public Account Deposit(Account account, double amount) {
        double depositAmount = amount;
        boolean validDeposit = false, validAccountNumber = false, validAccountCode = false;
    
        // Ask for account number or name
        while (!validAccountNumber){
        String detail = JOptionPane.showInputDialog(null, "Please enter your account number or name to search for your account:", "Account Search", JOptionPane.INFORMATION_MESSAGE);
        if (detail == null || detail.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Account search is required. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Exit gracefully if no input is provided
        }
    
        // Check if the input is an account number
        if (detail.matches("\\d{6}")) {
            try {
                int accountNumber = Integer.parseInt(detail);
                if (accountNumber == account.getAccountNumber()) {
                    validAccountNumber = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Account number not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid account number format. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Check if the input is a name
        else if (detail.matches("[a-zA-Z]+")) {
            if (detail.equalsIgnoreCase(account.getName())) {
                validAccountNumber = true;
            } else {
                JOptionPane.showMessageDialog(null, "Account name not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid account number or name.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }    
        // Ask for deposit amount
        while (!validDeposit) {
            String depositInput = JOptionPane.showInputDialog(null, "How much would you like to deposit? (or enter -1 to exit):", "Deposit", JOptionPane.INFORMATION_MESSAGE);
            if (depositInput.equals("-1")) {
                JOptionPane.showMessageDialog(null, "Operation canceled.", "Cancel", JOptionPane.INFORMATION_MESSAGE);
                return null; // Exit if user cancels or enters -1
            }
            else if (depositInput.equalsIgnoreCase("0")){
                JOptionPane.showMessageDialog(null, "Deposit amount must be greater than 0. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (depositInput.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Deposit amount is required. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
            }
    
        try {
            depositAmount = Double.parseDouble(depositInput);
            if (depositAmount <= 0) {
                JOptionPane.showMessageDialog(null, "Deposit amount must be greater than 0. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (depositAmount > 10000) {
                JOptionPane.showMessageDialog(null, "Deposit amount must be less than $10,000 at a time. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                validDeposit = true;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        while (!validAccountCode) {
            String accountCodeInput = JOptionPane.showInputDialog(null, "Please input your account code to proceed with the deposit:", "Account Code", JOptionPane.INFORMATION_MESSAGE);
            if (accountCodeInput == null || accountCodeInput.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Account code is required to proceed with the deposit. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
                break; // Exit the loop if no input is provided
            }

            if (!accountCodeInput.matches("\\d{4}")) {
                JOptionPane.showMessageDialog(null, "Account code must be 4 digits. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                continue; // Prompt again for valid input
            }

            try {
                int retry = 4;
                int accountCode = Integer.parseInt(accountCodeInput);
                if (accountCode != account.getLoginCode()) {
                    JOptionPane.showMessageDialog(null, "Wrong security code. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    retry--;
                    if (retry == 0) {
                        JOptionPane.showMessageDialog(null, "Too many failed attempts. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
                        break; // Exit the loop after too many failed attempts
                    }
                } else {
                    validAccountCode = true; // Exit the loop when the code is correct
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a 4-digit number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
            return null;
        }
    
        // Ask for account code
        while(validAccountCode == false){}
        int retry = 4;
        String accountCodeInput = JOptionPane.showInputDialog(null, "Please input your account code to proceed with the deposit:", "Account Code", JOptionPane.INFORMATION_MESSAGE);
        if (accountCodeInput == null || accountCodeInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Account code is required to proceed with the deposit. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        if (!accountCodeInput.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(null, "Account code must be 4 digits. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        try {
            int accountCode = Integer.parseInt(accountCodeInput);
            if (accountCode != account.getLoginCode()) {
                JOptionPane.showMessageDialog(null, "Wrong security code. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                retry--;
                if (retry == 0) {
                    JOptionPane.showMessageDialog(null, "Too many failed attempts. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
                    return null; // Exit if too many failed attempts
                }
            } else {
                validAccountCode = true;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a 4-digit number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        // Process the deposit
        if (validDeposit && validAccountCode && validAccountNumber) {
            JOptionPane.showMessageDialog(null, "Your deposit is processing, please wait a moment...", "Processing", JOptionPane.INFORMATION_MESSAGE);
            account.setBalance(account.getBalance() + depositAmount);
            JOptionPane.showMessageDialog(null, "Your deposit is successful. Your new balance is: $" + account.getBalance(), "Deposit Successful", JOptionPane.INFORMATION_MESSAGE);
            return account;
        } else {
            JOptionPane.showMessageDialog(null, "Oh no! Something went wrong. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public Account Withdraw(Account account, double amount) {
        double withdrawAmount = amount;
        boolean validWithdraw = false, validAccountNumber = false, validAccountCode = false;
    
        // Ask for account number or name
        while (validAccountNumber == false){
        String detail = JOptionPane.showInputDialog(null, "Please enter your account number or name to search for your account:", "Account Search", JOptionPane.INFORMATION_MESSAGE);
        if (detail == null || detail.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Account search is required. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Exit gracefully if no input is provided
        }
    
        // Check if the input is an account number
        if (detail.matches("\\d{6}")) {
            try {
                int accountNumber = Integer.parseInt(detail);
                if (accountNumber == account.getAccountNumber()) {
                    validAccountNumber = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Account number not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid account number format. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Check if the input is a name
        else if (detail.matches("[a-zA-Z]+")) {
            if (detail.equalsIgnoreCase(account.getName())) {
                validAccountNumber = true;
            } else {
                JOptionPane.showMessageDialog(null, "Account name not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid account number or name.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        }
        // Ask for withdraw amount
        while (validWithdraw == false){
        String withdrawInput = JOptionPane.showInputDialog(null, "How much would you like to withdraw? If you wish not to withdraw please enter -1 to cancel", "Withdraw", JOptionPane.INFORMATION_MESSAGE);
        if (withdrawInput == null || withdrawInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Withdraw amount is required. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        try {
            withdrawAmount = Double.parseDouble(withdrawInput);
            if (withdrawAmount <= 0 && withdrawAmount != -1) {
                JOptionPane.showMessageDialog(null, "Withdraw amount must be greater than 0. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                
            } else if (withdrawAmount > 10000) {
                JOptionPane.showMessageDialog(null, "Withdraw amount must be less than $10,000 at a time. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                
            } else if (withdrawAmount > account.getBalance()) {
                JOptionPane.showMessageDialog(null, "Insufficient balance. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                
            } else if (withdrawAmount == -1) {
                return null;
            } else {
                validWithdraw = true;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
        // Ask for account code
        while (validAccountCode == false){
        String accountCodeInput = JOptionPane.showInputDialog(null, "Please input your account code to proceed with the withdraw:", "Account Code", JOptionPane.INFORMATION_MESSAGE);
        if (accountCodeInput == null || accountCodeInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Account code is required to proceed with the withdraw. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        if (!accountCodeInput.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(null, "Account code must be 4 digits. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);

        }
    
        try {
            int accountCode = Integer.parseInt(accountCodeInput);
            int retry = 4;
            if (accountCode != account.getLoginCode()) {
                JOptionPane.showMessageDialog(null, "Wrong security code. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                retry--;
                if (retry == 0){
                    JOptionPane.showMessageDialog(null, "Too many failed attempts. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
                    return null; // Exit if too many failed attempts
                }
            } else {
                validAccountCode = true;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a 4-digit number.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
        // Process the withdraw
        if (validWithdraw && validAccountCode && validAccountNumber) {
            JOptionPane.showMessageDialog(null, "Your withdraw is processing, please wait a moment...", "Processing", JOptionPane.INFORMATION_MESSAGE);
            account.setBalance(account.getBalance() - withdrawAmount);
            JOptionPane.showMessageDialog(null, "Your withdraw is successful. Your new balance is: $" + account.getBalance(), "Withdraw Successful", JOptionPane.INFORMATION_MESSAGE);
            return account;
        } else {
            JOptionPane.showMessageDialog(null, "Oh no! Something went wrong. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        
        }
    }


    public Account Transfer(Account account, double amount) {
        double transferAmount = amount;
        boolean validTransfer = false, validAccountNumber = false, validRecipient = false, validAccountCode = false;
    
        // Ask for account number or name
        while (validAccountNumber == false){
        String detail = JOptionPane.showInputDialog(null, "Please enter your account number or name to search for your account:", "Account Search", JOptionPane.INFORMATION_MESSAGE);
        if (detail == null || detail.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Account search is required. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Exit gracefully if no input is provided
        }
    
        // Check if the input is an account number
        if (detail.matches("\\d{6}")) {
            try {
                int accountNumber = Integer.parseInt(detail);
                if (accountNumber == account.getAccountNumber()) {
                    validAccountNumber = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Account number not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid account number format. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Check if the input is a name
        else if (detail.matches("[a-zA-Z]+")) {
            if (detail.equalsIgnoreCase(account.getName())) {
                validAccountNumber = true;
            } else {
                JOptionPane.showMessageDialog(null, "Account name not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid account number or name.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
        // Ask for recipient account number or name
        while (!validRecipient) {
            String transferDetail = JOptionPane.showInputDialog(null, "Please enter the account number or name of the recipient:", "Transfer Detail", JOptionPane.INFORMATION_MESSAGE);
            if (transferDetail == null || transferDetail.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Transfer detail is required. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
                return null; // Exit gracefully if no input is provided
            }
    
            try {
                customer recipientCustomer;
                recipientCustomer = searchCustomer(transferDetail); // Search for customer in the database
                if (recipientCustomer != null) {
                    if (transferDetail.equals(String.valueOf(account.getAccountNumber())) || transferDetail.equalsIgnoreCase(account.getName())) {
                        JOptionPane.showMessageDialog(null, "You cannot transfer to your own account. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        validRecipient = true;
                    }
                } else {
                    int retry = JOptionPane.showConfirmDialog(null, "Recipient not found. Would you like to try again?", "Recipient Not Found", JOptionPane.YES_NO_OPTION);
                    if (retry == JOptionPane.NO_OPTION) {
                        JOptionPane.showMessageDialog(null, "Transfer canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
                        return null; // Exit if the user cancels
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An error occurred while searching for the recipient. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
    
        // Ask for transfer amount
        while (validTransfer == false){
        String transferInput = JOptionPane.showInputDialog(null, "How much would you like to transfer?", "Transfer Amount", JOptionPane.INFORMATION_MESSAGE);
        if (transferInput == null || transferInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Transfer amount is required. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        try {
            transferAmount = Double.parseDouble(transferInput);
            if (transferAmount <= 0|| transferAmount != -1) {
                JOptionPane.showMessageDialog(null, "Transfer amount must be greater than 0. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (transferAmount > account.getBalance()) {
                JOptionPane.showMessageDialog(null, "Insufficient balance. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (transferAmount == -1){
                JOptionPane.showMessageDialog(null, "Transfer canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
                return null; // Exit if user cancels or enters -1
            } else {
                validTransfer = true;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
        // Ask for transfer code
        while (validAccountCode == false){
        int retry = 4;
        String transferCodeInput = JOptionPane.showInputDialog(null, "Please input your transfer code to proceed:", "Transfer Code", JOptionPane.INFORMATION_MESSAGE);
        if (transferCodeInput == null || transferCodeInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Transfer code is required. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Exit gracefully if no input is provided
        }
        if (!transferCodeInput.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(null, "Transfer code must be 4 digits. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        try {
            int transferCode = Integer.parseInt(transferCodeInput);
            if (transferCode == account.getLoginCode()){
                validAccountCode = true;
            }
            if (transferCode != account.getLoginCode()) {
                JOptionPane.showMessageDialog(null, "Wrong transfer code. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                retry--;
                if (retry == 0){
                    JOptionPane.showMessageDialog(null, "Too many failed attempts. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
                    return null; // Exit if too many failed attempts
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a 4-digit number.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        }
        // Process the transfer
        if (validTransfer && validRecipient&&validAccountCode) {
            JOptionPane.showMessageDialog(null, "Your transfer is processing, please wait a moment...", "Processing", JOptionPane.INFORMATION_MESSAGE);
            account.setBalance(account.getBalance() - transferAmount);
            JOptionPane.showMessageDialog(null, "Your transfer is successful. Your new balance is: $" + account.getBalance(), "Transfer Successful", JOptionPane.INFORMATION_MESSAGE);
            return account;
        } else {
            JOptionPane.showMessageDialog(null, "Oh no! Something went wrong. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public Account CheckBalance(Account account) {
        boolean validAccountNumber = false, validAccountCode = false;

        // Ask for account number or name
        String detail = JOptionPane.showInputDialog(null, "Please enter your account number or name to search for your account:", "Account Search", JOptionPane.INFORMATION_MESSAGE);
        if (detail == null || detail.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Account search is required. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
        return null; // Exit gracefully if no input is provided
        }

        // Check if the input is an account number
        if (detail.matches("\\d{6}")) {
            try {
                int accountNumber = Integer.parseInt(detail);
                if (accountNumber == account.getAccountNumber()) {
                validAccountNumber = true;
            } else {
                JOptionPane.showMessageDialog(null, "Account number not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                return null; // Exit if account number is invalid
            }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid account number format. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }   
        }

        // Check if the input is a name
        else if (detail.matches("[a-zA-Z]+")) {
            if (detail.equalsIgnoreCase(account.getName())) {
            validAccountNumber = true;
        } else {
            JOptionPane.showMessageDialog(null, "Account name not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Exit if account name is invalid
        }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid account number or name.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Ask for account code
        String accountCodeInput = JOptionPane.showInputDialog(null, "Please input your secret code to proceed with the balance check:", "Account Code", JOptionPane.INFORMATION_MESSAGE);
        if (accountCodeInput == null || accountCodeInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Account code is required to proceed with the balance check. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Exit gracefully if no input is provided
        }
        if (!accountCodeInput.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(null, "Account code must be 4 digits. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        try {
            int accountCode = Integer.parseInt(accountCodeInput);
            if (accountCode != account.getLoginCode()) {
                JOptionPane.showMessageDialog(null, "Wrong security code. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            } else {
                validAccountCode = true;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a 4-digit number.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Process the balance check
        if (validAccountNumber && validAccountCode) {
            JOptionPane.showMessageDialog(null, "Your balance is: $" + account.getBalance(), "Balance Check", JOptionPane.INFORMATION_MESSAGE);
            return account;
        } else {
            JOptionPane.showMessageDialog(null, "Oh no! Something went wrong. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public customer RequestUserDetails(String string){
        Account account = new Account();
        boolean validAccountNumber = false, validAccountCode = false;
        String detail = JOptionPane.showInputDialog(null, "Please enter your account number or name to search for your account:", "Account Search", JOptionPane.INFORMATION_MESSAGE);
        if (detail == null || detail.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Account search is required. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Exit gracefully if no input is provided
        }
        if (detail.matches("\\d{6}")) {
            try {
                int accountNumber = Integer.parseInt(detail);
                if (accountNumber == account.getAccountNumber()) {
                    validAccountNumber = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Account number not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    return null; // Exit if account number is invalid
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid account number format. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else if (detail.matches("[a-zA-Z]+")) {
            if (detail.equalsIgnoreCase(account.getName())) {
                validAccountNumber = true;
            } else {
                JOptionPane.showMessageDialog(null, "Account name not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                return null; // Exit if account name is invalid
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid account number or name.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        String accountCodeInput = JOptionPane.showInputDialog(null, "Please input your secret code to proceed with the user details request:", "Account Code", JOptionPane.INFORMATION_MESSAGE);
        if (accountCodeInput == null || accountCodeInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Account code is required to proceed with the user details request. Operation canceled.", "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Exit gracefully if no input is provided
        }
        if (!accountCodeInput.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(null, "Account code must be 4 digits. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        try {
            int accountCode = Integer.parseInt(accountCodeInput);
            if (accountCode != account.getLoginCode()) {
                JOptionPane.showMessageDialog(null, "Wrong security code. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            } else {
                validAccountCode = true;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a 4-digit number.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (validAccountCode&&validAccountNumber){
            customer customer = new customer();
            customer = searchCustomer(detail);
            if (customer != null) {
                String userDetails = "Name: " + customer.getCustomerName() + "\n" +
                                     "Phone Number: " + customer.getPhoneNumber() + "\n" +
                                     "Address: " + customer.getAddress() + "\n" +
                                     "Email: " + customer.getEmail() + "\n" +
                                     "Date of Birth: " + customer.getCustomerDoB() + "\n" +
                                     "Account Number: " + account.getAccountNumber() + "\n";
                JOptionPane.showMessageDialog(null, userDetails, "User Details", JOptionPane.INFORMATION_MESSAGE);
                return customer;
            } else {
                JOptionPane.showMessageDialog(null, "User details not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

        }
        return null;
    }

    public boolean saveCustomer(customer customer) {
    File file = new File("user_data.csv");
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) { // Append mode
        // Write customer details
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

        // Write accounts associated with the customer
        for (Account account : customer.getAccounts()) {
            writer.write("Account Number: " + account.getAccountNumber());
            writer.newLine();
            writer.write("Account Type: " + account.getAccountType());
            writer.newLine();
            writer.write("Balance: " + account.getBalance());
            writer.newLine();
        }

        // Add a separator for readability
        writer.write("-------------------------");
        writer.newLine();

        writer.flush(); // Ensure all data is written to the file
        return true; // Indicate success
    } catch (IOException e) {
        System.out.println("Error saving customer data: " + e.getMessage());
        return false; // Indicate failure
    }
}
}
