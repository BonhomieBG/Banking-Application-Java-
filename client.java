// THIS FILE IS STILL UNDER DEVELOPMENT, USE AT YOUR OWN RISK FOR BUGS AND UNSTABILITY.

/*
   * Filename:        client.java
   * Author:          BONHOMIE BG
   * Date Created:    4/20/2025
   * Description:     Bank Management System that allows user to create an account, deposit, withdraw, transfer money, check balance and request user details.
   *                The system is designed to be user-friendly and easy to use. This system is designed to be used by both new and existing customers.
   *                The user
   * Predicted Test Cases: 
   * 
*/

// Main Implimentation of the Project
import javax.swing.JOptionPane;

enum accountType {CHECKING, SAVING};
enum transactionType {DEPOSIT, WITHDRAW, TRANFER}
enum aggreement {YES, NO};

public class client {
    public static void main(String[] args) {
        // Initialize objects
        boolean activeSession = true;
        MainMenu:
        while (activeSession){
        oldUser oldUser = new oldUser();
        bank bank = new bank("Republic bank of Cambodia", "855-763399966", "128 Packer St, Salum District, Cambodia","rpb.com.kh");
        checkingAccount checkingAccount = null; //use polymorphism to create account object
        savingAccount savingAccount = null; boolean existingUser = false;
        customer newCustomer = null; customer customer = new customer();
        Account newAccount = null;
        Utility utility = new Utility();
        AccountCreator accountCreator = new AccountCreator();

        String HelpMessage = "Welcome to "+ bank.getName() + 
                        ". Get in touch with us at "+bank.getAddress()+ " or via our email "+bank.getEmail()+ " Or our phone number, "+bank.getPhoneNumber()+"\n";
        String Notice = "You can retry up to 4 times";
        String [] startup = {"Existing customer", "New customer", "Exit"};
        String [] options = {"Create an account", "Deposit", "Withdraw", "Transfer", "Check balance", "Request user details", "Help", "Exit"};

        // Welcome messages
        int choice1 = JOptionPane.showOptionDialog(null, "Are you an existing customer?", "Welcome", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, startup, startup[0]);
        switch(choice1) {
            case 0:
            // Existing customer
            String input = JOptionPane.showInputDialog(null, "Please enter your account number or name to search for your account:", "Account Search", JOptionPane.INFORMATION_MESSAGE);
            customer = utility.searchCustomer(input); //search for customer in the file
            if (customer != null) {
                JOptionPane.showMessageDialog(null, "Welcome back! "+customer.getCustomerName(), "Welcome", JOptionPane.INFORMATION_MESSAGE);
                existingUser = true;
            } else {
                JOptionPane.showMessageDialog(null, "Customer not found, The program will now exit.");
                System.exit(0);
            }
            case 1:
            // New customer
            break;
            case 2:
            break MainMenu;
            default:
            break MainMenu;
        }

        int choice2 = JOptionPane.showOptionDialog(null, "What would you like to do?", "Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        switch(choice2){
            case 0: // Create an account
                if (existingUser){
                    int accountTypeInput = JOptionPane.showOptionDialog(null, "What type of account would you like to create?", "Account Type", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, accountType.values(), accountType.CHECKING);
                        if (accountTypeInput == 0) { // checking account existing user
                            newAccount=AccountCreator.createCheckingAccount(customer);
                        } else{
                            newAccount=AccountCreator.createSavingAccount(customer);
                        }
                    }
                if (!existingUser){
                    int creationOfAccount = JOptionPane.showConfirmDialog(null, "Would you like to register as our customer?", "Registration", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (creationOfAccount == JOptionPane.YES_OPTION) { // create an account
                        newCustomer=AccountCreator.createNewUserAccount();
                        JOptionPane.showMessageDialog(null, "Thank you for registering with us. We are happy to serve you here. Let get you setup with your account. Cancel anytime during the registeration by simply close the app.", "Registration", JOptionPane.INFORMATION_MESSAGE);
                         
                        // Ask for Account type
                        int accountTypeInput = JOptionPane.showOptionDialog(null, "What type of account would you like to create?", "Account Type", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, accountType.values(), accountType.CHECKING);
                        if (accountTypeInput == 0) { // checking account
                            newAccount=AccountCreator.createCheckingAccount(customer);
                        } else {
                            newAccount=AccountCreator.createSavingAccount(customer);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Thank you for using our service. Have a nice day!", "Goodbye", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                break;
            case 1: // Deposit
                
                String DepositInfo = JOptionPane.showInputDialog(null, "Please enter your account number or name to deposit money:", "Deposit", JOptionPane.INFORMATION_MESSAGE);
                String depositAmount = JOptionPane.showInputDialog(null, "Enter the amount you would like to deposit in USD:", "Deposit", JOptionPane.INFORMATION_MESSAGE);

            case 5: // Request Detail
                String DetailInfo = JOptionPane.showInputDialog(null, "Please enter your account number or name to request user details:", "Request User Details", JOptionPane.INFORMATION_MESSAGE);
                customer RequestDetail;
                RequestDetail = utility.RequestUserDetails(DetailInfo);
            break;
            case 6: // Help
                JOptionPane.showMessageDialog(null, HelpMessage, "Help", JOptionPane.INFORMATION_MESSAGE);
            break;
            case 7: // Exit
            System.exit (0);
            break;
            } 
        // Save the customer information to the file
        utility.saveCustomer(newCustomer); // save customer to the file
        } 
    }
}
