package com.nature.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePageObject {

	WebDriver browser;
	
	public HomePageObject(WebDriver webdriver){
		browser = webdriver;
	}
	
	public String getUrl(){
		return browser.getCurrentUrl();
	}
	
	public WebElement welcomeMessage(){
		return browser.findElement(By.cssSelector(".logon.links-above"));
	}
}
