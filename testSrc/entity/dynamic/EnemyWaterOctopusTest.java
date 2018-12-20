package entity.dynamic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tilemap.TileMap;

public class EnemyWaterOctopusTest {
	private EnemyWaterOctopus ewo;
	private TileMap tm;
	
	@Before
	public void setUp() {
		tm=new TileMap(32);
		ewo=new EnemyWaterOctopus(tm);	
	}
	
	
	
	@Test
	public void isFacingRightTest() {
		ewo.setFacingRight(true);
		assertTrue(ewo.isFacingRight());
		ewo.setFacingRight(false);
		assertFalse(ewo.isFacingRight());
	}
	
	@Test
	public void getFactorXTest() {
		ewo.setFactorX(2.4);
		assertTrue(ewo.getFactorX()==2.4);
	}
	
	@Test
	public void getFactorYTest() {
		ewo.setFactorY(5.4);
		assertTrue(ewo.getFactorY()==5.4);
	}
	
	@Test
	public void isMovingLeftTest() {
		ewo.setMovingLeft(true);
		assertTrue(ewo.isMovingLeft());
		ewo.setMovingLeft(false);
		assertFalse(ewo.isMovingLeft());
	}
	
	@Test
	public void isMovingRightTest() {
		ewo.setMovingRight(true);
		assertTrue(ewo.isMovingRight());
		ewo.setMovingRight(false);
		assertFalse(ewo.isMovingRight());
	}
	
	@Test
	public void isFallingTest() {
		ewo.setFalling(true);
		assertTrue(ewo.isFalling());
		ewo.setFalling(false);
		assertFalse(ewo.isFalling());
	}
	
	@Test
	public void intersectTest() {
		EnemyWaterOctopus ewo2=new EnemyWaterOctopus(tm);
		ewo2.setPosition(0, 32); //Collision on Y 
		assertTrue(ewo.intersects(ewo2));
		ewo2.setPosition(0, 32*1.3); //No Collision on Y
		assertFalse(ewo.intersects(ewo2));
		ewo2.setPosition(19, 0); //Collision on X
		assertTrue(ewo.intersects(ewo2));
		ewo2.setPosition(20, 0); //No Collision on X
		assertFalse(ewo.intersects(ewo2));
		
	}
	@Test
	public void test() {
		assertNotNull(ewo);	
	}
	
	@Test
	public void getXTest() {
		ewo.setX(5);
		assertEquals(5,ewo.getX());
	}
	
	@Test
	public void getYTest() {
		ewo.setY(10);
		assertEquals(10,ewo.getY());
	}
	
	@Test
	public void getDxTest() {
		ewo.setDx(2.3);
		assertTrue(ewo.getDx()==2.3);
	}
	
	@Test
	public void getDyTest() {
		ewo.setDy(4.3);
		assertTrue(ewo.getDy()==4.3);
	}
	
	@Test
	public void getTileMapTest() {
		assertEquals(tm,ewo.getTileMap());
	}
	
	@Test
	public void getTileSizeTest() {
		assertEquals(32,ewo.getTileSize());
	}
	
	@Test
	public void getWidthTest() {
		assertEquals(32,ewo.getWidth());
	}
	@Test
	public void getHeightTest() {
		assertEquals(32,ewo.getHeight());
	}
	
	@Test
	public void getCollisionBoxHeightTest() {
		assertTrue(ewo.getCollisionBoxHeight()==(int)(32*1.3));
	}
	
	@Test
	public void getCollisionBoxWidthTest() {
		assertEquals(20,ewo.getCollisionBoxWidth());
	}
	
	@Test
	public void notOnScreenTest() { //380*250
		ewo.setPosition(412, 0);
		assertFalse(ewo.notOnScreen());
		ewo.setPosition(413, 0);
		assertTrue(ewo.notOnScreen());
		ewo.setPosition(0,282);
		assertFalse(ewo.notOnScreen());
		ewo.setPosition(0, 283);
		assertTrue(ewo.notOnScreen());
		
	}

}
