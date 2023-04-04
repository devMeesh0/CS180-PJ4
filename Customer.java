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
    
    public Customer(String username, String password, String name, int numMessages, int phoneNum, String address, ArrayList<Seller> sellers) {
        super(username, password, name, numMessages,phoneNum,address);
        this.sellers = sellers;
    }
    
    public static ArrayList<Seller> viewListOfSellers() {
        //TODO: method that loads sellers arraylist with list of sellers from user.txt
        File f = new File("user.txt");
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            ArrayList<String> sellerList = new ArrayList<>();
            String line = "";
            while ((line = br.readLine()) != null) {
                sellerList.add(line);
            }
            return sellerList; // need to change
        } catch (FileNotFoundException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    public void searchForCustomers(String fileName ,String information) {
        //have some questions
    }
}

