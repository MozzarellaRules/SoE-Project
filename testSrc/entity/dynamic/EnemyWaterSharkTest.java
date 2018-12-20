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

	

}
