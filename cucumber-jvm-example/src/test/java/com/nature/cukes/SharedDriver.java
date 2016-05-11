package com.nature.cukes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.nature.utilities.BrowserFactory;

import cucumber.annotation.Before;

public class SharedDriver extends EventFiringWebDriver {
	private static final BrowserFactory BROWSER = new BrowserFactory();
	private static final WebDriver REAL_DRIVER = BROWSER.browser(); 
    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            REAL_DRIVER.close();
        }
    };

    static {
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    public SharedDriver() {
        super(REAL_DRIVER);
    }

    @Override
    public void close() {
        if(Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
    }
    
    @Before
    public void deleteAllCookies() {
        manage().deleteAllCookies();
    }
}