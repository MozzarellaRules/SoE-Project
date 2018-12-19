package entity.visual;

import entity.Sprite;
import entity.dynamic.Player;
import tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Health extends Sprite implements PlayerObserver {
    private BufferedImage imageHealth;
    private BufferedImage subImageHealth;

    private int health;

    public Health(TileMap tm) {
        super(tm);

        try {
            imageHealth = ImageIO.read((getClass().getResourceAsStream("/Objects/icon_health_player.png")));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set the health field
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * set the health image ( hearts)
     */
    private void setHealthImage() {
        int widthFrame = 19;
        int heightFrame = 16;

        if(health==4) { subImageHealth=imageHealth.getSubimage(0, 0, widthFrame*4, heightFrame); }
        else if(health==3) { subImageHealth=imageHealth.getSubimage(0, 0, widthFrame*3, heightFrame); }
        else if(health==2) { subImageHealth=imageHealth.getSubimage(0, 0, widthFrame*2, heightFrame); }
        else if(health==1) { subImageHealth=imageHealth.getSubimage(0, 0, widthFrame, heightFrame); }
    }

    /**
     * Update the Health Image
     */
    @Override
    public void update() {
        setHealthImage();
    }

    /**
     *Draw the image on the g panel
     * @param g
     */
    @Override
    public void draw(Graphics2D g) {
        g.drawImage(subImageHealth, 10, 10, null);
    }

    /**
     *
     * @param context is the player which must be osserved
     * @param event notify that the player's health is changend
     */
    @Override
    public void updateObserver(PlayerObservable context, PlayerObservable.PlayerEvent event) {
        if(event == PlayerObservable.PlayerEvent.HEALTH_MODIFIED) {
            Player player = (Player) context;
            setHealth(player.getHealth());
        }
    }
}
