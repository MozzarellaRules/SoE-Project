package entity;

import tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Health extends Sprite implements IObserver {
    private BufferedImage imageHealth;
    private BufferedImage subImageHealth;

    private int health;

    public Health(TileMap tm) {
        super(tm);

        try {
            imageHealth = ImageIO.read((getClass().getResourceAsStream("/Icons/life.png")));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setHealth(int health) {
        this.health = health;
    }

    private void setHealthImage() {
        int widthFrame = 18;
        int heightFrame = 16;
        if(health==3) { subImageHealth=imageHealth.getSubimage(0, 0, widthFrame*3, heightFrame); }
        else if(health==2) { subImageHealth=imageHealth.getSubimage(0, 0, widthFrame*2, heightFrame); }
        else if(health==1) { subImageHealth=imageHealth.getSubimage(0, 0, widthFrame, heightFrame); }
    }

    @Override
    public void updateObserver(IObservable obj, Object arg) {
        LevelOnePlayer player = (LevelOnePlayer) obj;
        health = (int) arg;
    }

    public void update() {
        setHealthImage();
    }

    public void draw(Graphics2D g) {
        g.drawImage(subImageHealth, 10, 10, null);
    }
}
