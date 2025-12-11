package com.cms.testcases;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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
import com.cms.utility.Log;
import com.cms.utility.Utility;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class TimesheetReportTest extends BaseTest {
	public SoftAssert sf;
	public JavascriptExecutor js;
	public boolean isSuccessful = false;
	public String clockInDate;
	public String FinalAlert;
	public String Timein ;
	public String clockInTime;
	public String updatedTimeStr;
	public String updatedTime2;
	public String token ;
	
	@BeforeClass
	@Parameters("browser")
	public void openUrl(String browser) throws IOException
	{
		initBrowser(browser);
		creatingObject();	
		//		Utility.showTooltip("Browser is Launched using Selenium Automation Tool");
		sf = new SoftAssert();
	}

//		@BeforeMethod
	public void LaunchUrl() throws IOException, InterruptedException
	{
		//		Thread.sleep(2000);
		launchLocalUrl();
	}
	

	@Test(priority=1)
	public void ValidateTimesheetReports() throws InterruptedException, IOException, ParseException
	{

		SoftAssert sf = new SoftAssert();
		Utility.showTooltip("Step 1 :-> Launching Timesheet Application using Automation Script ");
		
		Log.info(" Application is launched");
		
		launchLocalUrl();
		
		Thread.sleep(2000);

		lp.SendLine_ManagerName();

		lp.SendAdminPassword();

		lp.ClickonLoginBtn();
		
		Log.info("Line manager logged in successfully");
		
		Utility.showTooltip("Step 2 :->Now login with Line Manager Credential to Validate User status on Dashboard before user Clock-in ");
		
		String ActualProfileName=lp.GetProfileName();
		String ExpectedProfileName ="Welcome, LNM Testuser";		
		sf.assertEquals(ActualProfileName, ExpectedProfileName);
		Log.info("Profile name is verified");
		tsrp.ClickonTimesheetReports();
		tsrp.SelectReportByWeek();
		tsrp.SelectReport();
		
		Log.info("User Status is fetched");
	}
	

	
//		@AfterMethod
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



