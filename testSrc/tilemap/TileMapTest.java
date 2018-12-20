package tilemap;

import gamestate.LevelOneState;
import gamestate.LevelTwoState;
import main.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class TileMapTest extends Game {
    private TileMap tm;
    @BeforeEach
    void setUp() {
       tm = new TileMap(32);
    }

    @Test
    void testMapLevel1() {
        tm.loadTiles(LevelOneState.TILESET_PATH);
        tm.loadMap(LevelOneState.MAP_PATH);
        assertEquals(54,tm.getNumRows());
        assertEquals(149,tm.getNumCols());
        
        assertNotEquals(0,tm.getNumRows());
        assertNotEquals(0,tm.getNumCols());

        
        /*assertThrows(NullPointerException.class, () -> {
        tm.loadTiles("path_not_existing");      
        }); */  
        
    }
    
    @Test
    void testMapLevel2() {
    	tm.loadTiles(LevelTwoState.TILESET_PATH);
    	tm.loadMap(LevelTwoState.MAP_PATH);
    	 assertEquals(20,tm.getNumRows());
         assertEquals(62,tm.getNumCols());
    	assertNotEquals(0,tm.getNumRows());
        assertNotEquals(0,tm.getNumCols());
    }
    
    @Test
    void testGetTypeLevel1() {
    	 tm.loadTiles(LevelOneState.TILESET_PATH);
         tm.loadMap(LevelOneState.MAP_PATH);
         assertThrows(Exception.class, () -> {
        	 tm.getType(-1, -1);     	 
             });
         assertThrows(Exception.class, () -> {
        	 tm.getType(tm.getNumRows(),tm.getNumCols());     	 
             });
         
    }
    
    @Test
    void testGetTypeLevel2() {
    	 tm.loadTiles(LevelTwoState.TILESET_PATH);
         tm.loadMap(LevelTwoState.MAP_PATH);
         assertThrows(Exception.class, () -> {
        	 tm.getType(-1, -1);     	 
             });
         assertThrows(Exception.class, () -> {
        	 tm.getType(tm.getNumRows(),tm.getNumCols());     	 
             });
         
    }
    
    
 
}