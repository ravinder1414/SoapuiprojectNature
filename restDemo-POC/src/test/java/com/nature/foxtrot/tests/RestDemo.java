package com.nature.foxtrot.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.nature.foxtrot.common.Url;

public class RestDemo {
	
	public String  createUrl(){
        Url domain = new Url(System.getProperty("env"));
        return domain.createUrl();	
    }
  
	@Test (description = "Checks users existence based on the email provided")
  public void checkEmail() {
	  try {
		  DefaultHttpClient client = new DefaultHttpClient();	  
		  HttpGet get = new HttpGet(createUrl()+"nature.com/api/users/email/suresh.kolli226061982@mailinator.com");	
		  System.out.println(createUrl()+"nature.com/api/users/email/suresh.kolli226061982@mailinator.com");
		  HttpResponse response = client.execute(get);
	 
			if (response.getStatusLine().getStatusCode() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatusLine().getStatusCode());
			}
	 
		  } catch (Exception e) {
	 
			e.printStackTrace();
	 
		  }
	 
  }
	
	@Test(description ="Creates a new user on Nature.com")
	public void createNewUser() throws ClientProtocolException, IOException{
        long randomnumber = System.currentTimeMillis();
		try {
			 
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(createUrl()+"nature.com/api/users");
			System.out.println(createUrl()+"nature.com/api/users");
			StringEntity input = new StringEntity("firstname=Suresh&lastname=Kolli&email=testnatureapiuser"+randomnumber+"@mailinator.com&password=password&title=Mr&country=IN");

			
			input.setContentType("application/x-www-form-urlencoded");
			
			postRequest.setEntity(input);
			
			System.out.println(postRequest.getURI());	 
			HttpResponse response = httpClient.execute(postRequest);
	 
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatusLine().getStatusCode());
			}
	 
			BufferedReader br = new BufferedReader(
	                        new InputStreamReader((response.getEntity().getContent())));
	 
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
	 
			httpClient.getConnectionManager().shutdown();
	 
		  } catch (MalformedURLException e) {
	 
			e.printStackTrace();
	 
		  } catch (IOException e) {
	 
			e.printStackTrace();
		  }
	}		

	@Test(description = "Update user address - If record exists then it returns Status Code: 200 OK")
	public void updateUserAddress(){
		try {
			 
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPut putRequest = new HttpPut(createUrl()+"nature.com/api/users/10001115/address");
			System.out.println(createUrl()+"nature.com/api/users/10001115/address");

			StringEntity input = new StringEntity("addr1=MPS Ltd.&addr2=Plot No. 865&addr3=Phase V&city=Gurgaon&state=Haryana&country=IN&zip=110011&phone=0909090909");

			
			input.setContentType("application/x-www-form-urlencoded");
			
			putRequest.setEntity(input);
			
			System.out.println(putRequest.getURI());	 
			HttpResponse response = httpClient.execute(putRequest);
	 
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatusLine().getStatusCode());
			}
	 
			BufferedReader br = new BufferedReader(
	                        new InputStreamReader((response.getEntity().getContent())));
	 
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
	 
			httpClient.getConnectionManager().shutdown();
	 
		  } catch (MalformedURLException e) {
	 
			e.printStackTrace();
	 
		  } catch (IOException e) {
	 
			e.printStackTrace();
		  }
	}		
	
	


	
  @BeforeMethod
  public void beforeMethod() {
  }

  @AfterMethod
  public void afterMethod() {
  }

}

