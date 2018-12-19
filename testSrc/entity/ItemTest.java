package entity;

import static org.junit.Assert.*;

import java.awt.Graphics2D;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import tilemap.TileMap;


public class ItemTest {
	private Item it;
	private TileMap tm;
	
	@Before
	public void setUp() {
		tm= new TileMap(32);
		it= new Item(tm,"/Pirates/pirate_level_one.png",6);
	}
	
	@Test
	public void TestGetX(){
		it.setX(5);
		assertEquals(it.getX(),5);
	}
	
	@Test
	public void TestGetY(){
		it.setY(2);
		assertEquals(it.getY(),2);
	}
	
	@Test
	public void TestGetDx(){
		it.setDx(2.5);
		assertTrue(it.getDx()==2.5);
	}
	
	@Test
	public void TestGetDy(){
		it.setDy(5.6);
		assertTrue(it.getDy()==5.6);
	}
	
	@Test
	public void TestGetTileMap(){
		assertTrue(it.getTileMap()==tm);
	}
	
	@Test
	public void TestTileSize(){
		assertTrue(it.getTileSize()==32);
	}
	
	@Test
	public void TestGetgetWidth(){
		assertTrue(it.getWidth()==32);
	}
	
	@Test
	public void TestGetgetHeight(){
		assertTrue(it.getHeight()==32);
	}
	@Test
	public void	TestgetCollisionBoxHeight() {
		it.setCollisionBoxHeight(20);
		assertEquals(it.getCollisionBoxHeight(),20);
	}
	@Test
	public void TestgetCollisionBoxWidth() {
		it.setCollisionBoxWidth(20);
		assertEquals(it.getCollisionBoxWidth(),20);
	}
	
	@Test
	//lo schermo è 380*250 
	public void TestNotOnScreen() {
		it.getTileMap().setPosition(0, 0);
		it.setPosition(413,100);  //l'item è fuori dallo schermo 
		assertTrue(it.notOnScreen());
		it.setPosition(412,100);
		assertFalse(it.notOnScreen());
		it.setPosition(100,283);  //l'item è fuori dallo schermo 
		assertTrue(it.notOnScreen());
		it.setPosition(100,282);
		assertFalse(it.notOnScreen());
	}
	
	
	
	
	
	
	
	
	
	

}
