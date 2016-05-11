@regression
Feature: NatureJobs integration

	Scenario: Verify that NatureJobs is running with Foxtrot
		Given I am on the NatureJobs home page
		When I select a job from jobs of the week
		Then the job details should appear