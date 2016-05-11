Feature: Secure Referral Access functionality

	Scenario: Login should be successful when login with valid Society credentials
		Given I visit the Society Page and click on Member Login
		And submit the login form with a valid Username and Password
		When I Navigate on the fourth menu Find Research & Publications > Searchable Database
		And click on  second link IAOR Online Database. It will then navigate to https://www.informs.org/Find-Research-Publications/Searchable-Databases/International-Abstracts-in-Operations-Research-IAOR-Database
		Then I click on link proceed directly to International Abstracts in O.R. Online which will be the URL mentioned above which should goto IAOR journal and try searching a term
		And You should get some results instead of navigating to the login page
		

