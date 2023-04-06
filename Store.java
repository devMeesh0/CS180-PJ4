/**
 * CS180 PJ 4
 *
 * Store class
 *
 * @author Nathar Rusk
 * @version 4/5/2023
 */
public class Store {
    public String name;
    public String storeType;
    public String address;

    //TODO: create constructor, getters and setters
    public Store(String name, String storeType, String address) {
        this.name = name;
        this.storeType = storeType;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getStoreType() {
        return storeType;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //TODO: method that takes store and adds it to stores.txt
    public void publish() {

    }
}