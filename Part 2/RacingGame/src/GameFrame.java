import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private GamePanel gp;

    GameFrame() {
        super();
        setTitle("Racing Game");
        setBounds(0,0,Configurations.FRAME_WIDTH, Configurations.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);
        gp = new GamePanel();
        gp.setBounds(0,0,Configurations.FRAME_WIDTH, Configurations.FRAME_HEIGHT);
        cp.add(gp);
        setVisible(true);
    }
}