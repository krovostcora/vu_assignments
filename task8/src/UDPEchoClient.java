import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class UDPEchoClient {
    private static final int BUFSIZE = 512; // Max buf size
    private static final int TIMEOUT = 3000; // Timeout for response (ms)
    private static final int MAX_TRIES = 3; // Max retry attempts

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Parameter(s): <Server> <Port> <Message>");
            return;
        }

        String serverAddress = args[0]; // Server's IP or hostname
        int serverPort = Integer.parseInt(args[1]); // Server's port
        String message = args[2]; // mssg to send

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(TIMEOUT); // Set timeout for receiving packets

            // Prepare the packet to send
            byte[] sendBuffer = message.getBytes();
            InetAddress serverIP = InetAddress.getByName(serverAddress);
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverIP, serverPort);

            // Attempt to send the mssg and receive a response
            boolean responseReceived = false;
            int attempts = 0;
            while (!responseReceived && attempts < MAX_TRIES) {
                // Send the message
                socket.send(sendPacket);
                System.out.println("Sent: " + message);

                // Prepare a buf to receive the response
                byte[] receiveBuffer = new byte[BUFSIZE];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                try {
                    // Attempt to receive a response
                    socket.receive(receivePacket);
                    responseReceived = true;

                    // Display the received mssg
                    String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("Received: " + receivedMessage);

                } catch (SocketTimeoutException e) {
                    attempts++;
                    System.out.println("Timeout: No response from server. Retrying (" + attempts + "/" + MAX_TRIES + ")...");
                }
            }

            if (!responseReceived) {
                System.err.println("Failed to receive a response after " + MAX_TRIES + " attempts.");
            }

        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
