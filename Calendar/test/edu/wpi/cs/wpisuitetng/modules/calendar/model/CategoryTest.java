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
	public void test() {
		fail("Not yet implemented");
	}

}
