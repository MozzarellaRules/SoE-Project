package entity.dynamic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


import tilemap.TileMap;

public class EnemyGroundTest {

	private EnemyGround eg;
	private TileMap tm;
	
	@Before
	public void setUp() {
		tm= new TileMap(32);
		eg=new EnemyGround(tm);
	}
	
	
	@Test
	public void test() {
		assertNotNull(eg);	
	}
	
	@Test
	public void isDeadTest() {
		assertFalse(eg.isDead());
		eg.hit(2); //togliamo due punti vita 
		assertTrue(eg.isDead());
	}
	
	@Test
	public void isFacingRightTest() {
		eg.setFacingRight(true);
		assertTrue(eg.isFacingRight());
		eg.setFacingRight(false);
		assertFalse(eg.isFacingRight());
	}
	
	@Test
	public void getFactorXTest() {
		eg.setFactorX(2.4);
		assertTrue(eg.getFactorX()==2.4);
	}
	
	@Test
	public void getFactorYTest() {
		eg.setFactorY(5.4);
		assertTrue(eg.getFactorY()==5.4);
	}
	
	@Test
	public void isMovingLeftTest() {
		eg.setMovingLeft(true);
		assertTrue(eg.isMovingLeft());
		eg.setMovingLeft(false);
		assertFalse(eg.isMovingLeft());
	}
	
	@Test
	public void isMovingRightTest() {
		eg.setMovingRight(true);
		assertTrue(eg.isMovingRight());
		eg.setMovingRight(false);
		assertFalse(eg.isMovingRight());
	}
	
	@Test
	public void isFallingTest() {
		eg.setFalling(true);
		assertTrue(eg.isFalling());
		eg.setFalling(false);
		assertFalse(eg.isFalling());
	}
	
	@Test
	public void intersectTest() {
		EnemyGround eg2=new EnemyGround(tm);
		eg2.setPosition(0, 29); //Collision on Y
		assertTrue(eg.intersects(eg2));
		eg2.setPosition(0, 30); //No Collision on Y
		assertFalse(eg.intersects(eg2));
		eg2.setPosition(19, 0); //Collision on X
		assertTrue(eg.intersects(eg2));
		eg2.setPosition(20, 0); //No Collision on X
		assertFalse(eg.intersects(eg2));
		
	}
	@Test
	public void getXTest() {
		eg.setX(5);
		assertEquals(5,eg.getX());
	}
	
	@Test
	public void getYTest() {
		eg.setY(10);
		assertEquals(10,eg.getY());
	}
	
	@Test
	public void getDxTest() {
		eg.setDx(2.3);
		assertTrue(eg.getDx()==2.3);
	}
	
	@Test
	public void getDyTest() {
		eg.setDy(4.3);
		assertTrue(eg.getDy()==4.3);
	}
	
	@Test
	public void getTileMapTest() {
		assertEquals(tm,eg.getTileMap());
	}
	
	@Test
	public void getTileSizeTest() {
		assertEquals(32,eg.getTileSize());
	}
	
	@Test
	public void getWidthTest() {
		assertEquals(32,eg.getWidth());
	}
	@Test
	public void getHeightTest() {
		assertEquals(32,eg.getHeight());
	}
	
	@Test
	public void getCollisionBoxHeightTest() {
		assertEquals(30,eg.getCollisionBoxHeight());
	}
	
	@Test
	public void getCollisionBoxWidthTest() {
		assertEquals(20,eg.getCollisionBoxWidth());
	}
	
	@Test
	public void notOnScreenTest() { //380*250
		eg.setPosition(412, 0);
		assertFalse(eg.notOnScreen());
		eg.setPosition(413, 0);
		assertTrue(eg.notOnScreen());
		eg.setPosition(0,282);
		assertFalse(eg.notOnScreen());
		eg.setPosition(0, 283);
		assertTrue(eg.notOnScreen());
		
	}
}
