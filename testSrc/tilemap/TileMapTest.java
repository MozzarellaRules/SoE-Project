package tilemap;

import gamestate.LevelOneState;
import main.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TileMapTest extends Game {
    private TileMap tm;

    @BeforeEach
    void setUp() {
        tm = new TileMap(32);
    }

    @Test
    void testLoadMap() {
        tm.loadTiles(LevelOneState.TILESET_PATH);
        tm.loadMap(LevelOneState.MAP_PATH);

        assertNotEquals(tm.getNumRows(), 0);
        assertNotEquals(tm.getNumCols(), 0);

        /*
        assertThrows(Exception.class, () -> {
            tm.loadTiles("path_not_existing");
        });
        */
    }
}