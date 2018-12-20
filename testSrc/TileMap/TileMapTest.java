package TileMap;

import main.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tilemap.*;

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

    }

}