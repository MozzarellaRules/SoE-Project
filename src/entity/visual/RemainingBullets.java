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

        this.image = new ImageIcon("resources/Objects/icon_bullet.png").getImage();
    }

    public int getRemainingBullets() {
        return remainingBullets;
    }

    public void setRemainingBullets(int remainingBullets) {
        this.remainingBullets = remainingBullets;
    }

    @Override
    public void update() { }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, 0, 25, null);
        g.setFont(new Font("Arial",Font.BOLD,12));
        g.setColor(new Color(212, 154, 60));
        g.drawString("x"+(remainingBullets), 25, 50);
        g.setColor(new Color(0, 0, 0));
        g.drawString("x"+(remainingBullets), 26, 51);
    }

    @Override
    public void updateObserver(PlayerObservable context, PlayerObservable.PlayerEvent event) {
        if(event == PlayerObservable.PlayerEvent.BULLETS_MODIFIED) {
            PlayerGround player = (PlayerGround) context;
            setRemainingBullets(player.getRemainingBullets());
        }
    }
}
