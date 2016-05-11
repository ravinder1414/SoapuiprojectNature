package com.nature.foxtrot.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.nature.foxtrot.common.DriverSelector;
import com.nature.foxtrot.common.Url;

public class LoginAthensPageObject {

	WebDriver driver;

	public void navigateAthens(String JournalUrl) {
		DriverSelector webbrowser = new DriverSelector(
				System.getProperty("browser"));
		driver = webbrowser.getBrowser();
		Url domain = new Url(System.getProperty("env"));
		driver.get(domain.createUrl()
				+JournalUrl);
	}
	
	public String  createUrl(){
        Url domain = new Url(System.getProperty("env"));
        return domain.createUrl();	
    }
	
	

	public WebElement LoginViaAthens() {
		return driver.findElement(By.linkText("Login via Athens"));
	}

	public WebElement OpenAthensUserName() {
		return driver.findElement(By.xpath("//*[@id='logindiv']/form/div/table/tbody/tr[1]/td[2]/input"));
	}

	public WebElement OpenAthensPassword() {
		return driver.findElement(By.xpath("//*[@id='logindiv']/form/div/table/tbody/tr[2]/td[2]/input"));
	}

	public WebElement OpenAthensSubmit() {
		return driver
				.findElement(By
						.xpath("//*[@id='logindiv']/form/div/table/tbody/tr[3]/td[2]/input"));
	}

//	public WebElement OpenAthensValidation() {
//	return driver.findElement(By.xpath("//*[@id='hdr']/div[3]/div/p[1]"));
//}

    public WebElement OpenAthensContentValidation() {
	   return driver.findElement(By.xpath("//*[@id='articlebody']/p[1]"));
    }

	public WebDriver Browser() {
		return driver;

	}

}
