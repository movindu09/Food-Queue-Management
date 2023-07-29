package com.example.foodqueuemanagementsystemmaster;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Cashier extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static int burgerStock = 50;
    // stock variable to keep track of stock

    public static FoodQueue[] queues = new FoodQueue[3];

    static Scanner userIn = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {

        queues[0] = new FoodQueue(2);
        queues[1] = new FoodQueue(3);
        queues[2] = new FoodQueue(5);


        boolean menuLoop = true;
        while (menuLoop) // Loops the menu
        {
            try {
                System.out.println("""
                        100 or VFQ: View all Queues.
                        101 or VEQ: View all Empty Queues.
                        102 or ACQ: Add customer to a Queue.
                        103 or RCQ: Remove a customer from a Queue.
                        104 or PCQ: Remove a served customer.
                        105 or VCS: View Customers Sorted in alphabetical order.
                        106 or SPD: Store Program Data into file.
                        107 or LPD: Load Program Data from file.
                        108 or STK: View Remaining burgers Stock.
                        109 or AFS: Add burgers to Stock.
                        110 or IFQ: Print Income of each Queue.
                        111 or GUI: View the GUI.
                        999 or EXT: Exit the Program.
                        """);

                System.out.print("Please select an option: ");
                String menu = userIn.next().toUpperCase();
                printDollars();
                switch (menu) {
                    case "100", "VFQ" -> VFQ();
                    case "101", "VEQ" -> VEQ();
                    case "102", "ACQ" -> ACQ();
                    case "103", "RCQ" -> RCQ();
                    case "104", "PCQ" -> PCQ();
                    case "105", "VCS" -> VCS();
                    case "106", "SPD" -> SPD();
                    case "107", "LPD" -> LPD();
                    case "108", "STK" -> STK();
                    case "109", "AFS" -> AFS();
                    case "110", "IFQ" -> IFQ();
                    case "111", "GUI" -> GUI();
                    case "999", "EXT" -> {
                        System.out.println("Exiting the program....");
                        menuLoop = false;
                    }
                    default -> System.out.println("Please enter a valid input."); // used for input validation
                }
            } catch (InputMismatchException ex) {
                printDollars();
                System.out.println("Invalid input!");
            }
            printDollars();
            Thread.sleep(1000);
        }
    }

    // ---------------------------- other Methods -----------------------------------------------------------------------------------------------------------------

    static void printDollars() // Prints a line with dollar sign to divide the outputs(used for merely aesthetic purposes)
    {
        System.out.println("\n" + "$".repeat(40) + "\n");
    }


    static void GUI()
    {
        System.out.println("GUI Loaded Successfully!");
        myLaunch(GUIApplication.class);
    }

    static void VFQ() // View All Queues
    {
        System.out.println("*****************");
        System.out.println("* Queues *");
        System.out.println("*****************");
        for (int i = 0; i < queues.length; i++) {
            System.out.println("Queue " + (i + 1) + ":");
            queues[i].printQueueWithStatus();
        }
    }

    static void VEQ() {
        System.out.println("*****************");
        System.out.println("* Empty Queues *");
        System.out.println("*****************");
        for (int i = 0; i < queues.length; i++) {
            System.out.print("Queue " + (i + 1) + ": ");
            if (queues[i].isEmpty()) {
                System.out.println("Empty");
            } else {
                System.out.println("Not Empty");
            }
        }
    }

    static void ACQ() {
        System.out.println("*****************");
        System.out.println("* Add Customer *");
        System.out.println("*****************");
        addCustomerToQueue();
    }

    static void addCustomerToQueue() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first name: ");
        String firstName = scanner.next();

        System.out.print("Enter last name: ");
        String lastName = scanner.next();

        System.out.print("Enter number of burgers required: ");
        int burgersRequired = scanner.nextInt();

        Customer newCustomer = new Customer(firstName, lastName, burgersRequired);

        boolean customerAdded = false;
        FoodQueue selectedQueue = null;
        int shortestQueueLength = Integer.MAX_VALUE;

        for (FoodQueue queue : queues) {
            if (queue.getCustomers().isEmpty()) {
                queue.addCustomer(newCustomer);
                customerAdded = true;
                break;
            } else if (!queue.isFull() && queue.getLength() < shortestQueueLength) {
                shortestQueueLength = queue.getLength();
                selectedQueue = queue;
            }
        }

        if (!customerAdded && selectedQueue != null) {
            if (burgersRequired > burgerStock) {
                System.out.println("Cannot add customer to Queue. Insufficient burgers.");
            } else {
                selectedQueue.addCustomer(newCustomer);
                System.out.println("Customer " + newCustomer.getFullName() + " added to Queue " + (Arrays.asList(queues).indexOf(selectedQueue) + 1));
            }
        } else if (!customerAdded) {
            System.out.println("Cannot add customer to any queue. All queues are full.");
            addToWaitingList(newCustomer); // Add the customer to the waiting list
        } else {
            System.out.println("Customer " + newCustomer.getFullName() + " added to a queue.");
        }
    }

//    ------------------------------------------ Waiting List ---------------------------------------------------------------------------------------------------------------

    static int sizeOfWaitingList = 100;
    static Customer[] waitingList = new Customer[sizeOfWaitingList];
    static int front = 0;
    static int rear = 0;
    static boolean isEmpty = true;


    // Add a customer to the waiting list
    static void addToWaitingList(Customer customerExtra) {
        if (isEmpty) {
            waitingList[rear] = customerExtra;
            isEmpty = false;
            System.out.println("All queues are full! Customer " + customerExtra.getFullName() + " added to the waiting list.");
        } else if ((rear + 1) % waitingList.length == front) {
            System.out.println("Waiting list is full!");
        } else {
            rear = (rear + 1) % waitingList.length;
            waitingList[rear] = customerExtra;
            System.out.println("All queues are full! Customer " + customerExtra.getFullName() + " added to the waiting list.");
        }
    }


    static void RCQ() {
        System.out.println("*****************");
        System.out.println("* Remove Customer *");
        System.out.println("*****************");
        removeCustomerFromQueue();
    }

    private static void removeCustomerFromQueue() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Queue number (1, 2, or 3): ");
        int queueNumber = scanner.nextInt();

        if (queueNumber < 1 || queueNumber > queues.length) {
            System.out.println("Invalid Queue number.");
            return;
        }

        FoodQueue selectedQueue = queues[queueNumber - 1];

        System.out.print("Enter the position of the customer to remove (starting from 1): ");
        int position = scanner.nextInt();

        if (position < 1 || position > selectedQueue.getLength()) {
            System.out.println("Invalid position.");
            return;
        }

        Customer removedCustomer = selectedQueue.removeCustomer(position - 1);
        if (removedCustomer != null) {
            System.out.println("Customer " + removedCustomer.getFullName() + " removed from Queue " + queueNumber);
        } else {
            System.out.println("No customer found at position " + position + " in Queue " + queueNumber);
        }
    }

    static void PCQ() {
        System.out.println("*****************");
        System.out.println("* Serve Customers *");
        System.out.println("*****************");
        serveCustomers();
    }

    private static void serveCustomers() {
        int totalIncome = 0;
        for (FoodQueue queue : queues) {
            if (!queue.isEmpty()) {
                Customer customer = queue.serveCustomer();
                int burgersServed = Math.min(customer.getBurgersRequired(), 5);
                int price = burgersServed * 650;
                totalIncome += price;
                burgerStock -= burgersServed;
                System.out.println("Serving " + burgersServed + " burgers to customer: " + customer.getFullName());
            }
        }

        System.out.println("*****************");
        System.out.println("* Updated Queues *");
        System.out.println("*****************");
        for (int i = 0; i < queues.length; i++) {
            System.out.println("Queue " + (i + 1) + ":");
            queues[i].printQueue();
        }

        System.out.println("Burger Stock Count: " + burgerStock);
        System.out.println("Total Income: $" + totalIncome);

        // Move a customer from the waiting list to a queue
        if (!isEmpty) {
            FoodQueue selectedQueue = null;
            int shortestQueueLength = Integer.MAX_VALUE;

            for (FoodQueue queue : queues) {
                if (!queue.isFull() && queue.getLength() < shortestQueueLength) {
                    shortestQueueLength = queue.getLength();
                    selectedQueue = queue;
                }
            }

            if (selectedQueue != null) {
                Customer customerFromWaitingList = waitingList[front];
                if (customerFromWaitingList.getBurgersRequired() <= burgerStock) {
                    selectedQueue.addCustomer(customerFromWaitingList);
                    burgerStock -= customerFromWaitingList.getBurgersRequired();
                    String name = customerFromWaitingList.getFullName();

                    if (front == rear) {
                        isEmpty = true;
                    } else {
                        front = (front + 1) % waitingList.length;
                    }
                    System.out.println("Customer " + name + " moved from the waiting list to a queue.");
                } else {
                    System.out.println("Customer from the waiting list cannot be added to the queue due to insufficient burger stock.");
                }
            } else {
                System.out.println("Cannot move customer from the waiting list to any queue. All queues are full.");
            }
        }
    }


    static void VCS() {
        System.out.println("*****************");
        System.out.println("* Sorted Customers *");
        System.out.println("*****************");
        String[] sortedCustomers = getSortedCustomers();
        if (sortedCustomers.length > 0) {
            System.out.println("Customers in alphabetical order:");
            for (String customer : sortedCustomers) {
                System.out.println(customer);
            }
        } else {
            System.out.println("No customers found.");
        }
    }

    private static String[] getSortedCustomers() {
        List<Customer> customerList = new ArrayList<>();
        for (FoodQueue queue : queues) {
            customerList.addAll(queue.getCustomers());
        }

        customerList.sort(Customer::compareTo);

        String[] sortedCustomers = new String[customerList.size()];
        for (int i = 0; i < customerList.size(); i++) {
            Customer customer = customerList.get(i);
            sortedCustomers[i] = customer.getFullName();
        }

        return sortedCustomers;
    }

    static void SPD() {
        System.out.println("*****************");
        System.out.println("* Store Program Data into File *");
        System.out.println("*****************");
        storeProgramData();
    }

    private static void storeProgramData() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("program_data.txt"));

            // Write burger stock
            writer.write("Burger Stock: " + burgerStock);
            writer.newLine();

            // Write queues data
            for (int i = 0; i < queues.length; i++) {
                writer.write("Queue " + (i + 1) + ":\n");
                writer.write(queues[i].getQueueDataAsString());
                writer.newLine();
            }

            // Write waiting list data
            writer.write("Waiting List:\n");
            writer.write("Size: " + sizeOfWaitingList);
            writer.newLine();
            writer.write("Front: " + front);
            writer.newLine();
            writer.write("Rear: " + rear);
            writer.newLine();
            writer.write("IsEmpty: " + isEmpty);
            writer.newLine();
            writer.write("Customers:\n");
            for (int i = front; i != rear; i = (i + 1) % waitingList.length) {
                Customer customer = waitingList[i];
                writer.write(customer.getFullName() + ", " + customer.getBurgersRequired());
                writer.newLine();
            }
            if (!isEmpty) {
                Customer customer = waitingList[rear];
                writer.write(customer.getFullName() + ", " + customer.getBurgersRequired());
                writer.newLine();
            }

            writer.close();

            System.out.println("Program data stored in program_data.txt file.");
        } catch (IOException e) {
            System.out.println("Error occurred while storing program data: " + e.getMessage());
        }
    }


    static void LPD() {
        System.out.println("*****************");
        System.out.println("* Load Program Data from File *");
        System.out.println("*****************");
        loadProgramData();
    }

    private static void loadProgramData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("program_data.txt"));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Burger Stock: ")) {
                    burgerStock = Integer.parseInt(line.substring("Burger Stock: ".length()));
                } else if (line.startsWith("Queue ")) {
                    int queueIndex = Integer.parseInt(line.substring(6, 7)) - 1;
                    queues[queueIndex].loadQueueDataFromString(reader.readLine());
                } else if (line.equals("Waiting List:")) {
                    sizeOfWaitingList = Integer.parseInt(reader.readLine().substring(6));
                    front = Integer.parseInt(reader.readLine().substring(7));
                    rear = Integer.parseInt(reader.readLine().substring(6));
                    isEmpty = Boolean.parseBoolean(reader.readLine().substring(9));

                    waitingList = new Customer[sizeOfWaitingList];
                    for (int i = front; i != rear; i = (i + 1) % waitingList.length) {
                        String customerData = reader.readLine();
                        String[] customerParts = customerData.split(", ");
                        String fullName = customerParts[0];
                        int burgersRequired = Integer.parseInt(customerParts[1]);
                        waitingList[i] = new Customer(fullName, burgersRequired);
                    }
                    if (!isEmpty) {
                        String customerData = reader.readLine();
                        String[] customerParts = customerData.split(", ");
                        String fullName = customerParts[0];
                        int burgersRequired = Integer.parseInt(customerParts[1]);
                        waitingList[rear] = new Customer(fullName, burgersRequired);
                    }
                }
            }

            reader.close();

            System.out.println("Program data loaded from program_data.txt file.");
        } catch (IOException e) {
            System.out.println("Error occurred while loading program data: " + e.getMessage());
        }
    }


    static void STK() {
        System.out.println("*****************");
        System.out.println("* View Remaining Burger Stock *");
        System.out.println("*****************");
        viewRemainingBurgerStock();
    }

    private static void viewRemainingBurgerStock() {
        System.out.println("Remaining Burger Stock: " + burgerStock + " burgers");
    }

    static void AFS() {
        System.out.println("*****************");
        System.out.println("* Add Burgers to Stock *");
        System.out.println("*****************");
        addBurgersToStock();
    }

    private static void addBurgersToStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of burgers to add: ");
        int quantity = scanner.nextInt();

        if (quantity > 0) {
            int newStock = burgerStock + quantity;
            if (newStock <= 50) {
                burgerStock = newStock;
                System.out.println(quantity + " burgers added to the stock.");
            } else {
                System.out.println("Cannot add more than " + (50 - burgerStock) + " burgers. Please enter a smaller quantity.");
            }
        } else {
            System.out.println("Invalid quantity. Please enter a positive number of burgers to add.");
        }
    }

    static void IFQ() {
        System.out.println("*****************");
        System.out.println("* Print Income of each Queue *");
        System.out.println("*****************");
        printIncomeOfEachQueue();
    }

    private static void printIncomeOfEachQueue() {
        for (int i = 0; i < queues.length; i++) {
            int queueIncome = queues[i].getTotalIncome();
            System.out.println("Queue " + (i + 1) + " Income: $" + queueIncome);
        }
    }


    //    ------------------------------------------ GUI ---------------------------------------------------------------------------------------------------------------

    // Used the below reference to launch the GUI more than one time.
    //https://stackoverflow.com/questions/24320014/
    private static volatile boolean javaFxLaunched = false;
    public static void myLaunch(Class<? extends Application> applicationClass)
    {
        if (!javaFxLaunched)
        { // First time
            Platform.setImplicitExit(false);
            new Thread(() -> Application.launch(applicationClass)).start();
            javaFxLaunched = true;
        } else
        { // Next times
            Platform.runLater(() ->
            {
                try
                {
                    Application application = applicationClass.newInstance();
                    Stage primaryStage = new Stage();
                    application.start(primaryStage);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            });
        }
    }


}




