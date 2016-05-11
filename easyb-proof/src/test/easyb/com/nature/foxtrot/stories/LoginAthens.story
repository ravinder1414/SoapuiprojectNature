package com.nature.foxtrot.stories


import java.util.concurrent.TimeUnit;

import com.nature.foxtrot.common.CommonGenericFunctions;
import com.nature.foxtrot.pageobjects.LoginAthensPageObject;
import static junit.framework.Assert.*;

 LoginAthensPageObject loginA = new LoginAthensPageObject();
 CommonGenericFunctions commonfun = new CommonGenericFunctions();

scenario "OpenAthens - single sign on for institutions",{
	given "Login valid credentials-Username and Password",{
		loginA.navigateAthens("nature.com/nature/journal/v446/n7131/full/446009a.html");
		loginA.Browser().manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		loginA.LoginViaAthens().click();
		loginA.Browser().manage().timeouts()
				.implicitlyWait(10, TimeUnit.SECONDS);
		loginA.OpenAthensUserName().sendKeys("npggawekar");
		loginA.OpenAthensPassword().sendKeys("abc123");
	}
	
	when "I click submit button", {
		loginA.OpenAthensSubmit().click();
		loginA.Browser().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
//		Thread.sleep(5000L);
	}
	
	then "Full text access should be provided for article/journal to user ", {
//		Thread.sleep(5000L);
		loginA.Browser().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
//		assertEquals(loginA.OpenAthensValidation().getText(),"Full text access provided to NPG test\nby NWS");
		assertEquals(loginA.OpenAthensContentValidation().getText(),"In just over " +
			"a decade, two major ice shelves have collapsed on the eastern side " +
			"of the Antarctic peninsula, uncovering a part of the sea floor that " +
			"had not seen sunlight for several thousand years. A ten-week expedition " +
			"that ended in late January has shed light on the biology of these waters, " +
			"and has recovered samples of some 1,000 species from the region, several of which " +
			"may be new to science.");

		commonfun.captureScreen(loginA.Browser(),
			"OpenAthens-SingleSignOnForInstitutions");
	}
	and "Close the Browser",{
		loginA.Browser().close();
	}
}

scenario "Login via Athens-Nature",{
	given "Login valid credentials-Username and Password",{
		loginA.navigateAthens("nature.com/foxtrot/svc/login");
		loginA.Browser().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		loginA.LoginViaAthens().click();
		loginA.Browser().manage().timeouts()
				.implicitlyWait(10, TimeUnit.SECONDS);
		
		loginA.OpenAthensUserName().sendKeys("npggawekar");
		loginA.OpenAthensPassword().sendKeys("abc123");
	}
	
	when "I click submit button", {
		loginA.OpenAthensSubmit().click();
		loginA.Browser().manage().timeouts()
		.implicitlyWait(5, TimeUnit.SECONDS);
		assertEquals(loginA.Browser().getCurrentUrl(),loginA.createUrl()+"nature.com/");
	}
	
	then "User should be redirected to nature.com ", {
		assertEquals(loginA.Browser().getCurrentUrl(),loginA.createUrl()+"nature.com/");
		commonfun.captureScreen(loginA.Browser(),"LoginViaAthens-Nature");
	}
	and "Close the Browser",{
		loginA.Browser().close();
	}
}

//scenario "Login via Athens-Palgrave Journals",{
//	given "Login valid credentials-Username and Password",{
//		loginA.navigateAthens("palgrave-journals.com/foxtrot/svc/login");
//		loginA.Browser().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
//		loginA.LoginViaAthens().click();
//		loginA.Browser().manage().timeouts()
//				.implicitlyWait(10, TimeUnit.SECONDS);
//		
//		loginA.OpenAthensUserName().sendKeys("npggawekar");
//		loginA.OpenAthensPassword().sendKeys("abc123");
//	}
//	
//	when "I click submit button", {
//		loginA.OpenAthensSubmit().click();
//		loginA.Browser().manage().timeouts()
//		.implicitlyWait(5, TimeUnit.SECONDS);
//	}
//	
//	then "User should be redirected to Palgrave-Journals.com", {
//		assertEquals(loginA.Browser().getCurrentUrl(),
//				loginA.createUrl()+"palgrave-journals.com/nams/svc/athenslogin");
//		commonfun.captureScreen(loginA.Browser(),"LoginViaAthens-PalgraveJournals");
//	}
//	and "Close the Browser",{
//		loginA.Browser().quit();
//	}
//}
