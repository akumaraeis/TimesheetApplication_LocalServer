package com.cms.testcases;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.cms.basetest.BaseTest;
import com.cms.utility.Log;
import com.cms.utility.Utility;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class NegativeScenarioTimesheetSubmissionApproval_TaskHrLesser extends BaseTest {
	public SoftAssert sf;
	public SoftAssert sf2;
	public JavascriptExecutor js;
	public boolean isSuccessful = false;
	public String clockInDate;
	public boolean isTaskSuccessful = false ;
	public String FinalAlert;
	boolean keepProcessing = true;
	public String clockInTime;
	public String Timein;
	public String updatedTimeStr;
	public String token;
	public String Start_date ;
	public String weekContainerXPath;
	int totalWeeks ;
	public WebElement MinimizeBtn;
	public String ActualUserName;
	public String ExpectTimesheetApproveMsg;
	public String dateStr;
	public WebDriverWait wait;
	public String startText;
	public String endText;
	
	@BeforeClass
	@Parameters("browser")
	public void openUrl(String browser) throws IOException
	{
		initBrowser(browser);
		creatingObject();	
	}

	//	@BeforeMethod
	public void LaunchUrl() throws IOException, InterruptedException
	{
		LaunchUrl();
	}
	
	@Test(priority=1)
	public void DeleteTestUserRecord() throws InterruptedException, IOException
	{
		RestAssured.baseURI = getFile("baseurl");
		Response loginResponse = RestAssured.given()
				.header("Content-Type", "application/json")
				.body("{ \"username\": \"AutomationTestUser\", \"password\": \"Test@123\" }")
				.when().post("/api/auth/login/")
				.then().statusCode(200)
				.extract().response();
		token = loginResponse.jsonPath().getString("data.token");
		System.out.println("🔐 Token fetched: " + token);
		DeleteAutomationTestUserRecords(token);
	}
@Test(priority=0)
public void shouldNotsubmitTimesheetwhenTaskdurationLesser() throws IOException, InterruptedException
{
	sf = new SoftAssert();
	loginAsUser();
	creatingPreviousWeekTimesheetEntry();
	
}

	@Test(priority=2)
	public void ValidateTimesheetSubmissionFunctionality() throws InterruptedException, IOException
	{
		String str =  "Welcome, AutomationTesting";
		ActualUserName = str.replace("Welcome, ", "");
		System.out.println(ActualUserName);

//		sf.assertEquals(ActualProfileName, ExpectedProfileName);
		tsp.ClickonTimesheetSubmission();

		Utility.waitForSeconds(3);
		Utility.showTooltip("Step 2:-> Selecting Respective Week to Validate Timesheet submission Using Automation Script");
		Thread.sleep(5000);
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		Utility.showTooltip("Step 3 :-> After Selecting Week, Timesheet Entry from (Mon–Fri) are created in Background using API RestAssured");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");

		LocalDate today = LocalDate.now();


		LocalDate previousWeekMonday = today
				.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
				.minusWeeks(1);


		LocalDate previousWeekFriday = previousWeekMonday.plusDays(4);

		String prevMondayText = previousWeekMonday.format(formatter);
		String prevFridayText = previousWeekFriday.format(formatter);

		System.out.println("Previous Week Monday: " + prevMondayText);
		System.out.println("Previous Week Friday: " + prevFridayText);
		startText = prevMondayText;
		System.out.println("Previous week Monday :->"+startText);
		endText   = prevFridayText;
		System.out.println("Previous week Friday :->"+endText);

		DateTimeFormatter inputFormatter2 = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);
		LocalDate startDate = LocalDate.parse(startText, inputFormatter);
		LocalDate endDate   = LocalDate.parse(endText, inputFormatter);

		System.out.println("Parsed Start Date: " + startDate + " | End Date: " + endDate);

		DateTimeFormatter outputFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String baseTime = "04:30:00";

		// 🔥 Loop from startDate to endDate (inclusive)
		for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {

			String dateStr = date.format(outputFormatter2);

			System.out.println("Date Stamp entry for timestamp: " + dateStr);
			System.out.println("🗓️ Submitting entries for: → " + dateStr);

			sendAttendanceData(token,  "CLOCK_IN",  dateStr, baseTime);
			sendAttendanceData2(token, "BREAK_IN",  dateStr, addHours(baseTime, 1));
			sendAttendanceData2(token, "BREAK_OUT", dateStr, addHours(baseTime, 2));
			sendAttendanceData2(token, "CLOCK_OUT", dateStr, addHours(baseTime, 9));

			Utility.waitForSeconds(2);
			Log.info("Timesheet entry created successfully for date → " + dateStr);
		}
		System.out.println("now script will start adding task");
		Utility.waitForSeconds(2);
		// Add task and submit timesheet
		driverR.navigate().refresh();
		Utility.waitForSeconds(2);
		String weekXpath = "//div[contains(@class,'m-1 px-1 py-0 row')]";
		WebElement refreshedWeek = driverR.findElement(By.xpath(weekXpath));
		js = (JavascriptExecutor)driverR;
		Utility.scrollIntoView(driverR, js, refreshedWeek);
		Utility.safeClick(driverR, js, refreshedWeek);
		System.out.println("now script will click on week");

		Thread.sleep(2000);
        tsp.addingTaskToAvailableWeek();
		Utility.showTooltip("Step 5:-> After adding Task, submitting this weekly Timesheet using Automation Script");

		tsp.clickOnSubmitBtn();
		sf.assertAll();
	}

	public String addHours(String baseTime, int hoursToAdd) {

		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime time = LocalTime.parse(baseTime, timeFormatter);
		return time.plusHours(hoursToAdd).format(timeFormatter);
	}

	public void loginAsUser() throws IOException, InterruptedException
	{
		Utility.showTooltip("Executing Automation Script to Check Timesheet Submission by User and Approval Functionality by Line Manager.");

		Utility.waitForSeconds(5);

		launchLocalUrl();
		Log.info("Url is launched");

		Utility.showTooltip("Step 1:->After Launching Timesheet Application, Login as User to submit Weekly Timesheet using Automation Script");

	

		lp.SendUserName();
		Log.info("input user name");

		lp.SendPassword();
		Log.info("input user Password");

		lp.ClickonLoginBtn();
		Log.info("Click on Login Button");

		String ActualProfileName=lp.GetProfileName();
		String ExpectedProfileName ="Welcome, AutomationTesting!";
		Log.info("Profile Name is verified");
	}
	
	public void creatingPreviousWeekTimesheetEntry()
	{
		
	}
	public void sendAttendanceData(String token,String action, String timestamp, String time) throws IOException {
		HashMap<String, String> data = new HashMap<>();
		data.put("action", action);
		data.put("action_date", timestamp);
		data.put("action_time", time);

		System.out.println("→ Sending to " + ": " + timestamp);
		given()
		.contentType("application/json")
		.header("Authorization", "Token " + token)
		.body(data)
		.when()
		.post(getFile("PostApi"))
		.then()
		.statusCode(200)
		.log().all();
	}
	public void sendAttendanceData2(String token,String action, String timestamp, String time) throws IOException {
		HashMap<String, String> data = new HashMap<>();
		data.put("action", action);
		data.put("action_date", timestamp);
		data.put("action_time", time);
		System.out.println("→ Sending to " + ": " + timestamp);

		given()
		.contentType("application/json")
		.header("Authorization", "Token " + token)
		.body(data)
		.when()
		.post(getFile("PostApi"))
		.then()
		.statusCode(200)
		.log().all();
	}
	public void DeleteAutomationTestUserRecords(String token) throws IOException {
		given()
		.contentType("application/json")
		.header("Authorization", "Token " + token)
		.when()
		.post(getFile("DeletePostApi"))
		.then()
		.statusCode(200)
		.log().all();
	}

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


