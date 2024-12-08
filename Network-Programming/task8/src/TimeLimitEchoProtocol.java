import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimeLimitEchoProtocol implements Runnable {
    private final DatagramPacket packet;
    private final DatagramSocket socket;
    private final Logger logger;
    private final int timeLimit; // Time limit in milliseconds

    public TimeLimitEchoProtocol(DatagramPacket packet, DatagramSocket socket, Logger logger, int timeLimit) {
        this.packet = packet;
        this.socket = socket;
        this.logger = logger;
        this.timeLimit = timeLimit;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        int totalBytesEchoed = 0;

        try {
            // Check if the time limit is exceeded
            while ((System.currentTimeMillis() - startTime) < timeLimit) {
                // Echo the received packet back to the client
                socket.send(packet);
                totalBytesEchoed += packet.getLength(); // Add the length of the echoed packet
                logger.info("Echoed " + packet.getLength() + " bytes to " + packet.getAddress() + ":" + packet.getPort());

                // Simulate further packet processing if needed
                break; // Exit loop after one echo in this example
            }

            if ((System.currentTimeMillis() - startTime) >= timeLimit) {
                logger.warning("Time limit exceeded for client at " + packet.getAddress());
            }

        } catch (IOException e) {
            logger.log(Level.WARNING, "Error handling client", e);
        }
    }
}
