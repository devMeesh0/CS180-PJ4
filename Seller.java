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
    private List<Customer> customerList; //to instantiate a list of cust. to choose from
    
    public Seller(String username, String password, String Name, int numMessages, int phoneNum, String address) {
        //creates constructor
        super(username, password, Name, numMessages, phoneNum, address ); //pulls from user superclass
        this.customerList = listofCustomers(); //instantiates customer list or should it be...
        //customerList = new ArrayList<>();

    }
    
    public ArrayList<Customer> listofCustomers() {
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