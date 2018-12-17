package entity.dynamic;

import static org.junit.Assert.*;
import tilemap.TileMap;
import org.junit.Before;
import org.junit.Test;

public class ProjectileTest {

	private Projectile p;
	private TileMap tm;
	private boolean facingRight;
	
	@Before
	public void setUp() {
		tm=new TileMap(32);
		p=new Projectile(tm,facingRight);
		facingRight=true;
	}
	
	@Test
	public void test() {
		assertNotNull(p);
		}
}
