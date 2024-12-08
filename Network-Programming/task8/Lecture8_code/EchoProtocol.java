package Lecture8;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoProtocol implements Runnable{
	private static final int BUFSIZE = 32;
	private Socket clientSock;
	private Logger logger;
	
	public EchoProtocol(Socket cSock, Logger log) {
		clientSock = cSock;
		logger = log;
	}
	public static void handleEchoClient(Socket clientSock, Logger logger) {
		try{
			// Get the input and output I/O streams from socket
			InputStream in = clientSock.getInputStream();
			OutputStream out = clientSock.getOutputStream();
			int recvMsgSize;
			int totalBytesEchoed = 0; // Bytes received from client
			byte[] echoBuffer = new byte[BUFSIZE]; // Receive buffer
			
			// Receive until client closes connection, indicated by -1
			while ((recvMsgSize = in.read(echoBuffer)) != -1) {
				out.write(echoBuffer, 0, recvMsgSize);
				totalBytesEchoed += recvMsgSize;
			}
			logger.info("Client " + clientSock.getRemoteSocketAddress() + ", echoed "+ totalBytesEchoed + " bytes.");
		}catch (IOException ex) {
			logger.log(Level.WARNING, "Exception in echo protocol", ex);
		}finally {
			try {
				clientSock.close();
			} catch (IOException e) { }
		}
	}
	public void run() {
		handleEchoClient(clientSock, logger);
	}
}
