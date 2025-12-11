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

public class MultipleClockInTest extends BaseTest {
	public SoftAssert sf;
	public JavascriptExecutor js;
	public boolean isSuccessful ;
	public String clockInDate ;
	public boolean isTaskSuccessful = false ;
	public String FinalAlert;


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
	public void ValidateMultipleClockInScenario() throws InterruptedException, IOException
	{

		launchLocalUrl();

		Thread.sleep(2000);

		lp.SendUserName();

		lp.SendPassword();

		lp.ClickonLoginBtn();

		String ActualProfileName=lp.GetProfileName();
		String ExpectedProfileName ="Welcome, AutomationTesting (User)";

		sf.assertEquals(ActualProfileName, ExpectedProfileName);
//		mcp.ClickonAddNewTimesheet();
		String clockinTime="10:30";
		clockInDate = att.generateRandomDate();
		
		for (int i =1;i<=4;i++)
		{			
			 isSuccessful = false; 
			mcp.ClickonTimesheet();
			System.out.println("Times after clicking on Timesheet :=>"+i);
		while(!isSuccessful)
		{
			
			mcp.SelectClockinDate(clockInDate);			
			Thread.sleep(2000);
			mcp.SelectClockinTime(clockinTime);
			Thread.sleep(2000);
			mcp.SelectClockOutDate(clockInDate);
			Thread.sleep(2000);
			String clockoutTime = mcp.addhorsToTime(clockinTime, 4);
			mcp.SelectClockoutTime(clockoutTime);
			Thread.sleep(2000);
			mcp.SelectBreakDuration();
			Thread.sleep(2000);
			mcp.ClickonSubmit();
			FinalAlert = mcp.GetTaskAlert();
            
			if (FinalAlert.equals("Timesheet created successfully!")) {
				isSuccessful = true;
				clockinTime=clockoutTime;
				Thread.sleep(1000);
				
				// Exit the loop if successful
			}
		     else if (FinalAlert.equals("You have reached the maximum of 3 slots for today.")) {
	//	            System.out.println("Validation message appeared correctly after 3 timesheets: " + FinalAlert);
	//	            sf.assertEquals(FinalAlert, "You have reached the maximum of 3 slots for today.");
		            break; // Stop the loop
		        } else {
				// Optionally, you can add a delay or retry logic here
				Thread.sleep(1000); // Example: wait for 1 second before retrying
				clockInDate = att.generateRandomDate();
			}
			
		}	
		}
		String ActMsg = FinalAlert;
		String ExpMsg ="You have reached the maximum of 3 slots for today.";
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



