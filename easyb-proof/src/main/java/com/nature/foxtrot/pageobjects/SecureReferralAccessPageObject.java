package com.nature.foxtrot.pageobjects;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.nature.foxtrot.common.DriverSelector;

public class SecureReferralAccessPageObject {
    WebDriver driver;
    //here we make a fields where id's will store
    public static Set<?> setOfOldHandles = null;
    public static Set<?> setOfNewHandles = null;

    public void navigateSociety() {
        DriverSelector webbrowser = new DriverSelector(System.getProperty("browser"));
        driver = webbrowser.getBrowser();
        driver.get("https://www.informs.org/user/login");
    }

    public WebElement memberLogin() {
        return driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[2]/ul/li[6]/a"));
    }

    public WebElement UserName() {
        return driver.findElement(By.xpath("//*[@id='id1']"));
    }

    public WebElement Password() {
        return driver.findElement(By.xpath("//*[@id='id2']"));
    }

    public WebElement Submit() {
		return driver.findElement(By.name("LoginButton"));
//        return driver.findElement(By.xpath("html/body/div[1]/div/div[4]/div[4]/div/div[2]/form/div[4]/input"));
    }

    public WebElement UserConfirmation() {
		return driver.findElement(By.linkText("Niki Sammons"));
//        return driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[2]/ul/li[1]/strong/a"));
                                            
    }

    public WebElement FindReasearchandPublications() {
        return driver.findElement(By.xpath(".//*[@id='find']/a"));
    }

    public WebElement SearchableDatabase() {
        return driver.findElement(By.xpath("html/body/div[1]/div/div[5]/div[3]/div[1]/ul/li[4]/a"));
    }

    public WebElement IAOROnlineDatabase() {
        return driver.findElement(By.xpath("//*[@id='pagerdiv']/div[2]/p[4]/a"));
    }
    
    public WebElement IAOROnlineDatabaseImage() {
		return  driver.findElement(By.xpath("//img[contains(@src,'https://1-ps.googleusercontent.com/s/www.informs.org/var/ezflow_site/storage/images/media/iol/images/informs-publications/pubs-web-buttons/iaor-database/1073245-1-eng-US/xIAOR-Database_medium.jpg.pagespeed.ic.k-MnIk0IXh.jpg')]"));
//        return driver.findElement(By.xpath("html/body/div[1]/div/div[4]/div[5]/div[2]/div[3]/div/p/a[2]/img"));
    	
    }

//    public WebElement ProceedDirectly() {
//        return driver.findElement(By.xpath("//*[@id='pagerdiv']/h3/a"));
//    }

    public void clickTheLinkAndFocusOnANewWindow(WebDriver driver) {

        saveOldHandles(driver); // here we use method to save all window id before click 

        driver.findElement(By.xpath("//*[@id='pagerdiv']/h3/a")).click(); // here we find our button and click on it

        saveNewHandles(driver); // here we save all window id after click 

        ifNewWindowOccursFocusOnIt(driver); // this method selects and focuses on a new window if it really occurs, and if not it won't do anything (because sometimes we expect opening in new window but it opens in current window) code I'll show further

    }

    public WebElement verificationMessage() {
        return driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/h1/span"));
    }
    
    public WebElement Logout() {
		return driver.findElement(By.linkText("Logout"));
//        return driver.findElement(By.xpath("html/body/div[1]/div/div[3]/div[2]/ul/li[7]/a"));
    }
    
    public static void saveOldHandles(WebDriver driver) {
        if (setOfOldHandles != null) {
            setOfOldHandles.clear();
        }
        setOfOldHandles = driver.getWindowHandles(); // here we save id's of windows
    }

    // just the same for new set of id's

    public static void saveNewHandles(WebDriver driver) {
        if (setOfNewHandles != null) {
            setOfNewHandles.clear();
        }
        setOfNewHandles = driver.getWindowHandles();
    }

    // here is the code of method which decides whether to focus on a new window or not

    public static void ifNewWindowOccursFocusOnIt(WebDriver driver) {
        if (setOfNewHandles != null) {
            setOfNewHandles.removeAll(setOfOldHandles); // this method removeAll() take one set and puts it in another set and if there are same positions it will erase them and leaves only that are not equals
        }

        if (!setOfNewHandles.isEmpty()) {
            String newWindowHandle = (String) setOfNewHandles.iterator().next(); // here IF we have new window it will shift on it
            driver.switchTo().window(newWindowHandle);
        }

        else {

            System.out
                    .println("setOfNewHandles is null. Can't compare old and new handles. " +
                    		"New handle may have not enough time to load and save. " +
                    		"Maybe you should add some time to load new window by adding Thread.Sleep(3000); - " +
                    		"wait for 3 second ");
        }
    }

    public void switchToMainWindow(WebDriver driver) {
        if (!setOfNewHandles.isEmpty()) {
            String oldWindowHandle = (String) setOfOldHandles.iterator().next(); // here IF we have new window it will shift on it
            driver.switchTo().window(oldWindowHandle);
        }

    }

    public WebDriver Browser() {
        return driver;

    }
}
