package entity.dynamic;

import entity.visual.IObservable;
import entity.ImageAnimator;
import tilemap.TileMap;

public class PlayerWater extends Player {
    // Each element of the array refers to the number of frames (or columns) in a row of the sprite asset
    // The sprite asset now have 3 rows (to animate 3 states) so I'm defining the number of frames of each row
    private final int[] numFrames = {8, 8};

    // The integer refers to the row of the sprite asset
    private int DEFAULT_ROW = 0;
    private int SWIMMING_ROW = 1;

    private int oxygen;
    private int maxOxygen;
    private long oxygenTimer;

    public PlayerWater(TileMap tm) {
        super(tm);
        setCurrentRow(DEFAULT_ROW);
        loadSpriteAsset(numFrames, "/Pirates/pirate_level_two.png");

        setFactorY(0.6);

        oxygen = 50;
        maxOxygen = 50;
        oxygenTimer = System.nanoTime();




        imageAnimator = new ImageAnimator();
        imageAnimator.setFrames(getSprites().get(SWIMMING_ROW));
        imageAnimator.setDelay(100);
    }

    @Override
    public void hookUpdate() {

            long elapsed = (System.nanoTime()-oxygenTimer)/1000000;
            if(elapsed > 2000) {
                oxygen -=1 ;
                oxygenTimer = System.nanoTime();
                notifyObserver(PlayerEvent.OXYGEN_MODIFIED);

        }
            if (oxygen == 0)
                setDead(true);

            //System.out.println(oxygen);
    }


    //Increment the oxygen level by ten if a bubble is catched
    public void incrementOxygenLevel(){
        if(oxygen+10 > maxOxygen){
            oxygen = maxOxygen;
        }else
            oxygen+=10;
        notifyObserver(IObservable.PlayerEvent.OXYGEN_MODIFIED);
    }





}
