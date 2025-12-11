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
import com.cms.utility.Utility;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class AddTaskTest extends BaseTest {
	public SoftAssert sf;
	public JavascriptExecutor js;
	public boolean isSuccessful = false;
	public String clockInDate;
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
		//		Utility.showTooltip("Browser is Launched using Selenium Automation Tool");
		Utility.showTooltip("Executing Automation Script to Validate Add Task Scenario");
		sf = new SoftAssert();
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


@Test(priority=2)
public void ValidateClockIn() throws InterruptedException, IOException
{

       System.out.println("Final Token "+ (getToken()));
		clockInDate = att.generateRandomDate(); // Generate a date in February
        clockInTime = "05:00:00";
//		clockInTime = att.timestamp();
		Timein = clockInDate+"T"+clockInTime +"Z";
		System.out.println("Final Time-In given :->"+Timein);

		HashMap data = new HashMap();
		data.put("action", "CLOCK_IN" );
		data.put("action_date", clockInDate );
		data.put("action_time", clockInTime );
		System.out.println("found accountId detail for delete functions : "+Timein);
		given()
		.contentType("application/json")
		.headers("Authorization",("Token "+getToken()))
		.body(data)

		.when()
		.post("http://192.168.1.10:8085/api/clock-entries/action-test/")

		.then()
		.statusCode(200)
		.log().all();
		System.out.println("******Clock-in Functionality Verified************");

		
	}	
	
@Test(priority=3,dependsOnMethods="ValidateClockIn")
public void ValidateBreakIn() throws InterruptedException, IOException
{

       System.out.println("Final Token "+ (getToken()));
//		clockInDate = att.generateRandomDate(); // Generate a date in February
//		String clockInTime = att.timestamp();
       DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
       LocalTime originalTime = LocalTime.parse(clockInTime, timeFormatter);
       LocalTime updatedTime = originalTime.plusHours(1);
        updatedTimeStr = updatedTime.format(timeFormatter);
		 Timein  = clockInDate+"T"+updatedTimeStr +"Z";
		System.out.println("Final Time-In given :->"+updatedTimeStr);

		HashMap data = new HashMap();
		data.put("action", "BREAK_IN" );
		data.put("action_date", clockInDate );
		data.put("action_time", updatedTimeStr );
		System.out.println("found accountId detail for delete functions : "+Timein);
		given()
		.contentType("application/json")
		.headers("Authorization",("Token "+getToken()))
		.body(data)

		.when()
		.post("http://192.168.1.10:8085/api/clock-entries/action-test/")

		.then()
		.statusCode(200)
		.log().all();
		System.out.println("******Clock-in Functionality Verified************");
}

@Test(priority=4,dependsOnMethods="ValidateBreakIn")
public void ValidateBreakOut() throws InterruptedException, IOException
{

       System.out.println("Final Token "+ (getToken()));
//		clockInDate = att.generateRandomDate(); // Generate a date in February
//		String clockInTime = att.timestamp();
           DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
           LocalTime originalTime = LocalTime.parse(updatedTimeStr, timeFormatter);
           LocalTime updatedTime2 = originalTime.plusHours(1);
           String updatedTimeStr = updatedTime2.format(timeFormatter);

		String Timein = clockInDate+"T"+updatedTimeStr +"Z";
		System.out.println("Final Time-In given :->"+Timein);

		HashMap data = new HashMap();
		data.put("action", "BREAK_OUT" );
		data.put("action_date", clockInDate );
		data.put("action_time", updatedTimeStr );
		System.out.println("found accountId detail for delete functions : "+Timein);
		given()
		.contentType("application/json")
		.headers("Authorization",("Token "+getToken()))
		.body(data)

		.when()
		.post("http://192.168.1.10:8085/api/clock-entries/action-test/")

		.then()
		.statusCode(200)
		.log().all();
		System.out.println("******Clock-in Functionality Verified************");

}

@Test(priority=5,dependsOnMethods="ValidateBreakOut")
public void ValidateClockOut() throws InterruptedException, IOException
{

       System.out.println("Final Token "+ (getToken()));
//		clockInDate = att.generateRandomDate(); // Generate a date in February
//		String clockInTime = att.timestamp();
           DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
           LocalTime originalTime2 = LocalTime.parse(updatedTimeStr, timeFormatter);
           LocalTime updatedTime3 = originalTime2.plusHours(8);
           String updatedTimeStr = updatedTime3.format(timeFormatter);

		String Timein = clockInDate+"T"+updatedTime3 +"Z";
		System.out.println("Final Time-In given :->"+Timein);

		HashMap data = new HashMap();
		data.put("action", "CLOCK_OUT" );
		data.put("action_date", clockInDate );
		data.put("action_time", updatedTimeStr );
		System.out.println("found accountId detail for delete functions : "+Timein);
		given()
		.contentType("application/json")
		.headers("Authorization",("Token "+getToken()))
		.body(data)

		.when()
		.post("http://192.168.1.10:8085/api/clock-entries/action-test/")

		.then()
		.statusCode(200)
		.log().all();
		System.out.println("******Clock-in Functionality Verified************");

}
	@Test(priority=6)
	public void ValidateAddTaskFunctionalityAfterClockOut() throws InterruptedException, IOException, ParseException
	{
		Utility.showTooltip("Step 1 :-> Launching Timesheet Application using Automation Script ");

		launchLocalUrl();

		Thread.sleep(2000);

		lp.SendUserName();

		lp.SendPassword();

		lp.ClickonLoginBtn();

		String ActualProfileName=lp.GetProfileName();
		String ExpectedProfileName ="Welcome, AutomationTesting!";

		sf.assertEquals(ActualProfileName, ExpectedProfileName);
		
		Utility.showTooltip("User Profile Name Validated using AutomationScript.");

		    String inputDate = clockInDate;
	        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
	        SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy");

	        Date date = inputFormat.parse(inputDate);
	        String formattedDate = outputFormat.format(date);
            Thread.sleep(2000);
//		att.SendDateFilter(formattedDate);
//		Thread.sleep(2000);
		driverR.findElement(By.xpath("//button[normalize-space()='Clear']")).click();
	        

		List<WebElement> timesheetRows = driverR.findElements(By.xpath("//*[@class='shadow table table-light table-sm table-striped table-bordered table-hover']//tbody//tr"));

		
		boolean entryFound = false;

		for (WebElement row : timesheetRows) {
		    try {
		        // 1. Get the 3rd column (assuming that's where date lives)
		        WebElement dateTimeColumn = row.findElement(By.xpath(".//td[3]"));
		        String rowDateTime = dateTimeColumn.getText().trim();
		        
		        System.out.println("Checking row with date: " + rowDateTime);

		        // 2. Match the date
		        if (rowDateTime.startsWith(formattedDate)) {
		            System.out.println("✅ Matching Entry Found: " + rowDateTime);

		            // 3. Try to find "ADD TASK" inside this same row
		            List<WebElement> addTaskButtons = row.findElements(By.xpath(".//*[contains(translate(text(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'ADD TASK')]"));
		            
		            if (!addTaskButtons.isEmpty()) {
		                WebElement addTaskButton = addTaskButtons.get(0);

		                Utility.ExplicitWait(addTaskButton);
		                JavascriptExecutor js = (JavascriptExecutor)driverR;
		                js.executeScript("arguments[0].scrollIntoView(true);", addTaskButton);

		                Thread.sleep(1000);
		                addTaskButton.click();

		                System.out.println("🟢 Clicked 'ADD TASK' for date: " + formattedDate);
		                entryFound = true;
		                break;
		            } else {
		                System.out.println("⚠️ 'ADD TASK' button not found in matched row.");
		            }
		        }

		    } catch (NoSuchElementException e) {
		        System.out.println("❌ Error in row: Element not found - " + e.getMessage());
		    }
		}

		if (!entryFound) {
		    System.out.println("❌ No matching entry found with 'ADD TASK' for date: " + formattedDate);
		}

//				att.SelectCoreProcess();

				att.SelectSubProcess();

				att.ClickonActivity();

				att.SendTaskDescription();

				att.SendTaskDuration();

				att.ClickonTaskSubmit();

				String ActualSuccessfulMsg = att.GetTaskSuccessfulNotification();

				String ExpectSuccessfulMsg = "Task created successfully!";

				sf.assertEquals(ActualSuccessfulMsg, ExpectSuccessfulMsg);
				
				Utility.showTooltip("Task created successfully!");

				Utility.waitForSeconds(2);

				sf.assertAll();
			}
		
		//		att.ClickonAddTask();


	public void DeleteAutomationTestUserRecords(String token) {
	    given()
	        .contentType("application/json")
	        .header("Authorization", "Token " + token)
//	        .body(data)
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



