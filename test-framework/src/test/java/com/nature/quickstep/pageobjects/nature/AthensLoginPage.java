package com.nature.quickstep.pageobjects.nature;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.nature.quickstep.pageobjects.PageObject;
import com.nature.quickstep.util.WebDriverUtils;

/**
 * Provides the ability to interact with (and query the state of) the login page
 * on the Nature website.
 * 
 * @author mark.micallef
 * 
 */

public class AthensLoginPage extends PageObject {

	HomePage homePage = new HomePage();
	NatureLoginPage natureLoginPage = new NatureLoginPage();

	/**
	 * Navigates to the Nature Login Page
	 */

	public void navigateTo() {
		natureLoginPage.navigateTo();
		natureLoginPage.lnkAthensLogin().click();
	}

	// TODO: Also, consider having constants for the different journals and
	// then just pass in those constants here. E.g. JOURNAL_WITH_ATHENS_ACCESS
	// and JOURNAL_WITHOUT_ATHENS_ACCESS (if this makes sense). An enumeration
	// might also make sense but is probably an overkill.

	/**
	 * Logs in to Athens using the given username and password.
	 * 
	 * @param username
	 *            - Username
	 * @param password
	 *            - Password
	 */
	public void loginUsingAthensAccount(String username, String password) {

		// navigateToAthens();
		fldUsername().sendKeys(username);
		fldPassword().sendKeys(password);
		btnLogin().click();

		WebDriverUtils.waitForURLToContainSubstring(browser, "nature.com");
	}

	/**
	 * Logs out of Athens. <B>***CURRENTLY NOT WORKING***</B>
	 */

	public void logoutOfAthens() {
		String currentURL = browser.getCurrentUrl();
		browser.get("https://auth.athensams.net/?ath_dspid=NPG&ath_action=ssologout");
		WebDriverUtils.waitForURLToContainSubstring(browser, "athensams.net");
		browser.get(currentURL);
	}

	/**
	 * Provides a handle to the username field on the Athens login screen.
	 * 
	 * @return A <code>WebElement</code> as a handle to the username field.
	 */
	public WebElement fldUsername() {
		return browser.findElement(By.id("ath_uname"));
	}

	/**
	 * Provides a handle to the password field on the Athens login screen.
	 * 
	 * @return A <code>WebElement</code> as a handle to the password field.
	 */
	public WebElement fldPassword() {
		return browser.findElement(By.id("ath_passwd"));
	}

	/**
	 * Provides a handle to the login button on the Athens login screen.
	 * 
	 * @return A <code>WebElement</code> as a handle to the login button.
	 */
	public WebElement btnLogin() {
		return browser.findElement(By.name("submit"));
	}

	/**
	 * Returns the partial content of an article which is currently displayed in
	 * the browser.
	 * 
	 * @return A WebElement object which provides access to the article's content.
	 */
	//TODO: Check whether it makes sense to make this method available for different articles.
	public WebElement getArticleContent() {
		return browser.findElement(By.xpath("//*[@id='articlebody']/p[1]"));
	}


	/**
	 * Waits for the browser to be redirected to the Athens login page.
	 */
	public void waitForAthensLoginPage() {
		WebDriverUtils.waitForURLToContainSubstring(browser, "athensams.net");
	}

}
