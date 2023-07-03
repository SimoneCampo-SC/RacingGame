package Client;

import java.io.*;
import java.net.*;

public class TCPClient {

    public static void main(String[] args) {
        ClientThread clientThread = new ClientThread();
        clientThread.start();
    }
}