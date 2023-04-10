import java.io.*;

/**
 * CS180 PJ 4
 *
 * Store class
 *
 * @author Nathan Rusk
 * @version 4/5/2023
 */
public class Store {
    public String ownerUsername;
    public String storeName;
    public String storeType;
    public String address;

    //TODO: create constructor, getters and setters
    public Store(String ownerUserName, String storeName, String storeType, String address) {
        this.ownerUsername = ownerUserName;
        this.storeName = storeName;
        this.storeType = storeType;
        this.address = address;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreType() {
        return storeType;
    }

    public String getAddress() {
        return address;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //TODO: method that takes store and adds it to stores.txt
    public static void publish(String ownerUserName, String storeName, String storeType, String address) {
        try {
            File f = new File("stores.txt");
            FileOutputStream fos = new FileOutputStream(f, true);
            PrintWriter pw = new PrintWriter(fos);
            pw.write(ownerUserName + ","+ storeName + "," + storeType + "," + address + "\n");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}