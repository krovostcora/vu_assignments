package fifthTask;

import java.io.*;
import java.net.*;

public class TCPServer {

    public static void main(String[] args) {
        int port = 2000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                int x = Integer.parseInt(reader.readLine());
                int y = Integer.parseInt(reader.readLine());
                String operation = reader.readLine();

                int result = 0;
                switch (operation) {
                    case "+":
                        result = x + y;
                        break;
                    case "-":
                        result = x - y;
                        break;
                    case "*":
                        result = x * y;
                        break;
                    case "/":
                        if (y != 0) {
                            result = x / y;
                        } else {
                            writer.println("Error: Division by zero is not allowed");
                            continue;
                        }
                        break;
                    case "%":
                        result = x % y;
                        break;
                    default:
                        writer.println("Error: Invalid operation");
                        continue;
                }

                writer.println("RESULT: " + result);
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

