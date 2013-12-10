package edu.wpi.cs.wpisuitetng.modules.calendar.view.weather;

public class WeatherException extends Exception{
	public String toString(){
		return "Could Not Retrieve Weather Information!";
	}
}
