import java.awt.*;

public class Sprite {

    private boolean visible;
    protected int dx;
    protected int dy;
    protected int x = 40;
    protected int y = 60;
    private Image image;

    public Sprite() {
        this.visible = true;
    }

    public boolean isVisible() { return visible; }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public Image getImage() { return image; }

    // Setters
    public void setVisible(boolean visible) {
        this.visible = visible;
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
