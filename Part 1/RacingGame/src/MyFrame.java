import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private MyPanel mp;

    MyFrame() {
        super();
        setTitle("Racing Game");
        setBounds(100,100,500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(null);
        mp = new MyPanel();
        mp.setBounds(90,90,500,500);
        cp.add(mp);
    }
}
