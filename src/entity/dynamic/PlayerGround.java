package entity.dynamic;

import entity.ImageAnimator;
import entity.dynamic.Player;
import tilemap.TileMap;

public class PlayerGround extends Player {

    // The integer refers to the row of the sprite asset
    private int IDLE_ROW = 0;
    private int WALKING_ROW = 1;
    private int SHOOTING_ROW = 2;

    // Each element of the array refers to the number of frames (or columns) in a row of the sprite asset
    // The sprite asset now have 3 rows (to animate 3 states) so I'm defining the number of frames of each row
    private final int[] numFrames = {6,12,4};

    // Firing
    private int remainingBullets = 10;
    private boolean firing;

    public PlayerGround(TileMap tm) {
        super(tm);
        setCurrentRow(IDLE_ROW);
        loadSpriteAsset(numFrames, "/Pirates/pirate_1.png");

        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(getSprites().get(IDLE_ROW));
        imageAnimator.setDelay(100);
    }

    public int getRemainingBullets() {
        return remainingBullets;
    }

    public void setFiring(boolean firing) {
        this.firing = firing;
    }
    public void setRemainingBullets(int remainingBullets) {
        this.remainingBullets = remainingBullets;
        notifyObserver(PlayerEvent.BULLETS_MODIFIED);
    }

    public void gatherAmmo() {
        this.remainingBullets += 3;
        notifyObserver(PlayerEvent.BULLETS_MODIFIED);
    }

    @Override
    public void hookAnimation() {
        if(getDx() > 0)
            setFacingRight(true);
        else if(getDx() < 0)
            setFacingRight(false);

        if(getDx() != 0) {
            if(getCurrentRow() != WALKING_ROW) {
                setCurrentRow(WALKING_ROW);
                imageAnimator.setFrames(getSprites().get(WALKING_ROW));
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
