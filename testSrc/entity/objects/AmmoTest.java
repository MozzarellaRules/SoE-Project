package entity.objects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tilemap.TileMap;

public class AmmoTest {

	private TileMap tm;
	private Ammo am;
@Before
public void setUp() {
	tm=new TileMap(32);
	am=new Ammo(tm);
}

@Test
public void test(){
	    assertNotNull(am);
}

}
