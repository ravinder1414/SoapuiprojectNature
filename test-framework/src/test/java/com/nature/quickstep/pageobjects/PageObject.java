package com.nature.quickstep.pageobjects;

import org.openqa.selenium.WebDriver;

import com.nature.quickstep.util.Context;
import com.nature.quickstep.util.WebDriverUtils;
import com.nature.quickstep.util.Context.Website;

/**
 * This abstract class provides core functionality for all page objects and also specifies 
 * a number of methods which should be implemented by each page object.  All page objects should 
 * be subclasses of this class.
 * @author mark.micallef
 *
 */
public abstract class PageObject {

	protected WebDriver browser;
	
	/**
	 * Instantiates the page object given a reference to a WebDriver.
	 * @param browser
	 */
	public PageObject(WebDriver browser) {
		
		if (browser == null) {
			this.browser = WebDriverUtils.getBrowser();
		} else {
			this.browser = browser;
		}
		
	}
	
	/**
	 * Instantiates the page object using a default web browser.
	 */
	public PageObject() {
		this(null);
	}
	
	/**
	 * Navigates to a URL relative to the given website. 
	 * @param website - The Website
	 * @param relativeUrl - The relative URL
	 */
	
	public void navigateToRelativeURL(Website website, String relativeUrl) {
		browser.get(Context.getHomePageUrl(website) + relativeUrl);
	}
	
	
	/**
	 * Navigates to the page being represented, ideally using the same
	 * method that a user would. <BR><BR>
	 * <b>Note:</b>Should be implemented by every page object.
	 * 
	 */
	public abstract void navigateTo();

}
