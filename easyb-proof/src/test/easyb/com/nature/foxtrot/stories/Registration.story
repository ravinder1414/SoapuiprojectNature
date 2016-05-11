package com.nature.foxtrot.stories


import static junit.framework.Assert.*;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.nature.foxtrot.common.CommonGenericFunctions;
import  com.nature.foxtrot.pageobjects.*;

RegistrationPageObject reg = new RegistrationPageObject();
CommonGenericFunctions commonfun = new CommonGenericFunctions();
long rnum = commonfun.randomNumber();

scenario "Registering for a free nature.com account",{
	
	given "enter the valid parameters-firstname, lastname, email, password, confirm password",{
		reg.navigateRegistration("nature.com/register");
		reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		reg.firstname().sendKeys("easyb");
		reg.lastname().sendKeys("Proof"+rnum);
		reg.email().sendKeys("easybProof"+rnum+"@mailinator.com");
		reg.password().sendKeys("password");
		reg.confirmpassword().sendKeys("password");
	}
	and "check the terms and conditions and selecting Affilaites/Employer, Job Title, Industry, Areas of Interest and Specialities",{
		reg.termsAndconditions().click();
		reg.employer().clear();
		reg.employer().sendKeys("NPG");
		reg.jobTitle("Other");
		reg.industry("Other");
		reg.areaOfInterest("Other");
		reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		reg.specialities("Other");
	}
	
	when "I click Register button", {
		reg.register().click();
	}

	then "Registration should be successful", {
		assertEquals("Thank you for registering at nature.com, you are almost done.",
			reg.registrationConfirmation().getText());
        commonfun.captureScreen(reg.Browser(),"RegisteringForFreeNatureAccount");
		reg.Browser().close();
	}
}

<<<<<<< HEAD
scenario "Registration Email Verification and Confirmation",{
	
	given "I enter into email inbox",{
		reg.navigateUrl("http://"+"easybProof"+rnum+".mailinator.com/");
	}
	and "Registration Verification Email should appear in Email",{
		assertEquals(reg.confirmationEmail().getText(),"your nature registration");
	}
	
	when "I click the Email Verification Link and It should redirect to Nature.com website", {
		reg.confirmationEmail().click();
		reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		reg.confirmationEmailLink().click();
		reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	and "I enter password and click 'Submit' button",{
		reg.loginPassword().sendKeys("password");
		reg.loginSubmit().click();
	}
=======
scenario "Registration Email Verification and Confirmation--TEST",{
	given "I invoked DRUM API's and get Email Verification String",{
		
    	DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(
				"http://test.npg-services.nature.com/naturewebapi/users/accounts;email="
				+"easybProof"+rnum+"@mailinator.com"
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
		HttpGet getRequest1 = new HttpGet("http://test.npg-services.nature.com/naturewebapi/users/emails/"+ID+"/verificationKey?client=s@l1QA");
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
	
	when "I enter password and click 'Submit' button", {
    	
//    	reg.confirmationEmailLink().click();
		reg.loginPassword().sendKeys("password");
		reg.loginSubmit().click();
	}

	then "It should show message 'Thank you for registering at nature.com.'", {
		assertEquals(reg.registrationConfirmation().getText(),
			"Thank you for registering at nature.com.");
		commonfun.captureScreen(reg.Browser(),"Email Verification");
		reg.Browser().close();
	}
}
>>>>>>> DEMO12062013TEST

	then "It should show message 'Thank you for registering at nature.com.'", {
		assertEquals(reg.registrationConfirmation().getText(),"Thank you for registering at nature.com.");
		reg.Browser().close();
	}
}


after "stop Browser" , {
	then "Browser is closed", {
		reg.Browser().quit();
	}
}
