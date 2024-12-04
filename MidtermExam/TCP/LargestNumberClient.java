package Exam.TCP;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class LargestNumberClient {
    public static void main(String[] args) {
        String hostname = "localhost"; // Server hostname
        int port = 2024; // Server port

        try (Socket socket = new Socket(hostname, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server");

            // Get user input
            System.out.print("Enter a sequence of digits (0-9): ");
            String input = scanner.nextLine();

            // Send the input to the server
            out.println(input);

            // Read and print the server's response
            String response = in.readLine();
            System.out.println("Response from server: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}