package entity;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import entity.Sprite.*;

import org.junit.*;
import org.junit.jupiter.api.BeforeEach;

import com.sun.prism.Image;

import tilemap.TileMap;

public class ImageAnimatorTest {
	private ImageAnimator im;
	private TileMap tm;
	private Item it;
	private BufferedImage i;
	
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
	

