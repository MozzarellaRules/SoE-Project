package entity.visual;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tilemap.TileMap;

public class RemainingBulletsTest {
	
	private TileMap tm;
	private RemainingBullets rb;
	
	@Before
	public void setUp() {
		tm= new TileMap(32);
		rb= new RemainingBullets(tm); 
	}

	@Test
	public void test() {
		assertNotNull(rb);
	}

}
