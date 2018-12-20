package entity.objects;

import static org.junit.Assert.*;
import entity.Item;

import org.junit.Before;
import org.junit.Test;

import tilemap.TileMap;

public class TreasureMapTest {
	private TileMap tm;
	private Item treasure;
	
	@Before
	 public void setUp() {
		tm=new TileMap(32);
		treasure= new Item(tm,"/Objects/asset_map.png",12);
	}

	@Test
	public void test() {
		assertNotNull(treasure);
	}

}
