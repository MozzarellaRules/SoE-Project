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
	
	@Test
	public void gatherAmmoTest() {
		pg.setRemainingBullets(4);
		pg.gatherAmmo();
		assertEquals(7,pg.getRemainingBullets());
	}

	@Test
	public void getHealthTest() {
		assertEquals(3,pg.getHealth());
		pg.setHealth(6);
		assertEquals(6,pg.getHealth());
	}
	
	@Test
	public void deadTest() {
		pg.setHealth(4);
		pg.hit(4);
		assertTrue(pg.isDead());
		
	}
	
	@Test
	public void getXTest() {
		pg.setX(5);
		assertEquals(5,pg.getX());
	}
	
	@Test
	public void getYTest() {
		pg.setY(10);
		assertEquals(10,pg.getY());
	}
	
	@Test
	public void getDxTest() {
		pg.setDx(2.3);
		assertTrue(pg.getDx()==2.3);
	}
	
	@Test
	public void getDyTest() {
		pg.setDy(4.3);
		assertTrue(pg.getDy()==4.3);
	}
	
	@Test
	public void getTileMapTest() {
		assertEquals(tm,pg.getTileMap());
	}
	
	@Test
	public void getTileSizeTest() {
		assertEquals(32,pg.getTileSize());
	}
	
	@Test
	public void getWidthTest() {
		assertEquals(32,pg.getWidth());
	}
	@Test
	public void getHeightTest() {
		assertEquals(32,pg.getHeight());
	}
	
	@Test
	public void getCollisionBoxHeightTest() {
		assertEquals(20,pg.getCollisionBoxHeight());
	}
	
	@Test
	public void getCollisionBoxWidthTest() {
		assertEquals(20,pg.getCollisionBoxWidth());
	}
	
	@Test
	public void notOnScreenTest() { //380*250
		pg.setPosition(412, 0);
		assertFalse(pg.notOnScreen());
		pg.setPosition(413, 0);
		assertTrue(pg.notOnScreen());
		pg.setPosition(0,282);
		assertFalse(pg.notOnScreen());
		pg.setPosition(0, 283);
		assertTrue(pg.notOnScreen());
		
	}
}
