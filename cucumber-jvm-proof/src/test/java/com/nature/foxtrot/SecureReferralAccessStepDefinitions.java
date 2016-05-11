package com.nature.foxtrot;

import static junit.framework.Assert.*;

import java.util.concurrent.TimeUnit;

import com.nature.foxtrot.common.CommonGenericFunctions;
import com.nature.foxtrot.pageobjects.SecureReferralAccessPageObject;

//import cucumber.annotation.After;
//import cucumber.annotation.Before;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class SecureReferralAccessStepDefinitions {

    SecureReferralAccessPageObject raccess;
    CommonGenericFunctions commonfun ;

    public SecureReferralAccessStepDefinitions() {
        raccess = new SecureReferralAccessPageObject();
        commonfun = new CommonGenericFunctions();
    }

    @Given("^I visit the Society Page and click on Member Login$")
    public void I_visit_the_society_page(){
        raccess.navigateSociety();
        raccess.Browser().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Given("^submit the login form with a valid Username and Password$")
    public void I_submit_the_login_form_with_valid_credentials() {
    	raccess.Browser().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        raccess.UserName().sendKeys("sammons1027536");
        raccess.Password().sendKeys("INFORMS");
        raccess.Submit().click();
        raccess.Browser().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        
        assertEquals("Niki Sammons",raccess.UserConfirmation().getText());       
    }

    @When("^I Navigate on the fourth menu Find Research & Publications > Searchable Database$")
    public void I_navigate_the_fourth_menu() throws Throwable {
        raccess.FindReasearchandPublications().click();
//        raccess.Browser().manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        Thread.sleep(5000L);
    }

    @When("^click on  second link IAOR Online Database. It will then navigate to https://www.informs.org/Find-Research-Publications/Searchable-Databases/International-Abstracts-in-Operations-Research-IAOR-Database$")
    public void Click_second_link_IAOR_online() throws Throwable {
//		raccess.Browser().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    	Thread.sleep(5000L);
        raccess.IAOROnlineDatabaseImage().click();
		raccess.Browser().manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        assertEquals("https://www.informs.org/Find-Research-Publications/" +
        		"Searchable-Databases/International-Abstracts-in-Operations-" +
        		"Research-IAOR-Database",raccess.Browser().getCurrentUrl());
    }

    @Then("^I click on link proceed directly to International Abstracts in O.R. Online which will be the URL mentioned above which should goto IAOR journal and try searching a term$")
    public void I_click_on_link_proceed_directly() throws Throwable {
		raccess.clickTheLinkAndFocusOnANewWindow(raccess.Browser());
    }

    @Then("^You should get some results instead of navigating to the login page$")
    public void You_should_get_some_results() {

		raccess.Browser().manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		assertEquals("International Abstracts in Operations Research", 
				raccess.verificationMessage().getText());
        commonfun.captureScreen(raccess.Browser(),"SecureReferalAccess");
        raccess.switchToMainWindow(raccess.Browser());
        raccess.Logout();
        raccess.Browser().quit();
    }
}
