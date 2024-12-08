import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class UDPEchoServerExecutor2 {
    private static final int BUFSIZE = 512; // Buf size for messages
    private static final int TIME_LIMIT = 10000; // Time limit in milliseconds

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Parameter: <Port>");
            return;
        }

        int port = Integer.parseInt(args[0]); // Server port
        Logger logger = Logger.getLogger("practical");
        Executor service = Executors.newCachedThreadPool();

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("UDP Echo Server with Time Limit is running on port " + port);

            while (true) {
                byte[] buffer = new byte[BUFSIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Receive a packet from a client
                socket.receive(packet);

                // Pass the packet to TimeLimitEchoProtocol for processing
                service.execute(new TimeLimitEchoProtocol(packet, socket, logger, TIME_LIMIT));
            }
        } catch (IOException e) {
            logger.severe("Server exception: " + e.getMessage());
        }
    }
}
