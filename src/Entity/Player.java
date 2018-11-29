package Entity;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import TileMap.TileMap;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


//Classe per il personaggio principale
public class Player extends MapObject {

    // Sprite animation
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
    private boolean flinching=false;
    private long flinchTimer;
    

    // Firing
    private ArrayList<Projectile> projectiles;
    private boolean firing;
    private int maxProjectiles = 1;
    private int currentProjectile = 0;
    private BufferedImage imageHealth; 
    private BufferedImage subImageHealth;
    // Health not now
    public int health;
    private int maxHealth=3;
    private boolean dead;
    
    public int getHealth() {
    	return health;
    }
    public int getMaxHealth() {
    	return maxHealth;
    }
    private void setHealthImage() {
    	if(health==3) {
			subImageHealth=imageHealth.getSubimage(0, 0, 54, 16);
    	}
		if(health==2) {
			subImageHealth=imageHealth.getSubimage(0, 0, 36, 16);
		}
		if(health==1) {
			subImageHealth=imageHealth.getSubimage(0, 0, 18, 16); 	
		}
    }
    public Player(TileMap tm) {
        super(tm);

        width = 32; // width of the sprite image
        height = 32; // height of the sprite image
        cwidth = 20; // width of the collision box, CIOE' LA PARTE CHE VA A COLLIDERE
        cheight = 20; // height of the collision box,CIOE' come sopra
        
        // Movement parameters
        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;

        
        
        //Qui carico l'immagine e carico le animazioni che dovranno essere eseguite
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Pirates/pirates.png"));
            imageHealth=ImageIO.read((getClass().getResourceAsStream("/Icons/life.png")));
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
        health=maxHealth;
        facingRight = true;
        projectiles=new ArrayList<Projectile>();
        currentAction = IDLE;//Il primo stato è quello di IDLE
        animation = new Animation();//Creo l'animazione
        animation.setFrames(sprites.get(IDLE));//E la faccio partire dallo stato di IDLE
        animation.setDelay(100);//L'animazione si aggiorna ogni 100 (millsecondi?)
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
    
    //Update del giocatore--> avviene così--> Prevedo la prossima posizione,controllo se ci sono collisioni e mi muovo
    public void update() {
    	setHealthImage();
        getNextPosition(); // Update position
        checkTileMapCollision();//Calcolo se ci sono collisioni
        setPosition(xtemp, ytemp);//Mi sposto verso la destinazione

        //Importante per capire da che parte guardo così da disegnarlo bene
        if(right)
            facingRight = true;
        if(left)
            facingRight = false;

        // Create projectile
        if(firing){
            if(currentProjectile < maxProjectiles ) { // Check if max number of (simultaneous) projectiles has been reached
                Projectile pj = new Projectile(tileMap, facingRight); // Create new projectile
                pj.setPosition(x, y); // Put it near the pirate
                projectiles.add(pj); // Add it
                currentProjectile++; // Increases max number of projectiles created
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
        //Se sto facendo determinate azioni, imposto quell'azione  come quella corrente e faccio partire le animazioni di quella azione
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
                animation.setDelay(50);
            }
        }
        else { //Quando non sto facendo niente
            if(currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(100);
            }
        }
        //Discorso dell'animazione, una volta attaccato,devi fermarti e non ripetere sempre l'animazione
        if(animation.hasPlayedOnce()) {
            firing = false;
        }
        /*if(intersects(e)) {
        	hit();
        }*/
        //se vieni colpito da un nemico per 1 sec sei immune 
        if(flinching) {
        	long elapsed=(System.nanoTime()-flinchTimer)/1000000;
        	if(elapsed>1000) {
        		flinching=false;
        	}
        }
        //se cadi fuori della mappa manda gameover
        if(notOnScreen()) {
        	dead=true;
        }
        animation.update();
    }
    public void hit() {
    	if(flinching) return;
    	health-=1;
    	if(health<0) 
    		health =0;
    	if(health==0) 
    		dead=true;
    	flinching=true;
    	flinchTimer=System.nanoTime();
    	}
    public boolean isDead() {
    	return dead;
    }
    public void draw(Graphics2D g) {
        setMapPosition(); // update xmap and ymap
        g.drawImage(subImageHealth, 0, 20, null);
        // draw flaming projectiles
        for (int i=0; i < projectiles.size(); i++){
            projectiles.get(i).draw(g);
        }

        // draw player, se guardo a destra lo disegno in un modo, se invece sto andando verso sinistra lo disegno al contrario
        if(facingRight) {
            g.drawImage(animation.getImage(), (int)(x+xmap-width/2), (int)(y+ymap-height/2), null);
        }
        else {
            g.drawImage(animation.getImage(), (int)(x+xmap-width/2+width), (int)(y+ymap-height/2), -width, height, null);
        }
    }

}
