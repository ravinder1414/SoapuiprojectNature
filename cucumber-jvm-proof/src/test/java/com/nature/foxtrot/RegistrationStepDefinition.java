package com.nature.foxtrot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import static org.junit.Assert.*;

import com.nature.foxtrot.common.CommonGenericFunctions;
import com.nature.foxtrot.pageobjects.RegistrationPageObject;

//import cucumber.annotation.After;
//import cucumber.annotation.Before;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class RegistrationStepDefinition {
	RegistrationPageObject reg = new RegistrationPageObject(); ;
	CommonGenericFunctions commonfun = new CommonGenericFunctions(); ;
	final long rnum = commonfun.randomNumber();
	
	
	public void registration() {
		reg.navigateRegistration("nature.com/register");
        reg.firstname().sendKeys("Cucumber");
        reg.lastname().sendKeys("Jvm"+rnum);
        reg.email().sendKeys("CucumberJvm"+rnum+"@mailinator.com");
        System.out.println("CucumberJvm"+rnum+"@mailinator.com");
        reg.password().sendKeys("password");
        reg.confirmpassword().sendKeys("password");

        reg.termsAndconditions().click();
        reg.employer().clear();
        reg.employer().sendKeys("NPG");
        reg.jobTitle("Other");
        reg.industry("Other");
        reg.areaOfInterest("Other");
        reg.Browser().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        reg.specialities("Other");
    }
//1	
	@Given("^I enter the valid parameters$")
	   public void I_enter_the_valid_parameters(){
		registration();
	}
	
	@When("^I click Register button$")
	   public void I_click_Register_button(){
		reg.register().click();
	}
	
	@Then("^Registration should be successful$")
	   public void Registration_should_be_successful(){
		assertEquals("Thank you for registering at nature.com, you are almost done.",
				reg.registrationConfirmation().getText());
        commonfun.captureScreen(reg.Browser(),"SuccessfulRegistration");
        reg.Browser().close();
	}

// Email Verification -- TEST
	
	@Given("^I invoked DRUM API's and get Email Verification String$")
	   public void Email_Verification_Link_Construct() 
			   throws JsonProcessingException, IOException{
		registration();
		reg.register().click();
		reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		reg.Browser().close();
		
    	DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(
				"http://test.npg-services.nature.com/naturewebapi/users/accounts;email="
				+"CucumberJvm"+rnum+"@mailinator.com"
				+"?client=s@l1QA");
		getRequest.addHeader("accept", "application/json");

		HttpResponse response = httpClient.execute(getRequest);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())));
		
		StringBuffer apiRespponse= new StringBuffer();

		String output;
//		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			apiRespponse.append(output);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode contentNode = objectMapper.readTree(apiRespponse.toString());
		JsonNode usersNode = contentNode.get("users");
		System.out.println(usersNode.get(0).get("id"));
		
		JsonNode ID = usersNode.get(0).get("id");
		System.out.println(ID);
		
		DefaultHttpClient httpClient1 = new DefaultHttpClient();
		HttpGet getRequest1 = new HttpGet("http://test.npg-services.nature.com/naturewebapi/" +
				"users/emails/"+ID+"/verificationKey?client=s@l1QA");
		getRequest1.addHeader("accept", "application/json");

		HttpResponse response1 = httpClient1.execute(getRequest1);
		
		if (response1.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response1.getStatusLine().getStatusCode());
		}
		
		
		BufferedReader br1 = new BufferedReader(new InputStreamReader(
				(response1.getEntity().getContent())));
		
		StringBuffer apiResponse1= new StringBuffer();
		
		String output1;
//		System.out.println("Output from Server .... \n");
		while ((output1 = br1.readLine()) != null) {
			apiResponse1.append(output1);
//			System.out.println(output1);
		}
		
		ObjectMapper objectMapper1 = new ObjectMapper();
		JsonNode contentNode1 = objectMapper1.readTree(apiResponse1.toString());
		JsonNode usersNode1 = contentNode1.get("emailVerificationKey");
//		System.out.println(usersNode1.get("key"));
		
		String KEY = usersNode1.get("key").toString().replaceAll("\"", "");
	
		System.out.println(KEY);
		reg.navigateRegistration("nature.com/verify/"+KEY);
		System.out.println("nature.com/verify/"+KEY);
		reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	@When("^I enter password and click 'Submit' button$")
	   public void I_enter_password_and_click_submit(){
//    	reg.confirmationEmailLink().click();
		reg.loginPassword().sendKeys("password");
		reg.loginSubmit().click();
	}
	
	@Then("^Email Verification should be successful$")
	   public void Email_Verification_should_be_successful(){
		assertEquals(reg.registrationConfirmation().getText(),
				"Thank you for registering at nature.com.");
			commonfun.captureScreen(reg.Browser(),"Email Verification");
			reg.Browser().close();
	}
	
	
	
//2
	@Given("^Verification Email appear in my Inbox$")
	   public void Verification_Email_Appears_in_my_inbox(){
		registration();
		reg.register().click();
		reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		reg.Browser().close();
		reg.navigateUrl("http://"+"CucumberJvm"+rnum+".mailinator.com/");
		System.out.println("http://"+"CucumberJvm"+rnum+".mailinator.com/");
		assertEquals(reg.confirmationEmail().getText(),"your nature registration");
	}
	
	@When("^I click the Email Verification Link and It should redirect to Nature.com website$")
	   public void I_click_Email_Confirmation_Link(){
		reg.confirmationEmail().click();
		reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		reg.confirmationEmailLink().click();
		reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}
	
	@When("^I enter password and click 'Submit' button$")
	   public void I_enter_password_click_submit_button(){
		reg.loginPassword().sendKeys("password");
		reg.loginSubmit().click();

	}
	
	@Then("^Registration Email Verification should be successful$")
	   public void Registration_Email_Verification_should_be_successful(){
		assertEquals(reg.registrationConfirmation().getText(),"Thank you for registering at nature.com.");
		commonfun.captureScreen(reg.Browser(),"EmailVerification");
		reg.Browser().close();
	}


}
