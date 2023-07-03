package Client;
import Shared.*;
public class GameThread extends Thread {
    Game game;
    GamePanel gp;
    GameThread(Game game, GamePanel gp) {
        this.game = game;
        this.gp = gp;
    }

    @Override
    public void run() {
        while (!game.isOver()) {
            game.updateGame();
            gp.repaint();
            try {
                sleep(Configurations.TIMER_DELAY);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}