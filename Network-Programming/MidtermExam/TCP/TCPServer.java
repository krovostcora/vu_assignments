package Exam.TCP;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TCPServer {
    public static void main(String[] args) {
        int port = 2024;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Connected to client");
                    String response;
                    String input = in.readLine();

                    if (input == null || !input.matches("[0-9]+")) {
                        response = "wrong input";
                    }else{
                        List<String> digits = Arrays.asList(input.split(""));

                        Collections.sort(digits, Collections.reverseOrder());

                        response = String.join("", digits);
                    }
                    out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


