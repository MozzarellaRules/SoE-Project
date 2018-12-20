package entity.dynamic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tilemap.TileMap;

public class PlayerWaterTest {
	private PlayerWater pw;
	private TileMap tm;
	
	@Before
	public void setUp() {
		tm=new TileMap(32);
		pw=new PlayerWater(tm);
	}

	@Test
	public void test() {
		assertNotNull(pw);
	}

}
