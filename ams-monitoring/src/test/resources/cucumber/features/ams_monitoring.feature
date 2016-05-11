Feature: Article Metric Service (AMS) Monitoring at browser level

In order to monitor AMS 
I want to check weather an article is updated within last 24 hours or not
I want to check metrics information is updated or not


Scenario: Monitoring each article metrics data updated within last 24 hours
Given I am on article metrics page of an article
When  I check the last updated time
Then  It should be updated within last 24 hours
When I check the metrics data
Then  there should be some metrics data









