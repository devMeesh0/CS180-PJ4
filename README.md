# CS 180 Project 4, Started 3/31/2023

## Authors

Ameesh Daryani (Vocareum Workspace Submission),
Jiatong Sun, 
Nathan Rusk, 
Elijah Van Grootheest (Report Submission), 
Yifei Wang

# Table of Contents
- [Seller Class](#seller-class)

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
