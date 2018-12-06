package entity;

import gamestate.Level1State;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import tilemap.TileMap;

import static org.junit.jupiter.api.Assertions.*;

class FirstLevelPlayerTest {


    private FirstLevelPlayer player;
    private TileMap tm;

    @BeforeClass
    public void createTileMap(){
        tm = new TileMap(32);
        tm.loadTiles(Level1State.TILESET_PATH);
        tm.loadMap(Level1State.MAP_PATH);
    }

    @Test
    public void playerCreation(){

    }

    @BeforeEach
    public void createPlayer(){
        player = new FirstLevelPlayer(tm);
    }

    @Test
    public void testMovement(){
        int x;

    }

}