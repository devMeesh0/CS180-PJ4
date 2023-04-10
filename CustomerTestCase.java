import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
/**
 * CS180 PJ 4
 *
 * Customer TestCase
 *
 * @author Jiatong Sun
 * @version 4/4/2023
 */

public class CustomerTestCase {
    
    @Test
    public void testViewListOfSellers() {
        try {
            FileWriter fw = new FileWriter("user.txt");
            fw.write("Seller,Wyf,wyf2004,Yifei Wang,0,7783937383,12 ShangHai\n");
            fw.write("Seller,Cjf,cjf2004,Jifei Chen,5,2374839489,478 ChengDu\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Seller> sellers = Customer.viewListOfSellers();
        assertEquals(2, sellers.size());
        
        File file = new File("user.txt");
        file.delete();
    }
    @Test
    public void testViewListOfSellersWrongData() {
        try {
            FileWriter fw = new FileWriter("user.txt");
            fw.write("Customer,Wyf,wyf2004,Yifei Wang,0,7783937383,12 ShangHai\n");
            fw.write("Seller,Cjf,cjf2004,Jifei Chen,5,2374839489\n"); // Missing address field
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Seller> sellers = Customer.viewListOfSellers();
        assertNull(sellers);
        File file = new File("user.txt");
        file.delete();
    }
    @Test
    public void testSearchForCustomers() throws IOException {
        FileWriter fw = new FileWriter("user.txt");
        fw.write("Seller,Wyf,wyf2004,Yifei Wang,0,7783937383,12 ShangHai\n");
        fw.write("Seller,Cjf,cjf2004,Jifei Chen,5,2374839489,478 ChengDu\n");
        fw.close();
        Customer customer = new Customer();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        customer.searchForCustomers("user.txt","jf");
        String expected = "User Type: Seller\nUser Name: Cjf\nPassword: cjf2004\nName: Jifei Chen\nNumber of Message: 5\nPhone Number: 23748394890\nAddress: 478 ChengDu\n";
        assertEquals(expected, outContent.toString());
        File file = new File("user.txt");
        file.delete();
    }
 
    
    
    
    
    
    
}