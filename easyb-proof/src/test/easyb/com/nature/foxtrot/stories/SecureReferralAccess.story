package com.nature.foxtrot.stories

import com.nature.foxtrot.common.CommonGenericFunctions;
import com.nature.foxtrot.pageobjects.SecureReferralAccessPageObject;
import java.util.concurrent.TimeUnit;
import static junit.framework.Assert.*;


SecureReferralAccessPageObject raccess =new SecureReferralAccessPageObject();
CommonGenericFunctions commonfun = new CommonGenericFunctions();


//before "SetupBrowser", {
//    given "Browser initiated and go to Society Page", {
//        raccess.navigateSociety();
//    }
//}

scenario "User visits site through a referral link from a society website page, where the domain of the society webpage URL matches the domain of a URL in our referral database.",{
	given "User visits the Society Page and click on 'Member Login'",{
        raccess.navigateSociety();
        raccess.Browser().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
 	}
    
    and "login should be successful when login with valid credentials ",{
        raccess.UserName().sendKeys("sammons1027536");
        raccess.Password().sendKeys("INFORMS");
        raccess.Submit().click();
        raccess.Browser().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        
        assertEquals("Niki Sammons",raccess.UserConfirmation().getText());  
    }
	
	when "Navigate on the fourth menu Find Research & Publications > Searchable Database", {
        raccess.FindReasearchandPublications().click();
//        Thread.sleep(5000L);
	}
    
    and "Click on  second link IAOR Online Database. It will then navigate to https://www.informs.org/Find-Research-Publications/Searchable-Databases/International-Abstracts-in-Operations-Research-IAOR-Database",{
        raccess.IAOROnlineDatabaseImage().click();
		raccess.Browser().manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        assertEquals("https://www.informs.org/Find-Research-Publications/" +
        		"Searchable-Databases/International-Abstracts-in-Operations-" +
        		"Research-IAOR-Database",raccess.Browser().getCurrentUrl());
			raccess.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
	
	then "Click on link proceed directly to International Abstracts in O.R. Online which will be the URL mentioned above which should goto IAOR journal and try searching a term.", {
		raccess.clickTheLinkAndFocusOnANewWindow(raccess.Browser());
		raccess.Browser().manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
	}
    and "You should get some results instead of navigating to the login page.",{
		raccess.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		assertEquals("International Abstracts in Operations Research", 
				raccess.verificationMessage().getText());
			commonfun.captureScreen(raccess.Browser(),"SecureReferralAccess");
        raccess.switchToMainWindow(raccess.Browser());
        raccess.Logout();
        
    }
}

after "stop Browser" , {
    then "Browser is closed", {
        raccess.Browser().quit();
    }
}