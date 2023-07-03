package Server;

import Shared.Car;

import java.io.Serializable;

public class ServerData implements Serializable {
    public static Car playerOneCar = null;
    public static Car playerTwoCar = null;

    public static boolean playerOneReady = false;
    public static boolean playerTwoReady = false;
}
