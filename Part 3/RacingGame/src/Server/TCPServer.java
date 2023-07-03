package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class TCPServer {

    static int activeClients = 0;
    public static void main(String[] args) {
        int maxClients = 2;
        TCPClientHandler[] handlers = new TCPClientHandler[maxClients];

        // Declare a server socket and a client socket for the server
        ServerSocket service = null;
        Socket server = null;

        // Try to open a server socket on port 4000
        try {
            service = new ServerSocket(4000);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Create a socket object from the ServerSocket to listen and accept
        // connections. Open input and output streams
        try {
            do {
                server = service.accept();
                TCPClientHandler handler = new TCPClientHandler(server, activeClients + 1);
                Thread t = new Thread(handler);
                t.start();

                handlers[activeClients] = handler;
                activeClients++;

                if (activeClients == maxClients) {
                    break;
                }
            } while (true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        while (true) {
            // keep server open and alive as long as we have active client connections
            boolean allClientsAreActive = false;
            for (int i = 0; i < activeClients; i++) {
                TCPClientHandler handler = handlers[i];

                if (handler.isAlive()) {
                    allClientsAreActive = true;
                    break;
                }
            }

            if (!allClientsAreActive) {
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
