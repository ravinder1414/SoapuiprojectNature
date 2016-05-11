package com.nature.quickstep.util;

import java.net.URL;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.nature.quickstep.util.Context.Browser;

import static org.junit.Assert.*;

public class WebDriverUtils {

	/**
	 * A a static reference to browser which is used by all tests.
	 */
	private static WebDriver browser = null;

	/**
	 * A flag indicating whether or not the shutdown hook has been configured.
	 * The shutdown hook basically shuts down all browsers and does any required
	 * house keeping just before the JVM exits.
	 */
	private static boolean shutDownHookConfigured = false;

	/**
	 * Default timeout in milliseconds. This is used whenever there is a wait
	 * operation such as waiting for the browser to redirect to a different URL.
	 */
	public static long DEFAULT_TIMEOUT = 10000;

	/**
	 * This enumeration enumerates a number of webdriver related actions that
	 * are used as paramaters to specify behaviour of methods in this utility
	 * class.
	 * 
	 * @author mark.micallef
	 * 
	 */
	public static enum actions {
		DO_NOT_WAIT, WAIT_UNTIL_TRUE, WAIT_UNTIL_FALSE
	};

	/**
	 * Returns the static reference to the current browser.
	 * 
	 * @return Reference to the browser.
	 */
	public static WebDriver getBrowser() {

		if (browser == null) {

			// Sleep for a random period because browsers launching at the same
			// time might affect each other
			// TODO: Find a more efficient way to do this.

			if (Context.getMaxThreads() > 1) {
				Random r = new Random();
				int sleepSecs = r.nextInt(10);
				System.out.println("Sleeping for " + sleepSecs
						+ " seconds before launching browser");
				sleep(sleepSecs * 1000);
			}

			Browser whichBrowser = Context.getBrowserType();

			if (whichBrowser == Browser.firefox) {
				browser = launchFirefox();
			} else if (whichBrowser == Browser.phantomjs) {
				browser = launchPhantomJS();
			} else if (whichBrowser == Browser.iphone) {
				browser = launchIPhone();
			} else if (whichBrowser == Browser.ipad) {
				browser = launchIPad();
			} else {
				throw new RuntimeException("Unkown browser: "
						+ whichBrowser.toString());
			}

			if (whichBrowser != Browser.iphone && whichBrowser != Browser.ipad) {
				browser.manage().window().setSize(new Dimension(1280, 800));
			}
		}

		setShutDownHook();

		return browser;
	}

	/**
	 * Launches the firefox browser and returns a handle to it.
	 * 
	 * @return A handle to the firefox browser instance that has just been
	 *         launched.
	 */
	protected static WebDriver launchFirefox() {
		return new FirefoxDriver();
	}

	/**
	 * Launches the headless PhanthomJS browser and returns a handle to it.
	 * 
	 * @return A handle to the headless PhantomJS browser instance that has just
	 *         been launched.
	 */
	protected static WebDriver launchPhantomJS() {
		DesiredCapabilities caps = new DesiredCapabilities();
		
		
		String phantomJSLocation = "/usr/local/bin/phantomjs";
		
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			phantomJSLocation = ".\\target\\test-classes\\binaries\\phantomjs\\phantomjs.exe";
		}
		
		caps.setJavascriptEnabled(true);
		caps.setCapability("takesScreenshot", true);
		caps.setCapability(
				PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				phantomJSLocation);

		return new PhantomJSDriver(caps);
	}

	protected static WebDriver launchIPhone() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("device", "iPhone Simulator");
		capabilities.setCapability("version", "6.1");
		capabilities.setCapability("app", "safari");
		WebDriver driver = null;
		try {
			driver = new RemoteWebDriver(
					new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		} catch (Exception e) {
			e.printStackTrace();
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		return driver;
	}
	
	protected static WebDriver launchIPad() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("device", "iPad Simulator");
		capabilities.setCapability("version", "6.1");
		capabilities.setCapability("app", "safari");
		WebDriver driver = null;
		try {
			driver = new RemoteWebDriver(
					new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		} catch (Exception e) {
			e.printStackTrace();
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		return driver;
	}

	/**
	 * Sets up a shutdown hook in order to close all browsers just before the
	 * JVM exits.
	 */
	private static void setShutDownHook() {

		if (!shutDownHookConfigured) {
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					shutDown();
				}
			});

			shutDownHookConfigured = true;
		}
	}

	/**
	 * Convenience methods for explicit sleeps. Should be used sparingly.
	 * 
	 * @param millis
	 *            - The amount of miliseconds to sleep.
	 */
	public static void sleep(long millis) {

		try {
			Thread.sleep(millis);
		} catch (Exception e) {
		}

	}

	/**
	 * Waits (until DEFAULT_TIMEOUT milliseconds have expired) for the URL of
	 * the given browser instance to contain the given substring.
	 * 
	 * @param browser
	 *            - The browser
	 * @param substring
	 *            - The substring
	 */
	public static void waitForURLToContainSubstring(WebDriver browser,
			String substring) {

		long deadline = System.currentTimeMillis() + DEFAULT_TIMEOUT;

		if (browser == null) {
			browser = WebDriverUtils.getBrowser();
		}

		while (!browser.getCurrentUrl().contains(substring)
				&& System.currentTimeMillis() < deadline) {
			sleep(500);
		}

		assertTrue(
				"Violated expectation: Expected URL ("
						+ browser.getCurrentUrl() + ")  to contain \""
						+ substring + "\"",
				browser.getCurrentUrl().contains(substring));

	}

	/**
	 * Waits (until DEFAULT_TIMEOUT milliseconds have expired) for the URL of
	 * the given browser instance to <B><U>NOT</U></B> contain the given
	 * substring.
	 * 
	 * @param browser
	 *            - The browser
	 * @param substring
	 *            - The substring
	 */
	public static void waitForURLNotToContainSubstring(WebDriver browser,
			String substring) {

		long deadline = System.currentTimeMillis() + DEFAULT_TIMEOUT;

		if (browser == null) {
			browser = WebDriverUtils.getBrowser();
		}

		while (browser.getCurrentUrl().contains(substring)
				&& System.currentTimeMillis() < deadline) {
			sleep(500);
		}

		assertTrue(
				"Violated expectation: Expected URL ("
						+ browser.getCurrentUrl() + ") -->not<-- to contain \""
						+ substring + "\"",
				!browser.getCurrentUrl().contains(substring));

	}

	/**
	 * Waits (until DEFAULT_TIMEOUT milliseconds have expired) for the URL of
	 * the given browser instance to be equal to the given url.
	 * 
	 * @param browser
	 *            - The browser
	 * @param url
	 *            - The url
	 */
	public static void waitForURLToBe(WebDriver browser, String url) {

		long deadline = System.currentTimeMillis() + DEFAULT_TIMEOUT;

		if (browser == null) {
			browser = WebDriverUtils.getBrowser();
		}

		while (!browser.getCurrentUrl().equals(url)
				&& System.currentTimeMillis() < deadline) {
			sleep(500);
		}

		assertTrue(
				"Violated expectation: Expected URL ("
						+ browser.getCurrentUrl() + ") to be \"" + url + "\"",
				browser.getCurrentUrl().equals(url));

	}

	/**
	 * Waits (until DEFAULT_TIMEOUT milliseconds have expired) for the URL of
	 * the currently used browser instance to contain the given substring.
	 * 
	 * @param url
	 *            - The url
	 */
	public static void waitForURLToBe(String url) {
		waitForURLToBe(null, url);
	}

	/**
	 * Waits (until DEFAULT_TIMEOUT milliseconds have expired) for the URL of
	 * the currently used browser instance to <B><U>NOT</U></B> contain the
	 * given substring.
	 * 
	 * @param substring
	 *            - The substring
	 */
	public static void waitForURLNotToContainSubstring(String substring) {
		waitForURLNotToContainSubstring(null, substring);
	}

	/**
	 * Waits (until DEFAULT_TIMEOUT milliseconds have expired) for the URL of
	 * the currently used browser instance to be equal to the given url.
	 * 
	 * @param url
	 *            - The url
	 */
	public static void waitForURLToContainSubstring(String url) {
		waitForURLToContainSubstring(null, url);
	}

	/**
	 * Shuts down the current browser and carries out any required housekeeping.
	 * This is required for post-testsuite cleanups as well as for scenarios
	 * which request their own browser instance instead of reusing a common one.
	 */
	public static void shutDown() {
		if (browser != null) {
			browser.quit();
			browser = null;
		}

		// Shut down context
		Context.save();
	}
	
	/**
	 * Gives Current Url of the Browser
	 */
	
	public static String getCurrentUrl() {
		return browser.getCurrentUrl();

	}

}
