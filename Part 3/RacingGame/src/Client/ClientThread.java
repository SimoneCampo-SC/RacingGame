package Client;
import Shared.*;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientThread extends Thread {

    // Declare client socket
    private static Socket clientSocket = null;

    // Declare output stream and string to send to server
    private static DataOutputStream outputStream = null;

    // Declare input stream from server and string to store input received from server
    private static BufferedReader inputStream = null;
    private static String responseLine;

    private static ObjectOutput objectOutput = null;
    private static ObjectInput objectInput = null;

    // replace "localhost" with the remote server address, if needed
    // 5000 is the server port
    private static String serverHost = "localhost";

    private static GameFrame gameFrame = null;

    private static Boolean gameStarted = false;

    public void run() {
        // Create a socket on port 4000 and open input and output streams on that socket
        try {
            clientSocket = new Socket(serverHost, 4000);

            outputStream = new DataOutputStream(
                    clientSocket.getOutputStream()
            );

            inputStream = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()
                    )
            );

            objectOutput = new ObjectOutputStream(
                    clientSocket.getOutputStream()
            );

            objectInput = new ObjectInputStream(
                    clientSocket.getInputStream()
            );
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHost);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + serverHost);
        }

        // Write data to the socket
        if (
                clientSocket != null &&
                        outputStream != null &&
                        inputStream != null &&
                        objectOutput != null &&
                        objectInput != null
        ) {
            try {
                initialise();
                do {
                    responseLine = receiveMessage();
                    if (responseLine != null) {
                        System.out.println("SERVER: " + responseLine);
                        handleServerResponse(responseLine);
                    }
                    if (startGame()) {
                        gameFrame.getContentPanel().startGame();
                        break;
                    }
                } while (true);
                System.out.println("DONE");
                do {
                    try {
                        sendCarUpdate();
                        responseLine = receiveMessage();
                        if (responseLine != null) {
                            System.out.println("SERVER: " + responseLine);
                            handleServerResponse(responseLine);
                        }
                        Thread.sleep(100);
                        if (responseLine.equals("end")) {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } while (true);

                // close the input/output streams and socket
                outputStream.close();
                inputStream.close();
                objectOutput.close();
                objectInput.close();
                clientSocket.close();
            } catch (UnknownHostException e) {
                System.err.println("Trying to connect to unknown host: " + e);
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
    }

    private static Boolean startGame() {
        return gameStarted;
    }

    private static void initialise() {
        sendMessage("Connect");
    }

    private static String receiveMessage() {
        try {
            return inputStream.readLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static void sendMessage(String message) {
        try {
            outputStream.writeBytes(message + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static synchronized void sendCar() {
        if (ClientData.playerCar != null) {
            try {
                Car car = ClientData.playerCar;
                objectOutput.writeObject(car);
                objectOutput.flush();
                System.out.println("MY CAR HAS BEEN SENT: " + ClientData.playerCar.getName());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private synchronized static void sendCarUpdate() {
        sendMessage("own_car_update");
        sendCar();
    }

    private static void receiveOpponentCar() {
        Car inputCar = null;
        try {
            inputCar = (Car) objectInput.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (inputCar != null) {
            ClientData.opponentCar = inputCar;
        }
    }

    private static void handleServerResponse(String response) {
        switch (response) {
            case "pong":
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                sendMessage("ping");
                break;

            case "red":
                if (gameFrame == null) {
                    gameFrame = new GameFrame("red");
                    sendMessage("own_car");
                    System.out.println("My Car to send: " + ClientData.playerCar.getName());
                    sendCar();
                }
                break;

            case "yellow":
                if (gameFrame == null) {
                    gameFrame = new GameFrame("yellow");
                    System.out.println("My Car to send: " + ClientData.playerCar.getName());
                    sendMessage("own_car");
                    sendCar();
                }
                break;

            case "wait":
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                sendMessage("waiting");
                break;

            case "opponent_car":
                receiveOpponentCar();
                System.out.println("MY: " + ClientData.playerCar.getName());
                System.out.println("OP: " + ClientData.opponentCar.getName());
                sendMessage("ready");
                System.out.println("sending ready");
                break;

            case "start":
                System.out.println("received Start");
                gameStarted = true;
                break;

            case "opponent_car_update":
                receiveOpponentCar();
                break;

            case "collision_with_track_edge":
                // display collision and perform other tasks as necessary to handle this situation
                break;
            case "collision_with_foreign_kart":
                // display collision and perform other tasks as necessary to handle this situation
                break;
            case "collision_with_grass":
                // display collision and perform other tasks as necessary to handle this situation
                break;
        }
    }

    private static void shutdownClient() {
        // shutdown script
    }

}
