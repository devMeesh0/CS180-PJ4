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

    User currentUser;
        
    public static void main(String[] args) {
        // TODO: implement method
        Scanner scan = new Scanner(System.in);

        try {
            printWelcomeMenu(scan);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
        

        scan.close();
    }

    public static void printWelcomeMenu(Scanner scan) throws InvalidInputException {
        
        System.out.println("Hello and Welcome to the Seller-Customer interface!");
        System.out.println("Please select an option to begin:");
        System.out.println("1. Login");
        System.out.println("2. Create an account");
        System.out.println("3. Quit");
        int choice = Integer.parseInt(scan.nextLine());
        scan.nextLine();
        
        if (choice == 1 || choice == 2 || choice == 3) {
            switch (choice) {
                case 1: {
                    //TODO: add login method
                    break;
                }

                case 2: {
                    //TODO: add create account method
                    break;
                }

                case 3: {
                    System.out.println("Thanks for using the interface!");
                    break;
                }
            }
        } else throw new InvalidInputException("The choice entered was invalid!");
    }
    
    public String[] getStores() {
        // TODO: implement method
        return new String[0];
    }

    public void createAccount(String username, String name, String password, String accountType) {
        // TODO: implement method
    }

    public void sellerMenu() {
        // TODO: implement method
    }

    public void customerMenu() {
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
