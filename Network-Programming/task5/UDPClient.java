package fifthTask;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {

    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();

            InetAddress serverAddress = InetAddress.getByName("localhost");

            byte[] sendData;
            byte[] receiveData = new byte[1024];

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the 1st number: ");
            int x = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter the 2nd number: ");
            int y = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter the operation +, -, *, /, %: ");
            String operation = scanner.nextLine();

            String message = x + " " + y + " " + operation;
            sendData = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 9876);
            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());

            System.out.println("Server response: " + response);

            clientSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
