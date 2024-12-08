package Lecture8;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
public class TCPEchoServerExecutor {
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			throw new IllegalArgumentException("Parameter: <Port>");
		}
		int echoServPort = Integer.parseInt(args[0]);
		try (ServerSocket servSock = new ServerSocket(echoServPort)){
			Logger logger = Logger.getLogger("practical");
			Executor service = Executors.newCachedThreadPool();
			while (true){
				Socket clientSock = servSock.accept();
				service.execute(new EchoProtocol(clientSock, logger));
			}
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}