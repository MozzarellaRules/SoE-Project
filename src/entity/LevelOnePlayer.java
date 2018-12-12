package entity;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;


import entity.strategy.*;
import tilemap.TileMap;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelOnePlayer extends DynamicSprite implements IObservable {

    // Sprite frames
    private ArrayList<BufferedImage[]> sprites;

    // Each element of the array refers to the number of frames (or columns) in a row of the sprite asset
    // The sprite asset now have 3 rows (to animate 3 states) so I'm defining the number of frames of each row
    private final int[] numFrames = {6,12,12};

    // The integer refers to the row of the sprite asset
    private int IDLE_ROW = 0;
    private int WALKING_ROW = 1;
    private int JUMPING_ROW = 1;
    private int SHOOTING_ROW = 2;

    private int remainingBullets = 10;

    private boolean firing;
    private IObserver healthObserver;

    // Health
    private int health;
    private int maxHealth = 3;
    private boolean isDead;
    private BufferedImage imageHealth;
    private BufferedImage subImageHealth;
    private boolean flinching = false;
    private long flinchTimer;

    private ArrayList<IStrategy> strategies;
    private final static int
            moveLeftStrategy = 0,
            moveRightStrategy = 1,
            jumpStrategy = 2,
            fallStrategy = 3;

    public LevelOnePlayer(TileMap tm) {
        super(tm);

        strategies = new ArrayList<>();
        strategies.add(new MoveLeftStrategy());
        strategies.add(new MoveRightStrategy());
        strategies.add(new JumpStrategy());
        strategies.add(new FallStrategy());

        // Init parameters
        health = maxHealth;
        setFacingRight(true);
        current_row = IDLE_ROW;

        try {
            // Load frames of the sprite
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Pirates/pirate_1.png"));
            sprites= new ArrayList<BufferedImage[]>();
            for(int i=0; i<3; i++) { // i = number of rows
                BufferedImage[] bi= new BufferedImage[numFrames[i]];
                for(int j=0; j<numFrames[i]; j++) { // j = number of columns
                        bi[j] = spritesheet.getSubimage(j*width, i*height, width, height);
                }
                sprites.add(bi);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Animate sprite
        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(sprites.get(IDLE_ROW)); // Animate row IDLE_ROW of the sprite asset
        imageAnimator.setDelay(100);
    }

    public int getRemainingBullets() {
        return remainingBullets;
    }

    public void setRemainingBullets(int remainingBullets) {
        this.remainingBullets = remainingBullets;
    }

    public boolean isDead() { return isDead; }
    public int getHealth() { return health; }
    public int getMaxHealth() {	return maxHealth; }

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

    private void getNextDelta() {
        double dx = 0, dy = 0;

        if(isMovingLeft()) {
            setFacingRight(false);
            dx = strategies.get(LevelOnePlayer.moveLeftStrategy).recalcDx(getDx());
        }

        if(isMovingRight()) {
            setFacingRight(true);
            dx = strategies.get(LevelOnePlayer.moveRightStrategy).recalcDx(getDx());
        }

        if(isJumping()) {
            dy = strategies.get(LevelOnePlayer.jumpStrategy).recalcDy(getDy());
        }

        if(isFalling()) {
            dy = strategies.get(LevelOnePlayer.fallStrategy).recalcDy(getDy());
        }

        setDx(dx);
        setDy(dy);
    }

    @Override
    public void update(){
        getNextDelta();
        checkTileMapCollision();

        // Set sprite imageAnimator
        if(isMovingLeft() || isMovingRight()) {
            if(current_row != WALKING_ROW) {
                current_row = WALKING_ROW;
                imageAnimator.setFrames(sprites.get(WALKING_ROW));
                imageAnimator.setDelay(100);
            }
        }
        else if(isJumping()) {
            if (current_row != JUMPING_ROW) {
                current_row = JUMPING_ROW;
                imageAnimator.setFrames(sprites.get(JUMPING_ROW));
                imageAnimator.setDelay(50);
            }
        }
        else if(firing) {
            if (current_row != SHOOTING_ROW) {
                current_row = SHOOTING_ROW;
                imageAnimator.setFrames(sprites.get(SHOOTING_ROW));
                imageAnimator.setDelay(20);
            }
        }
        else {
            if(current_row != IDLE_ROW) {
                current_row = IDLE_ROW;
                imageAnimator.setFrames(sprites.get(IDLE_ROW));
                imageAnimator.setDelay(100);
            }
        }
        imageAnimator.update(); // Update frame of the sprite
        if(imageAnimator.hasPlayedOnce()) { firing = false; }

        // If the character was hit, he's immune for 1 sec
        if(flinching) {
        	long elapsed = (System.nanoTime()-flinchTimer)/1000000;
        	if(elapsed > 1000) {
        		flinching = false;
        	}
        }
    }

    public void gatherAmmo() {
        this.remainingBullets += 3;
    }

    public void setFiring(boolean firing) {
        this.firing = firing;
    }

    @Override
    public void draw(Graphics2D g) {
        // Draw remaining number of projectiles
        g.drawImage(new ImageIcon("resources/Objects/BulletIcon.png").getImage(), 0, 25, null);
        g.setFont(new Font("Arial",Font.BOLD,12));
        g.setColor(new Color(210, 225, 94));
        g.drawString("x"+(remainingBullets), 25, 50);
        g.setColor(new Color(1, 1, 78));
        g.drawString("x"+(remainingBullets), 26, 51);

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
