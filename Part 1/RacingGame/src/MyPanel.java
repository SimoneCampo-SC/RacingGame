import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPanel extends JPanel implements ActionListener{
    private ImageIcon[] redCar = new ImageIcon[17];
    private ImageIcon[] yellowCar = new ImageIcon[17];
    private float imageName = 0;
    private Timer t;

    private int currentImage = 0;

    MyPanel() {
        setLayout(null);
        loadCar();
        t = new Timer(1000 / 30, this);
        t.start();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        redCar[currentImage % 16].paintIcon(this, g, 0, 0);
        yellowCar[currentImage % 16].paintIcon(this, g, 100, 0);
        currentImage += 1;
    }

    public void loadCar() {
        for (int i = 0; i < 16; i++)
        {
            redCar[i] = new ImageIcon(getClass().getResource(String.format("redCar/%s.png", String.valueOf(imageName))));
            yellowCar[i] = new ImageIcon(getClass().getResource(String.format("yellowCar/%s.png", String.valueOf(imageName))));
            imageName += 22.5;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == t) {
            repaint();
        }
    }
}
