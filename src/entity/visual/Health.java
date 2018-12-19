package entity.visual;

import entity.Sprite;
import entity.dynamic.Player;
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
            imageHealth = ImageIO.read((getClass().getResourceAsStream("/Objects/icon_health.png")));
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
    public void update() {
        setHealthImage();
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(subImageHealth, 10, 10, null);
    }

    @Override
    public void updateObserver(IObservable context, IObservable.PlayerEvent event) {
        if(event == IObservable.PlayerEvent.HEALTH_MODIFIED) {
            Player player = (Player) context;
            setHealth(player.getHealth());
        }
    }
}
