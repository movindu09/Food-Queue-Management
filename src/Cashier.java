import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Cashier {
    public static int burgerStock = 50;
    //stock variable to keep track of stock
    public static String[] Cashier1 = new String[2];
    public static String[] Cashier2 = new String[3];
    public static String[] Cashier3 = new String[5];

    static Scanner userIn = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        boolean menuLoop = true;
        while (menuLoop)//  Loops the menu
        {
            try {
                System.out.println(
                        """
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
                                999 or EXT: Exit the Program.
                                """);

                System.out.print("Please select an option : ");
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
                    case "999", "EXT" -> {
                        System.out.println("Exiting the program....");
                        menuLoop = false;
                    }
                    default -> System.out.println("Please enter a valid input."); //used for input validation
                }
            } catch (InputMismatchException ex) {
                printDollars();
                System.out.println("Invalid input!");
            }
            printDollars();
            Thread.sleep(1000);
        }
    }


    //    ---------------------------- other Methods -----------------------------------------------------------------------------------------------------------------

    static void printDollars() //Prints a line with dollar sign to divide the outputs(used for merely aesthetic purposes)
    {
        System.out.println("\n" + "$".repeat(40) + "\n");
    }

    static void VFQ() //View All Queues
    {
        // Print the cashier's queue
        System.out.println("*****************");
        System.out.println("* Cashiers *");
        System.out.println("*****************");
        System.out.println("Cashier 1:");printArrayElements(Cashier1);
        System.out.println("Cashier 2:");printArrayElements(Cashier2);
        System.out.println("Cashier 3:");printArrayElements(Cashier3);
    }

    private static void printArrayElements(String[] array) {
        for (String element : array) {
            if (element != null) {
                System.out.print("O ");
            } else {
                System.out.print("X ");
            }
        }
        System.out.println();
    }

    static void VEQ() {
        // Print the empty queues
        System.out.println("*****************");
        System.out.println("* Empty Queues *");
        System.out.println("*****************");
        checkEmptyQueue(Cashier1, "Cashier 1");
        checkEmptyQueue(Cashier2, "Cashier 2");
        checkEmptyQueue(Cashier3, "Cashier 3");
    }

    private static void checkEmptyQueue(String[] queue, String queueName) {
        boolean isEmpty = true;
        for (String element : queue) {
            if (element != null) {
                isEmpty = false;
                break;
            }
        }
        if (isEmpty) {
            System.out.println(queueName + ": Empty");

        }
    }

    static void ACQ() {

        // Add a customer to a queue
        System.out.println("*****************");
        System.out.println("* Add Customer *");
        System.out.println("*****************");
        addCustomerToQueue();
    }

    private static void addCustomerToQueue() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        if (addCustomer(Cashier1, customerName)) {
            System.out.println("Customer " + customerName + " added to Cashier 1");
        } else if (addCustomer(Cashier2, customerName)) {
            System.out.println("Customer " + customerName + " added to Cashier 2");
        } else if (addCustomer(Cashier3, customerName)) {
            System.out.println("Customer " + customerName + " added to Cashier 3");
        } else {
            System.out.println("All Cashier are full. Customer " + customerName + " could not be added.");
        }
    }

    private static boolean addCustomer(String[] queue, String customerName) {
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] == null) {
                queue[i] = customerName;
                return true;
            }
        }
        return false;
    }

    static void RCQ() {
        System.out.println("*****************");
        System.out.println("* Remove Customer *");
        System.out.println("*****************");
        removeCustomerFromQueue();
    }

    private static void removeCustomerFromQueue() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Cashier number (1, 2, or 3): ");
        int queueNumber = scanner.nextInt();

        String[] selectedQueue;
        switch (queueNumber) {
            case 1 -> selectedQueue = Cashier1;
            case 2 -> selectedQueue = Cashier2;
            case 3 -> selectedQueue = Cashier3;
            default -> {
                System.out.println("Invalid Cashier number.");
                return;
            }
        }

        System.out.print("Enter the position of the customer to remove (starting from 1): ");
        int position = scanner.nextInt();

        if (position < 1 || position > selectedQueue.length) {
            System.out.println("Invalid position.");
            return;
        }

        String removedCustomer = selectedQueue[position - 1];
        if (removedCustomer != null) {
            selectedQueue[position - 1] = null;
            System.out.println("Customer " + removedCustomer + " removed from Cashier " + queueNumber);
        } else {
            System.out.println("No customer found at position " + position + " in Cashier " + queueNumber);
        }

    }

    static void PCQ() {
        // Serve customers and update burger stock
        System.out.println("*****************");
        System.out.println("* Serve Customers *");
        System.out.println("*****************");
        serveCustomers();

    }

    private static void serveCustomers() {
        serveBurgers(Cashier1);
        serveBurgers(Cashier2);
        serveBurgers(Cashier3);

        System.out.println("*****************");
        System.out.println("* Updated Queues *");
        System.out.println("*****************");
        printQueue(Cashier1, "Cashier 1");
        printQueue(Cashier2, "Cashier 2");
        printQueue(Cashier3, "Cashier 3");

        System.out.println("Burger Stock Count: " + burgerStock);


    }

    private static void serveBurgers(String[] queue) {
        if (queue[0] != null) {
            System.out.println("Serving 5 burgers to customer: " + queue[0]);
            queue[0] = null;
            for (int i = 1; i < queue.length; i++) {
                if (queue[i] != null) {
                    queue[i - 1] = queue[i];
                    queue[i] = null;
                }
            }
            burgerStock -= 5;
        }
        if (burgerStock <= 10) {
            System.out.println("Warning: Burger stock is low (" + burgerStock + " burgers remaining).");

        }
    }

    private static void printQueue(String[] queue, String queueName) {
        System.out.print(queueName + ": ");
        for (String element : queue) {
            if (element != null) {
                System.out.print(element + " ");
            }
        }
        System.out.println();
    }

    static void VCS() {
        // View Customers Sorted in alphabetical order
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
        List<String> customerList = new ArrayList<>();
        customerList.addAll(Arrays.asList(Cashier1));
        customerList.addAll(Arrays.asList(Cashier2));
        customerList.addAll(Arrays.asList(Cashier3));

        customerList.removeIf(Objects::isNull);
        String[] sortedCustomers = customerList.toArray(new String[0]);
        Arrays.sort(sortedCustomers);
        return sortedCustomers;

    }

    static void SPD() {
        // Store Program Data into file
        System.out.println("*****************");
        System.out.println("* Store Program Data into File *");
        System.out.println("*****************");
        storeProgramData();
    }

    private static void storeProgramData() {
        try {
            FileWriter fileWriter = new FileWriter("Customers_data.txt");

            // Write Cashier queues
            writeArrayToFile(fileWriter, Cashier1);
            writeArrayToFile(fileWriter, Cashier2);
            writeArrayToFile(fileWriter, Cashier3);

            // Write burger stock
            fileWriter.write("Burger Stock: " + burgerStock + "\n");

            fileWriter.close();
            System.out.println("Program data stored successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while storing program data.");
            e.printStackTrace();
        }
    }

    private static void writeArrayToFile(FileWriter fileWriter, String[] array) throws IOException {
        for (String element : array) {
            if (element != null) {
                fileWriter.write(element + "\n");
            }
        }
        fileWriter.write("\n");
    }

    static void LPD() {
        // Load Program Data from file
        System.out.println("*****************");
        System.out.println("* Load Program Data from File *");
        System.out.println("*****************");
        loadProgramData();
    }

    private static void loadProgramData() {
        try {
            FileReader fileReader = new FileReader("Customers_data.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            int currentQueue = 1;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("Burger Stock:")) {
                    // Load burger stock
                    String[] parts = line.split(":");
                    burgerStock = Integer.parseInt(parts[1].trim());
                } else if (line.isEmpty()) {
                    // Move to the next queue
                    currentQueue++;
                } else {
                    // Load customer name into the corresponding queue
                    switch (currentQueue) {
                        case 1 -> addCustomer(Cashier1, line.trim());
                        case 2 -> addCustomer(Cashier2, line.trim());
                        case 3 -> addCustomer(Cashier3, line.trim());
                        default -> System.out.println("Invalid queue number in the file.");
                    }
                }
            }

            bufferedReader.close();
            System.out.println("Program data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while loading program data.");
            e.printStackTrace();
        }
    }

    static void STK(){
        System.out.println("*****************");
        System.out.println("* View Remaining Burger Stock *");
        System.out.println("*****************");
        viewRemainingBurgerStock();
    }
    private static void viewRemainingBurgerStock() {
        System.out.println("Remaining Burger Stock: " + burgerStock + " burgers");
    }

    static void AFS() {
        // Add Burgers to Stock
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

}
//end fsfs fsadada

