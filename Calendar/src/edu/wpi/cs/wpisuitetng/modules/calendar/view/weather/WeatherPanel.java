package edu.wpi.cs.wpisuitetng.modules.calendar.view.weather;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons.TransparentButton;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class WeatherPanel extends JPanel implements ActionListener {
	private WeatherData wdata;

	private JPanel locationPanel;
	private JPanel temperaturePanel;
	private JPanel sunrisePanel;
	private JPanel sunsetPanel;
	private JPanel currentTimePanel;
	private JPanel weatherTypePanel;

	private TransparentButton prevButton;
	private TransparentButton nextButton;
	
	private int viewState;
	
	private JPanel visiblePanel;

	public WeatherPanel() {
		try{
			wdata = new WeatherData();
		}catch(Exception e){
			wdata = null;
		}
		
		viewState = 0;
		buildLayout();
	}

	public WeatherPanel(String loc){
		try{
			wdata = new WeatherData(loc);
		}catch(Exception e){
			wdata = null;
		}

		buildLayout();
	}

	private void buildLayout() {
		if(wdata != null) {
			try {
				prevButton = new TransparentButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/previous.png"))));
				nextButton = new TransparentButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/next.png"))));
			} catch (IOException e) {}
			
			prevButton.setActionCommand("prev");
			nextButton.setActionCommand("next");
			
			prevButton.addActionListener(this);
			nextButton.addActionListener(this);
			
			this.setLayout(new MigLayout("insets 0 n 0 n", "[][center][]", "[][]"));
			
			locationPanel = new JPanel();
			temperaturePanel = new JPanel();
			sunrisePanel = new JPanel();
			sunsetPanel = new JPanel();
			currentTimePanel = new JPanel();
			weatherTypePanel = new JPanel();
			
			locationPanel.add(new JLabel("Location: " + wdata.getLocation()));
			temperaturePanel.add(new JLabel("Temperature: " + wdata.getTemperature()));
			sunrisePanel.add(new JLabel("Sunrise: " + wdata.getSunrise()));
			sunsetPanel.add(new JLabel("Sunset: " + wdata.getSunset()));
			currentTimePanel.add(new JLabel("CurrentTime: " + wdata.getCurrentTime()));
			weatherTypePanel.add(new JLabel("WeatherType: " + wdata.getWeatherType()));
			
			visiblePanel = locationPanel;
			
			JLabel weatherTitle = new JLabel("Weather", JLabel.CENTER);
			weatherTitle.setFont(new Font(weatherTitle.getFont().getName(), Font.BOLD, 12));
			weatherTitle.setForeground(CalendarUtils.titleNameColor);
			
			this.add(weatherTitle, "cell 1 0");
			
			this.add(prevButton, "cell 0 1");
			this.add(nextButton, "cell 2 1");
			
			updateLayout();
		} else {
			this.add(new JLabel("Error Loading Weather Information"));
		}		
	}
	
	private void updateLayout() {
		switch(viewState) {
		case 0:
			prevButton.setVisible(false);
			this.remove(visiblePanel);
			visiblePanel = locationPanel;
			this.add(visiblePanel, "cell 1 1");
			break;
		case 1:
			this.remove(visiblePanel);
			visiblePanel = temperaturePanel;
			this.add(visiblePanel, "cell 1 1");
			prevButton.setVisible(true);
			break;
		case 2:
			this.remove(visiblePanel);
			visiblePanel = sunrisePanel;
			this.add(visiblePanel, "cell 1 1");
			break;
		case 3:
			this.remove(visiblePanel);
			visiblePanel = sunsetPanel;
			this.add(visiblePanel, "cell 1 1");
			break;
		case 4:
			nextButton.setVisible(true);
			this.remove(visiblePanel);
			visiblePanel = currentTimePanel;
			this.add(visiblePanel, "cell 1 1");
			break;
		case 5:
			nextButton.setVisible(false);
			this.remove(visiblePanel);
			visiblePanel = weatherTypePanel;
			this.add(visiblePanel, "cell 1 1");
			break;
		}
		
		this.updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("prev")) viewState--;
		else if(e.getActionCommand().equals("next")) viewState++;
		updateLayout();
	}
}
