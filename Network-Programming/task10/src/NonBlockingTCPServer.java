import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NonBlockingTCPServer {
    private static final int[] PORTS = {5000, 5001, 5002, 5003};

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        for (int port : PORTS) {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.bind(new InetSocketAddress(port));
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server listening on port: " + port);
        }

        while (true) {
            selector.select();
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();

                if (key.isAcceptable()) {
                    acceptConnection(key);
                } else if (key.isReadable()) {
                    handleClientRequest(key);
                }
            }
        }
    }

    private static void acceptConnection(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(key.selector(), SelectionKey.OP_READ);
        System.out.println("Accepted connection from: " + clientChannel.getRemoteAddress());
    }

    private static void handleClientRequest(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(256);

        int bytesRead = clientChannel.read(buffer);
        if (bytesRead == -1) {
            clientChannel.close();
            return;
        }

        buffer.flip();
        String request = new String(buffer.array(), 0, buffer.limit()).trim();
        System.out.println("Received: " + request);

        if ("exit".equalsIgnoreCase(request)) {
            System.out.println("Client requested to close connection.");
            clientChannel.close();
            return;
        }

        String response = processRequest(request);
        ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes());
        clientChannel.write(responseBuffer);
    }


    private static String processRequest(String request) {
        try {
            String[] parts = request.split(" ");
            if (parts.length != 3) {
                return "ERROR: Invalid format. Use: X Y operation";
            }

            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            String operation = parts[2];

            int result;
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
                    if (y == 0) {
                        return "ERROR: Division by zero";
                    }
                    result = x / y;
                    break;
                default:
                    return "ERROR: Unsupported operation";
            }

            return "RESULT: " + result;
        } catch (NumberFormatException e) {
            return "ERROR: Invalid numbers";
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
}
