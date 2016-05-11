package com.nature.foxtrot;

import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.*;

import com.nature.foxtrot.common.CommonGenericFunctions;
import com.nature.foxtrot.pageobjects.LoginShibbolethPageObject;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class LoginShibbolethStepDefinition {
	
	LoginShibbolethPageObject loginS ;
	CommonGenericFunctions commonfun ;
	
	
	public LoginShibbolethStepDefinition(){
		loginS = new LoginShibbolethPageObject();
		commonfun = new CommonGenericFunctions();
	}
//1	
	@Given("^I navigate to nature.com login page$")
    public void I_navigate_to_nature_login_page(){
		loginS.navigateShibboleth("nature.com/foxtrot/svc/login");
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
//1 & //7
		@When("^click on 'Login Via Institute' link$")
	    public void click_on_login_via_institute(){
			loginS.ShibbolethLoginViaInstitute().click();
			loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

//1	
		@Then("^it navigates to the instituion login page$")
	    public void It_navigates_to_the_institution_login_page(){
			assertEquals(loginS.createUrl()+"nature.com/nams/svc/institutelogin?target=/",
					loginS.Browser().getCurrentUrl());
				commonfun.captureScreen(loginS.Browser(),"LogInViaShibbolethNature");
				loginS.Browser().close();
	    }		
//2	
	@Given("^I navigate to nature.com institutional login page$")
    public void I_navigate_to_nature_intistutional_login_page(){
		loginS.navigateShibboleth("nature.com/nams/svc/institutelogin");
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
//2
		@When("^select country - United Kingdom and click 'Go' button$")
	    public void select_country_click_go(){
			loginS.ShibbolethCountry("United Kingdom");
			loginS.ShibbolethGo().click();
			loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	   }
//2
		@Then("^it navigates to the instituion Country login page$")
	    public void It_navigates_to_the_country_institution_login_page(){
			assertEquals(loginS.createUrl()+
					"nature.com/nams/svc/institutelogin?target=/index.html",
					loginS.Browser().getCurrentUrl());
			commonfun.captureScreen(loginS.Browser(),
				"ShibbolethInstitutionalloginSelectCountryNature");
			loginS.Browser().close();
	    }
//3 & //4(1)
	@Given("^I navigate to nature.com institutional login page and select country-United Kingdom$")
    public void I_navigate_to_nature_intistutional_login_page_select_country(){
		loginS.navigateShibboleth("nature.com/nams/svc/institutelogin");
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		loginS.ShibbolethCountry("United Kingdom");
	}

//3
		@When("^I type University name in search for an institution: and click 'Search' button$")
	    public void I_type_University_name_in_search_box(){
			loginS.SearchInstitution().sendKeys("Brunel University");
			loginS.SearchButton().click();
		}
//3  //9
		@Then("^'Brunel University' appears in search list$")
	    public void Brunel_University_appears_in_search(){
			assertEquals("Brunel University",loginS.ShibbolethBrunalUniversity().getText());
			commonfun.captureScreen(loginS.Browser(),
				"ShibbolethInstitutionalloginSearchInstitutionNature");
			loginS.Browser().close();
	    }		

//4	(2) //10(2)
	@Given("^I type 'Brunel University' in search for an instite and click 'Search' button$")
    public void Brunel_University_in_search_click_search_button(){
		loginS.SearchInstitution().sendKeys("Brunel University");
		loginS.SearchButton().click();
	}

//4 & //10
		@When("^'Brunel University' appears in search list and click the 'Brunel University'$")
	    public void click_Brunel_University_in_search_list(){
			loginS.ShibbolethBrunalUniversity().click();
			assertEquals("https://shibsles.brunel.ac.uk/idp/Authn/UserPassword",
					loginS.Browser().getCurrentUrl());
		}
//4(2)	//10(2)	
		@When("^enter 'Brunel University' credentials and click 'continue' button$")
	    public void enter_Brunel_University_credentials(){
			loginS.BrunalUniversityUserName().sendKeys("lbsrshib");
			loginS.BrunalUniversityPassword().sendKeys("Vn3wt8fh");
			loginS.BrunalUniversityContinue().click();
			loginS.Browser().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}	

//4
		@Then("^it navigates 'nature.com'$")
	    public void it_navigates_to_nature(){
			assertEquals(loginS.Browser().getCurrentUrl(),
					loginS.createUrl()+"nature.com/index.html");
				commonfun.captureScreen(loginS.Browser(),
					"ShibbolethInstitutionalLoginUserRedirectNature");
			loginS.Browser().close();
	    }
	
//5(1) & //6(1)	
		@Given("^I go to NCB journal and click View Full Access$")
		    public void ncb_journal_view_full_access() throws InterruptedException{
			loginS.navigateShibboleth("nature.com/ncb/journal/v12/n12/full/ncb2120.html#/access");
			loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			loginS.NCBJournalLogin().click();
			loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
// 5(2) & //5(2)	
	@Given("^Click on 'Login via your institution'$")
	    public void login_via_your_instituion(){
		loginS.ShibbolethLoginViaInstitute().click();
		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		loginS.ShibbolethCountry("United Kingdom");
		loginS.ShibbolethGo().click();
	}
//5(1) & //6(1)
	@When("^I select University/College/Institution from drop down and click select$")
    public void select_University_from_drop_down(){
	loginS.ShibbolethBrunalUniversity().click();
	assertEquals("https://shibsles.brunel.ac.uk/idp/Authn/UserPassword",loginS.Browser().getCurrentUrl());
    }
//5	(2)//12(2)
	@When("^Login with invalid credentials$")
    public void Login_with_invalid_credentials(){
	loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	loginS.BrunalUniversityUserName().sendKeys("lbsrshib1");
	loginS.BrunalUniversityPassword().sendKeys("Vn3wt8fh1");
	loginS.BrunalUniversityContinue().click();
   }	


//5
	@Then("^Brunel University login should be unsuccessful$")
    public void access_page_should_appear_to_user(){
	loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	assertEquals("Credentials not recognised.",
			loginS.BrunalUniversityWrongCredentials().getText());
	loginS.Browser().quit();
    }
//6(2) // 11(2)
	@When("^Login with valid username and password$")
    public void Login_with_valid_credentials(){
    loginS.BrunalUniversityUserName().sendKeys("lbsrshib");
    loginS.BrunalUniversityPassword().sendKeys("Vn3wt8fh");
	loginS.BrunalUniversityContinue().click();
}
//6
	@Then("^This should appear in header Full text access provided to Brunal University by Central Library$")
    public void confirmation_message(){
	loginS.Browser().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	commonfun.captureScreen(loginS.Browser(),"Shibboleth Access for  valid site account");
	assertEquals(loginS.BrunalUniversityContentAssertion().getText(),"Introduction");
//	assertEquals("Nek2A interacts with Hippo pathways components through a SARAH domain",
//			loginS.BrunalUniversityContentAssertion().getText());
	commonfun.captureScreen(loginS.Browser(),"Shibboleth Access for  valid site account");
	loginS.Browser().close();
    }	
	
}