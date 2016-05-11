package com.nature.foxtrot.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.nature.foxtrot.common.DriverSelector;
import com.nature.foxtrot.common.Url;

public class LoginShibbolethPageObject {
    WebDriver driver;
    
    public String  createUrl(){
        Url domain = new Url(System.getProperty("env"));
        return domain.createUrl();	
    }

    public void navigateShibboleth(String jornalUrl) {
        DriverSelector webbrowser = new DriverSelector(System.getProperty("browser"));
        driver = webbrowser.getBrowser();
        Url domain = new Url(System.getProperty("env"));
        driver.get(domain.createUrl() + jornalUrl);
    }
    
    public WebElement NCBJournalFullAccess(){
		return driver.findElement(By.linkText("View full access options"));
    }
    
    public WebElement NCBJournalLogin(){
		return driver.findElement(By.xpath("//*[@id='access-login']"));
    }
    
    public WebElement ShibbolethInstitutionalAccess(){
		return driver.findElement(By.xpath("//*[@id='access-login-institution']"));
    }

    public WebElement ShibbolethLoginViaInstitute() {
//        return driver.findElement(By.xpath("//*[@id='form-login']/fieldset/div/div/p[4]"));
        return driver.findElement(By.linkText("Login via your institution"));
    }

    public void ShibbolethCountry(String country) {
        Select sele = new Select(driver.findElement(By.xpath("//*[@id='loc-select']")));
        sele.selectByVisibleText(country);
    }

    public WebElement ShibbolethGo() {
        return driver.findElement(By.xpath("//*[@id='login-go']"));
    }
    
    public WebElement SearchInstitution(){
    	return driver.findElement(By.xpath("//*[@id='loc-search']"));
    }
    
    public WebElement SearchButton(){
		return driver.findElement(By.xpath("//*[@id='login-search']"));
    }


    public WebElement ShibbolethBrunalUniversity() {
        return driver.findElement(By.linkText("Brunel University"));
    }

    public WebElement BrunalUniversityUserName() {
        return driver.findElement(By.xpath("//*[@id='hometext']/div/div[1]/div/form/table/tbody/tr[1]/td[2]/input"));
    }

    public WebElement BrunalUniversityPassword() {
        return driver.findElement(By.xpath("//*[@id='hometext']/div/div[1]/div/form/table/tbody/tr[2]/td[2]/input"));
    }

    public WebElement BrunalUniversityContinue() {
        return driver.findElement(By.xpath("//*[@id='hometext']/div/div[1]/div/form/table/tbody/tr[3]/td[2]/button"));
    }
    
//    public WebElement BrunalUniversityConfirmationMessage(){
//		return driver.findElement(By.xpath("//*[@id='header']/div/p[1]"));
//		
//    }
    
    public WebElement BrunalUniversityContentAssertion(){
		return driver.findElement(By.xpath("//*[@id='introduction']/h1/a"));
    }

    
    public WebElement BrunalUniversityWrongCredentials(){
		return driver.findElement(By.xpath("//*[@id='hometext']/div/div[1]/div/p/font"));
    }
    
    public WebElement WelecomeToPalgrave(){
		return driver.findElement(By.xpath("//*[@id='content']/h1"));
    	
    }
    
    public WebElement beShibbolethLoginViaInstituteLink(){
		return driver.findElement(By.xpath("//*[@id='extranav']/div/div[1]/ul/li[4]/p[1]/a"));
    }
    
    public WebElement beContentAssertion(){
		return driver.findElement(By.xpath("//*[@id='articlebody']/p[1]"));
    }
    
    public WebElement BrunelUniversityLinkPalgrave(){
		return driver.findElement(By.xpath("//*[@id='form-login']/fieldset/div/fieldset[2]/ul/li/a"));
    }
    
    public WebDriver Browser() {
        return driver;
    }

}
