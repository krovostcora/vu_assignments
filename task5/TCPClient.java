package fifthTask;

import java.io.*;
import java.net.*;

public class TCPClient {

    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 2000;

        try (Socket socket = new Socket(hostname, port)) {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter the 1st number : ");
            int x = Integer.parseInt(userInput.readLine());

            System.out.print("Enter the 2nd number: ");
            int y = Integer.parseInt(userInput.readLine());

            System.out.print("Enter the operation +, -, *, /, %: ");
            String operation = userInput.readLine();

            writer.println(x);
            writer.println(y);
            writer.println(operation);

            // Receive and display the server's response
            String response = reader.readLine();
            System.out.println("Server response: " + response);

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
