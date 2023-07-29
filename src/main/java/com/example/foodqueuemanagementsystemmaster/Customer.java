package com.example.foodqueuemanagementsystemmaster;

public class Customer implements Comparable<Customer> {
    // Instance variables
    private String firstName;
    private String lastName;
    private final int burgerCount;

    // Constructor with first name, last name, and burger count
    public Customer(String firstName, String lastName, int burgerCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.burgerCount = burgerCount;
    }

    // Constructor with full name, which splits it into first name and last name
    public Customer(String fullName, int burgerCount) {
        String[] parts = fullName.split(" ");
        if (parts.length >= 2) {
            this.firstName = parts[0];
            this.lastName = parts[1];
        }
        this.burgerCount = burgerCount;
    }

    // Getter method for burger count
    public int getBurgersRequired() {
        return burgerCount;
    }

    // Getter method for first name
    public String getFirstName() {
        return firstName;
    }

    // Getter method for last name
    public String getLastName() {
        return lastName;
    }

    // Getter method for burger count
    public int getBurgerCount() {
        return burgerCount;
    }

    // Getter method for full name
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Implementation of the compareTo method from the Comparable interface
    // This method compares customers based on their full names in a case-insensitive manner
    @Override
    public int compareTo(Customer other) {
        return this.getFullName().compareToIgnoreCase(other.getFullName());
    }
}


