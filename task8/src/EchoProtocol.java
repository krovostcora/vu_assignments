import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Logger;

public class EchoProtocol implements Runnable {
    private final DatagramPacket packet;
    private final DatagramSocket socket;
    private final Logger logger;

    public EchoProtocol(DatagramPacket packet, DatagramSocket socket, Logger logger) {
        this.packet = packet;
        this.socket = socket;
        this.logger = logger;
    }

    @Override
    public void run() {
        try {
            // Echo the received message back to the client
            socket.send(packet);
            logger.info("Echoed message to " + packet.getAddress() + ":" + packet.getPort());
        } catch (Exception e) {
            logger.warning("Error handling client: " + e.getMessage());
        }
    }
}
