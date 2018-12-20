package entity.visual;

import entity.Sprite;
import entity.dynamic.PlayerGround;
import tilemap.TileMap;

import javax.swing.*;
import java.awt.*;

public class RemainingBullets extends Sprite implements PlayerObserver {
    private int remainingBullets;
    private Image image;

    public RemainingBullets(TileMap tm) {
        super(tm);

        this.image = new ImageIcon(getClass().getResource("/Objects/icon_bullet.png")).getImage();
    }

    /**
     *
     * @return the remainingBullets field
     */
    public int getRemainingBullets() {
        return remainingBullets;
    }

    /**
     *
     * @param remainingBullets
     */
    public void setRemainingBullets(int remainingBullets) {
        this.remainingBullets = remainingBullets;
    }


    @Override
    public void update() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }


    /**
     * Draws on the g element the Remaining Bullet class
     * @param g
     */
    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, 0, 25, null);
        g.setFont(new Font("Arial",Font.BOLD,12));
        g.setColor(new Color(212, 154, 60));
        g.drawString("x"+(remainingBullets), 25, 50);
        g.setColor(new Color(0, 0, 0));
        g.drawString("x"+(remainingBullets), 26, 51);
    }


    /**
     *
     * @param context is the parameter that must be observed
     * @param event is the parameter that indicates that the number of bullets is changed
     */
    @Override
    public void updateObserver(PlayerObservable context, PlayerObservable.PlayerEvent event) {
        if(event == PlayerObservable.PlayerEvent.BULLETS_MODIFIED) {
            PlayerGround player = (PlayerGround) context;
            setRemainingBullets(player.getRemainingBullets());
        }
    }
}
