import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class WebsiteServer {
    public static void main(String[] args) {
        boolean run = true;

        // open socket
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(4242);
            Socket socket = serverSocket.accept();

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream()); 

            
                // get output from socket, load first parameter into 'choice' variable
                // create switch statement to redirect the choice to any of the neccessary methods
                // if case -> quit, close socket and end program by setting run = false;
            while (run) {

                String choice = reader.readLine();


                switch (choice) {

                    case "Login" -> {
                        login(reader, writer);
                        break;
                    }

                    case "createAccount" -> {
                        System.out.println("createAccount Called");
                        createAccount(reader, writer);
                        break;
                    }

                    case "quit" -> {
                        run = false;                        
                        break;
                    }

                    case "deleteUser" -> {
                        System.out.println("deleteUser called");

                        String name = reader.readLine();

                        deleteUser(reader, writer, name);
                        break;
                    }

                    case "listOfCustomers" -> {
                        System.out.println("listOfCustomers called");
                        listOfCustomers(reader, writer);
                        break;
                    }

                    case "listOfSellers" -> {
                        System.out.println("listOfSellers Called");
                        listOfSellers(reader, writer);
                        break;
                    }

                    case "isInvisibleToYou" -> {
                        System.out.println("isInvisibleToYou Called");
                        isInvisibleToYou(reader, writer);
                    }

                    case "hasBlockedYou" -> {
                        System.out.println("hasBlockedYou called");
                        hasBlockedYou(reader, writer);
                    }

                    case "blockUser" -> {
                        System.out.println("BlockUser called");
                        blockUser(reader, writer);
                    }

                    case "createStore" -> {
                        System.out.println("createStore called");
                        createStore(reader, writer);
                    }

                    case "messageSellerList" -> {
                        System.out.println("messafeSellerList called");
                        messageSellerList(reader, writer);
                    }

                    case "sendNewMessage" -> {
                        System.out.println("sendNewMessage");
                        sendNewMessage(reader, writer);
                    }

                }
            }
            // recreate all methods that read or write to files + all methods that do any major processing
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendNewMessage(BufferedReader reader, PrintWriter writer) throws IOException {
       
        String currentName = reader.readLine();
        String recieverName = reader.readLine();
        String messToStr = reader.readLine();

        
        File file = new File(convoNamingScheme(currentName, recieverName));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            PrintWriter writerB = new PrintWriter(new FileOutputStream(file, true));
            writerB.append(messToStr);
            writerB.println();
            writerB.flush();
            
            writer.write("200");
            writer.println();
            writer.flush();
            writerB.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void messageSellerList(BufferedReader reader, PrintWriter writer) {
        ArrayList<Store> stores = new ArrayList<>();
        
        BufferedReader readerB;
        try {
            readerB = new BufferedReader(new FileReader("stores.txt"));
        
            
            String line;
            try {
                while ((line = readerB.readLine()) != null) {
                    String[] tokens = line.split(",");

                    stores.add(new Store(tokens[0], tokens[1], tokens[2], tokens[3]));
                }

                writer.write(stores.size());
                writer.println();
                writer.flush();

                for (int i = 0; i < stores.size(); i++) {
                    writer.write(stores.get(i).getOwnerUsername()
                                + "," + stores.get(i).getStoreName()
                                + "," + stores.get(i).getStoreType()
                                + "," + stores.get(i).getAddress());
                }

            } catch (IOException e) {
                //   Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void createStore(BufferedReader reader, PrintWriter writer) throws IOException {
        // write to file

        String[] tokens = reader.readLine().split(",");

        try {
            File file = new File("stores.txt");
            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(tokens[0] + "," + tokens[1] + "," + tokens[2] + "," + tokens[3]);
            pw.close();
            
            writer.write("200");
            writer.println();
            writer.flush();

        } catch (IOException e) {
            writer.write("500");
            writer.println();
            writer.flush();
            e.printStackTrace();
        }
    }

    private static void blockUser(BufferedReader reader, PrintWriter writer) throws IOException {
        String currentName = reader.readLine();
        String name = reader.readLine();
        


        if (reader.readLine().equals("listOfSellers")) {
            listOfSellers(reader, writer);
            ArrayList<Seller> sellers = new ArrayList<>();

            int size = Integer.parseInt(reader.readLine());
            
            for (int i = 0; i < size; i++) {
                String[] tokens = reader.readLine().split(",");
                sellers.add(new Seller(tokens[0], tokens[1], tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), tokens[5]));
            }

            for (int i = 0; i < sellers.size(); i++) {
                if (sellers.get(i).getName().equals(name)) {
                    File file = new File("blocked.txt");
                    FileWriter fw;
                    try {
                        fw = new FileWriter(file, true);
                        PrintWriter pw = new PrintWriter(fw);
                        pw.println(currentName + "," + name);
                        pw.close();
                        
                    } catch (IOException e) {
                        //   Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

        if (reader.readLine().equals("listOfSellers")) {
            listOfCustomers(reader, writer);
            ArrayList<Customer> customers = new ArrayList<>();

            int size = Integer.parseInt(reader.readLine());
            
            for (int i = 0; i < size; i++) {
                String[] tokens = reader.readLine().split(",");
                customers.add(new Customer(tokens[0], tokens[1], tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), tokens[5]));
            }

            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).getName().equals(name)) {
                    File file = new File("blocked.txt");
                    FileWriter fw;
                    try {
                        fw = new FileWriter(file, true);
                        PrintWriter pw = new PrintWriter(fw);
                        pw.println(currentName + "," + name);
                        pw.close();
                        
                    } catch (IOException e) {
                        //   Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        
    
    }

    private static void hasBlockedYou(BufferedReader reader, PrintWriter writer) throws IOException {
        String otherName = reader.readLine();
        String currentUserName = reader.readLine();

        try {
            BufferedReader readerB = new BufferedReader(new FileReader("blocked.txt"));

            String line;
            try {
                while ((line = readerB.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (tokens[1].equals(currentUserName) && tokens[0].equalsIgnoreCase(otherName)) {
                        writer.write("true");
                        writer.println();
                        writer.flush();
                        return;
                    }
                }
            } catch (IOException e) {
                //   Auto-generated catch block
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound!");
        }
        writer.write("false");
        writer.println();
        writer.flush();
    }

    private static void isInvisibleToYou(BufferedReader reader, PrintWriter writer) throws IOException {
        String otherName = reader.readLine();
        String currentUserName = reader.readLine();

        try {
            BufferedReader readerB = new BufferedReader(new FileReader("invisible.txt"));

            String line;
            try {
                while ((line = readerB.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (tokens[1].equals(currentUserName) && tokens[0].equalsIgnoreCase(otherName)) {
                        writer.write("true");
                        writer.println();
                        writer.flush();
                        return;
                    }
                }
            } catch (IOException e) {
                //   Auto-generated catch block
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound!");
        }
        writer.write("false");
        writer.println();
        writer.flush();
    
    }

    private static void listOfSellers(BufferedReader reader, PrintWriter writer) {
        ArrayList<Seller> sellers = new ArrayList<>();
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
                if (userType.equals("seller")) {
                    String userName = part[1];
                    String password = part[2];
                    String name = part[3].toLowerCase();
                    int numMessages = Integer.parseInt(part[4]);
                    int phoneNum = Integer.parseInt(part[5]);
                    String address = part[6];
                    Seller seller = new Seller(userName, password, name, numMessages, phoneNum, address);
                    sellers.add(seller);
                }
            }
            writer.write(String.valueOf(sellers.size()));
            writer.println();
            writer.flush();
            
            for (int i = 0; i < sellers.size(); i++) {
                String line2 = "";
                line2 += sellers.get(i).getUsername() + ",";
                line2 += sellers.get(i).getPassword() + ",";
                line2 += sellers.get(i).getName() + ",";
                line2 += sellers.get(i).getNumMessages() + ",";
                line2 += sellers.get(i).getPhoneNum() + ",";
                line2 += sellers.get(i).getAddress();

                writer.write(line2);
                writer.println();
                writer.flush();                                
            }
        } catch (FileNotFoundException e) {
            writer.write("-1");
            writer.println();
            writer.flush();
        } catch (Exception e) {
            writer.write("-2");
            writer.println();
            writer.flush();
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

    private static void listOfCustomers(BufferedReader reader, PrintWriter writer) {
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
                    String name = part[3].toLowerCase();
                    int numMessages = Integer.parseInt(part[4]);
                    int phoneNum = Integer.parseInt(part[5]);
                    String address = part[6];
                    Customer customer = new Customer(userName, password, name, numMessages, phoneNum, address);
                    customers.add(customer);
                }
            }
            writer.write(String.valueOf(customers.size()));
            writer.println();
            writer.flush();
            
            for (int i = 0; i < customers.size(); i++) {
                String line2 = "";
                line2 += customers.get(i).getUsername() + ",";
                line2 += customers.get(i).getPassword() + ",";
                line2 += customers.get(i).getName() + ",";
                line2 += customers.get(i).getNumMessages() + ",";
                line2 += customers.get(i).getPhoneNum() + ",";
                line2 += customers.get(i).getAddress();

                writer.write(line2);
                writer.println();
                writer.flush();                                
            }
        } catch (FileNotFoundException e) {
            writer.write("-1");
            writer.println();
            writer.flush();
        } catch (Exception e) {
            writer.write("-2");
            writer.println();
            writer.flush();
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

    private static void deleteUser(BufferedReader reader, PrintWriter writer, String name) {
        try {
            BufferedReader readerB = new BufferedReader(new FileReader("user.txt"));
            String[] lines = readerB.lines().toArray(String[]::new);
            readerB.close();
            int count = 1;
            for (int i = 0; i < lines.length; i++) {
                String[] tokens = lines[i].split(",");
                if (tokens[3].equals(name)) {
                    break;
                }
                count++;
            }

            int lineNumber = count;
            
                       
            lines[lineNumber - 1] = "";
            String[] newLines = new String[lines.length - 1];

            for (int i = 0; i < lines.length; i++) {
                if (i < lineNumber - 1) {
                    newLines[i] = lines[i];
                } else if (i == lineNumber - 1) {

                } else if (i > lineNumber - 1) {
                    newLines[i - 1] = lines[i];
                }
            }
            
            PrintWriter writerB = new PrintWriter(new FileWriter("user.txt"));
            for (String line : newLines) {
                writerB.println(line);
            }
            writerB.close();
            
           
        } catch (FileNotFoundException e) {
            System.out.println("The File does not exist!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createAccount(BufferedReader reader, PrintWriter writer) throws IOException {
        String[] tokens = reader.readLine().split(",");
        String name = tokens[0];
        String username = tokens[1];
        String password = tokens[2];
        String phoneNum = tokens[3];
        String address = tokens[4];
        String accountType = tokens[5];

        try (PrintWriter localWriter = new PrintWriter(new FileOutputStream(new File("user.txt"), true))) {
            localWriter.append(accountType + "," + username + "," + password + "," + name + "," + 0 + "," + phoneNum + ","
                    + address);
            localWriter.println();
            localWriter.flush();
            System.out.println("User created! You are now logged in");
            writer.write("created");
            writer.println();
            writer.flush();
            
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file for writing: user.txt");
            writer.write("error");
            writer.println();
            writer.flush();
            e.printStackTrace();
        }

    }

    public static void login(BufferedReader reader, PrintWriter writer) throws IOException {
        String username = reader.readLine();
        if (username.equals("-1")) {
            return; // Quit
        }
        String password = reader.readLine();
        
        try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens[1].equals(username) && tokens[2].equals(password)) {
                    writer.write("true");
                    writer.println();
                    writer.flush(); // Success

                    String send = "";
                    for (int i = 0; i < tokens.length; i++) {
                        if (i != tokens.length - 1) {
                            send += tokens[i] + ",";
                        }
                        if (i == tokens.length - 1) {
                            send += tokens[i];
                        }
                    }
                    
                    System.out.println(send);
                    writer.write(send);
                    writer.println();
                    writer.flush();
                    return;
                }
            }
            writer.write("false");
            writer.println();
            writer.flush();
            login(reader, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String convoNamingScheme(String str1, String str2) {
        String[] arr = {str1, str2};
        Arrays.sort(arr);
        return arr[0] + "_" + arr[1] + ".txt";
    }
}
