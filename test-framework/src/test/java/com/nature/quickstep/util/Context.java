package com.nature.quickstep.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;
import static org.junit.Assert.*;

/**
 * Manages context specific aspects of a test run. This can include URLs,
 * credentials, data sources, etc.
 * 
 * @author mark.micallef
 */

//TODO: Javadoc needs updating

public class Context {

	/**
	 * The path to the properties file which configures the context.
	 */
	private final static String propsFile = "stf.properties";

	/**
	 * A static reference to the Properties object.
	 */
	private static Properties props = null;

	/**
	 * Enumeration of all supported environments.
	 * 
	 * @author mark.micallef
	 * 
	 */
	public static enum Environment {
		test, staging, live, unkown
	};

	/**
	 * Enumeration of all supported browsers
	 * 
	 * @author mark.micallef
	 * 
	 */
	public static enum Browser {
		firefox, phantomjs, iphone, ipad, unkown
	};

	/**
	 * Enumeration of different modes in which the test framework can be
	 * executed.
	 * 
	 * <ul>
	 * <li><b>Test mode</b> runs all tests which are not tagged as unrunnable
	 * (e.g. wip, broken, manual, etc).</li>
	 * <li><b>Dev mode</b> only runs scenarios tagges as wip. This mode is
	 * usually used during development when creating/maintaining a scenario.</li>
	 * </ul>
	 * 
	 * @author mark.micallef
	 * 
	 */
	public static enum Mode {
		test, dev
	};

	/**
	 * Enumeration of all supported websites.
	 * 
	 * @author mark.micallef
	 * 
	 */
	public static enum Website {
		nature, palgraveJ, labanimal, unkown
	}

	// -------------------------------------------------------------------------
	// Property names
	// -------------------------------------------------------------------------

	/**
	 * Environment against which test will execute.
	 */
	public final static String pEnvironment = "env";

	/**
	 * Mode in which the test framework will execute.
	 */
	public final static String pMode = "mode";

	/**
	 * Any custom tags which the user wants to use for filtering scenarios.
	 */
	public final static String pTags = "tags";

	/**
	 * URL of nature website on test environment.
	 */
	public final static String pUrlNatureHomePageOnTest = "url-nature-homepage-test";

	/**
	 * URL of nature website on staging environment.
	 */
	public final static String pUrlNatureHomePageOnStaging = "url-nature-homepage-staging";

	/**
	 * URL of nature website on live environment.
	 */
	public final static String pUrlNatureHomePageOnLive = "url-nature-homepage-live";

	/**
	 * Browser to use for tests
	 */
	public final static String pBrowser = "browser";

	/**
	 * URL of palgrave-journals website on test environment.
	 */
	public final static String pUrlPalgraveJournalsHomePageOnTest = "url-palgrave-homepage-test";

	/**
	 * URL of palgrave-journals website on staging environment.
	 */
	public final static String pUrlPalgraveJournalsHomePageOnStaging = "url-palgrave-homepage-staging";

	/**
	 * URL of palgrave-journals website on live environment.
	 */
	public final static String pUrlPalgraveJournalsHomePageOnLive = "url-palgrave-homepage-live";

	/**
	 * URL of labanimal website on test environment.
	 */
	public final static String pUrllabanimalHomePageOnTest = "url-labanimal-homepage-test";

	/**
	 * URL of labanimal website on staging environment.
	 */
	public final static String pUrllabanimalHomePageOnStaging = "url-labanimal-homepage-staging";

	/**
	 * URL of labanimal website on live environment.
	 */
	public final static String pUrllabanimalHomePageOnLive = "url-labanimal-homepage-live";

	// -------------------------------------------------------------------------
	// Default values
	// -------------------------------------------------------------------------

	public final static Environment defEnvironment = Environment.test;
	public final static Mode defMode = Mode.test;
	public final static String defUrlNatureHomePageOnTest = "http://test-www.nature.com/";
	public final static String defUrlNatureHomePageOnStaging = "http://staging-www.nature.com/";
	public final static String defUrlNatureHomePageOnLive = "http://www.nature.com/";

	public final static Browser defBrowser = Browser.phantomjs;

	public final static String defUrlPalgraveHomePageOnTest = "http://test-www.palgrave-journals.com/";
	public final static String defUrlPalgraveHomePageOnStaging = "http://staging-www.palgrave-journals.com/";
	public final static String defUrlPalgraveHomePageOnLive = "http://www.palgrave-journals.com/";

	public final static String defUrlLabanimalHomePageOnTest = "http://test-www.labanimal.com/";
	public final static String defUrlLabanimalHomePageOnStaging = "http://staging-www.labanimal.com/";
	public final static String defUrlLabanimalHomePageOnLive = "http://www.labanimal.com/";

	// -------------------------------------------------------------------------
	// Arrays for management of properties
	// Ensure that any new properties are added to these arrays.
	// -------------------------------------------------------------------------

	public final static String[] allProperties = new String[] { pEnvironment,
			pUrlNatureHomePageOnTest, pUrlNatureHomePageOnStaging,
			pUrlNatureHomePageOnLive, pBrowser,
			pUrlPalgraveJournalsHomePageOnTest,
			pUrlPalgraveJournalsHomePageOnStaging,
			pUrlPalgraveJournalsHomePageOnLive, pUrllabanimalHomePageOnTest,
			pUrllabanimalHomePageOnStaging, pUrllabanimalHomePageOnLive };

	public final static String[] allDefaults = new String[] {
			defEnvironment.toString(), defUrlNatureHomePageOnTest,
			defUrlNatureHomePageOnStaging, defUrlNatureHomePageOnLive,
			defBrowser.toString(), defUrlPalgraveHomePageOnTest,
			defUrlPalgraveHomePageOnStaging, defUrlPalgraveHomePageOnLive,
			defUrlLabanimalHomePageOnTest, defUrlLabanimalHomePageOnStaging,
			defUrlLabanimalHomePageOnLive };

	// -------------------------------------------------------------------------
	// Getters and Setters
	// -------------------------------------------------------------------------

	/**
	 * Retrieves the environment against which this test run is being executed.
	 * 
	 * @return Environment instance
	 */
	public static Environment getEnvironment() {

		Environment result = Environment.unkown;

		String sEnv = System.getProperty(pEnvironment);

		if (sEnv == null) {
			sEnv = props.getProperty(pEnvironment, defEnvironment.toString());
		}

		for (Environment env : Environment.values()) {
			if (env.toString().equalsIgnoreCase(sEnv)) {
				result = env;
			}
		}

		assertNotEquals("Unkown environment", result, Environment.unkown);
		
		setEnvironment(result);

		return result;
	}

	public static void setEnvironment(Environment env) {
		props.setProperty(pEnvironment, env.toString());
	}

	/**
	 * Retrieves the mode in which the test run was executed.
	 * 
	 * @return Mode instance
	 */
	public static Mode getMode() {

		Mode result = defMode;

		String sMode = System.getProperty(pMode);

		if (sMode == null) {
			sMode = props.getProperty(pMode, defMode.toString());
		}

		for (Mode mode : Mode.values()) {
			if (mode.toString().equalsIgnoreCase(sMode)) {
				result = mode;
			}
		}

		setMode(result);

		return result;
	}

	/**
	 * Retrieves any custom tags required by the user. This is currently only
	 * supported as a command-line argument -Dtags={comma-separated list of
	 * tags}.
	 * 
	 * @return String tags, as passed in by the user (assumed to be a
	 *         comma-separated list)
	 */
	public static String[] getTags() {

		String sTags = System.getProperty(pTags);

		String[] result = new String[] {};

		if (sTags != null) {
			StringTokenizer tokenizer = new StringTokenizer(sTags, ",");
			result = new String[tokenizer.countTokens()];
			int i = 0;

			while (tokenizer.hasMoreTokens()) {
				result[i] = tokenizer.nextToken();
				i++;
			}

		}

		return result;
	}

	public static void setMode(Mode mode) {
		props.setProperty(pMode, mode.toString());
	}

	/**
	 * Retrieves the browser on which this test run is being executed.
	 * 
	 * @return Browser (enumeration) instance
	 */
	public static Browser getBrowserType() {

		Browser result = Browser.unkown;

		String sBrowser = System.getProperty(pBrowser);

		if (sBrowser == null) {
			sBrowser = props.getProperty(pBrowser, defEnvironment.toString());
		}

		for (Browser browser : Browser.values()) {
			if (browser.toString().equalsIgnoreCase(sBrowser)) {
				result = browser;
			}
		}

		// Save property in case it came from system property
		setBrowser(result);

		return result;
	}

	public static void setBrowser(Browser newBrowser) {
		props.setProperty(pBrowser, newBrowser.toString());
	}

	/**
	 * Constructs a URL based on the website which is being tested, the
	 * environment which is being tested on, and the relative URL as passed in
	 * via parameter.
	 * 
	 * @param site
	 *            - The site (Nature, Palgrave or Lab Animal)
	 * @param urlID
	 *            - The id of the url as in the properties file. <B>TODO:</B>
	 *            Revisit this and be sure it makes sense.
	 * @return A constructed URL which
	 */
	public static String getUrl(Website site, String urlID) {

		String propName = "url-" + site.toString().toLowerCase() + "-"
				+ urlID.toLowerCase() + "-"
				+ getEnvironment().toString().toLowerCase();

		return props.getProperty(propName);
	}

	public static String getHomePageUrl(Website site) {
		return getUrl(site, "homepage");
	}

	public static int getMaxThreads() {
		int result = 1;

		try {
			result = Integer.parseInt(System.getProperty("threads", "1"));
		} catch (Exception e) {
			// Do nothing. Simply return default value;
		}

		return result;
	}

	// -------------------------------------------------------------------------
	// Boiler plating
	// -------------------------------------------------------------------------

	/**
	 * Initialises the context by loading properties from disk. If the file does
	 * not exist, it is created with default values.
	 * 
	 * @return <code>true</code> if successful, <code>false</code> if not.
	 */
	public static boolean init() {

		boolean result = true;

		props = new Properties();

		try {
			props.load(new FileReader(propsFile));
		} catch (FileNotFoundException fnfe) {

			// Do nothing. File will be saved with default values by the end of
			// the method.

		} catch (IOException ioe) {

			// Unkown IO error. Print stack trace and stop tests.
			ioe.printStackTrace();
			result = false;
		}

		save();

		return result;
	}

	/**
	 * Saves the properties to file. Defaults are saved where the property value
	 * has not been specified.
	 */
	public static void save() {

		for (int i = 0; i < allProperties.length; i++) {
			props.setProperty(allProperties[i],
					props.getProperty(allProperties[i], allDefaults[i]));
		}

		try {
			props.store(new FileWriter(propsFile),
					"Properties configuring the salt test framework.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
