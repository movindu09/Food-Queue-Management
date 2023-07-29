package com.example.foodqueuemanagementsystemmaster;

import java.util.ArrayList;
import java.util.List;

public class FoodQueue {
    // Instance variables
    private int capacity;
    private List<Customer> customers;

    // Constructor
    public FoodQueue(int capacity) {
        this.capacity = capacity;
        this.customers = new ArrayList<>();
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return customers.isEmpty();
    }

    // Check if the queue is full
    public boolean isFull() {
        return customers.size() >= capacity;
    }

    // Get the current length of the queue
    public int getLength() {
        return customers.size();
    }

    // Get the list of customers in the queue
    public List<Customer> getCustomers() {
        return customers;
    }

    // Add a customer to the queue
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    // Remove a customer from the queue at the specified index
    public Customer removeCustomer(int index) {
        if (index >= 0 && index < customers.size()) {
            return customers.remove(index);
        }
        return null;
    }

    // Serve the customer at the front of the queue
    public Customer serveCustomer() {
        if (!isEmpty()) {
            return customers.remove(0);
        }
        return null;
    }

    // Print the names of customers in the queue
    public void printQueue() {
        for (Customer customer : customers) {
            System.out.println(customer.getFullName());
        }
    }

    // Print the status of the queue (O for occupied, X for empty) up to the capacity
    public void printQueueWithStatus() {
        for (int i = 0; i < capacity; i++) {
            if (i < customers.size()) {
                System.out.print("O ");
            } else {
                System.out.print("X ");
            }
        }
        System.out.println();
    }

    // Calculate and return the total income from serving customers in the queue
    public int getTotalIncome() {
        int totalIncome = 0;
        for (Customer customer : customers) {
            int burgersServed = Math.min(customer.getBurgersRequired(), 5);
            totalIncome += burgersServed * 650;
        }
        return totalIncome;
    }

    // Get a string representation of the queue data
    public String getQueueDataAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            sb.append(i + 1).append(". ")
                    .append(customer.getFullName()).append(", ")
                    .append(customer.getBurgersRequired()).append(" burgers\n");
        }
        return sb.toString();
    }

    // Load queue data from a string representation
    public void loadQueueDataFromString(String data) {
        customers.clear();

        String[] customerData = data.split("\n");
        for (String customerString : customerData) {
            String[] parts = customerString.split(", ");
            String fullName = parts[0];
            int burgersRequired = Integer.parseInt(parts[1].replaceAll("\\D", ""));

            Customer customer = new Customer(fullName, burgersRequired);
            customers.add(customer);
        }
    }
}
