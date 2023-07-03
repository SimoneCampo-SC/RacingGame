package Client;

import Shared.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener {
    private Game game;
    GameThread gameUpdate;

    private Boolean gameStart = false;

    GamePanel(String playerCar) {
        setLayout(null);
        game = new Game(playerCar);
        gameUpdate = new GameThread(game, this);
        addKeyListener(this);
        setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        removeAll();
        if (gameStart == false) {
            paintLobby(g);
        } else {
            if (game.isOver()) {
                paintEndGame(g, game.getWinner());
            } else {
                paintRaceTrack(g, game.getRaceTrack());
                paintCar(g, game.getPlayerCar());
                paintCar(g, game.getOpponentCar());
                if(game.getPlayerCar().getName() == "Red Car") {
                    paintRedCarElements(g);
                } else {
                    paintYellowCarElements(g);
                }
            }
        }
    }
    public void paintLobby(Graphics g) {
        g.drawString("You're waiting", 200, 200);
    }

    public void paintRaceTrack(Graphics g, RaceTrack raceTrack) {
        Color c1 = Color.green;
        g.setColor(c1);
        g.fillRect(raceTrack.getGrass().x, raceTrack.getGrass().y, raceTrack.getGrass().width, raceTrack.getGrass().height);

        Color c2 = Color.black;
        g.setColor(c2);
        g.drawRect(raceTrack.getOuterEdge().x, raceTrack.getOuterEdge().y, raceTrack.getOuterEdge().width, raceTrack.getOuterEdge().height);
        g.drawRect(raceTrack.getInnerEdge().x, raceTrack.getInnerEdge().y, raceTrack.getInnerEdge().width, raceTrack.getInnerEdge().height);

        Color c3 = Color.yellow;
        g.setColor(c3);
        g.drawRect(raceTrack.getMidLaneMarker().x, raceTrack.getMidLaneMarker().y, raceTrack.getMidLaneMarker().width, raceTrack.getMidLaneMarker().height);

        Color c4 = Color.white;
        g.setColor(c4);
        g.drawLine(Configurations.START_LINE_X1,Configurations.START_LINE_Y1,Configurations.START_LINE_X2,Configurations.START_LINE_Y2); // start line
    }

    public void startGame() {
        gameStart = true;
        gameUpdate.start();
    }

    public void paintCar(Graphics g, Car car) {
        car.getCarFrames()[car.getDirection()].paintIcon(this, g, car.getX(), car.getY());
    }

    public void paintRedCarElements(Graphics g) {
        paintRedCarLegend(g);
        paintRedCarStatistics(g);
    }

    public void paintYellowCarElements(Graphics g) {
        paintYellowCarLegend(g);
        paintYellowCarStatistics(g);
    }

    public void paintRedCarLegend(Graphics g) {
        g.setColor(Color.black);
        g.drawLine(Configurations.START_LINE_X1, Configurations.START_LINE_Y1 + 100, Configurations.START_LINE_X2,Configurations.START_LINE_Y1 + 250);
        JTextArea redCarCommands = new JTextArea();
        redCarCommands.setText(Configurations.RED_CAR_COMMANDS);
        redCarCommands.setBounds(100,625,200,200);
        redCarCommands.setOpaque(false);
        redCarCommands.setEditable(false);
        add(redCarCommands);
    }
    public void paintYellowCarLegend(Graphics g) {
        JTextArea yellowCarCommands = new JTextArea();
        yellowCarCommands.setText(Configurations.YELLOW_CAR_COMMANDS);
        yellowCarCommands.setBounds(550,625,200,200);
        yellowCarCommands.setOpaque(false);
        yellowCarCommands.setEditable(false);
        add(yellowCarCommands);
    }

    public void paintRedCarStatistics(Graphics g) {
        g.setColor(Color.black);
        g.drawString("RED CAR", 825, 150);
        g.drawString(String.format("- Speed: %s /100", game.getPlayerCar().getSpeed()), 840, 170);
    }

    public void paintYellowCarStatistics(Graphics g) {
        g.setColor(Color.black);
        g.drawString("YELLOW CAR", 825, 200);
        g.drawString(String.format("- Speed: %s /100", game.getOpponentCar().getSpeed()), 840, 220);
    }

    public void paintEndGame(Graphics g, String winner) {
        g.setColor(Color.black);
        JLabel messageLabel = new JLabel();
        if (winner.equals("")) {
            messageLabel.setText("Game is over: cars have collided. Press 'X' to Exit");
        } else {
            messageLabel.setText(String.format("%s won the game! Press 'X' to Exit", winner));
        }
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        messageLabel.setBounds(200,100,600,200);
        add(messageLabel);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'w' && !game.isOver()) {
            game.turnCarLeft();
        } else if (e.getKeyChar() == 's'&& !game.isOver()) {
            game.turnCarRight();
        } else if (e.getKeyChar() == 'q'&& !game.isOver()) {
            game.increaseCarSpeed();
        } else if (e.getKeyChar() == 'a'&& !game.isOver()) {
            game.decreaseCarSpeed();
        } else if (e.getKeyChar() == 'x' && game.isOver()) {
            exitProgram();
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }

    public void exitProgram() {
        System.exit(0);
    }
}
