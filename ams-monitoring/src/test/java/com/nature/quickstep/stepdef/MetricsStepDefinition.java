package com.nature.quickstep.stepdef;

import static junit.framework.Assert.*;

import com.nature.quickstep.pageobjects.ams.MetricsPage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MetricsStepDefinition {
	MetricsPage mpage = new MetricsPage();
	@Given("^I am on article metrics page of an article$")
	public void I_am_on_article_metrics_page_of_an_article() throws Throwable {
	    
	    mpage.navigateToRelativeURL("nature/journal/v498/n7454/nature12217/metrics");
	}

	@When("^I check the last updated time$")
	public void I_check_the_last_updated_time() throws Throwable {
	   mpage.lblLastUpdated().isDisplayed();	   
	}

	@Then("^It should be updated within last 24 hours$")
	public void It_should_be_updated_within_last_hours() throws Throwable {
	    assertEquals(true, mpage.validatelastupdatedtime());
	   
	}

	@When("^I check the metrics data$")
	public void I_check_the_metrics_data() throws Throwable {

	}

	@Then("^there should be some metrics data$")
	public void there_should_be_some_metrics_data() throws Throwable {
//         assertEquals(true,mpage.lbltwettercount().isDisplayed());
//         assertEquals(true,mpage.lblpageviewscount().isDisplayed());
		mpage.isPresent();
	}
}


