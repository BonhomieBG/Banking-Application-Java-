import java.time.LocalDate;
import javax.swing.JOptionPane;

public class AccountCreator {
    public static checkingAccount createCheckingAccount(customer customer) {
        double depositAmount = 0;
        boolean validAccountNumber = false, validDeposit = false, validAccountCode = false;
        int accountCode = 0, accountNumber = 0;

        // Prompt for account number
        String accountNumberInput = JOptionPane.showInputDialog(null, """
                Do you have any preferred account number in mind? The fee for a personalized account number is $25.00.
                To get a new number free of cost, you can just leave the field blank or say "no".
                Please enter your preferred 6-digit number:
                """);
        if (accountNumberInput == null) {
            System.exit(0); // Exit if "X" is clicked
        } else if (accountNumberInput.isEmpty() || accountNumberInput.equalsIgnoreCase("no")) {
            accountNumber = 0; // Default account number
            validAccountNumber = false; // No deduction needed
        } else {
            try {
                if (accountNumberInput.matches("\\d{6}")) {
                    accountNumber = Integer.parseInt(accountNumberInput);
                    validAccountNumber = true;

                    // Deduct $25.00 for personalized account number if the user is an existing
                    // customer
                    if (customer != null && !customer.getAccounts().isEmpty()) {
                        Account accountToDeduct = customer.getAccountByNumberOrName("Checking"); // Deduct from checking
                                                                                                 // account
                        if (accountToDeduct != null) {
                            double currentBalance = accountToDeduct.getBalance();
                            if (currentBalance >= 25.00) {
                                accountToDeduct.setBalance(currentBalance - 25.00); // Deduct $25.00
                                Utility.saveCustomer(customer); // Save updated balance to user_data.csv
                                Utility.printReceiptToFile(accountToDeduct, 25.00, Utility.TransactionType.WITHDRAW); // Print
                                                                                                                      // receipt
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Insufficient funds for personalized account number.", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                return null;
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No valid account found to deduct funds.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return null;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Account number must be 6 digits.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a 6-digit number.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        // Prompt for initial deposit
        String initialDeposit = JOptionPane.showInputDialog(null,
                "How much would you like to deposit? Minimum deposit for first-time account creation is $50.00.");
        if (initialDeposit == null) {
            System.exit(0); // Exit if "X" is clicked
        }
        try {
            depositAmount = Double.parseDouble(initialDeposit);
            if (depositAmount < 50.00) {
                JOptionPane.showMessageDialog(null, "Minimum deposit is $50.00.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                validDeposit = true;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter the amount in numeric value.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Prompt for account code
        String accountCodeInput = JOptionPane.showInputDialog(null, """
                Please input a 4-digit account code below.
                This code is used to log in to your account.
                Account code:
                """);
        if (accountCodeInput == null) {
            System.exit(0); // Exit if "X" is clicked
        } else if (!accountCodeInput.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(null, "Account code must be 4 digits.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                accountCode = Integer.parseInt(accountCodeInput);
                validAccountCode = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a 4-digit number.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        // Final validation and account creation
        if (validAccountNumber && validDeposit && validAccountCode) {
            JOptionPane.showMessageDialog(null, "Your account is processing, please wait a moment...", "Processing",
                    JOptionPane.INFORMATION_MESSAGE);
            return new checkingAccount(customer, depositAmount, accountNumber, accountCode);
        } else if (!validAccountNumber && validDeposit && validAccountCode) {
            JOptionPane.showMessageDialog(null, "Your account is processing, please wait a moment...", "Processing",
                    JOptionPane.INFORMATION_MESSAGE);
            return new checkingAccount(customer, depositAmount, accountCode);
        } else {
            JOptionPane.showMessageDialog(null, "Account creation failed. Please try again.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null; // Return null if account creation fails
        }
    }

    public static savingAccount createSavingAccount(customer customer) {
        double depositAmount = 0;
        boolean validAccountNumber = false, validDeposit = false, validAccountCode = false;
        int accountCode = 0, accountNumber = 0;

        // Prompt for account number
        String accountNumberInput = JOptionPane.showInputDialog(null, """
                Do you have any preferred account number in mind? The fee for a personalized account number is $25.00.
                To get a new number free of cost, you can just leave the field blank or say "no".
                Please enter your preferred 6-digit number:
                """);
        if (accountNumberInput == null) {
            System.exit(0); // Exit if "X" is clicked
        } else if (accountNumberInput.isEmpty() || accountNumberInput.equalsIgnoreCase("no")) {
            accountNumber = 0; // Default account number
            validAccountNumber = false; // No deduction needed
        } else {
            try {
                if (accountNumberInput.matches("\\d{6}")) {
                    accountNumber = Integer.parseInt(accountNumberInput);
                    validAccountNumber = true;

                    // Deduct $25.00 for personalized account number if the user is an existing
                    // customer
                    if (customer != null && !customer.getAccounts().isEmpty()) {
                        Account accountToDeduct = customer.getAccountByNumberOrName("Saving"); // Deduct from saving
                                                                                               // account
                        if (accountToDeduct != null) {
                            double currentBalance = accountToDeduct.getBalance();
                            if (currentBalance >= 25.00) {
                                accountToDeduct.setBalance(currentBalance - 25.00); // Deduct $25.00
                                Utility.saveCustomer(customer); // Save updated balance to user_data.csv
                                Utility.printReceiptToFile(accountToDeduct, 25.00, Utility.TransactionType.WITHDRAW); // Print
                                                                                                                      // receipt
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Insufficient funds for personalized account number.", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                return null;
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No valid account found to deduct funds.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return null;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Account number must be 6 digits.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a 6-digit number.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        // Prompt for initial deposit
        String initialDeposit = JOptionPane.showInputDialog(null,
                "How much would you like to deposit? Minimum deposit for first-time account creation is $50.00.");
        if (initialDeposit == null) {
            System.exit(0); // Exit if "X" is clicked
        }
        try {
            depositAmount = Double.parseDouble(initialDeposit);
            if (depositAmount < 50.00) {
                JOptionPane.showMessageDialog(null, "Minimum deposit is $50.00.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                validDeposit = true;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter the amount in numeric value.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Prompt for account code
        String accountCodeInput = JOptionPane.showInputDialog(null, """
                Please input a 4-digit account code below.
                This code is used to log in to your account.
                Account code:
                """);
        if (accountCodeInput == null) {
            System.exit(0); // Exit if "X" is clicked
        } else if (!accountCodeInput.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(null, "Account code must be 4 digits.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                accountCode = Integer.parseInt(accountCodeInput);
                validAccountCode = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a 4-digit number.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        // Final validation and account creation
        if (validAccountNumber && validDeposit && validAccountCode) {
            JOptionPane.showMessageDialog(null, "Your account is processing, please wait a moment...", "Processing",
                    JOptionPane.INFORMATION_MESSAGE);
            return new savingAccount(customer, depositAmount, accountNumber, accountCode);
        } else if (!validAccountNumber && validDeposit && validAccountCode) {
            JOptionPane.showMessageDialog(null, "Your account is processing, please wait a moment...", "Processing",
                    JOptionPane.INFORMATION_MESSAGE);
            return new savingAccount(customer, depositAmount, accountCode);
        } else {
            JOptionPane.showMessageDialog(null, "Account creation failed. Please try again.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null; // Return null if account creation fails
        }
    }

    public static customer createNewUserAccount() {
        String[] Agreement = { "I AGREE", "I DO NOT AGREE" };
        LocalDate Date = null;

        // Prompt for account name
        String AccountName;
        while (true) {
            AccountName = JOptionPane.showInputDialog(null, "Please enter your full name: ", "Account Name",
                    JOptionPane.INFORMATION_MESSAGE);
            if (AccountName == null) { // Handle "X" button
                JOptionPane.showMessageDialog(null, "The program will now close.", "Exit",
                        JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            } else if (AccountName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Name cannot be blank. Please enter your full name.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                break; // Valid input
            }
        }

        // Prompt for phone number
        String AccountPhone;
        while (true) {
            AccountPhone = JOptionPane.showInputDialog(null, "Please enter your phone number: ", "Account Phone",
                    JOptionPane.INFORMATION_MESSAGE);
            if (AccountPhone == null) { // Handle "X" button
                JOptionPane.showMessageDialog(null, "The program will now close.", "Exit",
                        JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            } else if (AccountPhone.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Phone number cannot be blank. Please enter your phone number.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                break; // Valid input
            }
        }

        // Prompt for address
        String AccountAddress;
        while (true) {
            AccountAddress = JOptionPane.showInputDialog(null, "Please enter your address: ", "Account Address",
                    JOptionPane.INFORMATION_MESSAGE);
            if (AccountAddress == null) { // Handle "X" button
                JOptionPane.showMessageDialog(null, "The program will now close.", "Exit",
                        JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            } else if (AccountAddress.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Address cannot be blank. Please enter your address.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                break; // Valid input
            }
        }

        // Prompt for email
        String AccountEmail;
        while (true) {
            AccountEmail = JOptionPane.showInputDialog(null, "Please enter your email: ", "Account Email",
                    JOptionPane.INFORMATION_MESSAGE);
            if (AccountEmail == null) { // Handle "X" button
                JOptionPane.showMessageDialog(null, "The program will now close.", "Exit",
                        JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            } else if (AccountEmail.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Email cannot be blank. Please enter your email.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                break; // Valid input
            }
        }

        // Prompt for date of birth
        String AccountDoB;
        while (true) {
            AccountDoB = JOptionPane.showInputDialog(null, "Please enter your date of birth (YYYY-MM-DD): ",
                    "Account Date of Birth", JOptionPane.INFORMATION_MESSAGE);
            if (AccountDoB == null) { // Handle "X" button
                JOptionPane.showMessageDialog(null, "The program will now close.", "Exit",
                        JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            } else if (AccountDoB.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Date of birth cannot be blank. Please enter your date of birth.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Date = LocalDate.parse(AccountDoB); // Parse the date of birth string to LocalDate
                    break; // Valid input
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Invalid date format. Please enter in YYYY-MM-DD format.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        // Terms of Service Agreement
        int ToSAgreement = JOptionPane.showOptionDialog(null,
                """
                        By clicking "I agree", you agree to our Terms of Service and Privacy Policy as stated below.
                        You, the user, agree to follow the terms and conditions set forth by the bank. US, We, The Bank, and The Bank of Cambodia reserves the right to change the terms and conditions at any time without notice.
                        You are allowed to open multiple accounts as long as you use the account at least once within 10 years.
                        The bank reserves the right to close any account that has been inactive for more than 10 years. Any funds in the account will be forfeited to the bank.
                        The bank is not responsible for any loss of funds due to inactivity or closure of the account. Any disputes will be resolved in accordance with the laws of the jurisdiction in which the bank is located.
                        You are free to withdraw, deposit, and transfer money to any other account at any time. The bank is not responsible for any loss of funds due to unauthorized access to your account.
                        This Program is brought to you at no additional charge by The Developer "Tekhour Khov" and "The Bank of Cambodia".
                        """,
                "Terms of Service", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, Agreement,
                Agreement[0]);
        if (ToSAgreement == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Thank you for being a valuable customer of the Bank of Cambodia.");
            return new customer(AccountName, AccountPhone, AccountEmail, AccountAddress, Date);
        } else {
            JOptionPane.showMessageDialog(null, "You must agree to the Terms of Service to create an account.",
                    "Agreement", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}