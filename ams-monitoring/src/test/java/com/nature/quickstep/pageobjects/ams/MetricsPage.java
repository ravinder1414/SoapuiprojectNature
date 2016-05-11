package com.nature.quickstep.pageobjects.ams;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.nature.quickstep.pageobjects.PageObject;

/***
 * This class gives the page objects of the article metrics page for any article
 * @author suresh.kumar
 *
 */

public class MetricsPage extends PageObject{

	
	@Override
	public void navigateTo() throws Exception {
			
	}
	
	/**
	 * Navigates to the nature RelativeUrl
	 * @throws Exception 
	 */
	public void navigateToRelativeURL(String RelativeUrl) throws Exception  {
		browser.get(context.getURL("nature.com/"+RelativeUrl));
	}

	/**
	 * Returns true if Twitter count object is present
	 */
	@Override
	public boolean isPresent() {

		boolean result = false;
		try {
			result = browser.findElement(By.xpath("//*[@id='constrain-content']" +
					"/section[1]/div/article/header/span[2]")).isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	
	/**
	 * 
	 * @return last updated time label on the page
	 */
	public WebElement lblLastUpdated(){
		WebElement lastupdated =null;
		try {
			lastupdated = browser.findElement(By.xpath("//*[@id='constrain-content']" +
					"/section[1]/div/article/header/span[2]"));
			
		} catch (Exception e) {
		}
		
		return lastupdated;
		
	}
	/***
	 * 
	 * @return time difference between last updated time and current time
	 * @throws ParseException
	 */
	public double timediffinhours() throws ParseException{
		
		String str = lblLastUpdated().getText();
		
		String lastupdatedtime = str.replace("Last updated: ","");
		
		DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss zzz");
		Date lastupdatedate = dateFormat.parse(lastupdatedtime);
		
		
		double timeDiff =  Calendar.getInstance().getTime().getTime() - lastupdatedate.getTime() ;
		timeDiff = timeDiff / (1000 * 60 * 60 );
		     
		
		return timeDiff;
		
	}
	
	/***
	 * 
	 * @return true if last updated time of article is within 24 hours
	 * @throws ParseException
	 */
	
	public boolean validatelastupdatedtime() throws ParseException{
		if (timediffinhours() < 24.0){
			return true;
		}else {
			return false;
		}
	}
}
