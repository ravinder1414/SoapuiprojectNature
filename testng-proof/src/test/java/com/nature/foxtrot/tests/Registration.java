package com.nature.foxtrot.tests;

import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.Test;

import com.nature.foxtrot.common.CommonGenericFunctions;
//import org.testng.annotations.BeforeMethod;
import com.nature.foxtrot.pageobjects.RegistrationPageObject;

public class Registration {
    RegistrationPageObject reg = new RegistrationPageObject();
    CommonGenericFunctions commonfun = new CommonGenericFunctions();
    long rnum = commonfun.randomNumber();
    

//Nature.com Scenarios
    public void registration() {
    	reg.navigateRegistration("nature.com/register");
        reg.firstname().sendKeys("testNg");
        reg.lastname().sendKeys("proof"+rnum);
        reg.email().sendKeys("testNgproof"+rnum+"@mailinator.com");
        System.out.println("testNgproof"+rnum+"@mailinator.com");
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

    @Test(description = "Successful registration-nature.com account")
    public void successfulRegistration() {
    	registration();
    	reg.register().click();
        assertEquals(reg.registrationConfirmation().getText(),
                "Thank you for registering at nature.com, you are almost done.");
        commonfun.captureScreen(reg.Browser());
        reg.Browser().close();
    }
    
    @Test(description="Verification and Verification email")
    public void verificationEmail() throws ClientProtocolException, IOException{
    	DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(
				"http://test.npg-services.nature.com/naturewebapi/users/accounts;email="
						+"testNgproof"+rnum+"@mailinator.com"
						+ "?client=s@l1QA");
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
//		Iterator<String> fieldNamesIterator = usersNode.get(0).getFieldNames();
//		for (Iterator<String> iterator = fieldNamesIterator; iterator.hasNext();) {
//			String fieldName = iterator.next();
//			System.out.println(fieldName);
//		}
		
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
		
		String URL = "nature.com/verify/"+KEY;
		System.out.println(URL);

    	reg.navigateRegistration(URL);
//    	System.out.println("nature.com/verify/"+KEY);
    	
//    	reg.confirmationEmailLink().click();
    	reg.Browser().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    	
    	reg.loginPassword().sendKeys("password");
    	reg.loginSubmit().click();
    	
    	assertEquals(reg.registrationConfirmation().getText(),
    			"Thank you for registering at nature.com.");
    	
    	commonfun.captureScreen(reg.Browser());
    	reg.Browser().close();
    }
    
//    @Test(description="Verification and Verification email")
//    public void verificationEmail(){
//    	reg.navigateUrl("http://"+"testNgproof"+rnum+".mailinator.com/");
//    	assertEquals(reg.confirmationEmail().getText(),"your nature registration");
//    	reg.confirmationEmail().click();
//    	reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//    	
//    	reg.confirmationEmailLink().click();
//    	reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//    	
//    	reg.loginPassword().sendKeys("password");
//    	reg.loginSubmit().click();
//    	
//    	assertEquals(reg.registrationConfirmation().getText(),"Thank you for registering at nature.com.");
//    }
   
//    @Test(description="Invalid Registration -nature.com account ")
//    public void invalidRegistrationregistrationEmail(){
//    	reg.navigateRegistration("nature.com/register");
//        reg.firstname().sendKeys("testNg");
//        reg.lastname().sendKeys("proof"+rnum);
//        reg.email().sendKeys("testNgproof"+rnum+"@mailinator.com");
//        reg.password().sendKeys("password");
//
//        reg.Browser().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        assertEquals(reg.emailValidation().getText(),
//                "This email is already in use.");
//    }
//    
//    @Test(description = "Registration validation-nature.com account- Email")
//    public void registrationValiadtion(){
//    	registration();
//    	reg.register().click();
//    	assertEquals(reg.invalidRegistartionErrorMessage().getText(),
//    			"We're sorry but we are unable to register you " +
//    			"as this e-mail address is already associated with an account. " +
//    			"Please log in via the login box in the top right-hand corner of the" +
//    			" page if this is your e-mail address.");
//    }
//    
//    @Test(description = "Registration validation-nature.com account- Affiliation/Employer")
//    public void registrationValidationEmployer(){
//    	registration();
//    	reg.employer().clear();
//    	reg.register().click();
//    	assertEquals(reg.employerValidation().getText(),
//    			"Affiliation / Employer is required.");
//    }
//    
//    
// 
////nature.com orcid scenarios    
//    public void orcidRegistration(){
//    	reg.navigateRegistration("nature.com/register");
//    	reg.orcidLink().click();
//    	reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//    	reg.orcidFirstName().sendKeys("Orcid");
//    	reg.orcidLastName().sendKeys("proof"+rnum);
//    	reg.orcidEmail().sendKeys("Orcid"+rnum+"@mailinator.com");
//    	reg.orcidReEnterEmail().sendKeys("Orcid"+rnum+"@mailinator.com");
//    	reg.orcidPassword().sendKeys("password1");
//    	reg.orcidConfirmationPassword().sendKeys("password1");
//    	reg.orcidConsent().click();
//    }
//    
//    @Test(description="ORCID account linking - form")
//    public void orcidAccountLinkingForm(){
//    	reg.navigateRegistration("nature.com/register");
//    	reg.orcidLink().click();
//        reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//        
//        assertEquals(reg.Browser().getCurrentUrl(), 
//        		"https://orcid.org/oauth/signin");
//        
//    }
//    
//    @Test(description="ORCID account linking - data")
//    public void orcidAccountLinkingData(){
//    	orcidRegistration();
//    	reg.orcidRegister().click();
//    	reg.Browser().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//    	reg.orcidAuthorize().click();
//    	assertEquals(reg.Browser().getCurrentUrl(),
//    			"https://orcid.org/oauth/authorize?" +
//    			"client_id=0000-0003-1952-7139&" +
//    			"response_type=code&scope=/orcid-profile/read-limited&" +
//    			"redirect_uri=http%3A%2F%2Fwww.nature.com%2Forcid%2Fcallback");
//    }
//    
//    @Test(description ="ORCID account linking - validation")
//    public void orcidAccountLinkingValidation(){
//    	orcidRegistration();
//    	reg.orcidRegister().click();
//    	reg.Browser().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//    	System.out.println(reg.orcidValidation().getText());
//    	assertEquals(reg.orcidValidation().getText(),"Orcid"+rnum+"@mailinator.com"
//    	+" already exists in our system. " +
//    	"Would you like to Sign in using "
//    	+"Orcid"+rnum+"@mailinator.com"+"?");
//    }

//
////Palgrave-journals.com scenarios
//    
//    public void registerPalgrave(){
//    	reg.navigateRegistration("palgrave-journals.com/nams/svc/register");
//    	System.out.println("Palgrave"+rnum);
//    	reg.emailPalgrave().sendKeys("Palgrave"+rnum+"@mailinator.com");
//    	reg.confirmEmailPalgrave().sendKeys("Palgrave"+rnum+"@mailinator.com");
//    	reg.passwordPalgrave().sendKeys("password");
//    	reg.confirmPasswordPalgrave().sendKeys("password");
//    	reg.name1Palgrave().sendKeys("Palgrave");
//    	reg.name2Palgrave().sendKeys("Test"+rnum);
//    	
//    	reg.palceOfWorkPalgrave("Other");
//    	reg.positionPalgrave("Other");
//    	reg.companyPalgrave().sendKeys("NPG");
//    	
//    	reg.continuePlagrave().click();
//    	reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//    	
//    }
//    
//    @Test(description="Successful registration-Palgrave Journals")
//    public void successfulRegistrationPalgrave(){
//    	registerPalgrave();
//    	reg.continuepage2Palgrave().click();
//    	reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//    	
//    	reg.confirmPalgrave().click();
//    	reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//
////    	assertEquals(reg.confirmationMessagePalgrave().getText(),
////    			"Your registration with Palgrave Macmillan has been successful!");
//    }
//    
//    @Test(description = "Invalid registration-Palgrave Journals")
//    public void invalidRegistrationPalgrave(){
//    	registerPalgrave();
//    	reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//
//    	assertEquals(reg.registrationErrorPalgrave().getText(),
//    			"We’re sorry but we are unable to register you as this e-mail address is \nalready associated with an account.");
//    }
//    
//    
//    @Test(description="Verification Email-Palgrave Journals")
//    public void verficationEmailPalgrave(){
//    	
//    	reg.navigateUrl("http://www.mailinator.com/");
//    	reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//    	reg.mailinatorSearch().sendKeys("Palgrave"+rnum);
//    	reg.mailinatorGo().click();
//    	reg.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//
//    	assertEquals(reg.confirmationEmailPalgrave().getText(),"your palgrave macmillan journals registr");
//    	reg.confirmationEmailPalgrave().click();
//    	
//    	reg.confirmationEmailLinkPalgrave().click();
//    	reg.Browser().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//    	
//    	reg.loginPassword().sendKeys("password");
//    	reg.loginSubmit().click();
//    	
//    	assertEquals(reg.userNameValidationPalgrave().getText(),"Palgrave"+rnum+"@mailinator.com");
//    	
//    }
//    
//// labanimal.com scenarios
//    
//    @Test(description="Successful registration-Labanimal")
//    public void successfulRegistrationLabanimal(){
//    	
//    }
//    
//    @Test(description = "Invalid registration-Labanimal")
//    public void invalidRegistrationLabanimal(){
//    	
//    }
//    
//    @Test(description="Registration Email field validation - Labanimal")
//    public void registrationEmailValidationLabanimal(){
//    	
//    }
//    
//    @Test(description="Registration Employer field validation-Labanimal")
//    public void registrationEmployerValidationLabanimal(){
//    	
//    }
//    
//    @Test(description="Verification Email-Labanimal")
//    public void verficationEmailLabanimal(){
//    	
//    }
    
    
//    @BeforeMethod
//    public void SetUpBrowser() {
//        reg.navigateRegistration();
//    }

//    @AfterMethod
//    public void QuitBrowser() {
//        reg.Browser().quit();
//    }

}
