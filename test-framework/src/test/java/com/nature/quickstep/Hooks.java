package com.nature.quickstep;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.nature.quickstep.util.Context;
import com.nature.quickstep.util.WebDriverUtils;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;


public class Hooks {
	
	/**
	 * Used to time scenarios when required
	 */
	private long startTimeStamp;

	
	/**
	 * Flag indicating whether or not the test runner has been initialised.
	 */
	private boolean initialised = false;
	
	@Before
	public void init() {
		if (!initialised) {
			Context.init();
		}
		
	}
	
	@Before("@cleanbrowser")
	public void resetBrowserInstance() {
		WebDriverUtils.shutDown();
	}
	
	@Before("@timed")
	public void startTimer() {
		startTimeStamp = System.currentTimeMillis();
	}
	
	@After("@timed")
	public void stopTimer(Scenario scenario) {
		int durationInSeconds = (int)((System.currentTimeMillis() - startTimeStamp) /1000);
		String message = "time-taken-seconds," + Integer.toString(durationInSeconds);
		scenario.write(message);
	}
	
	@After
	public void afterScenario(Scenario scenario) {
		if (scenario.isFailed()) {
			byte[] screenshot = ((TakesScreenshot) WebDriverUtils.getBrowser()).getScreenshotAs(OutputType.BYTES);
	        scenario.embed(screenshot, "image/png");
		}
	}
	
}
