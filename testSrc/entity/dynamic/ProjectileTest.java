package entity.dynamic;

import static org.junit.Assert.*;
import tilemap.TileMap;
import org.junit.Before;
import org.junit.Test;

public class ProjectileTest {

	private Projectile p;
	private TileMap tm;
	private boolean facingRight,facingRight2;
	
	@Before
	public void setUp() {
		facingRight=true;
		tm=new TileMap(32);
		p=new Projectile(tm,facingRight);
		
	}
	
	@Test
	public void test() {
		assertNotNull(p);
		}
	
	@Test
	public void isFacingRightTest() {
		p.setFacingRight(true);
		assertTrue(p.isFacingRight());
		p.setFacingRight(false);
		assertFalse(p.isFacingRight());
	}
	
	@Test
	public void getFactorXTest() {
		p.setFactorX(2.2);
		assertTrue(p.getFactorX()==2.2);
	}
	
	@Test
	public void getFactorYTest() {
		p.setFactorY(1.5);
		assertTrue(p.getFactorY()==1.5);
	}
	
	@Test
	public void isMovingLeftTest() {
		p.setMovingLeft(true);
		assertTrue(p.isMovingLeft());
		p.setMovingLeft(false);
		assertFalse(p.isMovingLeft());
	}
	
	@Test
	public void isMovingRightTest() {
		p.setMovingRight(true);
		assertTrue(p.isMovingRight());
		p.setMovingRight(false);
		assertFalse(p.isMovingRight());
	}
	
	
	
	@Test
	public void intersectTest() {
		facingRight2=true;
		Projectile p2=new Projectile(tm,facingRight2);
		p2.setPosition(0, 19); //Collision on Y
		assertTrue(p.intersects(p2));
		p2.setPosition(0, 20); //No Collision on Y
		assertFalse(p.intersects(p2));
		p2.setPosition(19, 0); //Collision on X
		assertTrue(p.intersects(p2));
		p2.setPosition(20, 0); //No Collision on X
		assertFalse(p.intersects(p2));
		
	}
	
	@Test
	public void getXTest() {
		p.setX(5);
		assertEquals(5,p.getX());
	}
	
	@Test
	public void getYTest() {
		p.setY(10);
		assertEquals(10,p.getY());
	}
	
	@Test
	public void getDxTest() {
		p.setDx(2.3);
		assertTrue(p.getDx()==2.3);
	}
	
	@Test
	public void getDyTest() {
		p.setDy(4.3);
		assertTrue(p.getDy()==4.3);
	}
	
	@Test
	public void getTileMapTest() {
		assertEquals(tm,p.getTileMap());
	}
	
	@Test
	public void getTileSizeTest() {
		assertEquals(32,p.getTileSize());
	}
	
	@Test
	public void getWidthTest() {
		assertEquals(32,p.getWidth());
	}
	@Test
	public void getHeightTest() {
		assertEquals(32,p.getHeight());
	}
	
	@Test
	public void getCollisionBoxHeightTest() {
		assertEquals(20,p.getCollisionBoxHeight());
	}
	
	@Test
	public void getCollisionBoxWidthTest() {
		assertEquals(20,p.getCollisionBoxWidth());
	}
	
	@Test
	public void notOnScreenTest() { //380*250
		p.setPosition(412, 0);
		assertFalse(p.notOnScreen());
		p.setPosition(413, 0);
		assertTrue(p.notOnScreen());
		p.setPosition(0,282);
		assertFalse(p.notOnScreen());
		p.setPosition(0, 283);
		assertTrue(p.notOnScreen());
		
	}
}
