package entity.dynamic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import tilemap.TileMap;
public class PlayerGroundTest {
	
	private PlayerGround pg;
	private TileMap tm;
	
	@Before
	public void setUp() {
		tm=new TileMap(32);
		pg=new PlayerGround(tm);
	}

	@Test
	public void test() {
		assertNotNull(pg);
	}
	
	@Test
	public void getRemainingBulletsTest() {
		pg.setRemainingBullets(10);
		assertEquals(10,pg.getRemainingBullets());
		pg.setRemainingBullets(0);
		assertEquals(0,pg.getRemainingBullets());	
		
	}

}
