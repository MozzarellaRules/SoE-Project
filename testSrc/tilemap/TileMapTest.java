package tilemap;

import gamestate.Level1State;
import main.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotEquals;


class TileMapTest extends Game {
    private TileMap tm;

    @BeforeEach
    void setUp() {
        tm = new TileMap(32);
    }

    @Test
    void testLoadMap() {
        tm.loadTiles(Level1State.TILESET_PATH);
        tm.loadMap(Level1State.MAP_PATH);

        assertNotEquals(tm.getNumRows(), 0);
        assertNotEquals(tm.getNumCols(), 0);

        /*
        assertThrows(Exception.class, () -> {
            tm.loadTiles("path_not_existing");
        });
        */
    }
}