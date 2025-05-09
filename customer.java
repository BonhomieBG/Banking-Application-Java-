
// Copyright (c) 2025 Tekhour Khov
// All rights reserved.
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation. 
//
// Class for customer information
import java.time.LocalDate;
import java.util.ArrayList;

public class customer {
    private String customerName, address, phoneNumber, email;
    private LocalDate dateOfBirth;
    private ArrayList<Account> accounts;

    public customer() {
        accounts = new ArrayList<>();
    }

    public customer(String name, String phone, String LocalAddress, String emailAddress, LocalDate dob) {
        this();
        customerName = name;
        phoneNumber = phone;
        address = LocalAddress;
        email = emailAddress;
        dateOfBirth = dob;
    }

    // Add a new account to the customer
    public void addAccount(Account account) {
        accounts.add(account);
    }

    // Get all accounts for this customer
    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    // Get a specific account by account number or name
    public Account getAccountByNumberOrName(String detail) {
        for (Account account : accounts) {
            if (String.valueOf(account.getAccountNumber()).equals(detail)
                    || account.getAccountType().equalsIgnoreCase(detail)) {
                return account;
            }
        }
        return null; // Return null if no matching account is found
    }

    public Account getAccountByNumber(int accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null; // Return null if no matching account is found
    }

    // Getter and setter for customer information
    public void setCustomerName(String name) {
        customerName = name;
    }

    public void setPhoneNumber(String phone) {
        phoneNumber = phone;
    }

    public void setAddress(String localAddress) {
        address = localAddress;
    }

    public void setEmail(String emailAddress) {
        email = emailAddress;
    }

    public void setCustomerDoB(LocalDate dob) {
        dateOfBirth = dob;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getCustomerDoB() {
        return dateOfBirth;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer Name: ").append(customerName).append("\n")
                .append("Address: ").append(address).append("\n")
                .append("Phone Number: ").append(phoneNumber).append("\n")
                .append("Email: ").append(email).append("\n")
                .append("Date of Birth: ").append(dateOfBirth).append("\n")
                .append("Accounts:\n");
        for (Account account : accounts) {
            sb.append(account.toString()).append("\n");
        }
        return sb.toString();
    }
}
