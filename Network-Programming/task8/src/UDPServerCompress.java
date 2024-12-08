import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class UDPServerCompress {
    private static final int BUFSIZE = 512; // Buffer size for incoming packets

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java UDPServerCompress <Port>");
            return;
        }

        int port = Integer.parseInt(args[0]); // Server port
        Logger logger = Logger.getLogger("UDPServerCompress"); // Logger instance
        ExecutorService executor = Executors.newCachedThreadPool(); // Thread pool

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("UDP Compression Server is running on port " + port);

            while (true) {
                byte[] buffer = new byte[BUFSIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Receive a packet from a client
                socket.receive(packet);

                // Pass the packet to the thread pool for processing
                executor.execute(new CompressProtocol(packet, socket, logger));
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }
}
