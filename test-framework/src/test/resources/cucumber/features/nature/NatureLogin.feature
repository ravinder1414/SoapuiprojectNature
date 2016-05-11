Feature: Nature login functionality

In order to have a personal experience
As a nature reader
I want to be able to log in to the website
 
@demo @timed
Scenario: Valid login using nature account
Given I am a nature reader using the nature website
And I am not currently logged in
When I log in using a valid nature account
Then I should be logged into the site

@demo
Scenario: Invalid login using nature account
Given I am a nature reader using the nature website
And I am not currently logged in
When I log in using an invalid nature account
Then I should not be logged into the site
