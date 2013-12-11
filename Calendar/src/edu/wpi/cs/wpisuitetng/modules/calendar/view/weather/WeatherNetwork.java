package edu.wpi.cs.wpisuitetng.modules.calendar.view.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherNetwork {
	private String host = "http://xml.weather.com/weather/local/";
	private String currentreg = "(?siU).*<locale>(.*)</locale>.*<ut>(.*)</ut>.*<ud>(.*)</ud>.*<us>(.*)</us>.*<up>(.*)</up>.*<ur>(.*)</ur>.*<loc id=\"(.*)\">.*<dnam>(.*)</dnam>.*<tm>(.*)</tm>.*<lat>(.*)</lat>.*<lon>(.*)</lon>.*<sunr>(.*)</sunr>.*<suns>(.*)</suns>.*<zone>(.*)</zone>.*<cc>.*<lsup>(.*)</lsup>.*<obst>(.*)</obst>.*<tmp>(.*)</tmp>.*<flik>(.*)</flik>.*<t>(.*)</t>.*<icon>(.*)</icon>.*<bar>.*<r>(.*)</r>.*<d>(.*)</d>.*<wind>.*<s>(.*)</s>.*<gust>(.*)</gust>.*<d>(.*)</d>.*<t>(.*)</t>.*<hmid>(.*)</hmid>.*<vis>(.*)</vis>.*<uv>.*<i>(.*)</i>.*<t>(.*)</t>.*<dewp>(.*)</dewp>.*<moon>.*<icon>(.*)</icon>.*<t>(.*)</t>.*<ppcp>(.*)</ppcp>.*";
	private String todayreg = "(?siU)<dayf>.*<lsup>(.*)</lsup>.*<day d=\"0\" t=\"(.*)\" dt=\"(.*)\".*<hi>(.*)</hi>.*<low>(.*)</low>.*<sunr>(.*)</sunr>.*<suns>(.*)</suns>.*<part p=\"d\">.*<icon>(.*)</icon>.*<t>(.*)</t>.*<wind>.*<s>(.*)</s>.*<gust>(.*)</gust>.*<d>(.*)</d>.*<t>(.*)</t>.*<bt>(.*)</bt>.*<ppcp>(.*)</ppcp>.*<hmid>(.*)</hmid>.*<part p=\"n\">.*<icon>(.*)</icon>.*<t>(.*)</t>.*<wind>.*<s>(.*)</s>.*<gust>(.*)</gust>.*<d>(.*)</d>.*<t>(.*)</t>.*<bt>(.*)</bt>.*<ppcp>(.*)</ppcp>.*<hmid>(.*)</hmid>.*";
	private String todayponereg = "(?siU)<day d=\"1\" t=\"(.*)\" dt=\"(.*)\".*<hi>(.*)</hi>.*<low>(.*)</low>.*<sunr>(.*)</sunr>.*<suns>(.*)</suns>.*<part p=\"d\">.*<icon>(.*)</icon>.*<t>(.*)</t>.*<wind>.*<s>(.*)</s>.*<gust>(.*)</gust>.*<d>(.*)</d>.*<t>(.*)</t>.*<bt>(.*)</bt>.*<ppcp>(.*)</ppcp>.*<hmid>(.*)</hmid>.*<part p=\"n\">.*<icon>(.*)</icon>.*<t>(.*)</t>.*<wind>.*<s>(.*)</s>.*<gust>(.*)</gust>.*<d>(.*)</d>.*<t>(.*)</t>.*<bt>(.*)</bt>.*<ppcp>(.*)</ppcp>.*<hmid>(.*)</hmid>.*";
	private String todayptworeg = "(?siU)<day d=\"2\" t=\"(.*)\" dt=\"(.*)\".*<hi>(.*)</hi>.*<low>(.*)</low>.*<sunr>(.*)</sunr>.*<suns>(.*)</suns>.*<part p=\"d\">.*<icon>(.*)</icon>.*<t>(.*)</t>.*<wind>.*<s>(.*)</s>.*<gust>(.*)</gust>.*<d>(.*)</d>.*<t>(.*)</t>.*<bt>(.*)</bt>.*<ppcp>(.*)</ppcp>.*<hmid>(.*)</hmid>.*<part p=\"n\">.*<icon>(.*)</icon>.*<t>(.*)</t>.*<wind>.*<s>(.*)</s>.*<gust>(.*)</gust>.*<d>(.*)</d>.*<t>(.*)</t>.*<bt>(.*)</bt>.*<ppcp>(.*)</ppcp>.*<hmid>(.*)</hmid>.*";

	private String location;

	public WeatherNetwork() {
		StringBuilder connectionbuild = new StringBuilder();
		connectionbuild.append("http://www.ip2location.com/");

		URL oracle;
		BufferedReader in;
		String wholeout = "";

		try {
			oracle = new URL(connectionbuild.toString());
			in = new BufferedReader(
					new InputStreamReader(oracle.openStream()));

			String inputLine;

			while ((inputLine = in.readLine()) != null)
				wholeout += inputLine;
			in.close();
		}catch(Exception e){
			System.err.println(e);
		}

		Pattern p = Pattern.compile("(?siU)<label for=\"chkZIPCode\">(.....)</label>");

		Matcher m = p.matcher(wholeout);
		if (m.find()){
			location = m.group(1);
		}
	}

	public WeatherNetwork(String loc){
		location = loc;
	}

	public List<String> MATCHERtoKWARGS(Matcher m){
		//Currently Doesn't Do KWARGS! Do we want Key Val?
		List<String> data = new LinkedList<String>();

		if (m.find()){
			for (int x = 1; x < m.groupCount(); x++){
				data.add(m.group(x));
			}
		}
		return data;
	}

	public List<String> grabCurrentInformation(){
		StringBuilder connectionbuild = new StringBuilder();
		connectionbuild.append(host);
		connectionbuild.append(location);
		connectionbuild.append("?cc=*&unit=f&dayf=1");

		URL oracle;
		BufferedReader in;
		String wholeout = "";

		try {
			oracle = new URL(connectionbuild.toString());
			in = new BufferedReader(
					new InputStreamReader(oracle.openStream()));

			String inputLine;

			while ((inputLine = in.readLine()) != null)
				wholeout += inputLine;
			in.close();

		}catch(Exception e){
			System.err.println(e);
		}

		Pattern p = Pattern.compile(currentreg);

		Matcher m = p.matcher(wholeout);

		return MATCHERtoKWARGS(m);
	}

	public List<String> grabTodayInformation(){
		StringBuilder connectionbuild = new StringBuilder();
		connectionbuild.append(host);
		connectionbuild.append(location);
		connectionbuild.append("?cc=*&unit=f&dayf=1");

		URL oracle;
		BufferedReader in;
		String wholeout = "";

		try {
			oracle = new URL(connectionbuild.toString());
			in = new BufferedReader(
					new InputStreamReader(oracle.openStream()));

			String inputLine;

			while ((inputLine = in.readLine()) != null)
				wholeout += inputLine;
			in.close();

		}catch(Exception e){
			System.err.println(e);
		}

		Pattern p = Pattern.compile(todayreg);

		Matcher m = p.matcher(wholeout);

		return MATCHERtoKWARGS(m);
	}

	public List<String> grabTodayPOneInformation(){
		StringBuilder connectionbuild = new StringBuilder();
		connectionbuild.append(host);
		connectionbuild.append(location);
		connectionbuild.append("?cc=*&unit=f&dayf=2");

		URL oracle;
		BufferedReader in;
		String wholeout = "";

		try {
			oracle = new URL(connectionbuild.toString());
			in = new BufferedReader(
					new InputStreamReader(oracle.openStream()));

			String inputLine;

			while ((inputLine = in.readLine()) != null)
				wholeout += inputLine;
			in.close();

		}catch(Exception e){
			System.err.println(e);
		}

		Pattern p = Pattern.compile(todayponereg);

		Matcher m = p.matcher(wholeout);

		return MATCHERtoKWARGS(m);
	}

	public List<String> grabTodayPTwoInformation(){
		StringBuilder connectionbuild = new StringBuilder();
		connectionbuild.append(host);
		connectionbuild.append(location);
		connectionbuild.append("?cc=*&unit=f&dayf=3");

		URL oracle;
		BufferedReader in;
		String wholeout = "";

		try {
			oracle = new URL(connectionbuild.toString());
			in = new BufferedReader(
					new InputStreamReader(oracle.openStream()));

			String inputLine;

			while ((inputLine = in.readLine()) != null)
				wholeout += inputLine;
			in.close();

		}catch(Exception e){
			System.err.println(e);
		}

		Pattern p = Pattern.compile(todayptworeg);

		Matcher m = p.matcher(wholeout);

		return MATCHERtoKWARGS(m);
	}
}
