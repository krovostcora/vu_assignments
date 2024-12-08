package Lecture8;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
public class TCPServerCompress {
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			throw new IllegalArgumentException("Parameter: <Port>");
		}
		int servPort = Integer.parseInt(args[0]);
		try (ServerSocket servSock = new ServerSocket(servPort)){
			Logger logger = Logger.getLogger("practical");
			Executor servis = Executors.newCachedThreadPool();
			while (true){
				Socket clientSock = servSock.accept(); // Wait for a connection
				servis.execute(new CompressProtocol(clientSock, logger));
			}
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
