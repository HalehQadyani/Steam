package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerMain {

    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9876;

    //url, user, pass for mysql
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mysqlTest";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "halehgh1383";

    public static void main(String args[]) throws SQLException, IOException, ClassNotFoundException{
        // Create database connection
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        System.out.println("connected to database!");

        //create the socket server object
        server = new ServerSocket(port);
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            System.out.println("Waiting for the client request");
            //creating socket and waiting for client connection
            Socket socket = server.accept();
            //read from socket to ObjectInputStream object
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            //convert ObjectInputStream object to String
            String message = (String) ois.readObject();
            System.out.println("Message Received: " + message);
            //create ObjectOutputStream object
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());


            if(message.equalsIgnoreCase("login")) {

            }
            else if(message.equalsIgnoreCase("create account")) {
                String accountData = (String) ois.readObject();
                String[] aData = accountData.split(",");
                System.out.println(aData);

                //write object to Socket
                oos.writeObject("Hi Client, you have created an account!");

            }
            //terminate the server if client sends exit request
            else if(message.equalsIgnoreCase("exit")) {
                break;
            }
            //close resources
            ois.close();
            oos.close();
            socket.close();
        }
        System.out.println("Shutting down Socket server!!");
        //close the ServerSocket object
        server.close();
    }

}