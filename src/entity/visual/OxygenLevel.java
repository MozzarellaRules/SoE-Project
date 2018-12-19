package entity.visual;

import entity.Sprite;
import entity.dynamic.PlayerWater;
import tilemap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OxygenLevel extends Sprite implements IObserver {
    private BufferedImage imageHealth;
    private BufferedImage subImageHealth;
    private int oxygen;

    public OxygenLevel(TileMap tm) {
        super(tm);

        /*
        try {
            imageHealth = ImageIO.read((getClass().getResourceAsStream("/Icons/icon_health.png")));
        } catch(Exception e) {
            e.printStackTrace();
        }
        */
    }

    public void setOxygen(int oxygen) {
        this.oxygen = oxygen;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        g.setFont(new Font("Arial",Font.BOLD,12));
        g.setColor(new Color(212, 154, 60));
        g.drawString("Oxygen: " + oxygen, 25, 50);
    }

    @Override
    public void updateObserver(IObservable context, IObservable.PlayerEvent event) {
        if(event == IObservable.PlayerEvent.OXYGEN_MODIFIED) {
            PlayerWater player = (PlayerWater) context;
            this.oxygen = player.getOxygen();
        }
    }
}