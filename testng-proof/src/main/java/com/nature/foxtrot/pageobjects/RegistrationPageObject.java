package com.nature.foxtrot.pageobjects;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.nature.foxtrot.common.DriverSelector;
import com.nature.foxtrot.common.Url;

public class RegistrationPageObject {

    WebDriver driver;

    public void navigateRegistration(String JournalUrl) {
        DriverSelector webbrowser = new DriverSelector(System.getProperty("browser"));
        driver = webbrowser.getBrowser();
        Url domain = new Url(System.getProperty("env"));
        driver.get(domain.createUrl() + JournalUrl);
    }
    
    public String  createUrl(){
        Url domain = new Url(System.getProperty("env"));
        return domain.createUrl();	
    }

    public void navigateUrl(String requiredUrl) {
        DriverSelector webbrowser = new DriverSelector(System.getProperty("browser"));
        driver = webbrowser.getBrowser();
                driver.get(requiredUrl);
    } 
    public WebElement firstname() {
        return driver.findElement(By.xpath("//*[@id='firstname']"));
    }

    public WebElement lastname() {
        return driver.findElement(By.xpath("//*[@id='lastname']"));
    }

    public WebElement email() {
        return driver.findElement(By.xpath("/html/body/div/div[2]/form/div/dl/dd[2]/input"));
    }
    
    public WebElement emailValidation(){
		return driver.findElement(By.cssSelector("strong"));
    }
    
    public WebElement confirmationEmail(){
		return driver.findElement(By.linkText("your nature registration"));
//		return driver.findElement(By.xpath("//*[@id='inboxList']/tbody/tr[2]/td[2]/a"));
    }
    
    public WebElement confirmationEmailLink(){
		return driver.findElement(By.partialLinkText(createUrl()+"nature.com/verify/"));
    }

    public WebElement password() {
        return driver.findElement(By.xpath("/html/body/div/div[2]/form/div/dl/dd[3]/input"));
    }
    
    public WebElement loginPassword(){
		return driver.findElement(By.xpath("//*[@id='login-password']"));
    	
    }
    
    public WebElement loginSubmit(){
    	return driver.findElement(By.xpath("//*[@id='reg-login']/input"));
    }
    
    public WebElement registrationComplete(){
		return driver.findElement(By.xpath("//*[@id='content']/h1"));
    	
    }

    public WebElement confirmpassword() {
        return driver.findElement(By.xpath("/html/body/div/div[2]/form/div/dl/dd[4]/input"));
    }

    public WebElement termsAndconditions() {
        return driver.findElement(By.xpath("//*[@id='agreeToTerms']"));
    }

    public WebElement employer() {
        return driver.findElement(By.xpath("//*[@id='company']"));
    }
    
    public WebElement employerValidation() {
        return driver.findElement(By.xpath("//*[@id='helper-company']/div/div/div/p"));
    }

    public void jobTitle(String position) {
        Select sele = new Select(driver.findElement(By.xpath("//*[@id='position']")));
        sele.selectByVisibleText(position);
    }

    public void industry(String industry) {
        Select sele = new Select(driver.findElement(By.xpath("//*[@id='place']")));
        sele.selectByVisibleText(industry);
    }

    public void areaOfInterest(String field) {
        Select sele = new Select(driver.findElement(By.xpath("//*[@id='field']")));
        sele.selectByVisibleText(field);
    }

    public void specialities(String speciality) {
        Select sele = new Select(driver.findElement(By.xpath("//*[@id='firstspeciality']")));
        sele.selectByVisibleText(speciality);
    }

    public WebElement register() {
        return driver.findElement(By.xpath("//*[@id='content']/input"));
    }

    public WebElement registrationConfirmation() {
        return driver.findElement(By.xpath("//*[@id='content']/h1"));

    }
    
    public WebElement invalidRegistartionErrorMessage(){
		return driver.findElement(By.xpath("//*[@id='content']/span"));
    	
    }
    
    public WebElement orcidLink(){
		return driver.findElement(By.xpath("//*[@id='orcid-register']"));
    }
    
    public WebElement orcidFirstName(){
		return driver.findElement(By.xpath("//*[@id='ng-app']/div/div[1]/div/input"));
    }
    
    public WebElement orcidLastName(){
		return driver.findElement(By.xpath("//*[@id='ng-app']/div/div[2]/div/input"));
    }
    
    public WebElement orcidEmail(){
		return driver.findElement(By.xpath("//*[@id='ng-app']/div/div[3]/div/input"));
    }
    
    public WebElement orcidReEnterEmail(){
		return driver.findElement(By.xpath("//*[@id='ng-app']/div/div[4]/div/input"));
    }
    
    public WebElement orcidPassword(){
		return driver.findElement(By.xpath("//*[@id='ng-app']/div/div[5]/div/input"));
    }
    
    public WebElement orcidConfirmationPassword(){
		return driver.findElement(By.xpath("//*[@id='ng-app']/div/div[6]/div/input"));
    }
    
    public WebElement orcidConsent(){
		return driver.findElement(By.xpath("//*[@id='ng-app']/div/div[9]/div/label[2]/input"));
    }
    
    public WebElement orcidRegister(){
		return driver.findElement(By.xpath("//*[@id='ng-app']/div/div[10]/button"));
    }

    public WebElement orcidRegisterConfirmation(){
		return driver.findElement(By.xpath("html/body/div[3]/div/div[2]/p[2]"));
    }
    
    public WebElement orcidAuthorize(){
		return driver.findElement(By.xpath("//*[@id='confirmationForm']/input[2]"));
    }
    
    public WebElement orcidValidation(){
		return driver.findElement(By.xpath("//*[@id='ng-app']/div/div[3]/div/span[2]/div"));
    }
    
    public WebElement emailPalgrave(){
		return driver.findElement(By.name("email"));
    }
    
    public WebElement confirmEmailPalgrave(){
    	return driver.findElement(By.name("emailConfirm"));
    }
    
    public WebElement passwordPalgrave(){
		return driver.findElement(By.name("password"));
    }
    
    public WebElement confirmPasswordPalgrave(){
		return driver.findElement(By.name("passwordConfirm"));
    }
    
    public WebElement name1Palgrave(){
		return driver.findElement(By.name("firstname"));
    }
    
    public WebElement name2Palgrave(){
		return driver.findElement(By.name("lastname"));
    }

    public void palceOfWorkPalgrave(String workplace) {
        Select sele = new Select(driver.findElement(By.name("place")));
        sele.selectByVisibleText(workplace);
    }
    
    public void positionPalgrave(String position) {
        Select sele = new Select(driver.findElement(By.name("position")));
        sele.selectByVisibleText(position);
    }
    
    public WebElement companyPalgrave(){
		return  driver.findElement(By.name("company"));
    }
    
    public WebElement continuePlagrave(){
		return driver.findElement(By.cssSelector("input[type=\"image\"]"));
    }
    
    public WebElement continuepage2Palgrave(){
		return driver.findElement(By.xpath("html/body/table[2]/tbody/tr[2]/td[3]/table/tbody/tr/td/" +
				"table[2]/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr/td[1]/form/span[2]/table/" +
				"tbody/tr[12]/td/input"));
    }
    
    public WebElement confirmPalgrave(){
		return driver.findElement(By.xpath("html/body/table[2]/tbody/tr[2]/td[3]/table/tbody/tr/td/" +
				"table[2]/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr/td[1]/form/p[2]/" +
				"table/tbody/tr[28]/td/input"));
    }
    
    public WebElement confirmationMessagePalgrave(){
		return driver.findElement(By.xpath("html/body/table[2]/tbody/tr[2]/td[3]/table/tbody/tr/td/" +
				"table[2]/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr/td[1]/p[1]/span[2]/b"));
    }
    
    public WebElement registrationErrorPalgrave(){
		return driver.findElement(By.xpath("html/body/table[2]/tbody/tr[2]/td[3]/table/tbody/tr/td/" +
				"table[2]/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr/td[1]/p[2]"));
    }
    
    public WebElement confirmationEmailLinkPalgrave(){
 		return driver.findElement(By.linkText("http://www.palgrave-journals.com/nams/svc/myaccount"));
     }
    
    
    public WebElement userNameValidationPalgrave(){
		return driver.findElement(By.xpath("html/body/table[2]/tbody/tr[2]/td[3]/table/tbody/tr/td/" +
				"table[2]/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr/td[1]/table[2]/tbody/tr[1]/td[2]"));
    }
    
    public WebElement confirmationEmailPalgrave(){
		return driver.findElement(By.linkText("your palgrave macmillan journals registr"));
    }
    
    public WebElement mailinatorSearch(){
		return driver.findElement(By.xpath("//*[@id='check_inbox_field']"));
    }
    
    public WebElement mailinatorGo(){
		return driver.findElement(By.xpath("//*[@id='checkInbox']/form/input[2]"));
    	
    }
    public WebDriver Browser() {
        return driver;

    }

}
