import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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
        } catch (IOException e ) {
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
                    //TODO: add login method
                    boolean success = login(scan);
                    if (success) {
                        System.out.println("You have successfully logged in!");
                        if (currentUser instanceof Customer) {
                            customerMenu();
                        }
                        if (currentUser instanceof Seller) {
                            sellerMenu();
                        }
                    } 
                    if (!success) {
                        System.out.println("Goodbye!");
                    }
                    break;
                }

                case 2: {
                    //TODO: add create account method
                    createAccount(scan, "user.txt");
                    break;
                }

                case 3: {
                    System.out.println("Thanks for using the interface!");
                    scan.close();
                    break;
                }
            }
        } else throw new InvalidInputException("The choice entered was invalid!");
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
                        setUser(line);
                        return true; // Success
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Invalid username or password. Please try again.");
        }
    }
    
    private static void setUser(String line) {
        
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
    
        try (PrintWriter writer = new PrintWriter(new File("user.txt"))) {
          writer.println(accountType + "," + username + "," + password + "," + name + "," + 0 + "," + phoneNum + "," + address);
          System.out.println("User created! You are now logged in");
        } catch (FileNotFoundException e) {
          System.err.println("Error opening file for writing: " + fileName);
          e.printStackTrace();
        }


      }

    public static void sellerMenu() {
        // TODO: implement method
        System.out.println("Welcome Seller " + currentUser.getName() + "!");
        System.out.println("Please Select an action  ");
    }

    public static void customerMenu() {
        // TODO: implement method
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
}
