package entity.dynamic;

import entity.ImageAnimator;
import tilemap.TileMap;

public class PlayerGround extends Player {

    // The integer refers to the row of the sprite asset
    private int IDLE_ROW = 0;
    private int MOVING_ROW = 1;
    private int SHOOTING_ROW = 2;

    // Each element of the array refers to the number of frames (or columns) in a row of the sprite asset
    // The sprite asset now have 3 rows (to animate 3 states) so I'm defining the number of frames of each row
    private final int[] numFrames = {6,12,4};

    // Firing
    private int remainingBullets = 10;
    private boolean firing;

    /**
     * Creates a player character to use on the first level.
     * @param tm is the map on which to create the character.
     */
    public PlayerGround(TileMap tm) {
        super(tm);
        setCurrentRow(IDLE_ROW);
        loadSpriteAsset(numFrames, "/Pirates/pirate_level_one.png");

        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(getSprites().get(IDLE_ROW));
        imageAnimator.setDelay(100);
    }

    /**
     * This method is used to acknowledge how many bullets the player has.
     * @return the remaining bullets.
     */
    public int getRemainingBullets() {
        return remainingBullets;
    }

    /**
     * This method sets the currently executed action to "firing" (our main and only attack).
     * @param firing is either true or false.
     */
    public void setFiring(boolean firing) {
        this.firing = firing;
    }

    /**
     * Sets the initial amount of bullets the player character starts with.
     * @param remainingBullets is set to 10 initially.
     */
    public void setRemainingBullets(int remainingBullets) {
        this.remainingBullets = remainingBullets;
        notifyObserver(PlayerEvent.BULLETS_MODIFIED);
    }

    /**
     * Every time the player character steps on a bunch of ammo, it gains 3 extra bullets.
     */
    public void gatherAmmo() {
        this.remainingBullets += 3;
        notifyObserver(PlayerEvent.BULLETS_MODIFIED);
    }

    /**
     * This hook method deals with the movement of the player character used in the first level.
     * It selects a certain group of frames depending on the action that is being executed (ex.: standing still or
     * moving).
     */
    @Override
    public void hookUpdate() {
        if(getDx() > 0)
            setFacingRight(true);
        else if(getDx() < 0)
            setFacingRight(false);

        if(getDx() != 0) {
            if(getCurrentRow() != MOVING_ROW) {
                setCurrentRow(MOVING_ROW);
                imageAnimator.setFrames(getSprites().get(MOVING_ROW));
                imageAnimator.setDelay(100);
            }
        }
        else if(firing) {
            if(getCurrentRow() != SHOOTING_ROW) {
                setCurrentRow(SHOOTING_ROW);
                imageAnimator.setFrames(getSprites().get(SHOOTING_ROW));
                imageAnimator.setDelay(20);
            }
        } else {
            if(getCurrentRow() != IDLE_ROW) {
                setCurrentRow(IDLE_ROW);
                imageAnimator.setFrames(getSprites().get(IDLE_ROW));
                imageAnimator.setDelay(100);
            }
        }

        if(imageAnimator.hasPlayedOnce()) { firing = false; }

        imageAnimator.update();
    }

}
