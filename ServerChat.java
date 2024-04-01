import java.io.*;
import java.net.*;
import java.sql.SQLOutput;
import java.util.*;
public class ServerChat {
    private static ServerSocket serverSocket;
    private static final int PORT = 1234;

    public static void main(String[] args) {
        System.out.println("Opening port...\n");
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ioEx) {
            System.out.println("Unable to attach to port!");
            System.exit(1);
        }
        do {
            handleClient();
        } while (true);
    }

    private static void handleClient() {
        Socket link = null;
        try {
            link = serverSocket.accept();

            Scanner input = new Scanner(link.getInputStream());
            Scanner userEntry = new Scanner(System.in);
            PrintWriter output = new PrintWriter(link.getOutputStream(), true);
            String friendsName = input.nextLine();
            System.out.println("Welcome to our chat room!\nAt anytime you want to log off and end the connection, send \"Bye\" to your friend.");
            System.out.print("Enter your name to enter the chat: ");
            String name = userEntry.nextLine();
            String message = input.nextLine();
            while (!message.equals("Bye!")) {
                System.out.println(friendsName + ": " + message);
                System.out.print(name + ": ");
                output.println(name + ": " + userEntry.nextLine());
                try {
                    message = input.nextLine();
                } catch (NoSuchElementException noSuchEx) {
                    output.println("No message was received!");
                }
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } finally {
            try {
                System.out.println("\n* Closing Connection... *");
                link.close();
            } catch (IOException ioEx) {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    }
}