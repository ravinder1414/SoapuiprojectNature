package com.nature.cukes;

import static org.junit.Assert.assertEquals;

import com.nature.pageobjects.HomePageObject;
import com.nature.utilities.Url;

import cucumber.annotation.en.Then;

public class HomePageStepDefinitions {

    HomePageObject home;

    public HomePageStepDefinitions(SharedDriver webDriver) {
        home = new HomePageObject(webDriver);
    }

    @Then("^I should be taken to the home page$")
    public void I_should_be_taken_to_the_home_page() throws Throwable {
        Url url = new Url("test");
        String expectedUrl = url.createUrl();
        String actualUrl = home.getUrl();

        assertEquals(expectedUrl + "/index.html", actualUrl);
    }

    @Then("^I should a welcome back message for the \"([^\"]*)\"$")
    public void I_should_a_welcome_back_message_for_the(String message) throws Throwable {
        String actualMessage = home.welcomeMessage().getText();
        assert (actualMessage.contains(message));
    }
}
