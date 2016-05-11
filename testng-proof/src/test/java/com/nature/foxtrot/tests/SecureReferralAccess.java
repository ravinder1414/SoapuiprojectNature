package com.nature.foxtrot.tests;

import static junit.framework.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.WebDriver;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.nature.foxtrot.pageobjects.SecureReferralAccessPageObject;
import com.nature.foxtrot.common.CommonGenericFunctions;

public class SecureReferralAccess {

    SecureReferralAccessPageObject raccess = new SecureReferralAccessPageObject();
    CommonGenericFunctions commonfun = new CommonGenericFunctions();

    @Test(description = "User visits site through a referral link from a society website page, where the domain of the society webpage URL matches the domain of a URL in our referral database.")
    public void SecureReferralAccessINFORMS() throws InterruptedException {
        raccess.navigateSociety();
        raccess.Browser().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        raccess.UserName().sendKeys("sammons1027536");
        raccess.Password().sendKeys("INFORMS");
        raccess.Submit().click();
        raccess.Browser().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        
        assertEquals("Niki Sammons",raccess.UserConfirmation().getText());  
        
        raccess.FindReasearchandPublications().click();
        Thread.sleep(5000L);
        raccess.IAOROnlineDatabaseImage().click();
		raccess.Browser().manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        assertEquals("https://www.informs.org/Find-Research-Publications/" +
        		"Searchable-Databases/International-Abstracts-in-Operations-" +
        		"Research-IAOR-Database",raccess.Browser().getCurrentUrl());

//		raccess.ProceedDirectly().click();
		raccess.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		raccess.clickTheLinkAndFocusOnANewWindow(raccess.Browser());

		raccess.Browser().manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		assertEquals("International Abstracts in Operations Research", 
				raccess.verificationMessage().getText());
        commonfun.captureScreen(raccess.Browser());
        raccess.switchToMainWindow(raccess.Browser());
        raccess.Logout();

        raccess.Browser().quit();
    }

}
