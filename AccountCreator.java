import java.time.LocalDate;
import javax.swing.JOptionPane;

public class AccountCreator{
    public static checkingAccount createCheckingAccount(customer customer) {
        double depositAmount = 0; boolean validAccountNumber = false; boolean validDeposit = false; 
        int accountCode = 0 , accountNumber = 0; boolean validAccountCode = false;

        String accountNumberInput = JOptionPane.showInputDialog(null, """
                                            
                                                                                      Do you have any prefered account number in mind?, the fee for personalized account number is $25.00
                                                                                       To get a new number free of cost, you can just leaving the field blank or say no
                                                                                       Please enter your prefered 6 digit numbers: """);
        if (accountNumberInput == null || accountNumberInput.equalsIgnoreCase("no")){
            accountNumber = 0; // Account number
            validAccountNumber = false;
        } else {
            try {
                if (accountNumberInput.matches("\\d{6}")){
                    accountNumber = Integer.parseInt(accountNumberInput);
                    if ((accountNumber < 100000 || accountNumber > 999999)){
                        JOptionPane.showMessageDialog(null, "Account number must be 6 digits", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        validAccountNumber = true;
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a 6 degit number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        String InitialDeposit = JOptionPane.showInputDialog(null, "How much would you like to deposit? Minimum deposit for first time of account creation is $50.00");
        if (InitialDeposit == null){
            JOptionPane.showMessageDialog(null, "Minium deposit is $50.00", "Error", JOptionPane.ERROR_MESSAGE);
            } try {
                depositAmount = Double.parseDouble(InitialDeposit);
                    if (depositAmount < 50.00) JOptionPane.showMessageDialog(null, "Minium deposit is $50.00", "Error", JOptionPane.ERROR_MESSAGE);
                    else {
                    validDeposit = true;    
                }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter the amount in numberic value", "Error", JOptionPane.ERROR_MESSAGE);
                } 
        String accountcodeInput = JOptionPane.showInputDialog(null, """
                                                                                        Please input 4 digits account code below.
                                                                                        This code is used to login into your account.
                                                                                        Account code : 
                                                                                            """);
        if (accountcodeInput == null || accountcodeInput.equalsIgnoreCase("no")){
            JOptionPane.showMessageDialog(null, "Account code is required to login into your account", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!accountcodeInput.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(null, "Account code must be 4 digits", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    accountCode = Integer.parseInt(accountcodeInput);
                    validAccountCode = true;
                } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a 4 digit number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        // Done of information requesting for checking account
        if (validAccountNumber == true && validDeposit == true && validAccountCode == true){
            JOptionPane.showMessageDialog(null, "Your account is processing, please wait a moment...", "Processing", JOptionPane.INFORMATION_MESSAGE);
            return new checkingAccount(customer, depositAmount, accountNumber, accountCode);
        } else if(validAccountNumber == false && validDeposit == true && validAccountCode == true){
            JOptionPane.showMessageDialog(null, "Your account is processing, please wait a moment...", "Processing", JOptionPane.INFORMATION_MESSAGE);
            return new checkingAccount(customer, depositAmount, accountCode);
        } else {
            JOptionPane.showMessageDialog(null, "Account creation failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Return null if account creation fails
        }
    }
    public static savingAccount createSavingAccount(customer customer) {
        double depositAmount = 0; boolean validAccountNumber = false; boolean validDeposit = false; 
        int accountCode = 0 , accountNumber = 0; boolean validAccountCode = false;

        String accountNumberInput = JOptionPane.showInputDialog(null, """
                                            
                                                                                      Do you have any prefered account number in mind?, the fee for personalized account number is $25.00
                                                                                       To get a new number free of cost, you can just leaving the field blank or say no
                                                                                       Please enter your prefered 6 digit numbers: """);
        if (accountNumberInput == null || accountNumberInput.equalsIgnoreCase("no")){
            accountNumber = 0; // Account number
            validAccountNumber = false;
        } else {
            try {
                if (accountNumberInput.matches("\\d{6}")){
                    accountNumber = Integer.parseInt(accountNumberInput);
                    validAccountNumber = true;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a 6 degit number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        String InitialDeposit = JOptionPane.showInputDialog(null, "How much would you like to deposit? Minimum deposit for first time of account creation is $50.00");
        if (InitialDeposit == null){
            JOptionPane.showMessageDialog(null, "Minium deposit is $50.00", "Error", JOptionPane.ERROR_MESSAGE);
            } try {
                depositAmount = Double.parseDouble(InitialDeposit);
                    if (depositAmount < 50.00) JOptionPane.showMessageDialog(null, "Minium deposit is $50.00", "Error", JOptionPane.ERROR_MESSAGE);
                    else {
                    validDeposit = true;    
                }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter the amount in numberic value", "Error", JOptionPane.ERROR_MESSAGE);
                } 
        String accountcodeInput = JOptionPane.showInputDialog(null, """
                                                                                        Please input 4 digits account code below.
                                                                                        This code is used to login into your account.
                                                                                        Account code : 
                                                                                            """);
        if (accountcodeInput == null || accountcodeInput.equalsIgnoreCase("no")){
            JOptionPane.showMessageDialog(null, "Account code is required to login into your account", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!accountcodeInput.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(null, "Account code must be 4 digits", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    accountCode = Integer.parseInt(accountcodeInput);
                    validAccountCode = true;
                } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a 4 digit number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        // Done of information requesting for checking account
        if (validAccountNumber == true && validDeposit == true && validAccountCode == true){
            JOptionPane.showMessageDialog(null, "Your account is processing, please wait a moment...", "Processing", JOptionPane.INFORMATION_MESSAGE);
            return new savingAccount(customer, depositAmount, accountNumber, accountCode);
        } else if(validAccountNumber == false && validDeposit == true && validAccountCode == true){
            JOptionPane.showMessageDialog(null, "Your account is processing, please wait a moment...", "Processing", JOptionPane.INFORMATION_MESSAGE);
            return new savingAccount(customer, depositAmount, accountCode);
        } else {
            JOptionPane.showMessageDialog(null, "Account creation failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Return null if account creation fails
        }
    }

    public static customer createNewUserAccount(){
        String Agreement[] = {"I AGREE", "I DO NOT AGREE"}; LocalDate Date = null;
        String AccountName = JOptionPane.showInputDialog(null, "Please enter your full name: ", "Account Name", JOptionPane.INFORMATION_MESSAGE);
        String AccountPhone = JOptionPane.showInputDialog(null, "Please enter your phone number: ", "Account Phone", JOptionPane.INFORMATION_MESSAGE);
        String AccountAddress = JOptionPane.showInputDialog(null, "Please enter your address: ", "Account Address", JOptionPane.INFORMATION_MESSAGE);
        String AccountEmail = JOptionPane.showInputDialog(null, "Please enter your email: ", "Account Email", JOptionPane.INFORMATION_MESSAGE);
        String AccountDoB = JOptionPane.showInputDialog(null, "Please enter your date of birth (YYYY-MM-DD): ", "Account Date of Birth", JOptionPane.INFORMATION_MESSAGE);
        int ToSAgreement = JOptionPane.showOptionDialog(null, """
                By clicking "I agree", you agree to our Terms of Service and Privacy Policy as stated below.
                You, the user, agree to follow the terms and conditions set forth by the bank. US, We, The Bank, and The Bank of Cambodia resurves the right to change the terms and conditions at any time without notice.
                You are allow to open multiple account as long as you are using the account at lease one times within 10 years.
                The bank reserves the right to close any account that has been inactive for more than 10 years. Any funds in the account will be forfeited to the bank.
                The bank is not responsible for any loss of funds due to inactivity or closure of the account. Any disputes will be resolved in accordance with the laws of the jurisdiction in which the bank is located.
                You are free to withdrawn, deposit, and transfer money to any other account at any time. The bank is not responsible for any loss of funds due to unauthorized access to your account.
                This Program bring to you at no additional charge by The Developer "Tekhour Khov" and "The Bank of Cambodia".
                """, "Terms of Service", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, Agreement, Agreement[0]);
        if (ToSAgreement == JOptionPane.YES_OPTION){
            JOptionPane.showMessageDialog(null, "Thank you for being the valuable customer of the Bank of Cambodia.");
            Date = LocalDate.parse(AccountDoB); // Parse the date of birth string to LocalDate
            return new customer(AccountName, AccountPhone, AccountEmail, AccountAddress, Date);
        }
        else {
            return null;
        }
    }
}