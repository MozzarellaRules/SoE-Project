package entity.dynamic;

import entity.dynamic.Player;
import tilemap.TileMap;

public class PlayerWater extends Player {

    // Each element of the array refers to the number of frames (or columns) in a row of the sprite asset
    // The sprite asset now have 3 rows (to animate 3 states) so I'm defining the number of frames of each row
    private final int[] numFrames = {6};

    // The integer refers to the row of the sprite asset
    private int IDLE_ROW = 0;

    public PlayerWater(TileMap tm) {
        super(tm);
        setCurrentRow(IDLE_ROW);
    }

    @Override
    public void hookAnimation() {
        return;
    }
}