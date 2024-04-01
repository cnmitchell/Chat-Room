import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientChat {
    private static InetAddress host;
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter IP address of the friend you would like to connect with: ");
            host = InetAddress.getByName(scanner.nextLine());
        }
        catch (UnknownHostException uhEx) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }
        accessServer();
    }

    private static void accessServer() {
        Socket link = null;
        try {
            link = new Socket(host, PORT);
            Scanner input = new Scanner(link.getInputStream());
            PrintWriter output = new PrintWriter(link.getOutputStream(), true);
            Scanner userEntry = new Scanner(System.in);
            String message, response;
            String name;
            System.out.println("Welcome to our chat room! \n At anytime you want to log off and end the connection, send \"Bye\" to your friend.");
            System.out.print("Enter your name for chatting: ");
            name = userEntry.nextLine();
            output.println(name);
            do {
                System.out.print(name + ": ");
                message = userEntry.nextLine();
                output.println(message);
                try {
                    response = input.nextLine();
                    System.out.println(response);
                }
                catch(NoSuchElementException noSuchEx) {
                    System.out.println("No message has been received from your friend!");
                }
            } while (!message.equals("Bye!"));
        }
        catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        finally {
            try {
                System.out.println("\n* Closing connection... *");
                link.close();
            }
            catch (IOException ioEx) {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    }
}
