import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class UDPEchoServerPool {
    private static final int BUFSIZE = 512; // Max buffer size

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Parameter(s): <Port> <Threads>");
            return;
        }

        int port = Integer.parseInt(args[0]); // Server port
        int threadPoolSize = Integer.parseInt(args[1]); // Number of threads in the pool

        // Create a fixed thread pool
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
        Logger logger = Logger.getLogger("UDPEchoServerPool");

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("UDP Echo Server is running on port " + port);

            while (true) {
                byte[] buffer = new byte[BUFSIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Receive a mssg from a client
                socket.receive(packet);

                // Submit the client handling task to the thread pool
                executor.submit(new EchoProtocol(packet, socket, logger));
            }
        } catch (IOException e) {
            logger.severe("Server exception: " + e.getMessage());
        } finally {
            // Shut down the thread pool
            executor.shutdown();
            logger.info("Server stopped and thread pool shut down.");
        }
    }
}
