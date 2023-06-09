import java.util.*;
import java.io.*;
/**
 * CS180 PJ 4
 *
 * User
 *
 * @author Jiatong Sun, Yifei Wang
 * @version 4/4/2023
 */

public class User {
    public String username;
    public String password;
    
    public String name;
    public int numMessages;
    public int phoneNum;
    public String address;
    public List<Message> messageHistoryList;
    public User() {

    }
    public User(String username, String password, String name, int numMessages, int phoneNum, String address) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.numMessages = numMessages;
        this.phoneNum = phoneNum;
        this.address = address;

        
    }
    //Getters and Setters

    public List<Message> getMessageHistoryList() {
        return messageHistoryList;
    }

    public void setMessageHistoryList(List<Message> messageHistoryList) {
        this.messageHistoryList = messageHistoryList;
    }

    //Create a new message
    public Message createMessage(User reciever, String message, String timestamp) {
        Message newMessage = new Message(message, this, reciever, timestamp);
        return newMessage;
    }
    public void addMessageToHistory(Message message) {
        messageHistoryList.add(message);
    }
    //Remove a message from the message history
    public void removeMessageFromHistory( int index) {
        messageHistoryList.remove(index);
    }
    //Retrieve all messages from the message history
    public List<Message> getAllMessages() {
        return messageHistoryList;
    }
    //Retrieve a specific message from the message history

    public void modifyingMessage(Message message, int index) {
        messageHistoryList.set(index, message);
    }

    //Retrieve all messages from the message history for the specified recipient
    public List<Message> getMessageHistoryByReciever(User reciever) {
        List<Message> messageHistory = new ArrayList<>();
        for (Message message : messageHistoryList) {
            if (message.getReciever().equals(reciever)) {
                messageHistory.add(message);
            }
        }
        return messageHistory;
    }

    //Retrieve all messages from the message history for the specified sender
    public List<Message> getMessageHistoryBySender(User sender) {
        List<Message> messageHistory = new ArrayList<>();
        for (Message message : messageHistoryList) {
            if (message.getSender().equals(sender)) {
                messageHistory.add(message);
            }
        }
        return messageHistory;
    }



    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getNumMessages() {
        return numMessages;
    }
    
    public void setNumMessages(int numMessages) {
        this.numMessages = numMessages;
    }
    
    public int getPhoneNum() {
        return phoneNum;
    }
    
    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    //  complete constructor, add getters and setters for fields, and complete methods marked below
    
    
    
    //  using scanner, ask the user to input their info again (name, phone num, address)
    public void modifyUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your name: ");
        String newName = scanner.nextLine();
        System.out.println("Please enter your phone number: ");
        int newPhoneNum = scanner.nextInt();
        System.out.println("Please enter your address: ");
        String newAddress = scanner.nextLine();
        this.name = newName;
        this.phoneNum = newPhoneNum;
        this.address = newAddress;
        System.out.println("User information updated.");
        scanner.close();
    }
    
    //  take username, password, and info out of all text files. set all values in this instance to null or zero
    public void deleteUser() {
    
    }
    
    //  add entry in blocked.txt detailing who is blocking who
    public void blockUser(User blocked) {
        try {
            File f = new File("blocked.txt");
            FileWriter fw = new FileWriter(f, true);
            fw.write(this.username + "," + blocked.getUsername() + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //  add entry in invisible.txt detailing who is being made invisible to who
    public void makeInvisible(User ghost) {
        try {
            File f = new File("invisible.txt");
            FileWriter fw = new FileWriter(f, true);
            fw.write(this.username + "," + ghost.getUsername() + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}