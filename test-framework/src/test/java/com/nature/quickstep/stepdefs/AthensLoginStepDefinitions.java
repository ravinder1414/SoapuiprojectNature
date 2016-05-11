package com.nature.quickstep.stepdefs;

import static org.junit.Assert.*;

import com.nature.quickstep.pageobjects.nature.AthensLoginPage;
import com.nature.quickstep.pageobjects.nature.HomePage;
import com.nature.quickstep.pageobjects.nature.NatureLoginPage;
import com.nature.quickstep.util.Context;
import com.nature.quickstep.util.Context.Website;

import static com.nature.quickstep.util.WebDriverUtils.*;
import cucumber.api.java.en.*;

public class AthensLoginStepDefinitions {

	HomePage homepage = new HomePage();
	NatureLoginPage natureLoginPage = new NatureLoginPage();
	AthensLoginPage athensLoginPage = new AthensLoginPage();

	// 1
	@Given("^I am a user on the nature.com website$")
	public void I_am_a_user_on_the_nature_website() {
		homepage.navigateTo();
	}


	@When("^I visit the Athens login page using the ([^\"]*) method$")
	public void I_visit_the_Athens_login_page_using(String method) throws Exception {

		if (method.equals("log in page")) {
			athensLoginPage.navigateTo();
		} else if (method.equals("in-article link")) {
			athensLoginPage.navigateToRelativeURL(Website.nature, "/nature/journal/v446/n7131/full/446009a.html");
			natureLoginPage.lnkAthensLogin().click();
		} else {
			throw new Exception("Unkown Athens login method: " + method);
		}

	}

	@Then("^I am taken to the Athens institutional login page$")
	public void I_am_taken_to_the_Athens_institutional_login_page() {
		athensLoginPage.waitForAthensLoginPage(); 
	}


	//2
	@Given("^I am viewing the marketing opportunity page for an article$")
	public void I_am_viewing_the_marketing_opportunity_page_for_an_article() {
		athensLoginPage.navigateToRelativeURL(Website.nature, "/nature/journal/v446/n7131/full/446009a.html");
	}

	@When("^I log in using Athens$")
	public void I_log_in_using_Athens() {
		natureLoginPage.lnkAthensLogin().click();
		athensLoginPage.loginUsingAthensAccount("npggawekar", "abc123");
	}

	@Then("^I am redirected to the full version of the article$")
	public void I_am_redirected_to_the_full_version_of_the_article() {
		
		assertEquals(
				athensLoginPage.getArticleContent().getText(),
				"In just over "
						+ "a decade, two major ice shelves have collapsed on the eastern side "
						+ "of the Antarctic peninsula, uncovering a part of the sea floor that "
						+ "had not seen sunlight for several thousand years. A ten-week expedition "
						+ "that ended in late January has shed light on the biology of these waters, "
						+ "and has recovered samples of some 1,000 species from the region, several of which "
						+ "may be new to science.");
	}

	// 3
	@Given("^I am viewing the nature.com login page$")
	public void I_am_viewing_the_nature_login_page() {
		natureLoginPage.navigateTo();
	}

	@Then("^I am redirected to Nature.com and I have access to articles$")
	public void I_am_redirected_to_Nature_and_I_have_access_to_articles() {
		
		assertEquals(getCurrentUrl(),
				Context.getHomePageUrl(Website.nature));
	}

}
