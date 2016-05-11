package com.nature.foxtrot;

import static junit.framework.Assert.*;

import java.util.concurrent.TimeUnit;

import com.nature.foxtrot.common.CommonGenericFunctions;
import com.nature.foxtrot.pageobjects.LoginAthensPageObject;

//import cucumber.annotation.After;
//import cucumber.annotation.After;
//import cucumber.annotation.Before;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class LoginAthensStepDefinition {
	
	LoginAthensPageObject loginA;
	CommonGenericFunctions commonfun ;

	public LoginAthensStepDefinition() {
		    loginA = new LoginAthensPageObject();
	        commonfun = new CommonGenericFunctions();
	    }
	 
	 @Given("^Login valid credentials-Username and Password$")
	    public void Login_valid_credentials()  {
		 loginA.navigateAthens("nature.com/nature/journal/v446/n7131/full/446009a.html");
		  loginA.Browser().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		  loginA.LoginViaAthensNCB().click();
		  loginA.Browser().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		  loginA.OpenAthensUserName().sendKeys("npggawekar");
		  loginA.OpenAthensPassword().sendKeys("abc123");
	    }
	 
	 @Given("^Login valid credentials-Username and Password for Nature$")
	    public void Login_valid_credentials_Nature() {
		  loginA.navigateAthens("nature.com/foxtrot/svc/login");
		  loginA.Browser().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		  loginA.LoginViaAthens().click();
		  loginA.Browser().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		  loginA.OpenAthensUserName().sendKeys("npggawekar");
		  loginA.OpenAthensPassword().sendKeys("abc123");
	    }
	 
	 @When("^I click Athens submit button$")
	    public void I_click_Athenssubmit_button() throws Throwable {
			loginA.OpenAthensSubmit().click();
			commonfun.captureScreen(loginA.Browser(),"Login OpenAthens with valid credentials");
			loginA.Browser().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	    }
	 
	 @Then("^Full text access should be provided for article/journal to user$")
	    public void Full_text_access() throws Throwable {
//			assertEquals(loginA.OpenAthensValidation().getText(),"Full text access provided to NPG test\nby NWS");
			assertEquals(loginA.OpenAthensContentValidation().getText(),"In just over " +
					"a decade, two major ice shelves have collapsed on the eastern side " +
					"of the Antarctic peninsula, uncovering a part of the sea floor that " +
					"had not seen sunlight for several thousand years. A ten-week expedition " +
					"that ended in late January has shed light on the biology of these waters, " +
					"and has recovered samples of some 1,000 species from the region, several of which " +
					"may be new to science.");

			commonfun.captureScreen(loginA.Browser(),"Login OpenAthens with valid credentials");
			loginA.Browser().close();
	    }
	 
	 @Then("^User should be redirected to nature.com$")
	    public void User_Should_Be_Redirected_Nature() throws Throwable {
		    assertEquals(loginA.Browser().getCurrentUrl(),loginA.createUrl()+"nature.com/");
			commonfun.captureScreen(loginA.Browser(),"LoginViaAthens-Nature");
			loginA.Browser().close();
	    }
	 
	 
}
