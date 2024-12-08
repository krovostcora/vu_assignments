package Lecture8;

import java.net.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
public class TCPEchoClient {
	public static void main(String[] args) throws IOException {
		if (args.length < 2)
			throw new IllegalArgumentException("Parameter(s): <Server> <Port>");
		String server = args[0];
		int servPort = Integer.parseInt(args[1]);
		try (Socket socket = new Socket(server, servPort);Scanner input= new Scanner ( System.in )) {
			System.out.println("Message for the server: ");
			String message = input.nextLine();
			byte[] data = message.getBytes();
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			out.write(data); // Send the encoded string to the server
			// Receive the same string back from the server
			int totalBytesRcvd = 0; // Total bytes received so far
			int bytesRcvd; // Bytes received in last read
			while (totalBytesRcvd < data.length) {
				if ((bytesRcvd = in.read(data, totalBytesRcvd, data.length - totalBytesRcvd)) == -1)
					throw new SocketException("Connection closed prematurely");
				totalBytesRcvd += bytesRcvd;
			} // data array is full
			System.out.println("Received from the server: " + new String(data));
		} catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("I/O error: " + ex.getMessage());
		}
	}
}