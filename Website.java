import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

/**
 * CS180 PJ 4
 * 
 * Main driver class for the project
 * 
 * @author Ameesh Daryani
 * @version 4/4/2023
 */

public class Website {

    public static User currentUser;

    public static void main(String[] args) {
        // TODO: implement method
        Scanner scan = new Scanner(System.in);

        try {
            printWelcomeMenu(scan);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Incorrect File Error");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }

        scan.close();
    }

    public static void printWelcomeMenu(Scanner scan) throws InvalidInputException, IOException {

        System.out.println("Hello and Welcome to the Seller-Customer interface!");
        System.out.println("Please select an option to begin:");
        System.out.println("1. Login");
        System.out.println("2. Create an account");
        System.out.println("3. Quit");
        int choice = Integer.parseInt(scan.nextLine());

        if (choice == 1 || choice == 2 || choice == 3) {
            switch (choice) {
                case 1: {
                    // TODO: add login method
                    boolean success = login(scan);
                    if (success) {
                        System.out.println("You have successfully logged in!");
                        if (currentUser instanceof Customer) {
                            customerMenu(scan);
                        }
                        if (currentUser instanceof Seller) {
                            sellerMenu(scan);
                        }
                    }
                    if (!success) {
                        System.out.println("Goodbye!");
                    }
                    break;
                }

                case 2: {
                    // TODO: add create account method
                    createAccount(scan, "user.txt");
                    break;
                }

                case 3: {
                    System.out.println("Thanks for using the interface!");
                    scan.close();
                    break;
                }
            }
        } else
            throw new InvalidInputException("The choice entered was invalid!");
    }

    public static boolean login(Scanner scanner) throws IOException {
        String username = null;
        String password = null;

        while (true) {
            System.out.print("Enter username (or -1 to quit): ");
            username = scanner.nextLine();
            if (username.equals("-1")) {
                return false; // Quit
            }

            System.out.print("Enter password: ");
            password = scanner.nextLine();

            try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (tokens[1].equals(username) && tokens[2].equals(password)) {
                        setUser(tokens);
                        return true; // Success
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private static void setUser(String[] tokens) {
        if (tokens[0].equals("seller")) {
            currentUser = new Seller(tokens[1], tokens[2], tokens[3], Integer.parseInt(tokens[4]),
                    Integer.parseInt(tokens[5]), tokens[6]);

        } else if (tokens[0].equals("customer")) {
            currentUser = new Customer(tokens[1], tokens[2], tokens[3], Integer.parseInt(tokens[4]),
                    Integer.parseInt(tokens[5]), tokens[6]);

        }
    }

    public String[] getStores(Scanner scan) {
        // TODO: implement method
        return new String[0];
    }

    public static void createAccount(Scanner scanner, String fileName) {
        // Prompt the user to enter their username, name, password, and account type
        System.out.print("Enter your full name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        String phoneNum;
        do {
            System.out.print("Enter your phone number (10 Digit number): ");
            phoneNum = scanner.nextLine();
        } while (phoneNum.length() != 10);

        String address;
        do {
            System.out.print("Enter your address (no commas): ");
            address = scanner.nextLine();
        } while (address.contains(","));

        String accountType;
        do {
            System.out.print("Enter your account type (Seller or Customer): ");
            accountType = scanner.nextLine().trim().toLowerCase();
        } while (!accountType.equals("seller") && !accountType.equals("customer"));

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File("user.txt"), true))) {
            writer.append(accountType + "," + username + "," + password + "," + name + "," + 0 + "," + phoneNum + ","
                    + address);
            writer.println();
            writer.flush();
            System.out.println("User created! You are now logged in");
            if (accountType.equals("seller")) {
                currentUser = new Seller(username, password, name, 0, Integer.parseInt(phoneNum), address);
                sellerMenu(scanner);
            } else if (accountType.equals("customer")) {
                currentUser = new Customer(username, password, name, 0, Integer.parseInt(phoneNum), address);
                customerMenu(scanner);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file for writing: " + fileName);
            e.printStackTrace();
        }

    }

    public static void sellerMenu(Scanner scan) {
        int choice;
        System.out.println("Welcome Seller " + currentUser.getName() + "!");
        System.out.println("Please Select an action:  ");
        System.out.println("1. Message Customer from search");
        System.out.println("2. Message Customer from list");
        System.out.println("3. Create Store");
        System.out.println("4. Block User");
        System.out.println("5. Ghost User (set Invisible)");
        System.out.println("6. Modify User");
        System.out.println("7. Delete Account");
        System.out.println("8. Logout");
        choice = scan.nextInt();
        scan.nextLine();

        switch (choice) {
            case 1:
                messageCustomerSearch(scan);
                break;
                
            case 2:

                break;
        
            case 3:

                break;

            case 4:

                break;

            case 5:

                break;

            case 6:

                break;

            case 7:

                break;

            case 8:
                System.out.println("Logging out...");
                currentUser = null;
                break;

            default:
                System.out.println("That's not a valid choice. Reloading menu...");
                sellerMenu(scan);
                break;
        }

    }

    

    private static void messageCustomerSearch(Scanner scan) {
        ArrayList<Customer> customers = listofCustomers();
        String searchCustomer;
        boolean found = false;
        Customer foundCust = null;

        do {
            System.out.print("Enter the name of the customer to search for: ");
            searchCustomer = scan.nextLine();

            for (Customer customer : customers) {
                if (customer.getName().equalsIgnoreCase(searchCustomer)) {
                    found = true;
                    foundCust = customer;
                    break;
                }
            }

            if (!found) {
                System.out.println("Customer " + searchCustomer + " could not be found.");

                System.out.println("Enter 1 to search again, or 2 to quit to main menu: ");
                int choice = scan.nextInt();
                scan.nextLine();

                if (choice == 2) {
                    messageCustomerSearch(scan);
                    break;
                }
            }

            if (found) {
                if (foundCust != null) {
                    messageMenu(scan, foundCust);
                } else {
                    System.out.println("There was an error with the customer search.");
                }
            } 

        } while (!found);

    }

    private static void messageMenu(Scanner scan, User reciever) {
        boolean quit = false;

        while (!quit) {
            // print the menu options
            System.out.println("Please select an option:");
            System.out.println("1. Send a new message");
            System.out.println("2. View message history");
            System.out.println("3. Edit a message");
            System.out.println("4. Delete a message");
            System.out.println("0. Main Menu");

            // read user input
            int choice = scan.nextInt();
            scan.nextLine(); // consume the newline character

            switch (choice) {
                case 0:
                    quit = true;
                    if (currentUser instanceof Customer) {
                        customerMenu(scan);
                    } else if (currentUser instanceof Seller) {
                        sellerMenu(scan);
                    }
                    break;
                case 1:
                    sendNewMessage(scan, reciever);
                    break;
                case 2:
                    viewMessageHistory(scan, reciever);
                    break;
                case 3:
                    System.out.println("You selected: Edit a message");
                    // add code to handle editing a message
                    break;
                case 4:
                    System.out.println("You selected: Delete a message");
                    // add code to handle deleting a message
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        
    }

    private static void viewMessageHistory(Scanner scan, User reciever) {
        System.out.println("You selected: View message history");
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./Conversations/" + convoNamingScheme(currentUser.getName(), reciever.getName())));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                System.out.print(tokens[2] + " | " + tokens[1] + ": " + tokens[0]);
                if (tokens[3].equals("true")) {
                    System.out.println(" (edited)");
                } else {
                    System.out.println();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The conversation does not exist! Please send a message to see the history.");
            messageMenu(scan, reciever);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Press enter to continue");
        scan.nextLine();
    }

    private static void sendNewMessage(Scanner scan, User reciever) {
        System.out.println("You selected: Send a new message");
        System.out.println("Enter the message you would like to send to " + reciever.getName() + ": ");
        String messageStr = scan.nextLine();
        Message message = new Message(messageStr, currentUser, reciever, new Date().toString());
        File file = new File("./Conversations/" + convoNamingScheme(currentUser.getName(), reciever.getName()));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(file, true));
            writer.append(message.toString());
            writer.println();
            writer.flush();
            System.out.println("Message Sent!");
            writer.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        messageMenu(scan, reciever);
    }

    public static void customerMenu(Scanner scanner) {
        int choice;
        System.out.println("Welcome Customer " + currentUser.getName() + "!");
        System.out.println("Please Select an action: ");
        System.out.println("1. Message Seller from search");
        System.out.println("2. Message Seller from list of Stores");
        System.out.println("3. Block User");
        System.out.println("4. Ghost User (set Invisible)");
        System.out.println("5. Modify User");
        System.out.println("6. Logout");
        
    }

    public void printStoreList() {
        // TODO: implement method
    }

    public void printCustomerList() {
        // TODO: implement method
    }

    public User searchCustomersByList() {

        return new User();
    }

    public User searchStoresByList() {

        return new User();
    }

    public User searchCustomerByKeyword() {

        return new User();
    }

    public User searchSellerByKeyword() {

        return new User();
    }

    public static ArrayList<Seller> listOfSellers() {
        //TODO: method that loads sellers arraylist with list of sellers from user.txt
        ArrayList<Seller> sellers = new ArrayList<>();
        File f = new File("user.txt");
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] part = line.split(",");
                String userType = part[0];
                if (userType.equals("seller")) {
                    String userName = part[1];
                    String password = part[2];
                    String name = part[3];
                    int numMessages = Integer.parseInt(part[4]);
                    int phoneNum = Integer.parseInt(part[5]);
                    String address = part[6];
                    Seller seller = new Seller(userName, password, name, numMessages, phoneNum, address);
                    sellers.add(seller);
                }
            }
            return sellers; // need to change
        } catch (FileNotFoundException e) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Customer> listofCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        File f = new File("user.txt");
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] part = line.split(",");
                String userType = part[0];
                if (userType.equals("customer")) {
                    String userName = part[1];
                    String password = part[2];
                    String name = part[3];
                    int numMessages = Integer.parseInt(part[4]);
                    int phoneNum = Integer.parseInt(part[5]);
                    String address = part[6];
                    Customer customer = new Customer(userName, password, name, numMessages, phoneNum, address);
                    customers.add(customer);
                }
            }
            return customers; // need to change
        } catch (FileNotFoundException e) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static String convoNamingScheme(String str1, String str2) {
        String[] arr = {str1, str2};
        Arrays.sort(arr);
        return arr[0] + "_" + arr[1] + ".txt";
    }
}
