package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.category.Category;

public class CategoryTest {

	Category cat1;
	Category cat2;
	
	@Before
	public void setUp() {
		cat1 = new Category("School", Color.blue);
		cat2 = new Category("Work", Color.red);
	}

	@Test
	public void testUniqueID(){
		Category check1 = new Category("First", Color.BLACK);
		Category check2 = new Category("Second", Color.BLUE);
		assertFalse(check1.getName().equals(check2.getName()));
	}
	
	@Test
	public void sameNameTest(){
		Category check1 = new Category("First", Color.BLACK);
		Category check2 = new Category("First", Color.BLUE);
		//Unique ID Should Only Be Given Out On Server Side
		assertTrue(check1.getUniqueID() == check2.getUniqueID());
	}
	
	@Test
	public void testEventInstanceCloning(){
		Category check1 = new Category("First", Color.BLACK);
		assertNotNull(check1.cloneFake());
	}
	
	@Test
	public void testConstructorCloning(){
		Category check1 = new Category("First", Color.BLACK);
		assertEquals(check1.getName(), check1.cloneFake().getName());
		assertEquals(check1.getColor(), check1.cloneFake().getColor());
	}
	
	@Test
	public void testCategoryName() {
		assertEquals("School",cat1.getName());
		cat1.setName("class");
		assertEquals("class",cat1.getName());
	}
	
	@Test
	public void testCategoryColor() {
		assertEquals(Color.red,cat2.getColor());
		cat2.setColor(Color.green);
		assertEquals(Color.green,cat2.getColor());
	}
	
	@Test
	public void testCategoryIsReal(){
		assertTrue(cat1.getisReal());
		assertFalse(cat1.cloneFake().getisReal());
	}
	
	@Test
	public void testCategoriesAreEqual()
	{
		cat1.setUniqueID(1);
		assertTrue(cat1.equals(cat1));
		assertFalse(cat1.equals(cat2));
		assertFalse(cat2.equals(null));
		assertFalse(cat1.equals(new Object()));
		
	}

}
