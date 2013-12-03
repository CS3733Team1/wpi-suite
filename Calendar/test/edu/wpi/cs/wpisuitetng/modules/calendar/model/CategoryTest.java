package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class CategoryTest {

	Category cat1;
	Category cat2;
	
	@Before
	public void setUp() {
		cat1 = new Category("School", Color.blue);
		cat2 = new Category("Work", Color.red);
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

}
