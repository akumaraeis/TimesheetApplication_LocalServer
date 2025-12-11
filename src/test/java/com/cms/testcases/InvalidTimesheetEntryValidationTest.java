package com.cms.testcases;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.cms.basetest.BaseTest;

public class InvalidTimesheetEntryValidationTest extends BaseTest {

	public SoftAssert sf;
	public JavascriptExecutor js;
	public boolean isSuccessful = false;
	public String clockInDate;
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
	public void ValidateInvalidTimesheetEntry() throws InterruptedException, IOException
	{

		launchLocalUrl();

		Thread.sleep(2000);

		lp.SendUserName();

		lp.SendPassword();

		lp.ClickonLoginBtn();

		String ActualProfileName=lp.GetProfileName();
		String ExpectedProfileName ="Welcome, AutomationTesting (User)";

		sf.assertEquals(ActualProfileName, ExpectedProfileName);
		itp.ClickonAddNewTimesheet();


			clockInDate = att.generateRandomDate(); // Generate a date in February
			itp.SelectClockinDate(clockInDate);
			Thread.sleep(2000);
			itp.SelectClockOutDate(clockInDate);
			Thread.sleep(2000);
			itp.SelectBreakDuration();
			Thread.sleep(2000);
			itp.ClickonSubmit();

			 FinalAlert = itp.GetTaskAlert();
            
		String ActMsg = FinalAlert;
		String ExpMsg ="Logout datetime must be after login datetime.";
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
