import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    private Image image;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        loadImage();

        int w = image.getWidth(this);
        int h =  image.getHeight(this);
        setPreferredSize(new Dimension(w, h));
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon("src/resources/image.png");
        image = ii.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}
