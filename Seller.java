import java.util.ArrayList;
+import java.util.*;
+import java.io.*;
/**
 * CS180 PJ 4
 *
 * Seller subclass extending User Super class
 *
 * @author Elijah Van Grootheest
 * @version 4/4/2023
 */
public class Seller extends User{
    public static ArrayList<Store> stores;


           public static ArrayList<Store> stores; //list of stores
    private List<Customer> customerList; //to instantiate a list of cust. to choose from
   public Seller(String username, String password, String Name, int numMessages, int phoneNum, String address) {
                //creates constructor
                      super(username,password,Name,numMessages,phoneNum,address); //pulls from user superclass
               this.customerList = customerList; //instantiates customer list or should it be...
                //customerList = new ArrayList<>();

                    public Seller() {
               }
           public ArrayList<Customer> ListofCustomers() {
                   try {
                           File fc = new File("user.txt"); //instantiates file for user info
                           BufferedReader bfrc = null; //intantiates buffer reader
                           bfrc = new BufferedReader(new FileReader(fc)); //created buffer reader and file reader
                           ArrayList<String> customerList = new ArrayList<>();
                           String line = "";
                             while ((line = bfrc.readLine()) != null) {
                                   if(User instanceof Customer) {
                                           //not 100% how to implement this but should only add line if user ==
                                                   // customer or rather AccountType defined in main and stored in
                                       // user.txt is customer
                                                           String[] parts = line.split(","); //splits csv at comma
                                           //customerList.add(line);
                                                   String name = parts[3]; //creates name using 3rd element in csv
                                             customerList.add(name); //adds to customer list
                                             bfrc.readLine(); //continues to read file
                                         }
                                 } bfrc.close(); //closes file

                         }
                         return customerList;
                     } catch (FileNotFoundException e) {
                         return null;
                     } catch (Exception e) {
                         e.printStackTrace;

                             }
    }
     public List<Customer> setCustomerList() {
                 this.customerList = customerList;
             }

             public List<Customer> getCustomerList() {
                 return customerList;
             }




    public static ArrayList<Store> parseStoresFile() {
                 //actually q about this. Are we just storing store names or
                        // store name and business owner name in a single string as 1 element of the array?
                        // TODO
                                return stores;
                 try {
                         File fs = new File("stores.txt"); //instantiates file for user info
                         BufferedReader bfrs = null; //intantiates buffer reader
                         bfrs = new BufferedReader(new FileReader(fc)); //created buffer reader and file reader
                         ArrayList<String> storesList = new ArrayList<>();
                         String line = "";
                         while ((line = bfrc.readLine()) != null) {
                                     storesList.add(line);
                                     bfrs.readLine();
                             } bfrs.close();


                                  }
                return storesList;
             } catch (FileNotFoundException e) {
                 return null;
             } catch (Exception e) {
                 return null;

    }

    public void createStore(String storeName) {
               return stores;
                }
            public void createStore(String ownerUsername, String storeName, String storeType, String address) {
              //creates Store object
                FileWriter fws = new FileWriter("stores.txt", true);
                fws.write(ownerUsername+","+storeName+","+storeType+","+address);
                fws.close();

    }
}
