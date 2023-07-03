import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Game {

    private Clip gameWon, gameFailed;

    private RaceTrack raceTrack;
    private boolean gameOver = false;
    private String winner = "";
    private Car redCar, yellowCar;

    public Game() {
        redCar = new Car("redCar/%s.png", "Red Car", Configurations.RED_CAR_INITIAL_X, Configurations.RED_CAR_INITIAL_Y);
        yellowCar = new Car("yellowCar/%s.png", "Yellow Car", Configurations.YELLOW_CAR_INITIAL_X, Configurations.YELLOW_CAR_INITIAL_Y);
        raceTrack = new RaceTrack();

        // Load the sound files
        try {
            AudioInputStream gameWonAudioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("gameWon.wav"));
            gameWon = AudioSystem.getClip();
            gameWon.open(gameWonAudioInputStream);

            AudioInputStream gameFailedAudioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("gameFailed.wav"));
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


    public Car getRedCar() {
        return redCar;
    }

    public Car getYellowCar() {
        return yellowCar;
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
        if (redCar.hasCollided(yellowCar)) {
            redCar.causeCrash();
            yellowCar.causeCrash();
            shutCars();
            playGameFailedSound();
            endGame();
        } else {
            updateCar(redCar);
            updateCar(yellowCar);
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
        redCar.shut();
        yellowCar.shut();
    }

    public void turnRedCarLeft() {
        redCar.turnLeft();
    }

    public void turnRedCarRight() {
        redCar.turnRight();
    }

    public void increaseRedCarSpeed() {
        redCar.increaseSpeed();
    }

    public void decreaseRedCarSpeed() {
        redCar.decreaseSpeed();
    }

    public void turnYellowCarLeft() {
        yellowCar.turnLeft();
    }

    public void turnYellowCarRight() {
        yellowCar.turnRight();
    }

    public void increaseYellowCarSpeed() {
        yellowCar.increaseSpeed();
    }

    public void decreaseYellowCarSpeed() {
        yellowCar.decreaseSpeed();
    }
}
