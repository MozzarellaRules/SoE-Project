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
	public void test() {
		assertNotNull(ewo);	
	}

}
