import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ClientSide implements Runnable {

    //private static int PORT = 7000;
    Socket socket;
    String input = " ";
    String output = " ";

    //create 2 threads to keep track of output and input
    Thread check1 = new Thread(this);
    Thread check2 = new Thread(this);

    public static void main(String[] args) throws Exception {
        ClientSide client = new ClientSide();

        /*
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter 1 to chat: ");
        client = scan.nextInt();
        */
    }

    public ClientSide() {
        try {
        
            //create a socket for client to connect 
            socket = new Socket("localhost", 6000);

            System.out.println("You are now connected to the server.");
            System.out.println("Type something.");

            //start the threads
            check1.start();
            check2.start();
        } catch(Exception e) {
            //throw new ApplicationException("Connection didn't work. Please try again.", e);
            System.exit(0);
        }
    }

    public void run() {
        try {
            if(Thread.currentThread() == check2) {
                do {
                    //create new buffer to read the output from server side
                    BufferedReader buffer1 = new BufferedReader(new InputStreamReader(System.in));
                    //get the output from the server using socket
                    PrintWriter printIt = new PrintWriter(socket.getOutputStream(), true); 
                    
                    //read output from the server and store it in empty string
                    input = buffer1.readLine();
                    //print out server's response
                    printIt.println(input);
                    
                    //when client types stop, program terminates
                } while (!input.equals("Stop"));
            } else {

                do {
                    //create another buffer to get input from the server
                    BufferedReader buffer2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    //prints out server's reponse
                    output = buffer2.readLine();
                    System.out.println("Server's Response: " + output);
                } while (!output.equals("Stop"));
            }
        } catch (Exception e) {
            //throw new ApplicationException("Cannot get response. Please try again.", e);
            System.exit(0);
        }
    }
}