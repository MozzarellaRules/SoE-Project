package entity.visual;

import entity.IObservable;
import entity.IObserver;
import entity.Sprite;
import entity.dynamic.PlayerGround;
import tilemap.TileMap;

import javax.swing.*;
import java.awt.*;

public class RemainingBullets extends Sprite implements IObserver {
    private int remainingBullets;
    private Image image;

    public RemainingBullets(TileMap tm) {
        super(tm);

        this.image = new ImageIcon("resources/Objects/BulletIcon.png").getImage();
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
        g.setColor(new Color(210, 225, 94));
        g.drawString("x"+(remainingBullets), 25, 50);
        g.setColor(new Color(1, 1, 78));
        g.drawString("x"+(remainingBullets), 26, 51);
    }

    @Override
    public void updateObserver(IObservable context, IObservable.PlayerEvent event) {
        if(event == IObservable.PlayerEvent.BULLETS_MODIFIED) {
            PlayerGround player = (PlayerGround) context;
            setRemainingBullets(player.getRemainingBullets());
        }
    }
}
