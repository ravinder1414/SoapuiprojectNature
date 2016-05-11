Feature: LoginShibboleth functionality

     Scenario: Log in via Shibboleth- Nature
		Given I navigate to nature.com login page
		When  click on 'Login Via Institute' link
		Then  it navigates to the instituion login page
	
	 Scenario: Shibboleth institutional login-Select Country-Nature
		Given I navigate to nature.com institutional login page
		When  select country - United Kingdom and click 'Go' button
		Then  it navigates to the instituion Country login page
	
	Scenario: Shibboleth institutional login-Search Institution-Nature
		Given I navigate to nature.com institutional login page and select country-United Kingdom
		When  I type University name in search for an institution: and click 'Search' button
		Then  'Brunel University' appears in search list
		
	Scenario: Shibboleth institutional login-Redirection-Nature
		Given I navigate to nature.com institutional login page and select country-United Kingdom
		And   I type 'Brunel University' in search for an instite and click 'Search' button
		When  'Brunel University' appears in search list and click the 'Brunel University'
		And   enter 'Brunel University' credentials and click 'continue' button
		Then it navigates 'nature.com'

    Scenario: Shibboleth Access for invalid site account
		Given I go to NCB journal and click View Full Access
		And   Click on 'Login via your institution'
		When  I select University/College/Institution from drop down and click select
		And   Login with invalid credentials
		Then  Brunel University login should be unsuccessful
	
	Scenario: Shibboleth Access for  valid site account 
		Given I go to NCB journal and click View Full Access
		And   Click on 'Login via your institution'
		When  I select University/College/Institution from drop down and click select
		And   Login with valid username and password
		Then  This should appear in header Full text access provided to Brunal University by Central Library
