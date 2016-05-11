package com.nature.quickstep.pageobjects.nature;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.nature.quickstep.pageobjects.PageObject;

/**
 * Provides the ability to interact with (and query the state of) the login page on the Nature website.
 * @author mark.micallef
 *
 */

public class NatureLoginPage extends PageObject {
	

	/**
	 * Navigates to the Login homepage.
	 */

	@Override
	public void navigateTo() {
		HomePage homePage = new HomePage();
		homePage.navigateTo();
		homePage.lnkLogIn().click();
	}
	
	/**
	 * Automates the user journey whereby a user attempts to log in using a Nature account.
	 * @param email - The user's e-mail address.
	 * @param password - The user's password.
	 */
	public void loginUsingNatureAccount(String email, String password) {
		navigateTo();
		fldEmail().sendKeys(email);
		fldPassword().sendKeys(password);
		btnSubmit().click();
	}
	
	/**
	 * Provides a handle to the e-mail field on the login screen.
	 * @return A <code>WebElement</code> as a handle to the e-mail field.
	 */
	public WebElement fldEmail() {
		return browser.findElement(By.id("login-username"));
	}
	
	/**
	 * Provides a handle to the password field on the login screen.
	 * @return A <code>WebElement</code> as a handle to the password field.
	 */
	public WebElement fldPassword() {
		return browser.findElement(By.id("login-password"));
	}
	
	/**
	 * Provides a handle to the submit button on the login screen.
	 * @return A <code>WebElement</code> as a handle to the submit button.
	 */
	public WebElement btnSubmit() {
		return browser.findElement(By.id("login-submit"));
	}
	
	/**
	 * Provides a handle to the "Login via Athens" link on the login screen.
	 * @return A <code>WebElement</code> as a handle to the "Login via Athens" link.
	 */
	public WebElement lnkAthensLogin() {
		
		return browser.findElement(By.linkText("Login via Athens"));
    }
	
	/**
	 * Provides a handle to the "Login via your institution" link on the login screen.
	 * @return A <code>WebElement</code> as a handle to the "Login via your institution" link.
	 */
	public WebElement lnkInstitutionLogin() {
		return browser.findElement(By.xpath("//a[text() = 'Login via your institution']"));
	}

	
	
	
		
}
