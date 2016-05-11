package com.nature.quickstep.pageobjects.nature;

import org.openqa.selenium.By;

import com.nature.quickstep.pageobjects.PageObject;

/**
 * Provides functionality to interact with (and query the state of) the news
 * section on the Nature home page.
 * 
 * @author mark.micallef
 * 
 */
public class NewsSection extends PageObject {

	/**
	 * This method does not do anything because it is assumed that the user
	 * would already be on a page that has a news section when this page object
	 * is used.
	 */

	@Override
	public void navigateTo() {
		// Not applicable
	}

	/**
	 * Extracts the date in <code>String</code> format from the news section.
	 * 
	 * @return The exact date text as depicted on the website.
	 */
	public String getDate() {
		return browser.findElement(
				By.xpath("//ul[@class='news xoxo']/li/h2/span")).getText();
	}

}
