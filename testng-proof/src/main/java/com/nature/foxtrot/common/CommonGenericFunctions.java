package com.nature.foxtrot.common;

import java.io.File;
import java.io.IOException;
import java.util.Date;
//import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CommonGenericFunctions {

	
	public  void captureScreen(WebDriver driver)  {
		
		try {
			Thread t=Thread.currentThread();
		    StackTraceElement element=t.getStackTrace()[2];
		    File screenshot = ((TakesScreenshot)driver).
		                        getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir")+
		    		"/src/screenshots/"+element.getMethodName()+".jpg"));
			
		} catch (IOException e){
			e.printStackTrace();
		}
    
	}
	
//	public int randomNumber1(int Min, int Max){
//		Random rn = new Random();
//		int n = Max - Min + 1;
//		int i = rn.nextInt() % n;
//		int randomNum = Min + i;
//		return randomNum;
//	}
	
	public long randomNumber(){
		
		Date dt = new Date();
		long milliseconds = dt.getTime();
		System.out.println(milliseconds);
		
		return milliseconds;
	}
}
