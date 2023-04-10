import java.util.ArrayList;
import java.util.*;
import java.io.*;
// Elijah
/**
 * CS180 PJ 4
 *
 * Seller subclass extending User Super class
 *
 * @author Elijah Van Grootheest
 * @version 4/4/2023
 */
public class Seller extends User{

    public static ArrayList<Store> stores; //list of stores
    private static List<Customer> customerList; //to instantiate a list of cust. to choose from
    
    public Seller(String username, String password, String Name, int numMessages, int phoneNum, String address) {
        //creates constructor
        super(username, password, Name, numMessages, phoneNum, address ); //pulls from user superclass
         //instantiates customer list or should it be...
        //customerList = new ArrayList<>();

    }
    
    
    
    public void setCustomerList(ArrayList<Customer> customers) {
        this.customerList = customerList;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public static ArrayList<Store> parseStoresFile() {
        //actually q about this. Are we just storing store names or
        // store name and business owner name in a single string as 1 element of the array?
        // TODO
        try {
            File fs = new File("stores.txt"); //instantiates file for user info
            BufferedReader bfrs = null; //intantiates buffer reader
            bfrs = new BufferedReader(new FileReader(fs)); //created buffer reader and file reader
            ArrayList<Store> storesList = new ArrayList<>();
            String line = "";
            while ((line = bfrs.readLine()) != null) {
                    // storesList.add(line);
                    bfrs.readLine();
            } bfrs.close();

            return storesList;
        } catch (FileNotFoundException e) {
            return null;
        } catch (Exception e) {
            return null;
        }

    // return stores; 
}

    public void publishStore(String ownerUsername, String storeName, String storeType, String address) {
    try (//adds store to stores.txt
        FileWriter fws = new FileWriter("stores.txt", true)) {
            fws.write(ownerUsername+","+storeName+","+storeType+","+address);
            fws.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}