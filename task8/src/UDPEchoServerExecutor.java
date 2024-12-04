import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class UDPEchoServerExecutor {

    private static final int BUFSIZE = 512; // Max buffer size for messages

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Parameter: <Port>");
        }

        int port = Integer.parseInt(args[0]); // Server port
        Logger logger = Logger.getLogger("practical");
        ExecutorService executor = Executors.newCachedThreadPool(); // Thread pool for clients

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("UDP Echo Server is running on port " + port);

            while (true) {
                byte[] buffer = new byte[BUFSIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Receive a message from a client
                socket.receive(packet);

                // Submit the packet to EchoProtocol for processing
                executor.execute(new EchoProtocol(packet, socket, logger));
            }
        } catch (IOException e) {
            logger.severe("Server exception: " + e.getMessage());
        }
    }
}
