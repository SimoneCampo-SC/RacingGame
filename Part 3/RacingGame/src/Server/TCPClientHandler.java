package Server;

import Client.ClientData;
import Shared.*;
import java.io.*;
import java.net.Socket;

public class TCPClientHandler implements Runnable {
    private Socket server = null;
    // Declare an input stream and String to store message from client
    private BufferedReader inputStream;
    private String line;
    // Declare an output stream to client
    private DataOutputStream outputStream;

    private ObjectInput objectInput = null;
    private ObjectOutput objectOutput = null;

    private boolean alive = true;

    private Boolean startGame = false;

    private String playerName;

    public TCPClientHandler(Socket server, int playerNumber) {
        this.server = server;
        if (playerNumber == 1) {
            this.playerName = "player_one";
        } else {
            this.playerName = "player_two";
        }
    }

    public void run() {
        try {
            inputStream = new BufferedReader(
                    new InputStreamReader(
                            server.getInputStream()
                    )
            );

            outputStream = new DataOutputStream(
                    server.getOutputStream()
            );

            objectInput = new ObjectInputStream(
                    server.getInputStream()
            );

            objectOutput = new ObjectOutputStream(
                    server.getOutputStream()
            );

            do {
                line = receiveMessage();
                if (line != null) {
                    System.out.println("CLIENT " + playerName + ": " + line);
                    handleClientResponse(line);
                }
                if (line.equals("CLOSE")) {
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            } while (true);

            // Comment out/remove the outputStream and server close statements if server should remain live
            outputStream.close();
            inputStream.close();
            objectOutput.close();
            objectInput.close();
            server.close();
        } catch (Exception e) {
            System.out.println("TCPClientHandler Exception: " + e.getMessage());
        }

        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    private void sendMessage(String message) {
        try {
            outputStream.writeBytes(message + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String receiveMessage() {
        try {
            return inputStream.readLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void sendOpponentCar() {
        System.out.println("Attempting to send opponent car to " + playerName);
        Car carToSend = null;
        switch (playerName) {
            case "player_one":
                System.out.println("SENDING: " + ServerData.playerTwoCar.getName());
                carToSend = ServerData.playerTwoCar;
                break;
            case "player_two":
                carToSend = ServerData.playerOneCar;
                break;
        }
        if (carToSend != null) {
            System.out.println("Sending " + carToSend.getName() + " to " + playerName);
            try {
                objectOutput.writeObject(carToSend);
                objectOutput.flush();
                System.out.println("Sent " + carToSend.getName() + " to " + playerName);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private synchronized void receiveCar() {
        Car inputCar = null;
        System.out.println(playerName);
        try {
            inputCar = (Car) objectInput.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (inputCar != null) {
            System.out.println("MY CAR HAS BEEN RECEIVED");
            switch (playerName) {
                case "player_one":
                    ServerData.playerOneCar = inputCar;
                    System.out.println("Get My Car: " + playerName);
                    System.out.println("My Car: " + ServerData.playerOneCar.getName());
                    break;
                case "player_two":
                    ServerData.playerTwoCar = inputCar;
                    System.out.println("Get My Car: " + playerName);
                    System.out.println("My Car: " + ServerData.playerTwoCar.getName());
                    break;
            }
        }
    }

    private void playerReady() {
        if (playerName == "player_one") {
            ServerData.playerOneReady = true;
        } else {
            ServerData.playerTwoReady = true;
        }
    }

    private boolean playersAreReady() {
        return ServerData.playerOneReady && ServerData.playerTwoReady;
    }

    private void handleClientResponse(String response) {
        switch (response) {
            case "ping":
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                sendMessage("pong");
                break;

            case "Connect":
                if (playerName == "player_one") {
                    sendMessage("red");
                    System.out.println("Client 1 Red connected");
                } else {
                    sendMessage("yellow");
                    System.out.println("Client 2 Yellow connected");
                }
                break;

            case "own_car":
                System.out.println("Receiving car");
                receiveCar();
                sendMessage("wait");
                break;

            case "waiting":
                if (ServerData.playerOneCar != null && ServerData.playerTwoCar != null) {
                    sendMessage("opponent_car");
                    sendOpponentCar();
                } else {
                    sendMessage("wait");
                }
                break;

            case "ready":
                playerReady();
                while(true) {
                    System.out.println("Player " + playerName + " is " + playersAreReady());
                    if (playersAreReady()) {
                        sendMessage("start");
                        break;
                    }
                }
                break;

            case "own_car_update":
                receiveCar();
                sendMessage("opponent_car_update");
                sendOpponentCar();
                break;
        }
    }
}
