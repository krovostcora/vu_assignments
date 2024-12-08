import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class CompressClient {
    private static final int BUFSIZE = 512; // Buf size for file chunks

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Parameter(s):  <Server> <Port> <File> <File>");
            return;
        }

        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);
        String filename = args[2];
        String outputFile = args[3];

        try (DatagramSocket socket = new DatagramSocket()) {
            // Open the file to send
            FileInputStream fileIn = new FileInputStream(filename);
            byte[] sendBuffer = new byte[BUFSIZE];

            // Send file in chunks
            int bytesRead;
            InetAddress serverIP = InetAddress.getByName(serverAddress);
            while ((bytesRead = fileIn.read(sendBuffer)) != -1) {
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, bytesRead, serverIP, serverPort);
                socket.send(sendPacket);
            }

            System.out.println("File sent to server: " + filename);

            // Receive the compressed response
            byte[] receiveBuffer = new byte[BUFSIZE];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            // Save the compressed data to the output file
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Compressed data saved to " + outputFile);
            }

        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
