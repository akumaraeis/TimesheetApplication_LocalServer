package com.cms.testcases;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import com.cms.utility.Log;
import com.cms.utility.Utility;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Validate_AttendanceFunctionality extends BaseTest {
	public SoftAssert sf;
	public JavascriptExecutor js;
	public boolean isSuccessful = false;
	public String clockInDate;
	public boolean isTaskSuccessful = false ;
	public String FinalAlert;
	public String Timein ;
	public String clockInTime;
	public String updatedTimeStr;
	public String updatedTime2;

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
		//		Thread.sleep(2000);
		LaunchUrl();
	}

	@Test(priority=0)
	public static  String getToken() {
		RestAssured.baseURI = "http://192.168.1.10:8085";

		Response response = given()
				.header("Content-Type", "application/json")
				.body("{ \"username\": \"AutomationTestUser\", \"password\": \"Test@123\" }")
				.when()
				.post("/api/auth/login/")
				.then()
				.statusCode(200)
				.extract().response();

		// Extract token from response
		JsonPath jsonPath = response.jsonPath();
		String token = response.jsonPath().getString("data.token");  // Change key if API returns it with a different name

		System.out.println("Token: " + token);
		return token;

	}
	@Test(priority=1)
	public void DeleteTestUserRecord() throws InterruptedException, IOException
	{

		RestAssured.baseURI = "http://192.168.1.10:8085";
		Response loginResponse = RestAssured.given()
				.header("Content-Type", "application/json")
				.body("{ \"username\": \"AutomationTestUser\", \"password\": \"Test@123\" }")
				.when().post("/api/auth/login/")
				.then().statusCode(200)
				.extract().response();
		String token = loginResponse.jsonPath().getString("data.token");
		System.out.println("🔐 Token fetched: " + token);
		DeleteAutomationTestUserRecords(token);

	}

	@Test(priority=1)
	public void ValidateClockIn() throws InterruptedException, IOException
	{

		System.out.println("Final Token "+ (getToken()));
		clockInDate = att.generateRandomDate(); // Generate a date in February
		//		clockInTime = att.timestamp();
		clockInTime = "05:00:00";
		Timein = clockInDate+"T"+clockInTime +"Z";
		System.out.println("Final Time-In given :->"+Timein);

		HashMap data = new HashMap();
		data.put("test_timestamp", Timein );
		System.out.println("found accountId detail for delete functions : "+Timein);
		given()
		.contentType("application/json")
		.headers("Authorization",("Token "+getToken()))
		.body(data)

		.when()
		.post("http://192.168.1.10:8085/api/attendance-test/clockin/")

		.then()
		.statusCode(201)
		.log().all();
		System.out.println("******Clock-in Functionality Verified************");
	}	

	@Test(priority=2,dependsOnMethods="ValidateClockIn")
	public void ValidateBreakIn() throws InterruptedException, IOException
	{

		System.out.println("Final Token "+ (getToken()));
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime originalTime = LocalTime.parse(clockInTime, timeFormatter);
		LocalTime updatedTime = originalTime.plusHours(1);
		updatedTimeStr = updatedTime.format(timeFormatter);
		Timein  = clockInDate+"T"+updatedTimeStr +"Z";
		System.out.println("Final Time-In given :->"+updatedTimeStr);

		HashMap data = new HashMap();
		data.put("test_timestamp", Timein );
		System.out.println("found accountId detail for delete functions : "+Timein);
		given()
		.contentType("application/json")
		.headers("Authorization",("Token "+getToken()))
		.body(data)

		.when()
		.patch("http://192.168.1.10:8085/api/attendance-test/breakin/")

		.then()
		.statusCode(201)
		.log().all();
		System.out.println("******BreakIn Functionality is  Verified************");
	}

	@Test(priority=3,dependsOnMethods="ValidateBreakIn")
	public void ValidateBreakOut() throws InterruptedException, IOException
	{

		System.out.println("Final Token "+ (getToken()));
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime originalTime = LocalTime.parse(updatedTimeStr, timeFormatter);
		LocalTime updatedTime2 = originalTime.plusHours(1);
		String updatedTimeStr = updatedTime2.format(timeFormatter);

		String Timein = clockInDate+"T"+updatedTimeStr +"Z";
		System.out.println("Final Time-In given :->"+Timein);

		HashMap data = new HashMap();
		data.put("test_timestamp", Timein );
		System.out.println("found accountId detail for delete functions : "+Timein);
		given()
		.contentType("application/json")
		.headers("Authorization",("Token "+getToken()))
		.body(data)

		.when()
		.patch("http://192.168.1.10:8085/api/attendance-test/breakout/")

		.then()
		.statusCode(201)
		.log().all();
		System.out.println("******BreakOut Functionality is Verified************");
	}

	@Test(priority=4,dependsOnMethods="ValidateBreakOut")
	public void ValidateClockOut() throws InterruptedException, IOException
	{

		System.out.println("Final Token "+ (getToken()));
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime originalTime2 = LocalTime.parse(updatedTimeStr, timeFormatter);
		LocalTime updatedTime3 = originalTime2.plusHours(8);
		String updatedTimeStr = updatedTime3.format(timeFormatter);

		String Timein = clockInDate+"T"+updatedTime3 +"Z";
		System.out.println("Final Time-In given :->"+Timein);

		HashMap data = new HashMap();
		data.put("test_timestamp", Timein );
		System.out.println("found accountId detail for delete functions : "+Timein);
		given()
		.contentType("application/json")
		.headers("Authorization",("Token "+getToken()))
		.body(data)

		.when()
		.patch("http://192.168.1.10:8085/api/attendance-test/clockout/")

		.then()
		.statusCode(201)
		.log().all();
		System.out.println("******Clock-Out Functionality is Verified************");
	}

	@Test(priority =5 ,dependsOnMethods="ValidateClockIn")
	public void ValidateAddedTimesheetEntry() throws InterruptedException, IOException, ParseException
	{
        Utility.showTooltip("Now Launching Timesheet Application to validate on UI for Clock-in/Out & Break-in/Out logged using API ");
        sf = new SoftAssert();
		launchLocalUrl();
        Log.info("URL IS Launched");
        
		Utility.waitForSeconds(2);

		lp.SendUserName();

		lp.SendPassword();

		lp.ClickonLoginBtn();

		String ActualProfileName=lp.GetProfileName();
		String ExpectedProfileName ="Welcome, AutomationTesting";

		sf.assertEquals(ActualProfileName, ExpectedProfileName);

		String inputDate = clockInDate;
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy");

		Date date = inputFormat.parse(inputDate);
		String formattedDate = outputFormat.format(date);
		System.out.println("Formatted date after apply condition :->"+formattedDate);
		att.SendDateFilter(formattedDate);
		Thread.sleep(2000);


		List<WebElement> timesheetRows = driverR.findElements(By.xpath("//*[@class='shadow table table-light table-sm table-striped table-bordered table-hover']//tbody//tr"));


		boolean entryFound = false;

		for (WebElement row : timesheetRows) {
			try {
				// 1. Get the 3rd column (assuming that's where date lives)
				WebElement dateTimeColumn = row.findElement(By.xpath(".//td[3]"));
				String rowDateTime = dateTimeColumn.getText().trim();
				String ActualClockinDate = rowDateTime.substring(0, 10);
				System.out.println("Actual Date found after trim"+ActualClockinDate);
				System.out.println("Checking row with date: " + rowDateTime);
				String ExpectClockinDate = formattedDate;
				sf.assertEquals(ActualClockinDate, ExpectClockinDate);

				// 2. Match the date
				if (rowDateTime.startsWith(formattedDate)) {
					System.out.println("✅ Matching Entry Found: " + rowDateTime);
				}
				else
				{
					System.out.println("✅ Matching Entry Not Found: " + rowDateTime);
				}
			}

			catch(Exception e)
			{
				System.out.println("Exception detail"+ e.getMessage());

			}
		} 
		sf.assertAll();
	}


	public void DeleteAutomationTestUserRecords(String token) {
   		given()
		.contentType("application/json")
		.header("Authorization", "Token " + token)
		//        .body(data)
		.when()
		.post("http://192.168.1.10:8085/api/utils/remove-automation-test-data/")
		.then()
		.statusCode(200)
		.log().all();
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



