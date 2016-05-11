Feature: Registration functionality

	Scenario: Registering for a free nature.com account
		Given I enter the valid parameters
		When  I click Register button
		Then  Registration should be successful
		
<<<<<<< HEAD
	Scenario: Registration Email verfication and confirmation
		Given Verification Email appear in my Inbox
		When  I click the Email Verification Link and It should redirect to Nature.com website 
		And   I enter password and click 'Submit' button
		Then  Registration Email Verification should be successful
=======
	Scenario: Registration- Email Verification and Confirmation
		Given I invoked DRUM API's and get Email Verification String
		When  I enter password and click 'Submit' button
		Then  Email Verification should be successful	
>>>>>>> DEMO12062013TEST
		
	
		
	