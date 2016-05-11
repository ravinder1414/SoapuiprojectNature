package com.nature.quickstep.stepdefs;



import com.nature.quickstep.pageobjects.nature.HomePage;
import com.nature.quickstep.pageobjects.nature.NatureLoginPage;

import static com.nature.quickstep.util.WebDriverUtils.actions.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

public class NatureLoginStepDefinitions {
	
	HomePage homepage = new HomePage();
	NatureLoginPage login = new NatureLoginPage();
	
	@Given("^I am not currently logged in$")
	public void I_am_not_currently_logged_in() throws Throwable {
	   
		if (homepage.isUserLoggedIn(DO_NOT_WAIT)) {
			homepage.logOut();
		}
		
		assertTrue(!homepage.isUserLoggedIn(DO_NOT_WAIT));
		
	}

	@When("^I log in using a valid nature account$")
	public void I_log_in_using_a_valid_nature_account() throws Throwable {
		login.loginUsingNatureAccount("mark.micallef@nature.com", "password");
		
	}

	@Then("^I should be logged into the site$")
	public void I_should_be_logged_into_the_site() throws Throwable {
		assertTrue(homepage.isUserLoggedIn(WAIT_UNTIL_TRUE));
	}


	@When("^I log in using an invalid nature account$")
	public void I_log_in_using_an_invalid_nature_account() throws Throwable {
		login.loginUsingNatureAccount("wrong username", "wrong password");
		//login.loginUsingNatureAccount("asdasd", "asdsda");
	}

	@Then("^I should not be logged into the site$")
	public void I_should_not_be_logged_into_the_site() throws Throwable {
		assertTrue(!homepage.isUserLoggedIn(DO_NOT_WAIT));
	}


}
