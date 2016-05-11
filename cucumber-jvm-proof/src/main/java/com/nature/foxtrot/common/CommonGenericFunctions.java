package com.nature.foxtrot.common;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CommonGenericFunctions {

	
	public  void captureScreen(WebDriver driver, String testcaseName)  {
		
		try {
//			Thread t=Thread.currentThread();
//		    StackTraceElement element=t.getStackTrace()[2];
		    File screenshot = ((TakesScreenshot)driver).
		                        getScreenshotAs(OutputType.FILE);
//		    FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir")+"/src/screenshots/"+element.getMethodName()+".jpg"));
		    FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir")+"/src/screenshots/"+testcaseName+".jpg"));
			
		} catch (IOException e){
			e.printStackTrace();
		}
    
	}
	
	public long randomNumber(){
		
		Date dt = new Date();
		long milliseconds = dt.getTime();
//		System.out.println(milliseconds);
		
		return milliseconds;
	}

}
