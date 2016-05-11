package com.nature.foxtrot.tests;


import static org.testng.Assert.*;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.nature.foxtrot.common.CommonGenericFunctions;
import com.nature.foxtrot.pageobjects.LoginShibbolethPageObject;


public class LoginShibboleth {

        LoginShibbolethPageObject loginS = new LoginShibbolethPageObject();
        CommonGenericFunctions commonfun = new CommonGenericFunctions();
        
        @Test(description = "Log in via Shibboleth- Nature")
        public void LoginViaShibbolethNature(){
        	loginS.navigateShibboleth("nature.com/foxtrot/svc/login");
    		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            loginS.ShibbolethLoginViaInstitute().click();
            loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            assertEquals(loginS.Browser().getCurrentUrl(),
            		loginS.createUrl()+"nature.com/nams/svc/institutelogin?target=/");
            commonfun.captureScreen(loginS.Browser());
        }
        
        @Test(description = "Shibboleth institutional login-Select Country-Nature")
        public void ShibbolethInstitutionalloginSelectCountryNature(){
        	loginS.navigateShibboleth("nature.com/nams/svc/institutelogin");
        	loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        	loginS.ShibbolethCountry("United Kingdom");
    		loginS.ShibbolethGo().click();
    		assertEquals(loginS.Browser().getCurrentUrl(),
    				loginS.createUrl()+"nature.com/nams/svc/institutelogin?target=/index.html");
    		commonfun.captureScreen(loginS.Browser());
        }
        
        @Test(description = "Shibboleth institutional login-Search Institution-Nature")
        public void ShibbolethInstitutionalloginSearchInstitutionNature(){
        	loginS.navigateShibboleth("nature.com/nams/svc/institutelogin");
        	loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        	loginS.ShibbolethCountry("United Kingdom");
    		loginS.SearchInstitution().sendKeys("Brunel University");
    		loginS.SearchButton().click();
    		assertEquals(loginS.ShibbolethBrunalUniversity().getText(),
    				"Brunel University");
    		commonfun.captureScreen(loginS.Browser());
        }
        
        @Test(description="Shibboleth institutional login- " +
        		"When user clicks an institution name, " +
        		"user is redirected to institution website and " +
        		"then to Nature.com after Istituional Login")
        public void ShibbolethInstitutionalLoginUserRedirectNature(){
        	ShibbolethInstitutionalloginSearchInstitutionNature();
        	loginS.ShibbolethBrunalUniversity().click();
    		assertEquals(loginS.Browser().getCurrentUrl(),
    				"https://shibsles.brunel.ac.uk/idp/Authn/UserPassword");
        	
        	loginS.BrunalUniversityUserName().sendKeys("lbsrshib");
        	loginS.BrunalUniversityPassword().sendKeys("Vn3wt8fh");
        	loginS.BrunalUniversityContinue().click();
        	loginS.Browser().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        	assertEquals(loginS.Browser().getCurrentUrl(),
        			loginS.createUrl()+"nature.com/index.html");
    		commonfun.captureScreen(loginS.Browser());
        }

        @Test(description = "Access to a NCB Jouranal/Article using " +
        		"Brunal University Shibboleth Credentials-Nature")
        public void LoginShibbolethToNCBJournalsViaBrunalUniversityNature(){
       		loginS.navigateShibboleth("nature.com/ncb/journal/" +
       				"v12/n12/full/ncb2120.html#/access");
    		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		loginS.NCBJournalLogin().click();
    		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		
    		loginS.ShibbolethLoginViaInstitute().click();
    		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		loginS.ShibbolethCountry("United Kingdom");
    		loginS.ShibbolethGo().click();
    		loginS.ShibbolethBrunalUniversity().click();
    		assertEquals(loginS.Browser().getCurrentUrl(),
    				"https://shibsles.brunel.ac.uk/idp/Authn/UserPassword");
        	
        	loginS.BrunalUniversityUserName().sendKeys("lbsrshib");
        	loginS.BrunalUniversityPassword().sendKeys("Vn3wt8fh");
        	loginS.BrunalUniversityContinue().click();
        	
        	loginS.Browser().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        	
        	assertEquals(loginS.BrunalUniversityContentAssertion().getText(),"Introduction");
        	
//    		assertEquals(loginS.BrunalUniversityConfirmationMessage().getText(),
//    				"Full text access provided to Brunel University by Library");
        	
    		commonfun.captureScreen(loginS.Browser());
        }
        
        @Test(description = "Trying to access to a NCB Jouranal/Article " +
        		"using invalid Brunal University Shibboleth Credentials-Nature")
        public void LoginShibbolethToNCBJournalsViaBrunalUniversityWithInvalidCredentialsNature(){
       		loginS.navigateShibboleth("nature.com/ncb/journal/v12/n12/full/ncb2120.html#/access");
    		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		loginS.NCBJournalLogin().click();
    		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		
    		loginS.ShibbolethLoginViaInstitute().click();
    		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		loginS.ShibbolethCountry("United Kingdom");
    		loginS.ShibbolethGo().click();
        	loginS.ShibbolethBrunalUniversity().click();
        	assertEquals(loginS.Browser().getCurrentUrl(),
        			"https://shibsles.brunel.ac.uk/idp/Authn/UserPassword");
        	
        	loginS.BrunalUniversityUserName().sendKeys("lbsrshib1");
        	loginS.BrunalUniversityPassword().sendKeys("Vn3wt8fh1");
        	loginS.BrunalUniversityContinue().click();
        	
    		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		assertEquals(loginS.BrunalUniversityWrongCredentials().getText(),
    				"Credentials not recognised.");
        	commonfun.captureScreen(loginS.Browser());
        }
        
        @Test(description="Log in via Shibboleth-Palgrave Journals")
        public void LoginViaShibbolethPalgraveJournals(){
        	loginS.navigateShibboleth("palgrave-journals.com/foxtrot/svc/login");
    		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		loginS.ShibbolethLoginViaInstitute().click();
            loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            assertEquals(loginS.createUrl()+
            		"palgrave-journals.com/nams/svc/institutelogin?target=/",
            		loginS.Browser().getCurrentUrl());
        	commonfun.captureScreen(loginS.Browser());
        }
        
        @Test(description = "Shibboleth institutional login-Select Country-Palgrave Journals")
        public void ShibbolethInstitutionalloginSelectCountryPalgraveJournals(){
        	loginS.navigateShibboleth("palgrave-journals.com/nams/svc/institutelogin");
        	loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        	loginS.ShibbolethCountry("United Kingdom");
    		loginS.ShibbolethGo().click();
    		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		
    		assertEquals(loginS.Browser().getCurrentUrl(),
    				loginS.createUrl()+"palgrave-journals.com/nams/svc/institutelogin?target=$request.getParameter(");
        	commonfun.captureScreen(loginS.Browser());
        }
        
        @Test(description = "Shibboleth institutional login-Search Institution-Palgrave Journals")
        public void ShibbolethInstitutionalloginSearchInstitutionPalgraveJournals(){
        	loginS.navigateShibboleth("palgrave-journals.com/nams/svc/institutelogin");
        	loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        	loginS.ShibbolethCountry("United Kingdom");
    		loginS.SearchInstitution().sendKeys("Brunel University");
    		loginS.SearchButton().click();
    		assertEquals(loginS.ShibbolethBrunalUniversity().getText(),
    				"Brunel University");
        	commonfun.captureScreen(loginS.Browser());
        }
        
        @Test(description="Shibboleth institutional login- " +
        		"When user clicks an institution name, " +
        		"user is redirected to institution website and " +
        		"then to palgrave-journals.com after Istituional Login")
        public void ShibbolethInstitutionalLoginUserRedirectPalgraveJournals(){
        	ShibbolethInstitutionalloginSearchInstitutionPalgraveJournals();
        	loginS.ShibbolethBrunalUniversity().click();
    		assertEquals(loginS.Browser().getCurrentUrl(),
    				"https://shibsles.brunel.ac.uk/idp/Authn/UserPassword");
        	
        	loginS.BrunalUniversityUserName().sendKeys("lbsrshib");
        	loginS.BrunalUniversityPassword().sendKeys("Vn3wt8fh");
        	loginS.BrunalUniversityContinue().click();
        	loginS.Browser().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        	assertEquals(loginS.WelecomeToPalgrave().getText(),
        			"Welcome to the Palgrave Macmillan Journals site");
        }
        
        @Test(description = "Access to a BE Jouranal/Article using " +
        		"Brunal University Shibboleth valid " +
        		"credentials-Palgrave Journals")
        public void LoginShibbolethToBEJournalsViaBrunalUniversityPalgraveJournals(){
       		loginS.navigateShibboleth("palgrave-journals.com/be/journal/" +
       				"v45/n1/full/be200938a.html");
    		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		loginS.beShibbolethLoginViaInstituteLink().click();
    		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		
    		loginS.ShibbolethCountry("United Kingdom");
    		loginS.SearchInstitution().sendKeys("Brunel University");
    		loginS.SearchButton().click();
    		loginS.BrunelUniversityLinkPalgrave().click();
    		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		
    		assertEquals(loginS.Browser().getCurrentUrl(),
    				"https://shibsles.brunel.ac.uk/idp/Authn/UserPassword");
        	
        	loginS.BrunalUniversityUserName().sendKeys("lbsrshib");
        	loginS.BrunalUniversityPassword().sendKeys("Vn3wt8fh");
        	loginS.BrunalUniversityContinue().click();
        	
        	loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        	assertEquals(loginS.beContentAssertion().getText(),"Professional economists" +
        			" are often tasked by local governments to study the economic and " +
        			"fiscal impacts of economic development projects, such as industry" +
        			" recruiting. A typical economic and fiscal impact study estimates" +
        			" the total economic output and job creation of a project. It also" +
        			" estimates the ripple economic impact this project brings to a" +
        			" region, as well as tax revenues generated for local government. In" +
        			" such studies, three types of impact—direct, indirect, and induced" +
        			" are usually analyzed. The direct impact refers to the total spending" +
        			" and job creation directly associated with an economic development" +
        			" project. For example, in the case of a construction project, direct" +
        			" impact refers to the total amount of construction spending and" +
        			" the construction jobs created by this project. Indirect impact" +
        			" refers to economic benefits to other regional businesses that" +
        			" support the direct spending. In the case of a construction project," +
        			" indirect impact is measured as increased sales for regional businesses" +
        			" that provide support services for construction, such as truck transportation" +
        			" and equipment leasing. Induced impact refers to the economic benefits to" +
        			" regional businesses as a result of additional household income. In" +
        			" the case of a construction project, the workers on the project will" +
        			" spend their wages at local businesses such as restaurants and retail" +
        			" shops. Those additional sales by local business are classified as induced impact.");
//    		assertEquals(loginS.Browser().getCurrentUrl(),
//    				loginS.createUrl()+"palgrave-journals.com/be/journal/v45/n1/full/be200938a.html");
    		commonfun.captureScreen(loginS.Browser());
        }
        
        @Test(description = "Access to a BE Jouranal/Article using " +
        		"Brunal University Shibboleth invalid " +
        		"credentials-Palgrave Journals")
        public void LoginShibbolethToBEJournalsViaBrunalUniversityWithInvalidCredentialsPalgraveJournals(){
       		loginS.navigateShibboleth("palgrave-journals.com/be/journal/" +
       				"v45/n1/full/be200938a.html");
    		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		loginS.beShibbolethLoginViaInstituteLink().click();
    		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		
    		loginS.ShibbolethCountry("United Kingdom");
    		loginS.SearchInstitution().sendKeys("Brunel University");
    		loginS.SearchButton().click();
    		loginS.BrunelUniversityLinkPalgrave().click();
    		loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		
    		assertEquals(loginS.Browser().getCurrentUrl(),
    				"https://shibsles.brunel.ac.uk/idp/Authn/UserPassword");
        	
        	loginS.BrunalUniversityUserName().sendKeys("lbsrshib1");
        	loginS.BrunalUniversityPassword().sendKeys("Vn3wt8fh1");
        	loginS.BrunalUniversityContinue().click();
        	
        	loginS.Browser().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        	assertEquals(loginS.BrunalUniversityWrongCredentials().getText(),
        			"Credentials not recognised.");
    		commonfun.captureScreen(loginS.Browser());
        }
        
        @AfterMethod
        public void QuitBrowser() {
            loginS.Browser().quit();
        }

}