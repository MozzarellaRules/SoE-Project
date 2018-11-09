import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Pirate extends Sprite {

    private int START_X = 350/2 - 32;
    private int START_Y = 350/2 - 32;
    private int dx;
    private int dy;
    private Image image;
    private int widthImage;
    private int heightImage;

    public Pirate() {
        initPirate();
    }

    private void initPirate() {
        ImageIcon ii = new ImageIcon("resources/pirate/Pirate Captain (Idle) GIF.gif");
        setImage(ii.getImage());

        this.widthImage = ii.getIconWidth();
        this.heightImage = ii.getIconHeight();
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void jump() {
        // to implement
    }

    public int getWidth() { return this.widthImage; }
    public int getHeight() { return this.heightImage; }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) { dx = -2; }
        if (key == KeyEvent.VK_RIGHT) { dx = 2; }
        if (key == KeyEvent.VK_UP) { dy = -2; }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) { dx = 0; }
        if (key == KeyEvent.VK_RIGHT) { dx = 0; }
        if (key == KeyEvent.VK_UP) { dy = 0; }
    }

}
