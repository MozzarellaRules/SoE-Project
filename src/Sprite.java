import java.awt.*;

public class Sprite {

    private boolean isVisible;
    protected int x;
    protected int y;
    protected Image image;

    public Sprite() {
        this.isVisible = true;
    }

    public boolean isVisible() { return isVisible; }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public Image getImage() { return image; }

    // Setters
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setImage(Image image) {
        this.image = image;
    }

}
