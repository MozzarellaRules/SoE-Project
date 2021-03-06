package entity.dynamic;

import entity.visual.PlayerObservable;
import entity.ImageAnimator;
import tilemap.TileMap;

public class PlayerWater extends Player {
    // Each element of the array refers to the number of frames (or columns) in a row of the sprite asset
    // The sprite asset now have 3 rows (to animate 3 states) so I'm defining the number of frames of each row
    private final int[] numFrames = {8, 8};

    // The integer refers to the row of the sprite asset
    private int DEFAULT_ROW = 0;
    private int MOVING_ROW = 1;

    private int oxygen;
    private int maxOxygen;
    private long oxygenTimer;

    /**
     * This method creates a player character to be used in the second level. It lacks the offensive capabilities of
     * it's 1st level counterpart, but it can swim.
     * @param tm is the map on which to generate it.
     */
    public PlayerWater(TileMap tm) {
        super(tm);
        setCurrentRow(DEFAULT_ROW);
        loadSpriteAsset(numFrames, "/Pirates/pirate_level_two.png");

        setFactorY(0.6);
        setHealth(4);

        oxygen = 50;
        maxOxygen = 50;
        oxygenTimer = System.nanoTime();

        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(getSprites().get(MOVING_ROW));
        imageAnimator.setDelay(100);
    }

    /**
     * Returns the remaining oxygen.
     * @return how much oxygen the player has left before dying.
     */
    public int getOxygen() { return oxygen; }

    /**
     * This method is used to set the amount of oxygen the player currently has.
     * @param oxygen is said amount of oxygen.
     */
    public void setOxygen(int oxygen) { this.oxygen = oxygen; }

    /**
     * This method updates both the oxygen meter and the player character's animations.
     */
    @Override
    public void hookUpdate() {
        long elapsed = (System.nanoTime()-oxygenTimer)/1000000;
        if(elapsed > 1000) {
            oxygen -=1;
            oxygenTimer = System.nanoTime();
            notifyObserver(PlayerEvent.OXYGEN_MODIFIED);
        }

        if(oxygen == 0)
            setDead(true);

        if(getDx() != 0) {
            if(getCurrentRow() != MOVING_ROW) {
                setCurrentRow(MOVING_ROW);
                imageAnimator.setFrames(getSprites().get(MOVING_ROW));
                imageAnimator.setDelay(100);
            }
        } else {
            if(getCurrentRow() != DEFAULT_ROW) {
                setCurrentRow(DEFAULT_ROW);
                imageAnimator.setFrames(getSprites().get(DEFAULT_ROW));
                imageAnimator.setDelay(100);
            }
        }
    }

    /**
     * Increments the oxygen level by ten whenever a bubble is touched.
     */
    public void incrementOxygenLevel(){
        if(oxygen+10 > maxOxygen) {
            oxygen = maxOxygen;
        } else
            oxygen += 10;
        notifyObserver(PlayerObservable.PlayerEvent.OXYGEN_MODIFIED);
    }
}
