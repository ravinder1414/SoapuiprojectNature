package com.nature.quickstep.pageobjects.nature;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.nature.quickstep.pageobjects.PageObject;
import com.nature.quickstep.util.Context;
import com.nature.quickstep.util.WebDriverUtils;
import com.nature.quickstep.util.Context.Website;

/**
 * Provides the ability to interact with (and query the state of) the login page on the Nature website.
 * @author mark.micallef
 *
 */

public class ShibbolethLoginPage extends PageObject {
	
	HomePage homePage = new HomePage();

	/**
	 * Navigates to the Login homepage.
	 */

	@Override
	public void navigateTo() {
		NatureLoginPage natureLoginPage = new NatureLoginPage();
		natureLoginPage.navigateTo();
		natureLoginPage.lnkInstitutionLogin().click();
	}
	
	/**
	 * Logs in using the <i>Search for an institution</i> feature.
	 * @param institution - The search string.
	 * @param username - The username to use once the institution is found.
	 * @param password - The password to use once the institution is found.
	 */
	public void loginBySearchingForAnInstitution(String institution, String username, String password) {
		navigateTo();
		fldInstitution().sendKeys(institution);
		btnSearch().click();
		
		//Assume results link text matches institution text exactly
		browser.findElement(By.linkText(institution)).click();
		
		//Wait for redirection
		WebDriverUtils.waitForURLNotToContainSubstring(browser, Context.getHomePageUrl(Website.nature));
		
		//Fill in institution-specific credentials 
		//TODO: Make the IDs here configurable. For now they will only work with Brunel
		browser.findElement(By.name("j_username")).sendKeys(username);
		browser.findElement(By.name("j_password")).sendKeys(password);
		browser.findElement(By.xpath("//button[@value='Login']")).click();
		
	}
	
	/**
	 * Provides a handle to the "search for an institution" field.
	 * @return A <code>WebElement</code> as a handle to the field.
	 */
	public WebElement fldInstitution() {
		return browser.findElement(By.id("loc-search"));
	}
	
	/**
	 * Provides a handle to thesearch button.
	 * @return A <code>WebElement</code> as a handle to the search button.
	 */
	public WebElement btnSearch() {
		return browser.findElement(By.id("login-search"));
	}
		
}
