package entity;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;


import tilemap.TileMap;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FirstLevelPlayer extends DynamicSprite {

    // Sprite frames
    private ArrayList<BufferedImage[]> sprites;

    // Each element of the array refers to the number of frames (or columns) in a row of the sprite asset
    // The sprite asset now have 3 rows (to animate 3 states) so I'm defining the number of frames of each row
    private final int[] numFrames = {6,12,12};

    // States
    // The integer refers to the row of the sprite asset
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 1;
    private static final int FALLING = 0;
    private static final int SHOOTING = 2;

    // Shooting
    private ArrayList<Projectile> projectiles;
    private boolean firing;
    private int simultaneousProj = 1; // Amount of bullets you can fire by pressing Spacebar ONCE (no spam)
    private int currentProjectile = 0; // Amount of bullets you actually fire by pressing Spacebar ONCE - limited by simultaneousProj
    private int remainingBullets = 10; // Ammo when you start the game

    // Health
    public int health;
    private int maxHealth = 3;
    private boolean isDead;
    private BufferedImage imageHealth;
    private BufferedImage subImageHealth;
    private boolean flinching = false;
    private long flinchTimer;

    public FirstLevelPlayer(TileMap tm) {
        super(tm);

        // Init parameters
        moveSpeed = 0.3;
        maxSpeed = 2.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;
        health = maxHealth;
        facingRight = true;
        currentAction = IDLE;

        try {
            // Load health image
            imageHealth = ImageIO.read((getClass().getResourceAsStream("/Icons/life.png")));

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

        projectiles = new ArrayList<Projectile>();

        // Animate sprite
        animation = new Animation();
        animation.setFrames(sprites.get(IDLE)); // Animate row IDLE of the sprite asset
        animation.setDelay(100);
    }

    public boolean isDead() { return isDead; }
    public int getHealth() { return health; }
    public int getMaxHealth() {	return maxHealth; }
    private void setHealthImage() {
        int widthFrame = 18;
        int heightFrame = 16;
        if(health==3) { subImageHealth=imageHealth.getSubimage(0, 0, widthFrame*3, heightFrame); }
        else if(health==2) { subImageHealth=imageHealth.getSubimage(0, 0, widthFrame*2, heightFrame); }
        else if(health==1) { subImageHealth=imageHealth.getSubimage(0, 0, widthFrame, heightFrame); }
    }
    public void hit(int damage) {
        if(flinching) // If the character is immune, go out
            return;
        health -= damage; // Decrement health
        // Check if the character is isDead
        if(health < 0)
            health = 0;
        else if(health == 0)
            isDead = true;
        flinching = true; // The sprite is now immune for 1 sec
        flinchTimer = System.nanoTime();
    }

    private void getNextPosition() {
        // Movement
        if(movingLeft) {
            setDx(getDx()-moveSpeed); // speed increases progressively
            if(getDx() < maxSpeed)
                setDx(-maxSpeed); // max speed reached
        }
        else if(movingRight) {
            setDx(getDx()+moveSpeed);
            if(getDx() > maxSpeed)
                setDx(maxSpeed);
        }
        else { // I'm not moving
            if(getDx() > 0) {
                setDx(getDx()-stopSpeed);
                if(getDx() < 0)
                    setDx(0);
            }
            else if(getDx() < 0) {
                setDx(getDx()+stopSpeed);
                if(getDx() > 0)
                    setDx(0);
            }
        }

        // Jumping
        if(jumping &&!falling) {
            setDy(jumpStart);
            falling = false;
        }

        // Falling
        if(falling) {
            setDy(getDy()+fallSpeed);

            /*if(getDy()> 0)
                jumping = false;
           */
            if(getDy() < 0 && !jumping)
                setDy(getDy()+stopJumpSpeed);
            if(getDy() > maxFallSpeed)
                setDy(maxFallSpeed);
        }
    }

    public void update(){
    	setHealthImage();
        getNextPosition(); // Update position
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if(movingRight) // If moving movingRight update facingRight
            facingRight = true;
        if(movingLeft) // If moving movingLeft update facingRight
            facingRight = false;

        // Create projectile
        if(firing){
            if(currentProjectile < simultaneousProj) { // Check if max number of (simultaneous) projectiles has been reached
                if (remainingBullets > 0){
                    Projectile pj = new Projectile(tileMap, facingRight); // Create new projectile
                    pj.setPosition(getX()-3, getY()); // Put it near the pirate
                    projectiles.add(pj); // Add it
                    currentProjectile++; // Increases max number of projectiles created
                    remainingBullets--;
                }
            }
        } else
            currentProjectile = 0; // I'm not firing anymore... reset the number of projectiles created

        // Update projectiles on the screen and check if they must be removed
        for (int i=0; i< projectiles.size();i++) {
            projectiles.get(i).update(); // Update projectile position
            if(projectiles.get(i).shouldRemove()) {
                projectiles.remove(i);
                i--;
            }
        }

        // Set sprite animation
        if(movingLeft || movingRight) {
            if(currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(100);
            }
        }
        else if(jumping) {
            if (currentAction != JUMPING) {
                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(50);
            }
        }
        else if(firing) {
            if (currentAction != SHOOTING) {
                currentAction = SHOOTING;
                animation.setFrames(sprites.get(SHOOTING));
                animation.setDelay(20);
            }
        }
        else {
            if(currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(100);
            }
        }
        animation.update(); // Update frame of the sprite
        if(animation.hasPlayedOnce()) { firing = false; }

        // If the character was hit, he's immune for 1 sec
        if(flinching) {
        	long elapsed = (System.nanoTime()-flinchTimer)/1000000;
        	if(elapsed > 1000) {
        		flinching = false;
        	}
        }
    }

    public void setFiring(boolean fire) { firing = fire; }

    public void checkAttack(ArrayList<Enemy> enemies ){
        // Check if the character hit an enemy
        for(Enemy e : enemies){
            boolean hit = false;

            // If there are not projectiles -> no attack
            if(projectiles.size() > 0) {
                int i = 0;

                do
                {
                    Projectile p = projectiles.get(i);

                    if(p.intersects(e)){
                        hit = true; // An enemy was hit
                        e.hit(1); // Set the enemy damage
                        projectiles.remove(p); // Remove the projectile from map
                    }

                    i++;
                } while(i < (projectiles.size()-1)  && !hit);
            }

            // If the enemy is isDead, remove him
            if (e.isDead()){
                enemies.remove(e);
                break;
            }
    }}

    public void gatherAmmo() {
        this.remainingBullets += 3;
    }

    public void draw(Graphics2D g) {
        // Draw projectiles
        for (Projectile p : projectiles) { p.draw(g); }

        // Draw remaining number of projectiles
        g.drawImage(new ImageIcon("resources/Objects/BulletIcon.png").getImage(), 0, 25, null);
        g.drawString("x"+(remainingBullets), 25, 50);
        g.setFont(new Font("Arial",Font.BOLD,12));
        g.setColor(new Color(249,0, 34));

        // Draw image health
        g.drawImage(subImageHealth, 10, 10, null);

        // Draw sprite
        if (facingRight) {
            g.drawImage(animation.getImage(), (int) (getX() + tileMap.getX() - width / 2), (int) (getY() + tileMap.getY() - height / 2), null);
        } else {
            g.drawImage(animation.getImage(), (int) (getX() + tileMap.getX() - width / 2 + width), (int) (getY() + tileMap.getY() - height / 2), -width, height, null);
        }
    }
}
