package com.nature.foxtrot.common;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.google.common.collect.ImmutableMap;

public class DriverSelector {

    private static final String INTERNET_EXPLORER = "ie";

    private static final String FIREFOX = "ff";

    public static final String CHROME = "chrome";
    
 
	public static final String Xport = System.getProperty("lmportal.xvfb.id",
			":1");
	
	// Setup Firefox binary to start in Xvfb
	public static final File firefoxPath = new File(System.getProperty(
			"lmportal.deploy.firefox.path", "/opt/firefox/firefox"));
	
	// Setup Chrome binary to start in Xvfb
	public static ChromeDriverService service;
	public static final File chromePath = new File(System.getProperty(
			"lmportal.deploy.chrome.path", "/opt/chrome/chrome"));
	
    String browserChoice;
    WebDriver driver;

    public DriverSelector(String browser) {
        browserChoice = browser;
    }

	public WebDriver getBrowser() {

		if (CHROME.equals(browserChoice)) {
			service = new ChromeDriverService.Builder().usingDriverExecutable(chromePath).
					usingAnyFreePort().withEnvironment(ImmutableMap.of("DISPLAY",":1")).build();
			try {
				service.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			driver = new ChromeDriver(service);
			return driver;
			
        } else if (FIREFOX.equals(browserChoice)) {
        	FirefoxBinary firefox = new FirefoxBinary(firefoxPath);
    		firefox.setEnvironmentProperty("DISPLAY", Xport);
    		FirefoxDriver driver = new FirefoxDriver(firefox, null);
        	
          return driver;
//        	return driver = new FirefoxDriver();

        } else if (INTERNET_EXPLORER.equals(browserChoice)) {
            System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")
                    + "\\resources\\IEDriverServer.exe");
            return driver = new InternetExplorerDriver();
 
        } else {
            System.out
                    .println("DEBUG: No browser was set for the BrowserFactory so I am running on firefox driver.  Please ignore this if you are running in an IDE");
        	FirefoxBinary firefox = new FirefoxBinary(firefoxPath);
    		firefox.setEnvironmentProperty("DISPLAY", Xport);
    		FirefoxDriver driver = new FirefoxDriver(firefox, null);
             
          return driver;
//            return driver = new FirefoxDriver();
            
        }
		
    }

    public WebDriver browser() {
        return driver;
    }

}
