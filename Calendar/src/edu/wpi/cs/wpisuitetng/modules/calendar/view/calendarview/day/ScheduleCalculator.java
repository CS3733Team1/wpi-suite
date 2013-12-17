package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;

public class ScheduleCalculator {
	private static final int pixPerMin = 1;
    private static final int timePix = 0; //width reserved for times on lefthand side
	
    public static int getLengthMinutes(ISchedulable sched){
        int length;
        length = (sched.getEndDate().getHours()-sched.getStartDate().getHours())*60;
        length += sched.getEndDate().getMinutes()-sched.getStartDate().getMinutes();
        return length;
    }
}
