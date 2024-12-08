package Lecture8;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
public class TimeLimitEchoProtocol implements Runnable{
	private static final int BUFSIZE = 32;
	private static final String TIMELIMIT = "10000"; //Default limit(ms)
	private static final String TIMELIMITPROP = "Timelimit"; // Property
	private static int timelimit;
	private Socket clientSock;
	private Logger logger;
	public TimeLimitEchoProtocol(Socket cSock, Logger log) {
		clientSock = cSock;
		logger = log;
		// Get the time limit from the System properties or take the default
		timelimit = Integer.parseInt(System.getProperty(TIMELIMITPROP,TIMELIMIT));
	}
	public static void handleEchoClient(Socket clientSock, Logger logger) {
		try{
			// Get the input and output I/O streams from socket
			InputStream in = clientSock.getInputStream();
			OutputStream out = clientSock.getOutputStream();
			int recvMsgSize; // Size of received message
			int totalBytesEchoed = 0; // Bytes received from client
			byte[] echoBuffer = new byte[BUFSIZE]; // Receive buffer
			long endTime = System.currentTimeMillis() + timelimit;
			int timeBoundMillis = timelimit;
			clientSock.setSoTimeout(timeBoundMillis);
			// Receive until client closes connection, indicated by -1
			while ((timeBoundMillis > 0) && ((recvMsgSize = in.read(echoBuffer)) != -1)) {
				out.write(echoBuffer, 0, recvMsgSize);
				totalBytesEchoed += recvMsgSize;
				timeBoundMillis = (int) (endTime - System.currentTimeMillis()) ;
				clientSock.setSoTimeout(timeBoundMillis);
			}
			logger.info("Client " + clientSock.getRemoteSocketAddress() + ", echoed "+ totalBytesEchoed + " bytes.");
		}
		catch (IOException ex) {
			logger.log(Level.WARNING, "Exception in echo protocol", ex);
		}
	}
	public void run() { 
		handleEchoClient(clientSock, logger);
	} 
}
