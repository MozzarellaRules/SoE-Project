package entity;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.awt.image.BufferedImage;
import entity.Sprite.*;
import entity.objects.Ammo;

import org.junit.*;
import org.junit.jupiter.api.BeforeEach;

import tilemap.TileMap;

public class ImageAnimatorTest {
	private ImageAnimator im;
	private TileMap tm;
	private Ammo ammo;
	@Before
	public void setUp() {
		im=new ImageAnimator();
	}
	@Test
	public void testhasplayedOnce() {
		assertFalse(im.hasPlayedOnce());
	}		
}
	

