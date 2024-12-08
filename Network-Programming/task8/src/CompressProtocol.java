import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

public class CompressProtocol implements Runnable {
    public static final int BUFSIZE = 512; // Buffer size for incoming packets
    private final DatagramPacket packet;  // Packet received from the client
    private final DatagramSocket socket;  // Datagram socket for communication
    private final Logger logger;          // Logger for logging events

    public CompressProtocol(DatagramPacket packet, DatagramSocket socket, Logger logger) {
        this.packet = packet;
        this.socket = socket;
        this.logger = logger;
    }

    public static void handleCompressClient(DatagramPacket packet, DatagramSocket socket, Logger logger) {
        try {
            // Compress the data received from the client
            byte[] compressedData = compressData(packet.getData(), packet.getLength());

            // Create a new packet with the compressed data
            DatagramPacket responsePacket = new DatagramPacket(
                    compressedData,
                    compressedData.length,
                    packet.getAddress(),
                    packet.getPort()
            );

            // Send the compressed data back to the client
            socket.send(responsePacket);

            logger.info("Compressed and sent data to " + packet.getAddress() + ":" + packet.getPort());
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error handling client", e);
        }
    }

    private static byte[] compressData(byte[] data, int length) throws IOException {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             GZIPOutputStream gzipStream = new GZIPOutputStream(byteStream)) {

            gzipStream.write(data, 0, length); // Write valid data to GZIP stream
            gzipStream.finish(); // Ensure all data is compressed
            return byteStream.toByteArray(); // Return the compressed data
        }
    }

    @Override
    public void run() {
        handleCompressClient(this.packet, this.socket, this.logger);
    }
}
