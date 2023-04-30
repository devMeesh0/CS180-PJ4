import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.net.SocketException;
import java.nio.Buffer;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * CS180 PJ 4
 *
 * Main driver class for the project
 *
 * @author Ameesh Daryani
 * @version 4/4/2023
 */

public class Website extends Thread {
    
    public static User currentUser;
    private static final Object LOCK = new Object(); // lock
    public static Scanner scanner = new Scanner(System.in); //to be deleted at a later date
    
    
    public static void main(String[] args) {
        //   implement method
        
        
        JOptionPane.showMessageDialog(null,"Welcome to the Seller Customer Interface!", "Seller-Customer Exchange", JOptionPane.PLAIN_MESSAGE);
        String hostName = "localhost";
        String port = "4242";
        
        try {
            Socket socket = new Socket(hostName, Integer.parseInt(port));
            if (socket.isConnected()) {
                JOptionPane.showMessageDialog(null, "Website connection established!", "Seller-Customer Exchange", JOptionPane.PLAIN_MESSAGE);
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            try {
                printWelcomeMenu(reader, writer);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            } catch (FileNotFoundException e) {
                System.out.println("Incorrect File Error");
            } catch (IOException e) {
                System.out.println("IO Exception");
            }
            
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
        scanner.close();
    }
    
    public static void printWelcomeMenu(BufferedReader reader, PrintWriter writer) throws InvalidInputException, IOException {
        
        System.out.println("Hello and Welcome to the Seller-Customer interface!");
        System.out.println("Please select an option to begin:");
        System.out.println("1. Login");
        System.out.println("2. Create an account");
        System.out.println("3. Quit");
        int choice = Integer.parseInt(scanner.nextLine());
        
        
        
        if (choice == 1 || choice == 2 || choice == 3) {
            switch (choice) {
                case 1: {
                    //   add login method
                    writer.write("Login");
                    writer.println();
                    writer.flush();
                    boolean success = login(reader, writer);
                    if (success) {
                        System.out.println("You have successfully logged in!");
                        if (currentUser instanceof Customer) {
                            customerMenu(reader, writer);
                        }
                        if (currentUser instanceof Seller) {
                            sellerMenu(reader, writer);
                        }
                    }
                    if (!success) {
                        System.out.println("Goodbye!");
                    }
                    break;
                }
                
                case 2: {
                    writer.write("createAccount");
                    writer.println();
                    writer.flush();
                    //   add create account method
                    createAccount(reader, writer);
                    break;
                }
                
                case 3: {
                    writer.write("quit");
                    writer.println();
                    writer.flush();
                    System.out.println("Thanks for using the interface!");
                    scanner.close();
                    break;
                }
            }
        } else
            throw new InvalidInputException("The choice entered was invalid!");
    }
    
    public static boolean login(BufferedReader reader, PrintWriter writer) throws IOException {
        synchronized (LOCK) {
            String username = null;
            String password = null;
            
            while (true) {
                System.out.print("Enter username (or -1 to quit): "); // @nathan
                username = scanner.nextLine(); // @nathan
                writer.write(username);
                writer.println();
                writer.flush();
                
                if (username.equals("-1")) {
                    return false; // Quit
                }
                
                
                System.out.print("Enter password: "); // @nathan
                password = scanner.nextLine(); // @nathan
                
                writer.write(password);
                writer.println();
                writer.flush();
                
                String next = reader.readLine();
                
                if (Boolean.parseBoolean(next)) {
                    setUser(reader.readLine().split(","));
                    return true;
                }
                
                System.out.println("Invalid username or password. Please try again.");
            }
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
    
    
    public static void createAccount(BufferedReader reader, PrintWriter writer) throws IOException {
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
        
        synchronized (LOCK) {
            writer.write(name + "," + username + "," + password + "," + phoneNum + "," + address + "," + accountType);
            writer.println();
            writer.flush();
            
            String result = reader.readLine();
            
            if (result.equals("created")) {
                if (accountType.equals("seller")) {
                    currentUser = new Seller(username, password, name, 0, Integer.parseInt(phoneNum), address);
                    sellerMenu(reader, writer);
                } else if (accountType.equals("customer")) {
                    currentUser = new Customer(username, password, name, 0, Integer.parseInt(phoneNum), address);
                    customerMenu(reader, writer);
                }
            } else if (result.equals("error")) {
                // add error opening file JOPtionPane Message
            }
        }
        
        
    }
    
    public static void sellerMenu(BufferedReader reader, PrintWriter writer) throws IOException {
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
        choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                System.out.println("gets here 1");
                messageCustomerSearch(reader, writer);
                break;
            
            case 2:
                messageCustomerList(reader, writer);
                break;
            
            case 3:
                createStore(reader, writer);
                break;
            
            case 4:
                blockUser(reader, writer);
                break;
            
            case 5:
                setInvisible(reader, writer);
                break;
            
            case 6:
                modifyUser(reader, writer);
                break;
            
            case 7:
                deleteUser(reader, writer);
                break;
            
            case 8:
                System.out.println("Logging out...");
                currentUser = null;
                break;
            
            default:
                System.out.println("That's not a valid choice. Reloading menu...");
                sellerMenu(reader, writer);
                break;
        }
        
    }
    
    
    
    private static void deleteUser(BufferedReader reader, PrintWriter writer) throws IOException {
        System.out.println("ARE YOU SURE YOU WANT TO DELETE ACCOUNT? (Y/N)");
        String choice = scanner.nextLine().toLowerCase();
        
        if (choice.equals("y")) {
            writer.write("deleteUser");
            writer.println();
            writer.flush();
            
            writer.write(currentUser.name);
            writer.println();
            writer.flush();
            
            System.out.println("File successfully updated.");
            System.out.println("Goodbye!");
        } else {
            if (currentUser instanceof Seller) {
                sellerMenu(reader, writer);
            }
            if (currentUser instanceof Customer) {
                customerMenu(reader, writer);
            }
        }
    }
    
    private static void modifyUser(BufferedReader reader, PrintWriter writer) throws IOException {
        currentUser.modifyUser();
        if (currentUser instanceof Seller) {
            sellerMenu(reader, writer);
        }
        if (currentUser instanceof Customer) {
            customerMenu(reader, writer);
        }
    }
    
    private static void setInvisible(BufferedReader reader, PrintWriter writer) throws IOException {
        System.out.println("Please enter the name of the User you would like to set yourself invisible for:");
        String name = scanner.nextLine().toLowerCase();
        
        if (currentUser instanceof Customer) {
            ArrayList<Seller> sellers = listOfSellers(reader, writer);
            for (int i = 0; i < sellers.size(); i++) {
                if (sellers.get(i).getName().equals(name)) {
                    synchronized (LOCK) {
                        File file = new File("invisible.txt");
                        FileWriter fw;
                        try {
                            fw = new FileWriter(file, true);
                            PrintWriter pw = new PrintWriter(fw);
                            pw.println(currentUser.getName() + "," + name);
                            pw.close();
                            System.out.println("The user can no longer see you.");
                        } catch (IOException e) {
                            //   Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
            customerMenu(reader, writer);
        }
        if (currentUser instanceof Seller) {
            ArrayList<Customer> customers = listofCustomers(reader, writer);
            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).getName().equals(name)) {
                    synchronized (LOCK) {
                        File file = new File("invisible.txt");
                        FileWriter fw;
                        try {
                            fw = new FileWriter(file, true);
                            PrintWriter pw = new PrintWriter(fw);
                            pw.println(currentUser.getName() + "," + name);
                            pw.close();
                            System.out.println("The user can no longer see you.");
                        } catch (IOException e) {
                            //   Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
            sellerMenu(reader, writer);
        }
    }
    
    private static void blockUser(BufferedReader reader, PrintWriter writer) throws IOException {
        System.out.println("Please enter the name of the User you would like to block:");
        String name = scanner.nextLine().toLowerCase();
        
        writer.write("blockUser");
        writer.println();
        writer.flush();
        
        writer.write(currentUser.getName());
        writer.println();
        writer.flush();
        
        writer.write(name);
        writer.println();
        writer.flush();
        
        if (currentUser instanceof Customer) {
            ArrayList<Seller> sellers = listOfSellers(reader, writer);
            writer.write("customer");
            writer.println();
            writer.flush();
            
            writer.write(sellers.size());
            writer.println();
            writer.flush();
            
            for (int i = 0; i < sellers.size(); i++) {
                writer.write(sellers.get(i).toString());
                writer.println();
                writer.flush();
            }
            
            System.out.println("The user has been blocked.");
            customerMenu(reader, writer);
        }
        
        if (currentUser instanceof Seller) {
            ArrayList<Customer> customers = listofCustomers(reader, writer);
            writer.write("seller");
            writer.println();
            writer.flush();
            
            writer.write(customers.size());
            writer.println();
            writer.flush();
            
            for (int i = 0; i < customers.size(); i++) {
                writer.write(customers.get(i).toString());
                writer.println();
                writer.flush();
            }
            
            System.out.println("The user has been blocked.");
            sellerMenu(reader, writer);
        }
    }
    
    private static boolean isInvisibleToYou(BufferedReader reader, PrintWriter writer, String otherName) throws IOException {
        synchronized (LOCK) {
            writer.write("isInvisibleToYou");
            writer.println();
            writer.flush();
            
            writer.write(otherName);
            writer.println();
            writer.flush();
            
            writer.write(currentUser.getName());
            writer.println();
            writer.flush();
            
            return Boolean.parseBoolean(reader.readLine());
            
        }
    }
    
    private static boolean hasBlockedYou(BufferedReader reader, PrintWriter writer, String otherName) throws IOException {
        synchronized (LOCK) {
            writer.write("hasBlockedYou");
            writer.println();
            writer.flush();
            
            writer.write(otherName);
            writer.println();
            writer.flush();
            
            writer.write(currentUser.getName());
            writer.println();
            writer.flush();
            
            return Boolean.parseBoolean(reader.readLine());
        }
    }
    
    private static void createStore(BufferedReader reader, PrintWriter writer) throws IOException {
        System.out.print("Enter store name: ");
        String name = scanner.nextLine();
        System.out.print("Enter store type (restauraunt, grocery store, etc.): ");
        String type = scanner.nextLine();
        System.out.print("Enter store address (no commas): ");
        String address = scanner.nextLine();
        
        writer.write("createStore");
        writer.println();
        writer.flush();
        
        writer.write(currentUser.getName() + "," + name + "," + type + "," + address);
        writer.println();
        writer.flush();
        
        int complete = Integer.parseInt(reader.readLine());
        
        if (complete == 200) {
            JOptionPane.showMessageDialog(null,"The Store has been added!", "Seller-Customer interchange", JOptionPane.INFORMATION_MESSAGE);
        }
        if (complete == 500) {
            JOptionPane.showMessageDialog(null, "There was an issue saving the store, please try again", "Seller-Customer interchange", JOptionPane.INFORMATION_MESSAGE);
        }
        
        sellerMenu(reader, writer);
    }
    
    private static void messageCustomerList(BufferedReader reader, PrintWriter writer) throws IOException {
        User reciever;
        ArrayList<Customer> customers = listofCustomers(reader, writer);
        
        System.out.println("The following is a list of customers that you can message: ");
        for (int i = 0; i < customers.size(); i++) {
            if (!isInvisibleToYou(reader, writer, customers.get(i).getName())) {
                System.out.println((i + 1) + ": " + customers.get(i).getName());
            } else {
                System.out.println((i + 1) + ": INVISIBLE");
            }
        }
        int choice = 0;
        do {
            System.out.println("Please enter the number of a customer:");
            choice = scanner.nextInt();
            scanner.nextLine();
        } while (!(choice >= 1 && choice <= customers.size()));
        
        reciever = customers.get(choice - 1);
        
        if (isInvisibleToYou(reader, writer, reciever.getName())) {
            System.out.println("That user is invisible to you! Please select another customer.");
            messageCustomerList(reader, writer);
        } else if (hasBlockedYou(reader, writer, reciever.getName())) {
            System.out.println("That user has blocked you! Please select another customer.");
            messageCustomerList(reader, writer);
        } else {
            messageMenu(reader, writer, reciever);
        }
        
    }
    
    private static void messageCustomerSearch(BufferedReader reader, PrintWriter writer) throws IOException {
        ArrayList<Customer> customers = listofCustomers(reader, writer);
        String searchCustomer;
        boolean found = false;
        Customer foundCust = null;
        boolean blocked = false;
        
        do {
            System.out.print("Enter the name of the customer to search for: ");
            searchCustomer = scanner.nextLine();
            
            for (Customer customer : customers) {
                if (customer.getName().equalsIgnoreCase(searchCustomer)) {
                    if (isInvisibleToYou(reader, writer, searchCustomer)) {
                        found = false;
                        break;
                    }
                    
                    if (hasBlockedYou(reader, writer, searchCustomer)) {
                        found = false;
                        blocked = true;
                        break;
                    }
                    
                    found = true;
                    foundCust = customer;
                    break;
                }
            }
            
            if (!found) {
                if (!blocked) {
                    System.out.println("Customer " + searchCustomer + " could not be found.");
                } else {
                    System.out.println("Customer " + searchCustomer + " has blocked you!");
                }
                System.out.println("Enter 1 to search again, or 2 to quit to main menu: ");
                int choice = Integer.parseInt(scanner.nextLine());
                
                if (choice == 2) {
                    customerMenu(reader, writer);
                    break;
                }
                
            }
            
            if (found) {
                if (foundCust != null) {
                    messageMenu(reader, writer, foundCust);
                } else {
                    System.out.println("There was an error with the customer search.");
                }
            }
            
        } while (!found);
        
    }
    
    
    private static void messageMenu(BufferedReader reader, PrintWriter writer, User reciever) throws IOException {
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
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character
            
            switch (choice) {
                case 0:
                    quit = true;
                    if (currentUser instanceof Customer) {
                        customerMenu(reader, writer);
                    } else if (currentUser instanceof Seller) {
                        sellerMenu(reader, writer);
                    }
                    break;
                case 1:
                    sendNewMessage(reader, writer, reciever);
                    break;
                case 2:
                    viewMessageHistory(reader, writer, reciever);
                    break;
                case 3:
                    editMessage(reader, writer, reciever);
                    break;
                case 4:
                    deleteMessage(reader, writer, reciever);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        
        
    }
    
    private static void deleteMessage(BufferedReader reader, PrintWriter writer, User reciever) throws IOException { //NET IO AFTER MERGE
        synchronized (LOCK) {
            System.out.println("You selected: Delete a message");
            try {
                BufferedReader readerB = new BufferedReader(new FileReader(convoNamingScheme(currentUser.getName(),
                        reciever.getName())));
                String[] lines = readerB.lines().toArray(String[]::new);
                readerB.close();
                int count = 1;
                
                System.out.println("Messages:");
                for (int i = 0; i < lines.length; i++) {
                    String[] tokens = lines[i].split(",");
                    if (tokens[1].equals(currentUser.getName())) {
                        System.out.print((count) + ": " + tokens[2] + " | " + tokens[1] + ": " + tokens[0]);
                        if (tokens[3].equals("true")) {
                            System.out.println(" (edited)");
                        } else {
                            System.out.println();
                        }
                        count++;
                    }
                }
                
                int lineNumber;
                System.out.println("Enter the number of the message you would like to delete:");
                lineNumber = scanner.nextInt();
                scanner.nextLine();
                
                int count2 = 1;
                for (int i = 0; i < lines.length; i++) {
                    String[] tokens = lines[i].split(",");
                    if (count2 == lineNumber) {
                        lineNumber = i + 1;
                        break;
                    }
                    if (tokens[1].equals(currentUser.getName())) {
                        count2++;
                    }
                }
                
                
                lines[lineNumber - 1] = "";
                String[] newLines = new String[lines.length - 1];
                
                for (int i = 0; i < lines.length; i++) {
                    if (i < lineNumber - 1) {
                        newLines[i] = lines[i];
                    } else if (i == lineNumber - 1) {
                    
                    } else if (i > lineNumber - 1) {
                        newLines[i - 1] = lines[i];
                    }
                }
                
                PrintWriter writerB = new PrintWriter(new FileWriter(convoNamingScheme(currentUser.getName(),
                        reciever.getName())));
                for (String line : newLines) {
                    writerB.println(line);
                }
                writerB.close();
                
                System.out.println("File successfully updated.");
            } catch (FileNotFoundException e) {
                System.out.println("The conversation does not exist!");
                messageMenu(reader, writer, reciever);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void editMessage(BufferedReader reader, PrintWriter writer, User reciever) throws IOException {  //NET IO AFTER MERGE
        synchronized (LOCK) {
            System.out.println("You selected: Edit a message");
            try {
                BufferedReader readerB = new BufferedReader(new FileReader(convoNamingScheme(currentUser.getName(),
                        reciever.getName())));
                String[] lines = readerB.lines().toArray(String[]::new);
                readerB.close();
                int count = 1;
                
                System.out.println("Messages:");
                for (int i = 0; i < lines.length; i++) {
                    String[] tokens = lines[i].split(",");
                    if (tokens[1].equals(currentUser.getName())) {
                        System.out.print((count) + ": " + tokens[2] + " | " + tokens[1] + ": " + tokens[0]);
                        if (tokens[3].equals("true")) {
                            System.out.println(" (edited)");
                        } else {
                            System.out.println();
                        }
                        count++;
                    }
                }
                
                int lineNumber;
                System.out.println("Enter the number of the message you would like to change:");
                lineNumber = scanner.nextInt();
                scanner.nextLine();
                
                int count2 = 1;
                for (int i = 0; i < lines.length; i++) {
                    String[] tokens = lines[i].split(",");
                    if (count2 == lineNumber) {
                        lineNumber = i + 1;
                        break;
                    }
                    if (tokens[1].equals(currentUser.getName())) {
                        count2++;
                    }
                }
                
                System.out.printf("Enter the new content for line %d:%n", count2);
                String newLineContent = scanner.nextLine();
                
                lines[lineNumber - 1] = newLineContent + "," + currentUser.getName() + ","
                        + new Date().toString() + "," + true;
                
                PrintWriter writerB = new PrintWriter(new FileWriter(convoNamingScheme(currentUser.getName(),
                        reciever.getName())));
                for (String line : lines) {
                    writerB.println(line);
                }
                writerB.close();
                
                System.out.println("File successfully updated.");
            } catch (FileNotFoundException e) {
                System.out.println("The conversation does not exist!");
                messageMenu(reader, writer, reciever);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void viewMessageHistory(BufferedReader reader, PrintWriter writer, User reciever) { //NET IO AFTER MERGE
        System.out.println("You selected: View message history");
        synchronized (LOCK) {
            try {
                BufferedReader readerB = new BufferedReader(new FileReader(convoNamingScheme(currentUser.getName(),
                        reciever.getName())));
                String line;
                while ((line = readerB.readLine()) != null) {
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Press enter to continue");
            scanner.nextLine();
        }
    }
    
    private static void sendNewMessage(BufferedReader reader, PrintWriter writer, User reciever) throws NumberFormatException, IOException {
        System.out.println("You selected: Send a new message");
        System.out.println("Enter the message you would like to send to " + reciever.getName() + ": ");
        String messageStr = scanner.nextLine();
        Message message = new Message(messageStr, currentUser, reciever, new Date().toString());
        
        writer.write("sendNewMessage");
        writer.println();
        writer.flush();
        
        writer.write(currentUser.getName());
        writer.println();
        writer.flush();
        
        writer.write(reciever.getName());
        writer.println();
        writer.flush();
        
        writer.write(message.toString());
        writer.println();
        writer.flush();
        
        int complete = Integer.parseInt(reader.readLine());
        
        if (complete == 200) {
            System.out.println("Message Sent!");
        }
        
        
    }
    
    public static void customerMenu(BufferedReader reader, PrintWriter writer) throws IOException {
        int choice;
        System.out.println("Welcome Customer " + currentUser.getName() + "!");
        System.out.println("Please Select an action: ");
        System.out.println("1. Message Seller from search");
        System.out.println("2. Message Seller from list of Stores");
        System.out.println("3. Block User");
        System.out.println("4. Ghost User (set Invisible)");
        System.out.println("5. Modify User");
        System.out.println("6. Delete User");
        System.out.println("7. Logout");
        choice = scanner.nextInt();
        scanner.nextLine();
        
        
        switch (choice) {
            case 1:
                messageSellerSearch(reader, writer);
                break;
            
            case 2:
                messageSellerList(reader, writer);
                break;
            
            case 3:
                blockUser(reader, writer);
                break;
            
            case 4:
                setInvisible(reader, writer);
                break;
            
            case 5:
                modifyUser(reader, writer);
                break;
            
            case 6:
                deleteUser(reader, writer);
                break;
            
            case 7:
                System.out.println("Logging out...");
                currentUser = null;
                break;
            
            default:
                System.out.println("That's not a valid choice. Reloading menu...");
                customerMenu(reader, writer);
                break;
        }
    }
    
    private static void messageSellerList(BufferedReader reader, PrintWriter writer) throws IOException {
        writer.write("messageSellerList");
        writer.println();
        writer.flush();
        
        User reciever = null;
        ArrayList<Store> stores = new ArrayList<Store>();
        ArrayList<Seller> sellers = listOfSellers(reader, writer);
        
        int storeSize = Integer.parseInt(reader.readLine());
        
        for (int i = 0; i < storeSize; i++) {
            String[] tokens = reader.readLine().split(",");
            stores.add(new Store(tokens[0], tokens[1], tokens[2], tokens[3]));
        }
        
        
        System.out.println("The following is a list of Stores that you can contact: ");
        for (int i = 0; i < stores.size(); i++) {
            System.out.println((i + 1) + ": " + stores.get(i).getStoreName());
        }
        int choice = 0;
        do {
            System.out.println("Please enter the number of a store:");
            choice = scanner.nextInt();
            scanner.nextLine();
        } while (!(choice >= 1 && choice <= stores.size()));
        
        String storeSellerName = stores.get(choice - 1).getOwnerUsername();
        
        for (int i = 0; i < sellers.size(); i++) {
            if (sellers.get(i).getName().equals(storeSellerName)) {
                reciever = sellers.get(i);
                break;
            }
        }
        
        messageMenu(reader, writer, reciever);
        
    }
    
    private static void messageSellerSearch(BufferedReader reader, PrintWriter writer) throws IOException {
        ArrayList<Seller> sellers = listOfSellers(reader, writer);
        String searchSeller;
        boolean found = false;
        Seller foundSell = null;
        boolean blocked = false;
        
        do {
            System.out.print("Enter the name of the Sellers to search for: ");
            searchSeller = scanner.nextLine();
            
            for (Seller seller : sellers) {
                if (seller.getName().equalsIgnoreCase(searchSeller)) {
                    if (isInvisibleToYou(reader, writer, searchSeller)) {
                        found = false;
                        break;
                    }
                    
                    if (hasBlockedYou(reader, writer, searchSeller)) {
                        found = false;
                        blocked = true;
                    }
                    
                    found = true;
                    foundSell = seller;
                    break;
                }
            }
            
            if (!found) {
                if (!blocked) {
                    System.out.println("Seller " + searchSeller + " could not be found.");
                } else {
                    System.out.println("Seller " + searchSeller + " has blocked you!" );
                }
                
                System.out.println("Enter 1 to search again, or 2 to quit to main menu: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                if (choice == 2) {
                    messageSellerSearch(reader, writer);
                    break;
                }
            }
            
            if (found) {
                if (foundSell != null) {
                    messageMenu(reader, writer, foundSell);
                } else {
                    System.out.println("There was an error with the Seller search.");
                }
            }
            
        } while (!found);
    }
    
    public static ArrayList<Seller> listOfSellers(BufferedReader reader, PrintWriter writer) throws IOException {
        //  method that loads sellers arraylist with list of sellers from user.txt
        writer.write("listOfSellers");
        writer.println();
        writer.flush();
        
        String sizeSTR = reader.readLine();
        int size = Integer.parseInt(sizeSTR);
        
        ArrayList<Seller> sellers = new ArrayList<>();
        
        if (size != -1 && size != -2) {
            for (int i = 0; i < size; i++) {
                String[] tokens = reader.readLine().split(",");
                String userName = tokens[0];
                String password = tokens[1];
                String name = tokens[2].toLowerCase();
                int numMessages = Integer.parseInt(tokens[3]);
                int phoneNum = Integer.parseInt(tokens[4]);
                String address = tokens[5];
                Seller seller = new Seller(userName, password, name, numMessages, phoneNum, address);
                sellers.add(seller);
            }
        }
        
        
        return sellers;
        
    }
    
    public static ArrayList<Customer> listofCustomers(BufferedReader reader, PrintWriter writer) throws IOException {
        writer.write("listOfCustomers");
        writer.println();
        writer.flush();
        
        String sizeSTR = reader.readLine();
        int size = Integer.parseInt(sizeSTR);
        
        ArrayList<Customer> customers = new ArrayList<>();
        
        if (size != -1 && size != -2) {
            for (int i = 0; i < size; i++) {
                String[] tokens = reader.readLine().split(",");
                String userName = tokens[0];
                String password = tokens[1];
                String name = tokens[2].toLowerCase();
                int numMessages = Integer.parseInt(tokens[3]);
                int phoneNum = Integer.parseInt(tokens[4]);
                String address = tokens[5];
                Customer customer = new Customer(userName, password, name, numMessages, phoneNum, address);
                customers.add(customer);
            }
        }
        
        return customers;
    }
    public static String convoNamingScheme(String str1, String str2) {
        String[] arr = {str1, str2};
        Arrays.sort(arr);
        return arr[0] + "_" + arr[1] + ".txt";
    }
}
