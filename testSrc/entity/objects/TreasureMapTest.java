package entity.objects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tilemap.TileMap;

public class TreasureMapTest {
	private TileMap tm;
	private TreasureMap treasure;
	
	@Before
	 public void setUp() {
		tm=new TileMap(32);
		treasure= new TreasureMap(tm);
	}

	@Test
	public void test() {
		assertNotNull(treasure);
	}

}
