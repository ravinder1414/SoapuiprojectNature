Feature: Nature.com institutional login via Shibboleth

In order to access my institution's subscription
As a reader affiliated with an institution
I want to be able to login via Shibboleth

@new
Scenario: Navigating to the Shibboleth institutional login page
Given I am a reader trying to log in via Shibboleth
And I am viewing the Nature.com login page
When I click the link to the Shibboleth institutional login page
Then I am taken to the Shibboleth institutional login page

@new
Scenario Outline: Selecting a country gives me a list of institutions
Given I am a reader trying to log in via Shibboleth
When I select the <country> my institution is located in
Then I see a list of institutions which are located in that country
And each institution's name is linked to its Shibboleth login page

|country|
|Austria|
|Belgium|
|Chile|
|China|
|Germany|
|Hungary|
|Japan|
|Portugal|
|Spain|
|Switzerland|
|United Kingdom|

@new
Scenario: Institution search success
Given I am a reader trying to log in via Shibboleth
When I search for an institution name
And there is at least one institution name which matches the term I searched for
Then I see a list of all the institution names which contain the term I searched for

@new
Scenario: Institution search no results
Given I am a reader trying to log in via Shibboleth
When I search for an institution name
And there are no institution names which match the term I searched for
Then no institution names will be listed



