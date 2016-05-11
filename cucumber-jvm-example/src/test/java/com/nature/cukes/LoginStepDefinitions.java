package com.nature.cukes;

import com.nature.pageobjects.LoginPageObject;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.When;

public class LoginStepDefinitions {

	LoginPageObject login;
	
	public LoginStepDefinitions(SharedDriver webDriver){
		login = new LoginPageObject(webDriver);
	}
	
	@Given("^I am on login page$")
	public void I_am_on_login_page() throws Throwable {
	    login.navigate();
	}

	@When("^I submit the login form with a valid \"([^\"]*)\" and \"([^\"]*)\"$")
	public void I_submit_the_login_form_with_a_valid_and(String username, String pass) throws Throwable {
	    login.username().sendKeys(username);
	    login.password().sendKeys(pass);
	    login.submit().click();
	}
	
}
