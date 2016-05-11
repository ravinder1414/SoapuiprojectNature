Feature: LoginAthens functionality

	Scenario: OpenAthens - single sign on for institutions
		Given Login valid credentials-Username and Password
		When I click Athens submit button
		Then Full text access should be provided for article/journal to user
		
	Scenario: Login via Athens-Nature
		Given Login valid credentials-Username and Password for Nature
		When I click Athens submit button
		Then User should be redirected to nature.com 
	
