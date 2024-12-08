import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Logger;

public class UDPEchoServerThread {
    private static final int BUFSIZE = 512; // Max buffer size

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java UDPEchoServerThread <Port>");
            return;
        }

        int port = Integer.parseInt(args[0]); // Server port
        Logger logger = Logger.getLogger("UDPEchoServerThread");

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("UDP Echo Server is running on port " + port);

            while (true) {
                byte[] buffer = new byte[BUFSIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Receive a mssg from a client
                socket.receive(packet);

                // Spawn a new thread to handle the client using EchoProtocol
                Thread thread = new Thread(new EchoProtocol(packet, socket, logger));
                thread.start();
                logger.info("Created and started Thread " + thread.getName());
            }
        } catch (IOException e) {
            logger.severe("Server exception: " + e.getMessage());
        }
    }
}
