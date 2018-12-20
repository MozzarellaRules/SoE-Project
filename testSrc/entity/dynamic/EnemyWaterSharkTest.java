package entity.dynamic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tilemap.TileMap;

public class EnemyWaterSharkTest {
	
	private EnemyWaterShark ews;
	private TileMap tm;
	
	@Before
	public void setUp() {
		tm=new TileMap(32);
		ews=new EnemyWaterShark(tm);	
	}

	@Test
	public void test() {
		assertNotNull(ews);	
	}

	
	
	@Test
	public void isFacingRightTest() {
		ews.setFacingRight(true);
		assertTrue(ews.isFacingRight());
		ews.setFacingRight(false);
		assertFalse(ews.isFacingRight());
	}
	
	@Test
	public void getFactorXTest() {
		ews.setFactorX(2.4);
		assertTrue(ews.getFactorX()==2.4);
	}
	
	@Test
	public void getFactorYTest() {
		ews.setFactorY(5.4);
		assertTrue(ews.getFactorY()==5.4);
	}
	
	@Test
	public void isMovingLeftTest() {
		ews.setMovingLeft(true);
		assertTrue(ews.isMovingLeft());
		ews.setMovingLeft(false);
		assertFalse(ews.isMovingLeft());
	}
	
	@Test
	public void isMovingRightTest() {
		ews.setMovingRight(true);
		assertTrue(ews.isMovingRight());
		ews.setMovingRight(false);
		assertFalse(ews.isMovingRight());
	}
	
	@Test
	public void isFallingTest() {
		ews.setFalling(true);
		assertTrue(ews.isFalling());
		ews.setFalling(false);
		assertFalse(ews.isFalling());
	}
	
	@Test
	public void intersectTest() {
		EnemyWaterShark ews2=new EnemyWaterShark(tm);
		ews2.setPosition(0, 19); //Collision on Y
		assertTrue(ews.intersects(ews2));
		ews2.setPosition(0, 20); //No Collision on Y
		assertFalse(ews.intersects(ews2));
		ews2.setPosition(20, 0); //Collision on X
		assertTrue(ews.intersects(ews2));
		ews2.setPosition(20*1.5, 0); //No Collision on X
		assertFalse(ews.intersects(ews2));
		
	}
	
	@Test
	public void getXTest() {
		ews.setX(5);
		assertEquals(5,ews.getX());
	}
	
	@Test
	public void getYTest() {
		ews.setY(10);
		assertEquals(10,ews.getY());
	}
	
	@Test
	public void getDxTest() {
		ews.setDx(2.3);
		assertTrue(ews.getDx()==2.3);
	}
	
	@Test
	public void getDyTest() {
		ews.setDy(4.3);
		assertTrue(ews.getDy()==4.3);
	}
	
	@Test
	public void getTileMapTest() {
		assertEquals(tm,ews.getTileMap());
	}
	
	@Test
	public void getTileSizeTest() {
		assertEquals(32,ews.getTileSize());
	}
	
	@Test
	public void getWidthTest() {
		assertEquals(32,ews.getWidth());
	}
	@Test
	public void getHeightTest() {
		assertEquals(32,ews.getHeight());
	}
	
	@Test
	public void getCollisionBoxHeightTest() {
		assertEquals(20,ews.getCollisionBoxHeight());
	}
	
	@Test
	public void getCollisionBoxWidthTest() {
		assertTrue(ews.getCollisionBoxWidth()==20*1.5);
	}
	
	@Test
	public void notOnScreenTest() { //380*250
		ews.setPosition(412, 0);
		assertFalse(ews.notOnScreen());
		ews.setPosition(413, 0);
		assertTrue(ews.notOnScreen());
		ews.setPosition(0,282);
		assertFalse(ews.notOnScreen());
		ews.setPosition(0, 283);
		assertTrue(ews.notOnScreen());
		
	}

}
