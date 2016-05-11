package com.nature.utilities;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class BrowserFactory {
    WebDriver driver;

    public BrowserFactory() {
        String browser = System.getProperty("browser");
        if ("chrome".equals(browser)) {
            driver = new ChromeDriver();
        } else if ("ff".equals(browser)) {
            FirefoxBinary binary = new FirefoxBinary(new File("/Applications/Firefox.app/Contents/MacOS/firefox-bin"));
            FirefoxProfile profile = new FirefoxProfile();
            driver = new FirefoxDriver(binary, profile);
        } else {
            System.out
                    .println("DEBUG: No browser was set for the BrowserFactory so I am running Chrome.  Please ignore this if you are running in an IDE");
            driver = new ChromeDriver();
        }
    }

    public WebDriver browser() {
        return driver;
    }
}
