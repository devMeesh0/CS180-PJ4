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
        //   implement method
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
                    //   add login method
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
                    //   add create account method
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
        //   implement method
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
                messageCustomerList(scan);
                break;
        
            case 3:
                createStore(scan);
                break;

            case 4:
                blockUser(scan);
                break;

            case 5:
                setInvisible(scan);
                break;

            case 6:
                modifyUser(scan);
                break;

            case 7:
                deleteUser(scan);
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

    

    private static void deleteUser(Scanner scan) {
        System.out.println("ARE YOU SURE YOU WANT TO DELETE ACCOUNT? (Y/N)");
        String choice = scan.nextLine().toLowerCase();

        if (choice.equals("y")) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("user.txt"));
                String[] lines = reader.lines().toArray(String[]::new);
                reader.close();
                int count = 1;
                for (int i = 0; i < lines.length; i++) {
                    String[] tokens = lines[i].split(",");
                    if (tokens[3].equals(currentUser.getName())) {
                        break;
                    }
                    count++;
                }
    
                int lineNumber = count;
                
                           
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
                
                PrintWriter writer = new PrintWriter(new FileWriter("user.txt"));
                for (String line : newLines) {
                    writer.println(line);
                }
                writer.close();
                
                System.out.println("File successfully updated.");
            } catch (FileNotFoundException e) {
                System.out.println("The File does not exist!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Goodbye!");
        } else {
            if (currentUser instanceof Seller) {
                sellerMenu(scan);
            }
            if (currentUser instanceof Customer) {
                customerMenu(scan);
            }
        }
    }

    private static void modifyUser(Scanner scan) {
        currentUser.modifyUser();
        if (currentUser instanceof Seller) {
            sellerMenu(scan);
        }
        if (currentUser instanceof Customer) {
            customerMenu(scan);
        }
    }

    private static void setInvisible(Scanner scan) {
        System.out.println("Please enter the name of the User you would like to set yourself invisible for:");
        String name = scan.nextLine().toLowerCase();

        if (currentUser instanceof Customer) {
            ArrayList<Seller> sellers = listOfSellers();
            for (int i = 0; i < sellers.size(); i++) {
                if (sellers.get(i).getName().equals(name)) {
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
            customerMenu(scan);
        } 
        if (currentUser instanceof Seller) {
            ArrayList<Customer> customers = listofCustomers();
            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).getName().equals(name)) {
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
            sellerMenu(scan);
        }
    }

    private static void blockUser(Scanner scan) {
        System.out.println("Please enter the name of the User you would like to block:");
        String name = scan.nextLine().toLowerCase();

        if (currentUser instanceof Customer) {
            ArrayList<Seller> sellers = listOfSellers();
            for (int i = 0; i < sellers.size(); i++) {
                if (sellers.get(i).getName().equals(name)) {
                    File file = new File("blocked.txt");
                    FileWriter fw;
                    try {
                        fw = new FileWriter(file, true);
                        PrintWriter pw = new PrintWriter(fw);
                        pw.println(currentUser.getName() + "," + name);
                        pw.close();
                        System.out.println("The user has been blocked.");
                    } catch (IOException e) {
                        //   Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            customerMenu(scan);
        }
        
        if (currentUser instanceof Seller) {
            ArrayList<Customer> customers = listofCustomers();
            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).getName().equals(name)) {
                    File file = new File("blocked.txt");
                    FileWriter fw;
                    try {
                        fw = new FileWriter(file, true);
                        PrintWriter pw = new PrintWriter(fw);
                        pw.println(currentUser.getName() + "," + name);
                        pw.close();
                        System.out.println("The user has been blocked.");
                    } catch (IOException e) {
                        //   Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            sellerMenu(scan);
        }
    }

    private static boolean isInvisibleToYou(User other) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("invisible.txt"));

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (tokens[1].equals(currentUser.getName()) && tokens[0].equals(other.getName())) {
                        return true;
                    }
                }
            } catch (IOException e) {
                //   Auto-generated catch block
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound!");
        }
        return false;
    }

    private static boolean hasBlockedYou(User other) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("blocked.txt"));

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (tokens[1].equals(currentUser.getName()) && tokens[0].equals(other.getName())) {
                        return true;
                    }
                }
            } catch (IOException e) {
                //   Auto-generated catch block
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound!");
        }
        return false;
    }

    private static void createStore(Scanner scan) {
        System.out.print("Enter store name: ");
        String name = scan.nextLine();
        System.out.print("Enter store type (restauraunt, grocery store, etc.): ");
        String type = scan.nextLine();
        System.out.print("Enter store address (no commas): ");
        String address = scan.nextLine();
        
        // write to file
        try {
            File file = new File("stores.txt");
            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(currentUser.getName() + "," + name + "," + type + "," + address);
            pw.close();
            System.out.println("Store added to file.");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
            e.printStackTrace();
        }
        sellerMenu(scan);
    }

    private static void messageCustomerList(Scanner scan) {
        User reciever;
        ArrayList<Customer> customers = listofCustomers();
        System.out.println("The following is a list of customers that you can message: ");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i + 1) + ": " + customers.get(i).getName());
        }
        int choice = 0;
        do {
            System.out.println("Please enter the number of a customer:");
            choice = scan.nextInt();
            scan.nextLine();
        } while (!(choice >= 1 && choice <= customers.size()));

        reciever = customers.get(choice - 1);

        messageMenu(scan, reciever);

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
                    editMessage(scan, reciever);
                    break;
                case 4:
                    deleteMessage(scan, reciever);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        
    }

    private static void deleteMessage(Scanner scan, User reciever) {
        System.out.println("You selected: Delete a message");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(convoNamingScheme(currentUser.getName(),
                reciever.getName())));
            String[] lines = reader.lines().toArray(String[]::new);
            reader.close();
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
            lineNumber = scan.nextInt();
            scan.nextLine();

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
            
            PrintWriter writer = new PrintWriter(new FileWriter(convoNamingScheme(currentUser.getName(),
                reciever.getName())));
            for (String line : newLines) {
                writer.println(line);
            }
            writer.close();
            
            System.out.println("File successfully updated.");
        } catch (FileNotFoundException e) {
            System.out.println("The conversation does not exist!");
            messageMenu(scan, reciever);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void editMessage(Scanner scan, User reciever) {
        System.out.println("You selected: Edit a message");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(convoNamingScheme(currentUser.getName(),
                reciever.getName())));
            String[] lines = reader.lines().toArray(String[]::new);
            reader.close();
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
            lineNumber = scan.nextInt();
            scan.nextLine();

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
            String newLineContent = scan.nextLine();
            
            lines[lineNumber - 1] = newLineContent + "," + currentUser.getName() + "," 
                + new Date().toString() + "," + true;
            
            PrintWriter writer = new PrintWriter(new FileWriter(convoNamingScheme(currentUser.getName(),
                reciever.getName())));
            for (String line : lines) {
                writer.println(line);
            }
            writer.close();
            
            System.out.println("File successfully updated.");
        } catch (FileNotFoundException e) {
            System.out.println("The conversation does not exist!");
            messageMenu(scan, reciever);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private static void viewMessageHistory(Scanner scan, User reciever) {
        System.out.println("You selected: View message history");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(convoNamingScheme(currentUser.getName(),
                reciever.getName())));
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
        File file = new File(convoNamingScheme(currentUser.getName(), reciever.getName()));
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
    }

    public static void customerMenu(Scanner scan) {
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
        choice = scan.nextInt();
        scan.nextLine();

        
        switch (choice) {
            case 1:
                messageSellerSearch(scan);
                break;
                
            case 2:
                messageSellerList(scan);
                break;
        
            case 3:
                blockUser(scan);
                break;

            case 4:
                setInvisible(scan);
                break;

            case 5:
                modifyUser(scan);
                break;

            case 6:
                deleteUser(scan);
                break;

            case 7:
                System.out.println("Logging out...");
                currentUser = null;
                break;

            default:
                System.out.println("That's not a valid choice. Reloading menu...");
                customerMenu(scan);
                break;
        }
    }

    private static void messageSellerList(Scanner scan) {
        User reciever = null;
        ArrayList<Store> stores = new ArrayList<Store>();
        ArrayList<Seller> sellers = listOfSellers();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("stores.txt"));
            
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");

                    stores.add(new Store(tokens[0], tokens[1], tokens[2], tokens[3]));
                }
            } catch (IOException e) {
                //   Auto-generated catch block
                e.printStackTrace();
            }

            System.out.println("The following is a list of Stores that you can contact: ");
            for (int i = 0; i < stores.size(); i++) {
                System.out.println((i + 1) + ": " + stores.get(i).getStoreName());
            }
            int choice = 0;
            do {
                System.out.println("Please enter the number of a store:");
                choice = scan.nextInt();
                scan.nextLine();
            } while (!(choice >= 1 && choice <= stores.size()));

            String storeSellerName = stores.get(choice - 1).getOwnerUsername();

            for (int i = 0; i < sellers.size(); i++) {
                if (sellers.get(i).getName().equals(storeSellerName)) {
                    reciever = sellers.get(i);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            //   Auto-generated catch block
            e.printStackTrace();
        }
        messageMenu(scan, reciever);

    }

    private static void messageSellerSearch(Scanner scan) {
        ArrayList<Seller> sellers = listOfSellers();
        String searchSeller;
        boolean found = false;
        Seller foundSell = null;

        do {
            System.out.print("Enter the name of the Sellers to search for: ");
            searchSeller = scan.nextLine();

            for (Seller seller : sellers) {
                if (seller.getName().equalsIgnoreCase(searchSeller)) {
                    found = true;
                    foundSell = seller;
                    break;
                }
            }

            if (!found) {
                System.out.println("Seller " + searchSeller + " could not be found.");

                System.out.println("Enter 1 to search again, or 2 to quit to main menu: ");
                int choice = scan.nextInt();
                scan.nextLine();

                if (choice == 2) {
                    messageSellerSearch(scan);
                    break;
                }
            }

            if (found) {
                if (foundSell != null) {
                    messageMenu(scan, foundSell);
                } else {
                    System.out.println("There was an error with the Seller search.");
                }
            } 

        } while (!found);
    }

    public static ArrayList<Seller> listOfSellers() {
        //  method that loads sellers arraylist with list of sellers from user.txt
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
                    String name = part[3].toLowerCase();
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
                    String name = part[3].toLowerCase();
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
