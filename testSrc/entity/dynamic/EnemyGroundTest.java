package entity.dynamic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

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
	public void isDeadTest() {
		assertFalse(eg.isDead());
		eg.hit(2); //togliamo due punti vita 
		assertTrue(eg.isDead());
	}
	
	@Test
	public void isFacingRightTest() {
		assertTrue(eg.isFacingRight());
		eg.setFacingRight(false);
		assertFalse(eg.isFacingRight());
	}
}
