import java.util.*;

public class Customer extends User {
    public static ArrayList<Seller> sellers;
    
    public Customer() {
    
    }

    //TODO: method that loads sellers arraylist with list of sellers from user.txt
    public static ArrayList<Seller> viewListOfSellers() {
        return sellers;
    }
    
    
}
