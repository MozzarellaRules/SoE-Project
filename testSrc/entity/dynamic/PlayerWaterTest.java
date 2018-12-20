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
	
	@Test
	public void getOxygenTest() {
		assertEquals(50,pw.getOxygen());
		pw.setOxygen(30);
		assertEquals(30,pw.getOxygen());
		
	}
	
	@Test
	public void incrementOxygenLevel() {
		pw.setOxygen(30);
		pw.incrementOxygenLevel();
		assertEquals(40,pw.getOxygen());
		pw.incrementOxygenLevel();
		assertEquals(50,pw.getOxygen()); //Max Oxygen reached
		pw.incrementOxygenLevel();
		assertEquals(50,pw.getOxygen());
	}
	
	
	@Test
	public void getHealthTest() {
		assertEquals(4,pw.getHealth());
		pw.setHealth(6);
		assertEquals(6,pw.getHealth());
	}
	
	@Test
	public void deadTest() {
		pw.setHealth(3);
		pw.hit(3);
		assertTrue(pw.isDead());
		
	}
	@Test
	public void getXTest() {
		pw.setX(5);
		assertEquals(5,pw.getX());
	}
	
	@Test
	public void getYTest() {
		pw.setY(10);
		assertEquals(10,pw.getY());
	}
	
	@Test
	public void getDxTest() {
		pw.setDx(2.3);
		assertTrue(pw.getDx()==2.3);
	}
	
	@Test
	public void getDyTest() {
		pw.setDy(4.3);
		assertTrue(pw.getDy()==4.3);
	}
	
	@Test
	public void getTileMapTest() {
		assertEquals(tm,pw.getTileMap());
	}
	
	@Test
	public void getTileSizeTest() {
		assertEquals(32,pw.getTileSize());
	}
	
	@Test
	public void getWidthTest() {
		assertEquals(32,pw.getWidth());
	}
	@Test
	public void getHeightTest() {
		assertEquals(32,pw.getHeight());
	}
	
	@Test
	public void getCollisionBoxHeightTest() {
		assertEquals(20,pw.getCollisionBoxHeight());
	}
	
	@Test
	public void getCollisionBoxWidthTest() {
		assertEquals(20,pw.getCollisionBoxWidth());
	}
	
	@Test
	public void notOnScreenTest() { //380*250
		pw.setPosition(412, 0);
		assertFalse(pw.notOnScreen());
		pw.setPosition(413, 0);
		assertTrue(pw.notOnScreen());
		pw.setPosition(0,282);
		assertFalse(pw.notOnScreen());
		pw.setPosition(0, 283);
		assertTrue(pw.notOnScreen());
		
	}

}
