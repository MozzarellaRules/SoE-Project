package entity;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import entity.strategy.*;
import tilemap.TileMap;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Player extends DynamicSprite implements IObservable {

    // Sprite frames
    private ArrayList<BufferedImage[]> sprites;

    // Health
    private int health;
    private int maxHealth = 3;
    private boolean isDead;
    private boolean flinching = false;
    private long flinchTimer;
    private IObserver healthObserver;

    private boolean facingRight;

    protected int IDLE_ROW = 0;

    public Player(TileMap tm) {
        super(tm);

        // Falling when the game starts
        setStrategyX(StrategyFactory.getInstance().getStopStrategyX());
        setStrategyY(StrategyFactory.getInstance().getFallStrategy());

        // Init parameters
        health = maxHealth;
        setFacingRight(true);
    }

    public void loadSpriteAsset(int numFrames[], String path) {
        try {
            // Load frames of the sprite
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Pirates/pirate_1.png"));
            sprites = new ArrayList<BufferedImage[]>();
            for(int i=0; i<numFrames.length; i++) { // i = number of rows
                BufferedImage[] bi= new BufferedImage[numFrames[i]];
                for(int j=0; j<numFrames[i]; j++) { // j = number of columns
                    bi[j] = spritesheet.getSubimage(j*width, i*height, width, height);
                }
                sprites.add(bi);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(sprites.get(IDLE_ROW)); // Animate row IDLE_ROW of the sprite asset
        imageAnimator.setDelay(100);
    }

    protected ArrayList<BufferedImage[]> getSprites() {
        return sprites;
    }

    public boolean isFacingRight() { return facingRight; }
    public void setFacingRight(boolean facingRight) { this.facingRight = facingRight; }

    public boolean isDead() { return isDead; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }

    public void hit(int damage) {
        if(flinching) // If the character is immune, go out
            return;
        health -= damage;
        if(health < 0) {
            health = 0;
        }
        if(health == 0) {
            isDead = true;
        } else {
            flinching = true; // The sprite is now immune for 1 sec
            flinchTimer = System.nanoTime();
        }
        notifyObserver();
    }

    public abstract void hookAnimation();

    @Override
    public void update(){
        getNextDelta();
        checkTileMapCollision();

        if(current_row != IDLE_ROW) {
            current_row = IDLE_ROW;
            imageAnimator.setFrames(sprites.get(IDLE_ROW));
            imageAnimator.setDelay(100);
        }
        hookAnimation();

        imageAnimator.update(); // Update frame of the sprite

        // If the character was hit, he's immune for 1 sec
        if(flinching) {
        	long elapsed = (System.nanoTime()-flinchTimer)/1000000;
        	if(elapsed > 1000) {
        		flinching = false;
        	}
        }
    }

    @Override
    public void draw(Graphics2D g) {
        // Draw remaining number of projectiles
        /*
        g.drawImage(new ImageIcon("resources/Objects/BulletIcon.png").getImage(), 0, 25, null);
        g.setFont(new Font("Arial",Font.BOLD,12));
        g.setColor(new Color(210, 225, 94));
        g.drawString("x"+(remainingBullets), 25, 50);
        g.setColor(new Color(1, 1, 78));
        g.drawString("x"+(remainingBullets), 26, 51);
        */

        if(isFacingRight()) {
            g.drawImage(
                    imageAnimator.getImage(),
                    (int)(getX()+tileMap.getX()-width/2),
                    (int)(getY()+tileMap.getY()-height/2),
                    null);
        }
        else {
            g.drawImage(
                    imageAnimator.getImage(),
                    (int)(getX()+tileMap.getX()-width/2+width),
                    (int)(getY()+tileMap.getY()-height/2),
                    -width,
                    height,
                    null);
        }
    }

    @Override
    public void addObserver(IObserver o) {
        healthObserver = o;
    }

    @Override
    public void deleteObserver(IObserver o) {
        healthObserver = null;
    }

    @Override
    public void notifyObserver() {
        healthObserver.updateObserver(this, getHealth());
    }
}
