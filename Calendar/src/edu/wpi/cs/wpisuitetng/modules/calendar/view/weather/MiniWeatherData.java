package edu.wpi.cs.wpisuitetng.modules.calendar.view.weather;

import java.util.List;

public class MiniWeatherData {
	private String date;
	private String WeatherType;
	private String highTemp, lowTemp;
	
	public MiniWeatherData(List<String> data){
		date = data.get(0);
		WeatherType = data.get(8);
		highTemp = data.get(3);
		lowTemp = data.get(4);
	}
}
