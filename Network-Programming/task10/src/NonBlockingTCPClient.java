import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NonBlockingTCPClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String serverHost = "localhost"; // Default to localhost

        System.out.println("Enter server port (5000 / 5001 /5002 /5003):");
        int serverPort;
        try {
            serverPort = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid port. Exiting.");
            return;
        }

        try (SocketChannel clientChannel = SocketChannel.open()) {
            clientChannel.connect(new InetSocketAddress(serverHost, serverPort));
            System.out.println("Connected to server on " + serverHost + ":" + serverPort);

            while (true) {
                System.out.println("Enter a calculation (e.g., 'x + y', 'x y +', or 'exit' to quit):");
                String input = scanner.nextLine().trim();

                if ("exit".equalsIgnoreCase(input)) {
                    System.out.println("Exiting client.");
                    break;
                }

                String formattedRequest = formatRequest(input);
                if (formattedRequest == null) {
                    System.out.println("Invalid input format. Try again.");
                    continue;
                }

                ByteBuffer requestBuffer = ByteBuffer.wrap(formattedRequest.getBytes());
                clientChannel.write(requestBuffer);

                ByteBuffer responseBuffer = ByteBuffer.allocate(256);
                clientChannel.read(responseBuffer);
                responseBuffer.flip();
                String response = new String(responseBuffer.array(), 0, responseBuffer.limit()).trim();
                System.out.println("Server response: " + response);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static String formatRequest(String input) {
        // Remove all whitespace
        input = input.replaceAll("\\s+", "");

        // Check if input contains an operator in compact form (e.g., x+y)
        String[] tokens = input.split("([+\\-*/])");
        if (tokens.length == 2) {
            String operator = input.replaceAll("[^+\\-*/]", ""); // Extract operator
            if (operator.length() == 1) {
                return tokens[0] + " " + tokens[1] + " " + operator;
            }
        }

        // Check if input is already in "x y operation" form
        tokens = input.split(" ");
        if (tokens.length == 3) {
            return input;
        }

        return null; // Invalid format
    }
}
