@cleanbrowser
Feature: Nature.com institutional login via Athens

In order to access my institution (which is a member of Athens)'s subscription
As a reader 
I want to be able to login via Athens

@wip
Scenario Outline: Navigating to the Athens institutional login page
Given I am a user on the nature.com website
And I am not currently logged in
When I visit the Athens login page using the <method> method
Then I am taken to the Athens institutional login page

Examples:
|method|           
|log in page|
|in-article link|


@wip
Scenario: Logging in with Athens to read an article
Given I am a user on the nature.com website
And I am not currently logged in
And I am viewing the marketing opportunity page for an article 
When I log in using Athens
Then I am redirected to the full version of the article 

@wip
Scenario: Logging in with Athens to view any article on Nature.com
Given I am a user on the nature.com website
And I am not currently logged in
And I am viewing the nature.com login page
When I log in using Athens
Then I am redirected to Nature.com and I have access to articles