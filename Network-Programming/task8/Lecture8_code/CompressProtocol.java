package Lecture8;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.zip.GZIPOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
public class CompressProtocol implements Runnable{
	public static final int BUFSIZE = 1024; // Size of receive buffer
	private Socket clientSock;
	private Logger logger;
	public CompressProtocol(Socket clntSock, Logger log) {
		clientSock = clntSock;
		logger = log;
	}
	public static void handleCompressClient(Socket clientSock, Logger logger){
		try {
			// Get the input and output streams from socket
			InputStream in = clientSock.getInputStream();
			GZIPOutputStream out = new GZIPOutputStream(clientSock.getOutputStream());
			byte[] buffer = new byte[BUFSIZE]; // Allocate read/write buffer
			int bytesRead; // Number of bytes read
			while ((bytesRead = in.read(buffer)) != -1)
				out.write(buffer, 0, bytesRead);
			out.finish(); // Flush bytes from GZIPOutputStream
			logger.info("Client " + clientSock.getRemoteSocketAddress() + " finished");
		}catch (IOException ex) {
			logger.log(Level.WARNING, "Exception in echo protocol", ex);
		}
		try { // Close socket
			clientSock.close();
		} catch (IOException e) {
			logger.info("Exception = " + e.getMessage());
		}
	}
	public void run() {
		handleCompressClient(this.clientSock, this.logger);
	}
}
