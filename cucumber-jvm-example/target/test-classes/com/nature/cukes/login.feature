@regression
Feature: Login functionality

	Scenario Outline: Verify that you are able to login with correct credentials
		Given I am on login page
		When I submit the login form with a valid "<login>" and "<password>"
		Then I should be taken to the home page
		And I should a welcome back message for the "<user>"
		
	Examples:
		| login                     | password    | user              |
		| m.winteringham@nature.com | password123 | Mark Winteringham |
		 