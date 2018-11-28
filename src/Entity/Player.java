package Entity;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import TileMap.TileMap;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MapObject {

    // Sprite animation
    private ArrayList<BufferedImage[]> sprites;

    // Each element of the array refers to the number of frames (or columns) in a row of the sprite asset
    // The sprite asset now have 3 rows (to animate 3 states) so I'm defining the number of frames of each row
    private final int[] numFrames = {6,12,4};

    // States
    // The integer refers to the row of the sprite asset
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 1;
    private static final int FALLING = 0;
    private static final int SHOOTING = 2;
    private static int maxBullets = 10; //Ammo when you start the game
    // Firing
    private ArrayList<Projectile> projectiles;
    private boolean firing;
    private int simultaneousProj = 1; //amount of bullets you can fire by pressing Spacebar ONCE (no spam)
    private int currentProjectile = 0;

    /* not now
    private int health;
    private int maxHealth;
    private boolean dead;
    */

    public Player(TileMap tm) {
        super(tm);



        // Movement parameters
        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;

        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Pirates/pirate_1.png"));
            sprites= new ArrayList<BufferedImage[]>();
            for(int i=0; i<3; i++) { // i = number of row
                BufferedImage[] bi= new BufferedImage[numFrames[i]];
                for(int j=0; j<numFrames[i]; j++) { // j = number of column
                        bi[j] = spritesheet.getSubimage(j*width, i*height, width, height);
                }
                sprites.add(bi);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        facingRight = true;
        projectiles=new ArrayList<Projectile>();
        currentAction = IDLE;
        animation = new Animation();
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(100);
    }

    public void setFiring(boolean fire) {
        firing = fire;
    }

    private void getNextPosition() {
        // Movement
        if(left) {
            dx -= moveSpeed; // speed increases progressively
            if(dx < -maxSpeed)
                dx = -maxSpeed; // max speed reached
        }
        else if(right) {
            dx += moveSpeed;
            if(dx > maxSpeed)
                dx = maxSpeed;
        }
        else { // I'm not moving
            if(dx > 0) {
                dx -= stopSpeed;
                if(dx < 0)
                    dx = 0;
            }
            else if(dx < 0) {
                dx += stopSpeed;
                if(dx > 0)
                    dx = 0;
            }
        }

        // Jumping
        if(jumping &&!falling) {
            dy = jumpStart;
            falling = false;
        }

        // Falling
        if(falling) {
            dy += fallSpeed;

            if(dy > 0)
                jumping = false;
            if(dy < 0 && !jumping)
                dy += stopJumpSpeed;
            if(dy > maxFallSpeed)
                dy = maxFallSpeed;
        }

        // Cannot attack while moving
        if((currentAction== SHOOTING)&&!(jumping||falling)) {
            dx = 0;
        }
    }

    public void update() {
        getNextPosition(); // Update position
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if(right)
            facingRight = true;
        if(left)
            facingRight = false;

        // Create projectile
        if(firing){
            if(currentProjectile < simultaneousProj) { // Check if max number of (simultaneous) projectiles has been reached
                if (maxBullets > 0){
                Projectile pj = new Projectile(tileMap, facingRight); // Create new projectile
                pj.setPosition(x-3, y); // Put it near the pirate
                projectiles.add(pj); // Add it
                currentProjectile++; // Increases max number of projectiles created
                maxBullets--;
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
        if(left || right) {
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

        if(animation.hasPlayedOnce()) {
            firing = false;
        }


        animation.update();
    }



    public void checkAttack(ArrayList<Enemy> enemies ){



        Projectile projectile;

        for (Enemy e :enemies){
            int i = 0;
            boolean hit = false;

            if(projectiles.size() > 0){
            projectile = projectiles.get(i);

            do
            {

                projectile = projectiles.get(i);
                i++;
               if (projectile.intersects(e)){
                   hit = true;
               }

            }while(i < projectiles.size() -1  && !hit);

            if (hit){
                e.hit(1);
                projectiles.remove(projectile);

            }



            if (e.isDead()){
                enemies.remove(e);
                break;
            }


        }

    }}

    public void draw(Graphics2D g) {

        //if (!notOnScreen()){ // Vogliamo gestire la condizione in cui il nemico non è sullo schermo
        //In questo caso la draw non dovrebbe essere eseguita per questioni di ottimizzazione
        setMapPosition(); // update xmap and ymap

        for(Projectile p: projectiles){
            p.draw(g);
        }


        if(facingRight) {
            g.drawImage(animation.getImage(), (int)(x+xmap-width/2), (int)(y+ymap-height/2), null);
        }
        else {
            g.drawImage(animation.getImage(), (int)(x+xmap-width/2+width), (int)(y+ymap-height/2), -width, height, null);
        }
    }


}
