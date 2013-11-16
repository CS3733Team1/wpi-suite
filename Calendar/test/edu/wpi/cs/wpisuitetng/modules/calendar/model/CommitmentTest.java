package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import static org.junit.Assert.*;

import java.util.Date;
import java.awt.Color;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;



import org.junit.Before;
import org.junit.Test;


public class CommitmentTest {

	Date Date1;
	Date Date2;
	Date Date3;
	Commitment c1;
	Commitment c2;
	Commitment c3;
	Commitment c4;
	Commitment c5;
	Category cat1;
	Category cat2;
	
	@Before
	public void setUp() {
		Date1 = new Date(113, 11, 11);
		Date2 = new Date(93, 6, 6);
		Date3 = new Date(100, 1, 1);
		cat1 = new Category("school", Color.blue);
		cat2 = new Category("Work", Color.red);
		c1 = new Commitment("CS 3733", Date1);
		c2 = new Commitment("meeting1", Date2);
		c3 = new Commitment("class", Date1, "CS 3733 class");
		c4 = new Commitment("class", Date1, cat1);
		c5 = new Commitment("class", Date1, "other class", cat2);
	}
	
	@Test
	public void testCommitmentname() {
		assertEquals("CS 3733",c1.getName());
		c1.setName("Soft Eng");
		assertEquals("Soft Eng",c1.getName());
	}
	
	@Test
	public void testCommitmentDate() {
		assertEquals(Date1,c1.getDueDate());
		c1.setDueDate(Date2);
		assertEquals(Date2,c1.getDueDate());
	}
	
	@Test
	public void testCommitmentDescription() {
		assertEquals("CS 3733 class",c3.getDescription());
		c3.setDescription("Soft Eng");
		assertEquals("Soft Eng",c3.getDescription());
	}
	
	@Test
	public void testCommitmentCategory() {
		assertEquals(cat1,c4.getCategory());
		c4.setCategory(cat2);
		assertEquals(cat2,c4.getCategory());
	}
	
	@Test
	public void testCategoryName() {
		assertEquals("school",cat1.getName());
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
