package fifthTask;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {

    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(9876);
            byte[] receiveData = new byte[1024];
            byte[] sendData;

            System.out.println("UDP Server is running and waiting for requests...");

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

                String[] parts = receivedMessage.split(" ");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                String operation = parts[2];

                int result = 0;
                boolean validOperation = true;

                switch (operation) {
                    case "+":
                        result = x + y;
                        break;
                    case "-":
                        result = x - y;
                        break;
                    case "*":
                        result = x * y;
                        break;
                    case "/":
                        if (y != 0) {
                            result = x / y;
                        } else {
                            validOperation = false;
                        }
                        break;
                    case "%":
                        if (y != 0) {
                            result = x % y;
                        } else {
                            validOperation = false;
                        }
                        break;
                    default:
                        validOperation = false;
                }

                String resultMessage;
                if (validOperation) {
                    resultMessage = "RESULT: " + result;
                } else {
                    resultMessage = "Error: Invalid operation or division by zero";
                }

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                sendData = resultMessage.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

