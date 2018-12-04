package TileMap;

import Main.Game;
import jdk.nashorn.internal.runtime.ECMAErrors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileMapTest extends Game {
    private TileMap tm;

    @BeforeEach
    void setUp() {
        tm = new TileMap(32);
    }

    @Test
    void testLoadTiles() {
        tm.loadTiles("path_not_existing");

        assertEquals(tm.getNumRows(), 0);
        assertEquals(tm.getNumCols(), 0);

        /*
        assertThrows(Exception.class, () -> {
            tm.loadTiles("path_not_existing");
        });
        */
    }

    @Test
    void testLoadMap() {

        /*
        assertThrows(Exception.class, () -> {
            tm.loadMap("path_not_existing");
        });
        */
    }
}