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
                }
            }
            // recreate all methods that read or write to files + all methods that do any major processing
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
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
                line2 += customers.get(i).getUsername();
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
}
