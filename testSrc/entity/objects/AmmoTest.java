package entity.objects;

import static org.junit.Assert.*;


import entity.Item;
import org.junit.Before;
import org.junit.Test;

import tilemap.TileMap;

public class AmmoTest {


	private TileMap tm;
	private Item am;
@Before
public void setUp() {
	tm=new TileMap(32);
	am=new Item(tm,"/Objects/asset_ammo.png",6);
}

@Test
public void test(){
	    assertNotNull(am);
}

}
