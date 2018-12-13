package gamestate;

import tilemap.Background;
import tilemap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;

public class LevelTwoState extends GameState {
    public static String BG_PATH = "/Background/full_background2.jpeg";
    private GameStateManager gsm;

    private TileMap tileMap;
    private Background bg;

    public LevelTwoState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    @Override
    public void init() {
        bg = new Background(BG_PATH,0.5);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        bg.draw(g);

        g.setFont(new Font("Arial",Font.BOLD,12));
        g.setColor(new Color(0, 109, 225));
        g.drawString("Level Two", 25, 50);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
