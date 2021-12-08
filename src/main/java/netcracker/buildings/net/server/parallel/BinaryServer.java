package netcracker.buildings.net.server.parallel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class BinaryServer {
    private static final int PORT = 5634;
    private static final LinkedList<BinaryServerThread> SERVER_THREADS = new LinkedList<>();
    private static int number = 1;

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = server.accept();
                System.out.println("Клиент " + number + " подключен");
                try {
                    SERVER_THREADS.add(new BinaryServerThread(socket, number++));
                } catch (IOException e) {
                    System.out.println("Клиент " + number++ + " завершил работу в связи с ошибкой");
                    socket.close();
                }
            }
        }
    }
}
