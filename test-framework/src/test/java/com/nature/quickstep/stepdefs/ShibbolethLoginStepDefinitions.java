package com.nature.quickstep.stepdefs;



import com.nature.quickstep.pageobjects.nature.HomePage;
import com.nature.quickstep.pageobjects.nature.ShibbolethLoginPage;

import cucumber.api.java.en.*;

public class ShibbolethLoginStepDefinitions {
	
	HomePage homepage = new HomePage();
	ShibbolethLoginPage login = new ShibbolethLoginPage();
	
	@When("^I login through my institution using valid credentials$")
	public void I_login_through_my_institution_using_valid_credentials() throws Throwable {
		login.loginBySearchingForAnInstitution("Brunel University", "lbsrshib", "Vn3wt8fh");
	}
	

}
