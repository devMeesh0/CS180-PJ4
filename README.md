# CS 180 Project 4, Started 3/31/2023

## Authors

Ameesh Daryani (Vocareum Workspace Submission/README generation),
Jiatong Sun,        
Nathan Rusk, 
Elijah Van Grootheest (Report Submission), 
Yifei Wang

# Table of Contents
- [Seller Class](#seller-class)
- [Customer Class](#customer-class)
- [User Class](#user-class)
- [Store Class](#store-class)

# Seller Class 
The Seller class is a subclass of the User superclass. It extends the functionality of the parent class by including additional methods and fields specific to a seller.

## Fields

`stores`: an ArrayList of Store objects, representing the list of stores owned by the seller.
`customerList`: a List of Customer objects, representing the list of customers available to the seller to choose from.

## Constructors

`Seller(String username, String password, String Name, int numMessages, int phoneNum, String address)`: a constructor that instantiates a Seller object with the specified parameters. It calls the constructor of the parent class User to set the common fields such as username, password, name, number of messages, phone number, and address.

## Methods

`setCustomerList(ArrayList<Customer> customers)`: a method that sets the customerList field to the provided list of customers.
`getCustomerList()`: a method that returns the customerList field.
`parseStoresFile()`: a static method that reads the stores.txt file and returns an ArrayList of Store objects. Each line in the file should contain information about a store, including the store owner's username, store name, store type, and address.
`publishStore(String ownerUsername, String storeName, String storeType, String address)`: a method that adds a new store to the stores.txt file with the specified details.

## Author
This class was created by Elijah Van Grootheest on 4/4/2023 for the CS180 PJ 4 project.


# Customer Class
This Java class represents a Customer, which is a subclass of User. It contains methods for retrieving and setting a list of Sellers and for searching for Customers in a file based on given information.

## Fields
`sellers` : An ArrayList of type Seller that holds all the Sellers associated with this Customer.
## Constructors
`Customer()` : A default constructor that creates an empty Customer object.
`Customer(String username, String password, String name, int numMessages, int phoneNum, String address)`: A constructor that creates a Customer object with the given username, password, name, number of messages, phone number, and address.
## Methods
`getSellers() `: Returns the ArrayList of Sellers associated with this Customer.
`setSellers(ArrayList<Seller> sellers)` : Sets the ArrayList of Sellers associated with this Customer to the given ArrayList.
`searchForCustomers(String fileName ,String information)` : Searches for Customers in a file based on given information. This method takes two parameters - fileName which is the name of the file to be searched and information which is the search query. The method reads the file line by line and searches for Customers whose username, name, phone number, or address contains the given information. If a match is found, the method prints the User Type, User Name, Password, Name, Number of Message, Phone Number, and Address of the Customer.
## Author
This class was created by Jiatong Sun on 4/4/2023 as a part of CS180 PJ 4 project.

# User Class
This is a Java class that represents a user. The class has fields for the user's username, password, name, number of messages, phone number, address, and message history list. The message history list is a list of Message objects that the user has sent or received. The class also has methods for creating, modifying, and deleting messages, as well as for modifying and deleting the user's account. The class can also block or make invisible another user.

## Class Methods
## Constructors
`User()`: This is the default constructor for the User class. It creates an empty User object.
`User(String username, String password, String name, int numMessages, int phoneNum, String address)`: This is a constructor for the User class that takes in parameters for the user's username, password, name, number of messages, phone number, and address. It initializes the User object with these values.
# Getters and Setters
`String getUsername()`: This method returns the user's username.
`void setUsername(String username)`: This method sets the user's username.
`String getPassword()`: This method returns the user's password.
`void setPassword(String password)`: This method sets the user's password.
`String getName()`: This method returns the user's name.
`void setName(String name)`: This method sets the user's name.
`int getNumMessages()`: This method returns the number of messages the user has sent or received.
`void setNumMessages(int numMessages)`: This method sets the number of messages the user has sent or received.
`int getPhoneNum()`: This method returns the user's phone number.
`void setPhoneNum(int phoneNum)`: This method sets the user's phone number.
`String getAddress()`: This method returns the user's address.
`void setAddress(String address)`: This method sets the user's address.
`List<Message> getMessageHistoryList()`: This method returns the user's message history list.
`void setMessageHistoryList(List<Message> messageHistoryList)`: This method sets the user's message history list.
# Message Methods
`Message createMessage(User reciever, String message, String timestamp)`: This method creates a new Message object with the user as the sender and the specified User object as the receiver. It takes in parameters for the receiver, message, and timestamp, and returns the new Message object.
void addMessageToHistory(Message message): This method adds a Message object to the user's message history list.
void removeMessageFromHistory(int index): This method removes a Message object from the user's message history list at the specified index.
`List<Message> getAllMessages()`: This method returns a list of all Message objects in the user's message history list.
void modifyingMessage(Message message, int index): This method modifies a Message object in the user's message history list at the specified index.
`List<Message> getMessageHistoryByReciever(User reciever)`: This method returns a list of Message objects in the user's message history list that were sent to the specified User object.
`List<Message> getMessageHistoryBySender(User sender)`: This method returns a list of Message objects in the user's message history list that were sent by the specified User object.
# Account Methods
`void modifyUser()`: This method prompts the user to input their name, phone number, and address again, and updates the user's account with the new information.
`deleteUser()`: This method deletes the user's account by removing their username, password, and personal information from all text files. All fields in the current User instance are set to null or zero.
`blockUser(User blocked)`: This method adds an entry to blocked.txt detailing who is blocking who. It takes another User object as input and adds the current user's username and the other user's username to the text file.
`makeInvisible(User ghost)`: This method adds an entry to invisible.txt detailing who is being made invisible to who. It takes another User object as input and adds the current user's username and the other user's username to the text file.

# Store Class
The Store class represents a store object that contains the store owner's username, the store name, the store type, and the store address. It also provides a constructor, getters, and setters for these fields. Additionally, it includes a static method publish that takes in the store's details and writes them to a file called stores.txt.

## Fields
`ownerUsername`: a String that represents the store owner's username.
`storeName`: a String that represents the store's name.
`storeType`: a String that represents the store's type.
`address`: a String that represents the store's address.
## Methods
## Constructor
`public Store(String ownerUserName, String storeName, String storeType, String address)`: 
Creates a new instance of the Store class with the provided store owner's username, store name, store type, and store address.

## Getters and Setters

`public String getOwnerUsername()`
`public String getStoreName()`
`public String getStoreType()`
`public String getAddress()`
`public void setOwnerUsername(String ownerUsername)`
`public void setStoreName(String storeName)`
`public void setStoreType(String storeType)`
`public void setAddress(String address)`
These methods are used to access and modify the fields of a Store object.

## Publish

`public static void publish(String ownerUserName, String storeName, String storeType, String address)`:
This method takes in the store's details as arguments and writes them to a file called stores.txt. If the file does not exist, it will be created. If it does exist, the new store information will be appended to the end of the file.