package Exam.UPD;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoinChangeServer {
    public static void main(String[] args) {
        int port = 2024;
        int[] coins = {50, 10, 5, 2, 1};

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(requestPacket);

                String receivedData = new String(requestPacket.getData(), 0, requestPacket.getLength());
                int sum;

                try {
                    sum = Integer.parseInt(receivedData.trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input received: " + receivedData);
                    continue;
                }

                List<String> result = compteCoins(sum, coins);

                String response = String.join(", ", result);

                byte[] responseBytes = response.getBytes();
                InetAddress clientAddress = requestPacket.getAddress();
                int clientPort = requestPacket.getPort();
                DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, clientAddress, clientPort);
                socket.send(responsePacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> compteCoins(int sum, int[] coins) {
        List<String> result = new ArrayList<>();

        for (int coin : coins) {
            if (sum >= coin) {
                int count = sum / coin;
                sum %= coin;
                result.add("(" + coin + ", " + count + ")");
            }
        }

        return result;
    }
}

