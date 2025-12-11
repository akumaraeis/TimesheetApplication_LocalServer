package com.cms.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.cms.basetest.BaseTest;
import com.cms.utility.Utility;

public class TimeOverlappingScenarioTest extends BaseTest {
	public SoftAssert sf;
	public JavascriptExecutor js;
	public boolean isSuccessful = false;
	public String clockInDate ="";
	public boolean isTaskSuccessful = false ;
	public String FinalAlert;
//	public String FinalAlert2;


	@BeforeClass
	@Parameters("browser")
	public void openUrl(String browser) throws IOException
	{
		initBrowser(browser);
		creatingObject();	
		//		Utility.showTooltip("Browser is Launched using Selenium Automation Tool");
		sf = new SoftAssert();
	}

	//	@BeforeMethod
	public void LaunchUrl() throws IOException, InterruptedException
	{
		//		Thread.sleep(2000);
		LaunchUrl();
	}

	@Test(priority=1)
	public void ValidateTimeOverlappingScenario() throws InterruptedException, IOException
	{

		launchLocalUrl();

		Thread.sleep(2000);

		lp.SendUserName();

		lp.SendPassword();

		lp.ClickonLoginBtn();

		String ActualProfileName=lp.GetProfileName();
		String ExpectedProfileName ="Welcome, AutomationTesting (User)";

		sf.assertEquals(ActualProfileName, ExpectedProfileName);
		tol.ClickonAddNewTimesheet();
 
		
		while(!isSuccessful)
		{
			clockInDate = att.generateRandomDate();
			 // Generate a date in February
			tol.SelectClockinDate(clockInDate);
			Thread.sleep(2000);
			tol.SelectClockOutDate(clockInDate);
			Thread.sleep(2000);
			tol.SelectBreakDuration();
			Thread.sleep(2000);
			tol.ClickonSubmit();

			 FinalAlert = tol.GetTaskAlert();
            
			if (FinalAlert.equals("Timesheet created successfully!")) {
				isSuccessful = true;  // Exit the loop if successful
				
			} else {
				// Optionally, you can add a delay or retry logic here
				Thread.sleep(1000); // Example: wait for 1 second before retrying
//			    tol.ClickonBackBtn();
//			    Thread.sleep(1000);
//			    tol.ClickonAddNewTimesheet();
			}
				
		}
//		String enteredTimesheetDate = clockInDate ; // Example input date
//		String formattedDate = att.convertDateFormat(enteredTimesheetDate);
//		
//		att.SendDateFilter(formattedDate);
//		Thread.sleep(2000);
		tol.ClickonAddNewTimesheet();
		Thread.sleep(2000);
			 // Generate a date in February
			tol.SelectClockinDate2(clockInDate);
			Thread.sleep(2000);
			tol.SelectClockOutDate2(clockInDate);
			Thread.sleep(2000);
			tol.SelectBreakDuration();
			Thread.sleep(2000);
			tol.ClickonSubmit();

			 String FinalAlert2 = tol.GetTaskAlert();	
	
		String ActMsg = FinalAlert2;
		String ExpMsg ="Time entry overlaps with an existing timesheet.";
//		String ExpMsg ="Timesheet created successfully!";
		sf.assertEquals(ActMsg, ExpMsg);


		sf.assertAll();

	}
	//	@AfterMethod
	public void closeURL()
	{
		driverR.navigate().to("about:blank");
	}

	@AfterClass
	public void closebrowser()
	{

		teardown();
	}

}



