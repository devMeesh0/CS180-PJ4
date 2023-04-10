import java.util.*;
import java.io.*;

/**
 * CS180 PJ 4
 *
 * Customer class extends User
 *
 * @author Jiatong Sun
 * @version 4/4/2023
 */

public class Customer extends User {
    public static ArrayList<Seller> sellers;
    public Customer() {
    
    }
    
    public Customer(String username, String password, String name, int numMessages, int phoneNum, String address) {
        super(username, password, name, numMessages,phoneNum,address);
    }
    
    public ArrayList<Seller> getSellers() {
        return sellers;
    }
    
    public void setSellers(ArrayList<Seller> sellers) {
        this.sellers = sellers;
    }
    
    
    
    public void searchForCustomers(String fileName ,String information) {
        try {
            Scanner scanner = new Scanner(System.in);
            File f = new File(fileName);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String input = scanner.nextLine();
                String[] part = line.split(",");
                if (part.length != 7) {
                    continue;
                }
                String userType = part[0];
                String userName = part[1];
                String password = part[2];
                String name = part[3];
                int numMessages = Integer.parseInt(part[4]);
                int phoneNum = Integer.parseInt(part[5]);
                String address = part[6];
                if (userName.contains(information) ||
                        name.contains(information) ||
                        phoneNum == Integer.parseInt(information) ||
                        address.contains(information)) {
                    System.out.printf("User Type: %s\nUser Name: %s\nPassword: %s" +
                                    "\nName: %s\nNumber of Message: \n" +
                                    "Phone Number: %s\nAddress: %s",
                            userType, userName, password, name, numMessages, phoneNum, address);
                    System.out.println();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


