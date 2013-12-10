package edu.wpi.cs.wpisuitetng.modules.calendar.view.weather;

import java.util.List;

public class WeatherData {
	private String location;
	private String temperature;
	private String sunrise;
	private String sunset;
	private String currentTime;
	private String weatherType;

	private MiniWeatherData tomorrow, dayaftertom, today;

	public WeatherData() throws WeatherException {
		WeatherNetwork datamine = new WeatherNetwork();

		List<String> data = datamine.grabCurrentInformation();
		if (data.size() == 0) throw new WeatherException();
		else parseData(data);

		//DONT USE THIS
		List<String> ty = datamine.grabTodayInformation();
		if (ty.size() == 0) today = null;
		else today = new MiniWeatherData(ty);

		List<String> tom = datamine.grabTodayPOneInformation();
		if (ty.size() == 0) tomorrow = null;
		else tomorrow = new MiniWeatherData(tom);

		List<String> tytom = datamine.grabTodayPTwoInformation();
		if (ty.size() == 0) dayaftertom = null;
		else dayaftertom = new MiniWeatherData(tytom);
	}

	public WeatherData(String loc) throws WeatherException {
		WeatherNetwork datamine = new WeatherNetwork(loc);

		List<String> data = datamine.grabCurrentInformation();
		if (data.size() == 0) throw new WeatherException();
		else parseData(data);

		//DONT USE THIS
		List<String> ty = datamine.grabTodayInformation();
		if (ty.size() == 0) today = null;
		else today = new MiniWeatherData(ty);

		List<String> tom = datamine.grabTodayPOneInformation();
		if (ty.size() == 0)tomorrow = null;
		else tomorrow = new MiniWeatherData(tom);

		List<String> tytom = datamine.grabTodayPTwoInformation();
		if (ty.size() == 0) dayaftertom = null;
		else dayaftertom = new MiniWeatherData(tytom);		
	}

	private void parseData(List<String> d) {
		StringBuilder loc = new StringBuilder();
		loc.append(d.get(7));
		location = loc.toString();

		StringBuilder temp = new StringBuilder();
		temp.append(d.get(19));
		//Comment These Out if You Don't Want C or F
		temp.append(d.get(1));
		temperature = temp.toString();

		StringBuilder srise = new StringBuilder();
		srise.append(d.get(11));
		sunrise = srise.toString();

		StringBuilder sset = new StringBuilder();
		sset.append(d.get(12));
		sunset = sset.toString();

		StringBuilder ctime = new StringBuilder();
		ctime.append(d.get(14));
		currentTime = ctime.toString();

		StringBuilder wtype = new StringBuilder();
		wtype.append(d.get(18));
		weatherType = wtype.toString();
		
		System.err.println(d);
	}
	
	public String getLocation(){return location;}
	public String getTemperature(){return temperature;}
	public String getSunrise(){return sunrise;}
	public String getSunset(){return sunset;}
	public String getCurrentTime(){return currentTime;}
	public String getWeatherType(){return weatherType;}
}
