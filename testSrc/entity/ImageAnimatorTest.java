package entity;
import static org.junit.Assert.*;
import java.awt.image.BufferedImage;
import org.junit.*;




import tilemap.TileMap;

public class ImageAnimatorTest {
	private ImageAnimator im;

	@Before
	public void setUp() {
		im=new ImageAnimator();
	}
	

	@Test
	public void testHasPlayedOnce() {
		assertFalse(im.hasPlayedOnce());
	}		
	
	@Test
	public void testGetFrame() {
		im.setFrame(5);
		assertEquals(im.getFrame(),5);
	}		
}
	

