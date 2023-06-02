package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientMain {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        while(true){
            System.out.println("Enter your choice from the following menu:");
            System.out.println("1.login 2.create account 3.Exit");
            int choice;
            choice = sc.nextInt();
            //establish socket connection to server
            socket = new Socket(host.getHostName(), 9876);
            //write to socket using ObjectOutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Sending request to Socket Server");
            if(choice == 3){
                oos.writeObject("exit");
            }
            else if(choice == 2) {
                oos.writeObject("create account");
                System.out.println("Enter username: ");
                String username = sc.next();
                System.out.println("Enter password: ");
                String password= sc.next();
                System.out.println("Birth date: ");
                int birthDate = sc.nextInt();
                oos.writeObject(username +","+ password +","+ birthDate);
                //read the server response message
                ois = new ObjectInputStream(socket.getInputStream());
                String message = (String) ois.readObject();
                System.out.println("Message: " + message);
            }
            else{
                oos.writeObject("login");
            }

            //close resources
            ois.close();
            oos.close();
            Thread.sleep(100);
        }
    }

    public static void menu(int choice){
        if(choice == 1){
            System.out.println("");
        }
    }
}

