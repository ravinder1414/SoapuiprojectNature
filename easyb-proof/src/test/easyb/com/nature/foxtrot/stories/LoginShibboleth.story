package com.nature.foxtrot.stories

import java.util.concurrent.TimeUnit;

import com.nature.foxtrot.common.CommonGenericFunctions;
import com.nature.foxtrot.pageobjects.LoginShibbolethPageObject;
import static junit.framework.Assert.*;

LoginShibbolethPageObject loginS = new LoginShibbolethPageObject();
CommonGenericFunctions commonfun = new CommonGenericFunctions();

scenario "Log in via Shibboleth- Nature",{
	given "I navigate to nature.com login page",{
		loginS.navigateShibboleth("nature.com/foxtrot/svc/login");
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	when "click on 'Login Via Institute' link", {
		loginS.ShibbolethLoginViaInstitute().click();
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	then "it navigates to the instituion login page", {
		assertEquals(loginS.createUrl()+"nature.com/nams/svc/institutelogin?target=/",
			loginS.Browser().getCurrentUrl());
		commonfun.captureScreen(loginS.Browser(),"LogInViaShibbolethNature");
	}
	and "Close the Browser",{
		loginS.Browser().close();
	}
}

scenario "Shibboleth institutional login-Select Country-Nature",{
	given "I navigate to nature.com institutional login page",{
		loginS.navigateShibboleth("nature.com/nams/svc/institutelogin");
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	when "select country - United Kingdom and click 'Go' button", {
		loginS.ShibbolethCountry("United Kingdom");
		loginS.ShibbolethGo().click();
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	then "it navigates to the instituion Country login page", {
		assertEquals(loginS.createUrl()+
			"nature.com/nams/svc/institutelogin?target=/index.html",
			loginS.Browser().getCurrentUrl());
	commonfun.captureScreen(loginS.Browser(),
		"ShibbolethInstitutionalloginSelectCountryNature");
	}
	and "Close the Browser",{
		loginS.Browser().close();
	}
}

scenario "Shibboleth institutional login-Search Institution-Nature",{
	given "I navigate to nature.com institutional login page and select country-United Kingdom",{
		loginS.navigateShibboleth("nature.com/nams/svc/institutelogin");
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		loginS.ShibbolethCountry("United Kingdom");
	}
	when "I type 'Brunel University' in search for an institution: and click 'Search' button", {
		loginS.SearchInstitution().sendKeys("Brunel University");
		loginS.SearchButton().click();
	}
	then "'Brunel University' appears in search list", {
		assertEquals("Brunel University",loginS.ShibbolethBrunalUniversity().getText());
		commonfun.captureScreen(loginS.Browser(),
			"ShibbolethInstitutionalloginSearchInstitutionNature");
	}
	and "Close the Browser",{
		loginS.Browser().close();
	}
}

scenario "Shibboleth institutional login- When user clicks an institution name, user is redirected to institution website and then to Nature.com after Istituional Login",{
	given "I navigate to nature.com institutional login page and select country-United Kingdom",{
		loginS.navigateShibboleth("nature.com/nams/svc/institutelogin");
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		loginS.ShibbolethCountry("United Kingdom");
	}
	and "I type 'Brunel University' in search for an institution: and click 'Search' button",{
		loginS.SearchInstitution().sendKeys("Brunel University");
		loginS.SearchButton().click();
	}
	when "'Brunel University' appears in search list and click the 'Brunel University'", {
		loginS.ShibbolethBrunalUniversity().click();
		assertEquals("https://shibsles.brunel.ac.uk/idp/Authn/UserPassword",
				loginS.Browser().getCurrentUrl());
	}
	and "enter 'Brunel University' credentials and click 'continue' button",{
		loginS.BrunalUniversityUserName().sendKeys("lbsrshib");
		loginS.BrunalUniversityPassword().sendKeys("Vn3wt8fh");
		loginS.BrunalUniversityContinue().click();
		loginS.Browser().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	then "it navigates 'nature.com'", {
		assertEquals(loginS.Browser().getCurrentUrl(),
			loginS.createUrl()+"nature.com/index.html");
		commonfun.captureScreen(loginS.Browser(),
			"ShibbolethInstitutionalLoginUserRedirectNature");
	}
	and "Close the Browser",{
		loginS.Browser().close();
	}
}


scenario "Access to a NCB Jouranal/Article using Brunal University Shibboleth valid Credentials-Nature",{
	given "Go to NCB journal and click 'View Full Access'",{
		loginS.navigateShibboleth("nature.com/ncb/journal/v12/n12/full/ncb2120.html#/access");
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		loginS.NCBJournalLogin().click();
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
    and "Click on 'Login via your institution'",{
		loginS.ShibbolethLoginViaInstitute().click();
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		loginS.ShibbolethCountry("United Kingdom");
		loginS.ShibbolethGo().click();
    }
	
	when "Select University/College/Institution from drop down and click select", {
		loginS.ShibbolethBrunalUniversity().click();
		assertEquals(loginS.Browser().getCurrentUrl(),
			"https://shibsles.brunel.ac.uk/idp/Authn/UserPassword");
	}
    
    and "Login with valid credentials",{
        loginS.BrunalUniversityUserName().sendKeys("lbsrshib");
        loginS.BrunalUniversityPassword().sendKeys("Vn3wt8fh");
		loginS.BrunalUniversityContinue().click();
    }
	
	then "This should appear in header 'Full text access provided to Brunal University by Central Library'", {
		loginS.Browser().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		assertEquals(loginS.BrunalUniversityContentAssertion().getText(),"Introduction");
		
//		assertEquals(loginS.BrunalUniversityContentAssertion().getText(),
//			"Nek2A interacts with Hippo pathways components through a SARAH domain");
		commonfun.captureScreen(loginS.Browser(),"ShibbolethAccessForValidSiteAccount");
	}
	and "Close the Browser",{
		loginS.Browser().close();
	}
}

scenario "Access to a NCB Jouranal/Article using Brunal University Shibboleth invalid Credentials-Nature",{
    given "Go to NCB journal and click 'View Full Access'",{
        loginS.navigateShibboleth("nature.com/ncb/journal/v12/n12/full/ncb2120.html#/access");
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		loginS.NCBJournalLogin().click();
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);  
	 }
	
    and "Click on 'Login via your institution'",{
		loginS.ShibbolethLoginViaInstitute().click();
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		loginS.ShibbolethCountry("United Kingdom");
		loginS.ShibbolethGo().click();
    }
	
    when "Select University/College/Institution from drop down and click select", {
		loginS.ShibbolethBrunalUniversity().click();
		assertEquals(loginS.Browser().getCurrentUrl(),
			"https://shibsles.brunel.ac.uk/idp/Authn/UserPassword");
    }
    
    and "Login with invalid credentials",{
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		loginS.BrunalUniversityUserName().sendKeys("lbsrshib1");
		loginS.BrunalUniversityPassword().sendKeys("Vn3wt8fh1");
		loginS.BrunalUniversityContinue().click();
    }
    
    then "Access page should appear to user if license for Nature is not associated with his site account'", {
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		assertEquals(loginS.BrunalUniversityWrongCredentials().getText(),
			"Credentials not recognised.");
		commonfun.captureScreen(loginS.Browser(), 
			"ShibbolethAccessForInvalidSiteAccount");
    }
	and "Close the Browser",{
		loginS.Browser().close();
	}
}

//scenario "Log in via Shibboleth-Palgrave Journals",{
//	given "I navigate to palgrave-journals.com login page",{
//		loginS.navigateShibboleth("palgrave-journals.com/foxtrot/svc/login");
//		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//	}
//	when "click on 'Login Via Institute' link", {
//		loginS.ShibbolethLoginViaInstitute().click();
//		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//	}
//	then "it navigates to the Palgrave instituion login page", {
//		assertEquals(loginS.Browser().getCurrentUrl(),
//			loginS.createUrl()+"palgrave-journals.com/nams/svc/institutelogin?target=/");
//		commonfun.captureScreen(loginS.Browser(),
//			"LoginViaShibbolethPalgraveJournals");
//	}
//	and "Close the Browser",{
//		loginS.Browser().close();
//	}
//}
//
//scenario "Shibboleth institutional login-Select Country-Palgrave Journals",{
//	given "I navigate to palgrave-journals.com login page",{
//		loginS.navigateShibboleth("palgrave-journals.com/nams/svc/institutelogin");
//		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//	}
//	when "select country - United Kingdom and click 'Go' button", {
//		loginS.ShibbolethCountry("United Kingdom");
//		loginS.ShibbolethGo().click();
//		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//	}
//	then "it navigates to the institution login page", {
//		assertEquals(loginS.Browser().getCurrentUrl(),
//			loginS.createUrl()+
//			"palgrave-journals.com/nams/svc/institutelogin?target=\$request.getParameter(");
//		commonfun.captureScreen(loginS.Browser(),
//			"ShibbolethInstitutionalloginSelectCountryPalgraveJournals");
//	}
//	and "Close the Browser",{
//		loginS.Browser().close();
//	}
//}
//
//scenario "Shibboleth institutional login-Search Institution-Palgrave Journals",{
//	given "I navigate to nature.com institutional login page and select country-United Kingdom",{
//		loginS.navigateShibboleth("palgrave-journals.com/nams/svc/institutelogin");
//		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//		loginS.ShibbolethCountry("United Kingdom");
//	}
//	when "I type 'Brunel University' in search for an institution: and click 'Search' button", {
//		loginS.SearchInstitution().sendKeys("Brunel University");
//		loginS.SearchButton().click();
//	}
//	then "Brunel University' appears in search list", {
//		assertEquals(loginS.ShibbolethBrunalUniversity().getText(),
//			"Brunel University");
//		commonfun.captureScreen(loginS.Browser(),
//			"ShibbolethInstitutionalloginSearchInstitutionPalgraveJournals");
//	}
//	and "Close the Browser",{
//		loginS.Browser().close();
//	}
//}
//
//scenario "Shibboleth institutional login- When user clicks an institution name, user is redirected to institution website and then to Palgrave-journals.com after Istituional Login",{
//	given "I navigate to nature.com institutional login page and select country-United Kingdom",{
//		loginS.navigateShibboleth("palgrave-journals.com/nams/svc/institutelogin");
//		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//		loginS.ShibbolethCountry("United Kingdom");
//	}
//	and "I type 'Brunel University' in search for an institution: and click 'Search' button",{
//		loginS.SearchInstitution().sendKeys("Brunel University");
//		loginS.SearchButton().click();
//	}
//	when "'Brunel University' appears in search list and click the 'Brunel University'", {
//		loginS.ShibbolethBrunalUniversity().click();
//		assertEquals("https://shibsles.brunel.ac.uk/idp/Authn/UserPassword",
//				loginS.Browser().getCurrentUrl());
//	}
//	and "enter 'Brunel University' credentials and click 'continue' button",{
//		loginS.BrunalUniversityUserName().sendKeys("lbsrshib");
//		loginS.BrunalUniversityPassword().sendKeys("Vn3wt8fh");
//		loginS.BrunalUniversityContinue().click();
//		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//	}
//	then "it navigates Palgrave-journals.com", {
//		assertEquals(loginS.WelecomeToPalgrave().getText(),
//			"Welcome to the Palgrave Macmillan Journals site");
//	}
//	and "Close the Browser",{
//		loginS.Browser().close();
//	}
//}
//
//scenario "Access to a BE Jouranal/Article using Brunal University Shibboleth valid Credentials-Palgrave Journals",{
//	given "Go to BE journal and click 'View Full Access'",{
//		loginS.navigateShibboleth("palgrave-journals.com/be/journal/" +
//			"v45/n1/full/be200938a.html");
//        loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//	}
//	and "Click on 'Login via your institution'",{
//		loginS.beShibbolethLoginViaInstituteLink().click();
//		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//	}
//	when "Select University/College/Institution from drop down and click select", {
//		loginS.ShibbolethCountry("United Kingdom");
//		loginS.SearchInstitution().sendKeys("Brunel University");
//		loginS.SearchButton().click();
//		loginS.BrunelUniversityLinkPalgrave().click();
//		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//		
//		assertEquals(loginS.Browser().getCurrentUrl(),
//			"https://shibsles.brunel.ac.uk/idp/Authn/UserPassword");
//	}
//	and "Login with valid credentials",{
//		loginS.BrunalUniversityUserName().sendKeys("lbsrshib");
//		loginS.BrunalUniversityPassword().sendKeys("Vn3wt8fh");
//		loginS.BrunalUniversityContinue().click();
//		
//		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//	}
//	then "Full access should be granted to Journal", {
//		
//		assertEquals(loginS.beContentAssertion().getText(),"The author wishes to thank two referees for their thoughtful suggestions. The views expressed in this paper are those of the author. They do not necessarily represent those of Chmura Economics & Analytics.");
//
////		assertEquals(loginS.Browser().getCurrentUrl(),
////			loginS.createUrl()+
////			"palgrave-journals.com/be/journal/v45/n1/full/be200938a.html");
//	commonfun.captureScreen(loginS.Browser(),
//		"LoginShibbolethToBEJournalsViaBrunalUniversityPalgraveJournals");
//	}
//	and "Close the Browser",{
//		loginS.Browser().close();
//	}
//}
//
//scenario "Access to a BE Jouranal/Article using Brunal University Shibboleth invalid Credentials-Palgrave Journals",{
//	given "Go to BE journal and click 'View Full Access'",{
//		loginS.navigateShibboleth("palgrave-journals.com/be/journal/" +
//			"v45/n1/full/be200938a.html");
//		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//	}
//	and "Click on 'Login via your institution'",{
//		loginS.beShibbolethLoginViaInstituteLink().click();
//		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//	}
//	when "Select University/College/Institution from drop down and click select", {
//		loginS.ShibbolethCountry("United Kingdom");
//		loginS.SearchInstitution().sendKeys("Brunel University");
//		loginS.SearchButton().click();
//		loginS.BrunelUniversityLinkPalgrave().click();
//		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//		
//		assertEquals(loginS.Browser().getCurrentUrl(),
//			"https://shibsles.brunel.ac.uk/idp/Authn/UserPassword");
//	}
//	and "Login with invalid credentials",{
//		loginS.BrunalUniversityUserName().sendKeys("lbsrshib1");
//		loginS.BrunalUniversityPassword().sendKeys("Vn3wt8fh1");
//		loginS.BrunalUniversityContinue().click();
//		
//		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//	}
//	then "Brunel University login should be unsuccessful", {
//		assertEquals(loginS.BrunalUniversityWrongCredentials().getText(),
//			"Credentials not recognised.");
//	commonfun.captureScreen(loginS.Browser(),
//		"LoginShibbolethToBEJournalsViaBrunalUniversityWithInvalidCredentialsPalgraveJournals");
//	}
//	and "Close the Browser",{
//		loginS.Browser().quit();
//	}
//}
//
