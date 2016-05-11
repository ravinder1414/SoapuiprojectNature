package com.nature.cukes;

import org.openqa.selenium.WebElement;

import com.nature.pageobjects.NatureJobsPageObject;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class NatureJobsStepDefinitions {

	NatureJobsPageObject naturejobs;
	
	public NatureJobsStepDefinitions(SharedDriver webDriver){
		naturejobs = new NatureJobsPageObject(webDriver);
	}
	
	@Given("^I am on the NatureJobs home page$")
	public void I_am_on_the_NatureJobs_home_page() throws Throwable {
	    naturejobs.navigate();
	}

	@When("^I select a job from jobs of the week$")
	public void I_select_a_job_from_jobs_of_the_week() throws Throwable {
	    WebElement job = naturejobs.jobOfTheWeekEntry();
	    job.click();
	}

	@Then("^the job details should appear$")
	public void the_job_details_should_appear() throws Throwable {
		naturejobs.jobDetails();
	}
	
}
