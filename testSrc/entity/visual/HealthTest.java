package entity.visual;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tilemap.TileMap;

public class HealthTest {
	
	private TileMap tm;
	private Health h;
	
	@Before
	public void setUp() {
		tm= new TileMap(32);
		h=new Health(tm);
	}

	@Test
	public void test() {
		assertNotNull(h);
	}

}
