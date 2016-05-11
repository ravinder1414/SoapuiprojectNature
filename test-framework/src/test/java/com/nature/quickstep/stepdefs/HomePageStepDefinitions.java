package com.nature.quickstep.stepdefs;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.nature.quickstep.pageobjects.nature.HomePage;
import com.nature.quickstep.pageobjects.nature.NewsSection;
import com.nature.quickstep.util.Context;
import com.nature.quickstep.util.WebDriverUtils;
import com.nature.quickstep.util.Context.Website;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

public class HomePageStepDefinitions {

	HomePage homepage = new HomePage();

	@Given("^I am a nature reader$")
	public void I_am_a_nature_reader() throws Throwable {
	}

	@Given("^I am a nature reader using the nature website$")
	public void I_am_a_nature_reader_using_the_nature_website()
			throws Throwable {
		I_am_a_nature_reader();
		visit_the_nature_website();
	}

	@When("^I visit the nature website$")
	public void visit_the_nature_website() throws Throwable {
		homepage.navigateTo();
	}

	@Then("^I should see an image of the front page of the current issue$")
	public void I_should_see_an_image_of_the_front_page_of_the_current_issue()
			throws Throwable {
		assertTrue(homepage.isCoverPresent());
	}

	@Then("^I should see the Inside Nature section$")
	public void I_should_see_the_inside_nature_section() throws Throwable {
		assert (homepage.isInsideNaturePresent());
	}

	@Then("^I should see the News section$")
	public void I_should_see_the_News_section() throws Throwable {
		assert (homepage.isNewsSectionPresent());
	}

	@Then("^the News section should show today's date$")
	public void the_News_section_should_show_today_s_date() throws Throwable {

		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");

		NewsSection newsSection = new NewsSection();

		assertEquals(sdf.format(new Date()), newsSection.getDate());
	}
	
	@Then("^I should be redirected to the nature website$")
	public void I_should_be_redirected_to_the_nature_website() throws Throwable {
	    //WebDriverUtils.waitForURLToContainSubstring(Context.getHomePageUrl(Website.nature));
		WebDriverUtils.waitForURLToBe(Context.getHomePageUrl(Website.nature));
		
	}
	
	@Then("^I should not be redirected to the nature website$")
	public void I_should_not_be_redirected_to_the_nature_website() throws Throwable {
	    WebDriverUtils.waitForURLNotToContainSubstring(Context.getHomePageUrl(Website.nature));
	    
	}
	
}
