Feature: Home page layout

In order to maximise my browsing experience
As a nature reader
I want to have content available to me on the nature website
 
Scenario: Homepage displays the current issue cover
Given I am a nature reader
When I visit the nature website
Then I should see an image of the front page of the current issue

Scenario: Home page has an "Inside Nature.com" section
Given I am a nature reader
When I visit the nature website
Then I should see the Inside Nature section

Scenario: Home page has a news section
Given I am a nature reader
When I visit the nature website
Then I should see the News section
And the News section should show today's date
