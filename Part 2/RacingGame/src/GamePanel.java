import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener {
    private Game game;
    GameThread gameUpdate;

    GamePanel() {
        setLayout(null);
        game = new Game();
        gameUpdate = new GameThread(game, this);
        gameUpdate.start();
        addKeyListener(this);
        setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        removeAll();
        if (game.isOver()) {
            paintEndGame(g, game.getWinner());
        } else {
            paintRaceTrack(g, game.getRaceTrack());
            paintCar(g, game.getRedCar());
            paintCar(g, game.getYellowCar());
            paintRedCarElements(g);
            paintYellowCarElements(g);
        }
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
        g.drawString("RED CAR", 825, 150);
        g.drawString(String.format("- Speed: %s /100", game.getRedCar().getSpeed()), 840, 170);
    }

    public void paintYellowCarStatistics(Graphics g) {
        g.drawString("YELLOW CAR", 825, 200);
        g.drawString(String.format("- Speed: %s /100", game.getYellowCar().getSpeed()), 840, 220);
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

/*    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == t) {
            for (int i = 0; i < 2; i++) {
                UpdateGame updateGame = new UpdateGame(game, this);
                updateGame.start();
            }
        }
    }*/
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'w' && !game.isOver()) {
            game.turnRedCarLeft();
        } else if (e.getKeyChar() == 's'&& !game.isOver()) {
            game.turnRedCarRight();
        } else if (e.getKeyChar() == 'q'&& !game.isOver()) {
            game.increaseRedCarSpeed();
        } else if (e.getKeyChar() == 'a'&& !game.isOver()) {
            game.decreaseRedCarSpeed();
        } else if (e.getKeyChar() == 'o'&& !game.isOver()) {
            game.turnYellowCarLeft();
        } else if (e.getKeyChar() == 'k' && !game.isOver()) {
            game.turnYellowCarRight();
        } else if (e.getKeyChar() == 'p' && !game.isOver()) {
            game.increaseYellowCarSpeed();
        } else if (e.getKeyChar() == 'l' && !game.isOver()) {
            game.decreaseYellowCarSpeed();
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
