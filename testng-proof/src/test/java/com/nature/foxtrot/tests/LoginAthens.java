package com.nature.foxtrot.tests;

import static org.testng.Assert.*;

import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.nature.foxtrot.pageobjects.LoginAthensPageObject;
import com.nature.foxtrot.common.CommonGenericFunctions;

public class LoginAthens {

	LoginAthensPageObject loginA = new LoginAthensPageObject();
	CommonGenericFunctions commonfun = new CommonGenericFunctions();
	
	@Test(description="Login via Athens-Nature")
	public void 	LoginViaAthensNature(){
		loginA.navigateAthens("nature.com/foxtrot/svc/login");
		loginA.Browser().manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		loginA.LoginViaAthens().click();
		loginA.Browser().manage().timeouts()
				.implicitlyWait(10, TimeUnit.SECONDS);
		
		loginA.OpenAthensUserName().sendKeys("npggawekar");
		loginA.OpenAthensPassword().sendKeys("abc123");
		loginA.OpenAthensSubmit().click();
		assertEquals(loginA.Browser().getCurrentUrl(),loginA.createUrl()+"nature.com/");
		commonfun.captureScreen(loginA.Browser());
	}
	
	@Test(description="	Login via Athens-Palgrave Journals")
	public void 	LoginViaAthensPalgraveJournals(){
		loginA.navigateAthens("palgrave-journals.com/foxtrot/svc/login");
		loginA.Browser().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		loginA.LoginViaAthens().click();
		loginA.Browser().manage().timeouts()
				.implicitlyWait(10, TimeUnit.SECONDS);
		
		loginA.OpenAthensUserName().sendKeys("npggawekar");
		loginA.OpenAthensPassword().sendKeys("abc123");
		loginA.OpenAthensSubmit().click();
		
		assertEquals(loginA.Browser().getCurrentUrl(),
				loginA.createUrl()+"palgrave-journals.com/nams/svc/athenslogin");
		commonfun.captureScreen(loginA.Browser());
	}
	
	
	@Test(description = "Login should be successful " +
			"when login with valid OpenAthens credentials")
	public void AthensLoginNature() {
		loginA.navigateAthens("nature.com/nature/journal/v446/n7131/full/446009a.html");
		loginA.Browser().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		loginA.LoginViaAthens().click();
		loginA.Browser().manage().timeouts()
				.implicitlyWait(10, TimeUnit.SECONDS);
		loginA.OpenAthensUserName().sendKeys("npggawekar");
		loginA.OpenAthensPassword().sendKeys("abc123");
		loginA.OpenAthensSubmit().click();

		assertEquals(loginA.OpenAthensContentValidation().getText(),"In just over " +
				"a decade, two major ice shelves have collapsed on the eastern side " +
				"of the Antarctic peninsula, uncovering a part of the sea floor that " +
				"had not seen sunlight for several thousand years. A ten-week expedition " +
				"that ended in late January has shed light on the biology of these waters, " +
				"and has recovered samples of some 1,000 species from the region, several of which " +
				"may be new to science.");
//		assertEquals(loginA.OpenAthensValidation().getText(),
//				"Full text access provided to NPG test\nby NWS");
		commonfun.captureScreen(loginA.Browser());
	}

	@AfterMethod
	public void QuitBrowser() {
		loginA.Browser().quit();
	}

}
