package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 * This is a model for the filter list. It contains all of the filters the user has created.
 *  It extends AbstractListModel so that it can provide
 * the model data to the JList component in the CalendarPanel.
 * 
 * @author Nicholas Otero
 * 
 */

public class FilterModel extends AbstractListModel<Object>
{
	private static FilterModel filterModel;
	
	/** The list of filter */
	private List<Filter> filters;
	
	/** The active Event filter */
	private Filter activeEventFilter;
	
	/** The active Category filter */
	private Filter activeCommitmentFilter;
	
	
	private FilterModel() 
	{
		this.filters = new ArrayList<Filter>();
	}
	
	static public FilterModel getFilterModel() 
	{
		if (filterModel == null)
			filterModel = new FilterModel();
		return filterModel;
	}
	
}//end class
	

	/**
	 * Constructs a new calendar with no commitments.
	 */
	