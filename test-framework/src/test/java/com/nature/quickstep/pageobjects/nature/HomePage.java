package com.nature.quickstep.pageobjects.nature;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.nature.quickstep.pageobjects.PageObject;
import com.nature.quickstep.util.Context;
import com.nature.quickstep.util.WebDriverUtils;
import com.nature.quickstep.util.Context.Website;
import com.nature.quickstep.util.WebDriverUtils.actions;

import static com.nature.quickstep.util.WebDriverUtils.actions.*;
import static org.junit.Assert.*;

public class HomePage extends PageObject {

	/**
	 * Navigates to the Nature homepage.
	 */
	@Override
	public void navigateTo() {
		browser.get(Context.getHomePageUrl(Website.nature));
	}

	/**
	 * Checks to see whether the element displaying the cover of the current
	 * Nature issue is present.
	 * 
	 * @return <code>true</code> if present, <code>false</code> if not.
	 */
	public boolean isCoverPresent() {
		return browser.findElements(By.className("cover-link")).size() == 1;
	}

	/**
	 * Checks to see whether the element displaying the inside nature section is
	 * present.
	 * 
	 * @return <code>true</code> if present, <code>false</code> if not.
	 */
	public boolean isInsideNaturePresent() {
		return browser.findElements(
				By.xpath("//h2[text() = 'Inside nature.com']")).size() == 1;
	}

	/**
	 * Checks to see whether the element displaying the news section is present.
	 * 
	 * @return <code>true</code> if present, <code>false</code> if not.
	 */
	public boolean isNewsSectionPresent() {
		return browser.findElements(By.className("news")).size() == 1;
	}

	/**
	 * Checks to see if a user is currently logged in.
	 * 
	 * @return <code>true</code> if present, <code>false</code> if not.
	 */
	//TODO: Review to see if it's worth splitting this into two methods (also similar methods)
	public boolean isUserLoggedIn(actions action) {

		long deadline = System.currentTimeMillis()
				+ WebDriverUtils.DEFAULT_TIMEOUT;
		
		boolean expectedResult = false;

		if (action == WAIT_UNTIL_TRUE) {
			expectedResult = true;
		}

		boolean result = (browser.findElements(By.className("login")).size() == 0);

		while ((action == WAIT_UNTIL_TRUE || action == WAIT_UNTIL_FALSE)
				&& result != expectedResult
				&& System.currentTimeMillis() < deadline) {
			result = browser.findElements(By.className("login")).size() == 0;
		}

		return result;
	}

	/**
	 * Logs the user out of the system. If the user is not logged in, an
	 * assertion will fail.
	 */

	public void logOut() {
		assertTrue(isUserLoggedIn(actions.WAIT_UNTIL_TRUE));
		lnkLogOut().click();
	}

	/**
	 * Returns a handle to the log out link.
	 * 
	 * @return <code>WebElement</code> as a handle to the log out link.
	 */
	public WebElement lnkLogOut() {
		return browser.findElement(By.className("logoff"));
	}

	/**
	 * Returns a handle to the log in link.
	 * 
	 * @return <code>WebElement</code> as a handle to the log in link.
	 */
	public WebElement lnkLogIn() {
		return browser.findElement(By.className("login"));
	}
	
	
}
