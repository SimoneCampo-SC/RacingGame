import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.geom.Area;

public class Car {
    private ImageIcon[] carFrames = new ImageIcon[17];
    private String name;

    private int direction = 0, speed = 0;

    public Rectangle carShape;

    private int prevX, prevY;

    private Clip crashSound, engineSound;

    public int checkPoints = 0;

    public Car(String root, String carName, int x, int y) {
        name = carName;
        carShape = new Rectangle(x, y, 50, 25);
        float currentImage = 0;
        // Load the sound files
        try {
            AudioInputStream carCrashAudioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("crash.wav"));
            crashSound = AudioSystem.getClip();
            crashSound.open(carCrashAudioInputStream);

            AudioInputStream carEngineAudioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("carEngine.wav"));
            engineSound = AudioSystem.getClip();
            engineSound.open(carEngineAudioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < carFrames.length - 1; i++) {
            String path = String.format(root, String.valueOf(currentImage));
            carFrames[i] = new ImageIcon(getClass().getResource(path));
            currentImage += 22.5;
        }
    }

    public String getName() {
        return name;
    }

    public int getDirection() {return direction % 16; }

    public ImageIcon[] getCarFrames() {return carFrames; }

    public int getSpeed() {
        return speed * 10;
    }

    public void turnRight() {
        direction = (direction + 1) % 16;
        adjustShape();
    }

    public void turnLeft() {
        if (direction - 1 == -1) {
            direction = 16;
        }
        direction -= 1;
        adjustShape();
    }

    public void adjustShape() {
        if (direction == 0 || direction == 1 || direction == 8 || direction == 9 || direction == 10 || direction == 15) {
            carShape.width = 50;
            carShape.height = 25;
        } else {
            carShape.width = 25;
            carShape.height = 50;
        }

    }

    public void moveForward() {
        double angle = Math.toRadians(direction * 22.5);
        prevX = carShape.x;
        prevY = carShape.y;
        updateX(speed * Math.cos(angle));
        updateY(speed * Math.sin(angle));
    }

    public void causeCrash() {
        speed = 0;
        setX(prevX);
        setY(prevY);
        stopEngineSound();
        crashSound.setFramePosition(0); // rewind to the beginning
        crashSound.start(); // start playing
    }
    public void increaseSpeed() {
        if ((speed + 2) > 10) {
            speed = 10;
        } else {
            speed += 2;
        }
        playEngineSound();
    }

    public void decreaseSpeed() {
        if ((speed - 2) <= 0) {
            speed = 0;
            stopEngineSound();
        } else {
            speed -= 2;
            playEngineSound();
        }
    }
    public int getX() {return carShape.x; }
    public int getY() {return carShape.y; }

    public void setX(int x) {
        carShape.x = x;
    }

    public void setY(int y) {
        carShape.y = y;
    }

    public void updateX(double x) {
        carShape.x += x;
    }

    public void updateY(double y) {
        carShape.y += y;
    }

    public void updateCheckPoint() {
        checkPoints += 1;
    }

    public int getCheckPoint() {
        return checkPoints;
    }

    public boolean hasWon() {
        return checkPoints == 5;
    }

    public void shut() {
        speed = 0;
        stopEngineSound();
    }

    public boolean hasCollided(Car opponentCar) {
        return Math.abs(getX() - opponentCar.getX()) < 30 && Math.abs(getY() - opponentCar.getY()) < 30;
    }

    public void playEngineSound() {
        if (!engineSound.isActive()) {
            engineSound.setFramePosition(0);
            engineSound.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    public void stopEngineSound() {
        if (engineSound.isActive()) {
            engineSound.stop();
        }
    }
}