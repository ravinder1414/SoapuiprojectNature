package com.nature.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.nature.utilities.Url;

public class NatureJobsPageObject {

	WebDriver browser;
	
	public NatureJobsPageObject(WebDriver webdriver){
		browser = webdriver;
	}
	
	public void navigate(){
		Url domain = new Url(System.getProperty("env"));
		browser.get(domain.createUrl() + "/naturejobs/science");
	}
	
	public WebElement jobOfTheWeekEntry(){
		return browser.findElement(By.cssSelector(".job-details h3 a"));
	}
	
	public WebElement jobDetails(){
		return browser.findElement(By.cssSelector(".rh-panel.shaded.job-details-panel"));
	}
	
}
