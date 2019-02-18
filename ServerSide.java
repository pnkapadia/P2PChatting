import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;
import java.io.PrintWriter;
import java.net.*;

public class ServerSide implements Runnable {

    ServerSocket socket;
    Socket start;
    String input = " ";
    String output = " ";

    //create 2 threads for input and output
    Thread check1 = new Thread(this);
    Thread check2 = new Thread(this);

    public static void main(String[] args) {
     ServerSide server = new ServerSide();
    }

    public ServerSide() {
        try {

            //create new server-side socket and give port
            socket = new ServerSocket(6000);
            System.out.println("Server is waiting for Client to start...");

            //created new socket to connect to client
            start = new Socket();
            
            //connects the client and server. Gets the IP address of the client once connected
            start = socket.accept();
            System.out.println("Client is connected to the IP: " + socket.getInetAddress().getHostAddress());
            
            System.out.println("Type something.");

            //start the threads
            check1.start();
            check2.start();

        } catch (Exception e) {
           // throw new ApplicationException("Connection didn't work. Please try again.", e);
           System.exit(0);
        }
    }

    public void run() {
        try {
            if (Thread.currentThread() == check1) {
                do {
                    //create new buffer to read user input 
                    BufferedReader buffer1 = new BufferedReader(new InputStreamReader(System.in));
                    //create new printWriter to print out the user's output from socket
                    PrintWriter printIt = new PrintWriter(start.getOutputStream(), true);

                    //create a string to read input lines from buffer
                    input = buffer1.readLine();
                    printIt.println(input);

                    //user types stop and program exits 
                } while (!input.equals("Stop"));
            } else {
                do {
                    //create new buffer to get output from the client
                    BufferedReader buffer2 = new BufferedReader(new InputStreamReader(start.getInputStream()));
                    
                    //prints out client's response
                    output = buffer2.readLine();
                    System.out.println("Client's Response: " + output);

                //user types stop and program exits 
                } while (!output.equals("Stop"));
            }
        } catch (Exception e) {
            //throw new ApplicationException("Cannot get response. Please try again.", e);
            System.exit(0);
        }
    }
}