package com.nature.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.nature.utilities.Url;

public class LoginPageObject {

	WebDriver browser;
	
	public LoginPageObject(WebDriver webdriver){
		browser = webdriver;
	}
	
	public void navigate(){
		Url domain = new Url(System.getProperty("env"));
		browser.get(domain.createUrl() + "/foxtrot/svc/login");
	}
	
	public WebElement username(){
		return browser.findElement(By.cssSelector("#login-username"));
	}
	
	public WebElement password(){
		return browser.findElement(By.cssSelector("#login-password"));
	}

	public WebElement submit(){
		return browser.findElement(By.cssSelector("#login-submit"));
	}
}
