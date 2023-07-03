package Client;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import Shared.*;

public class Game {

    private Clip gameWon, gameFailed;

    private RaceTrack raceTrack;
    private boolean gameOver = false;
    private String winner = "";

    private String carName;

    public Game(String mainCar) {
        carName = mainCar;
        if (carName == "red") {
            ClientData.playerCar = new Car("carProperties/redCar/%s.png", "Red Car", Configurations.RED_CAR_INITIAL_X, Configurations.RED_CAR_INITIAL_Y);
        } else {
            ClientData.playerCar = new Car("carProperties/yellowCar/%s.png", "Yellow Car", Configurations.YELLOW_CAR_INITIAL_X, Configurations.YELLOW_CAR_INITIAL_Y);
        }
            raceTrack = new RaceTrack();

        // Load the sound files
        try {
            AudioInputStream gameWonAudioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("gameSound/gameWon.wav"));
            gameWon = AudioSystem.getClip();
            gameWon.open(gameWonAudioInputStream);

            AudioInputStream gameFailedAudioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("gameSound/gameFailed.wav"));
            gameFailed = AudioSystem.getClip();
            gameFailed.open(gameFailedAudioInputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playGameWonSound() {
        gameWon.setFramePosition(0);
        gameWon.start();
    }

    public void playGameFailedSound() {
        gameFailed.setFramePosition(0); // rewind to the beginning
        gameFailed.start(); // start playing
    }


    public Car getPlayerCar() {
        return ClientData.playerCar;
    }

    public Car getOpponentCar() {
        return ClientData.opponentCar;
    }

    public RaceTrack getRaceTrack() {
        return raceTrack;
    }

    public void endGame() {
        gameOver = true;
    }

    public boolean isOver() {
        return gameOver;
    }

    public String getWinner() {
        return winner;
    }

    public synchronized void updateGame() {
        if (ClientData.playerCar.hasCollided(ClientData.opponentCar)) {
            ClientData.playerCar.causeCrash();
            ClientData.opponentCar.causeCrash();
            shutCars();
            playGameFailedSound();
            endGame();
        } else {
            updateCar(ClientData.playerCar);
            updateCar(ClientData.opponentCar);
        }
    }

    public void updateCar(Car car) {
        if(!raceTrack.carInTrack(car)) {
            car.causeCrash();
        } else {
            if (car.getSpeed() <= 0) return;
            car.moveForward();
            if (!raceTrack.checkPointCrossed(car)) return;
            car.updateCheckPoint();
            if (!car.hasWon()) return;
            winner = car.getName();
            shutCars();
            playGameWonSound();
            endGame();
        }
    }

    public void shutCars() {
        ClientData.playerCar.shut();
        ClientData.opponentCar.shut();
    }

    public void turnCarLeft() {
        ClientData.playerCar.turnLeft();
    }

    public void turnCarRight() {
        ClientData.playerCar.turnRight();
    }

    public void increaseCarSpeed() {
        ClientData.playerCar.increaseSpeed();
    }

    public void decreaseCarSpeed() {
        ClientData.playerCar.decreaseSpeed();
    }
}
