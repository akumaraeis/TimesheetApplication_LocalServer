package com.cms.testcases;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.NoSuchElementException;


import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import java.util.List;

public class TimesheetSubmissionScenario2 extends BaseTest {
	public SoftAssert sf;
	public JavascriptExecutor js;
	public boolean isSuccessful = false;
	public String clockInDate;
	public boolean isTaskSuccessful = false ;
	public String FinalAlert;
	boolean keepProcessing = true;
//	private int index=2;
	public String clockInTime;
	public String Timein;
	public String updatedTimeStr;
	public  String token;
	String Start_date ;
	String weekContainerXPath;
	int totalWeeks =0;
	public WebElement MinimizeBtn;
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
	public void DeleteTestUserRecord() throws InterruptedException, IOException
	{

        RestAssured.baseURI = "https://tsbackend.ndtatlas.com";
        Response loginResponse = RestAssured.given()
                .header("Content-Type", "application/json")
                .body("{ \"username\": \"AutomationTestUser\", \"password\": \"Test@123\" }")
                .when().post("/api/auth/login/")
                .then().statusCode(200)
                .extract().response();
         token = loginResponse.jsonPath().getString("data.token");
        System.out.println("🔐 Token fetched: " + token);
//        Utility.waitForSeconds(2);
        DeleteAutomationTestUserRecords(token);
//        Utility.waitForSeconds(1);

	}
	@Test(priority=2)
	public void ValidateaddNewTimesheetFunctionalityWithClockOutScenario() throws InterruptedException, IOException
	{

		Utility.showTooltip("Executing Automation Script to Check Negative Scenario that if Clock-out is missing");
		
		Thread.sleep(3000);
		
		launchLocalUrl();
		
		Utility.showTooltip("Step 1:-> After Launching Timesheet Application, Login as User and validate login Functionality");

		Thread.sleep(3000);

		lp.SendUserName();

		lp.SendPassword();

		lp.ClickonLoginBtn();

		String ActualProfileName=lp.GetProfileName();
		String ExpectedProfileName ="Welcome, AutomationTesting";

		sf.assertEquals(ActualProfileName, ExpectedProfileName);
		tsp.ClickonTimesheetSubmission();
		Thread.sleep(3000);
		Utility.showTooltip("Step 2:-> Selecting Respective Week to Validate Timesheet submission Using Automation Script");        
        Thread.sleep(5000);
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");



		List<WebElement> allWeeks = driverR.findElements(By.xpath("//div[contains(@class,'p-1 shadow mb-2 bg-gradient border-2')]"));
		System.out.println("Total week blocks found: " + allWeeks.size());
		totalWeeks = allWeeks.size();
        // API login
		
		Utility.showTooltip("Step 3 :->Now Creating Timesheet Entry from (Mon-Fri)such that Clock-Out is missing in one of entry using API RestAssured ");
	
		for (int index = 2; index <= totalWeeks; index++) {
		    String weekXPath = "(//div[contains(@class,'p-1 shadow mb-2 bg-gradient border-2')])[" + index + "]";
		    WebElement weekElement = driverR.findElement(By.xpath(weekXPath));
		    js = (JavascriptExecutor) driverR;
		    js.executeScript("arguments[0].scrollIntoView(true);", weekElement);
		    Utility.safeClick(driverR, js, weekElement);

		    WebDriverWait wait = new WebDriverWait(driverR, Duration.ofSeconds(10));
		    WebElement statusElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[contains(@class,'px-3 py-1')])[2]")));
		    String status = statusElement.getText().replace("\u00A0", " ").trim();
		    System.out.println("Week " + index + " Status: '" + status + "'");

		    if (status.equalsIgnoreCase("NOT SUBMITTED")) {
		        String startText = driverR.findElement(By.xpath("(" + weekXPath + "/div/div/span)[1]")).getText().trim();
		        String endText = driverR.findElement(By.xpath("(" + weekXPath + "/div/div/span)[3]")).getText().trim();

		        DateTimeFormatter inputFormatter2 = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);
		        LocalDate startDate = LocalDate.parse(startText, inputFormatter2);
		        LocalDate endDate = LocalDate.parse(endText, inputFormatter2);
		        System.out.println("Parsed Start Date: " + startDate + " | End Date: " + endDate);

	        DateTimeFormatter outputFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		            String baseTime = "04:30:00";
		            LocalDate date = startDate.plusDays(2);
		            String dateStr = date.format(outputFormatter2);
		            System.out.println("🗓️ Submitting entries for: " + dateStr);
		            sendAttendanceData(token, dateStr + "T" + baseTime + "Z", "clockin");
		            sendAttendanceData2(token, dateStr + "T" + addHours(baseTime, 1) + "Z", "breakin");
		            sendAttendanceData2(token, dateStr + "T" + addHours(baseTime, 2) + "Z", "breakout");
//		            sendAttendanceData2(token, dateStr + "T" + addHours(baseTime, 9) + "Z", "clockout");
		            Thread.sleep(2000);
		        
		        for (LocalDate date2 = startDate.plusDays(3); !date2.isAfter(endDate); date2 = date2.plusDays(1)) {
		            String dateStr2 = date2.format(outputFormatter2);
		            System.out.println("🗓️ Submitting entries for: " + dateStr2);
		            sendAttendanceData(token,  dateStr2 + "T" + baseTime + "Z", "clockin");
		            sendAttendanceData2(token, dateStr2 + "T" + addHours(baseTime, 1) + "Z", "breakin");
		            sendAttendanceData2(token, dateStr2 + "T" + addHours(baseTime, 2) + "Z", "breakout");
		            sendAttendanceData2(token, dateStr2 + "T" + addHours(baseTime, 9) + "Z", "clockout");
		            Thread.sleep(2000);
		        }

		        // Add task and submit timesheet
		        driverR.navigate().refresh();
		        Utility.waitForSeconds(2);
		        WebElement refreshedWeek = Utility.waitForElementToBeClickable(driverR, By.xpath(weekXPath), 10);
		        Utility.scrollIntoView(driverR, js, refreshedWeek);
		        Utility.safeClick(driverR, js, refreshedWeek);

		        List<WebElement> taskButtons = driverR.findElements(By.xpath("//*[contains(text(),'Add Task')]"));
		        System.out.println("Total Add Task buttons: " + taskButtons.size());
		        Utility.showTooltip("Step 4:-> After Creating weekly TimesheetEntry,Now adding task to all entry using Automation Script");
		        for (int i = 1; i <= taskButtons.size(); i++) {
		            try {
		                WebElement MinimizeBtn = driverR.findElement(By.xpath("(//*[contains(@class,'accordion-button')])[" + i + "]"));
		                Utility.scrollIntoView(driverR, js, MinimizeBtn);
		                MinimizeBtn.click();

		                WebElement addTaskBtn = driverR.findElement(By.xpath("(//*[contains(text(),'Add Task')])[1]"));
		                Utility.scrollIntoView(driverR, js, addTaskBtn);
		                wait.until(ExpectedConditions.elementToBeClickable(addTaskBtn));
		                Utility.safeClick(driverR, js, addTaskBtn);

		                att.SelectSubProcess();
		                att.ClickonActivity();
		                att.SendTaskDescription();
		                att.SendTaskDuration();
		                att.ClickonTaskSubmit();
		                Thread.sleep(1000);
		                
						WebElement SuccessfulMsg = driverR.findElement(By.xpath("//*[contains(text(),'Task created successfully!')]"));
						Utility.highlightElement(SuccessfulMsg);
						String ActualSuccessfulMsg = SuccessfulMsg.getText();
						String ExpectSuccessfulMsg = "Task created successfully!";
						sf.assertEquals(ActualSuccessfulMsg, ExpectSuccessfulMsg);


		                WebElement MinimizeBtn2 = driverR.findElement(By.xpath("(//*[contains(@class,'accordion-button')])[1]"));
		                Utility.scrollIntoView(driverR, js, MinimizeBtn2);
		                MinimizeBtn2.click();
		                Utility.waitForSeconds(2);
		            } catch (ElementClickInterceptedException e) {
		                System.out.println("Add Task Click Intercepted: Retrying via JS click.");
		                js.executeScript("arguments[0].click();", driverR.findElement(By.xpath("(//*[contains(text(),'Add Task')])[1]")));
		            }
		        }
		        Utility.showTooltip("Step 5:-> After adding Task, submitting this week Timesheet using Automation Script");
		        // Submit timesheet
		        try {
		            WebElement actionsBtn = Utility.waitForElementToBeClickable(driverR, By.xpath("//button[normalize-space()='Actions']"), 10);
		            Utility.scrollIntoView(driverR, js, actionsBtn);
		            Utility.highlightElement(actionsBtn);
		            Utility.safeClick(driverR, js, actionsBtn);
		            Utility.waitForSeconds(2);

		            WebElement submitBtn = Utility.waitForElementToBeClickable(driverR, By.xpath("//a[normalize-space()='Submit Timesheet']"), 10);
		            Utility.scrollIntoView(driverR, js, submitBtn);
		            Utility.highlightElement(submitBtn);
		            Utility.safeClick(driverR, js, submitBtn);
		            Utility.waitForSeconds(2);

		            WebElement confirmSubmit = Utility.waitForElementToBeClickable(driverR, By.xpath("//button[normalize-space()='Submit']"), 10);
		            Utility.scrollIntoView(driverR, js, confirmSubmit);
		            Utility.highlightElement(confirmSubmit);
		            Utility.safeClick(driverR, js, confirmSubmit);
		            Utility.waitForSeconds(2);
		            System.out.println("✅ Timesheet submitted for week " + index);
		           
		            WebElement ConfirmAlert = driverR.findElement(By.xpath("//*[contains(text(),'Please clock out every timesheet for the selected week.')]"));
                    Utility.highlightElement(ConfirmAlert);
		            String ActualTimesheetSuccesful = ConfirmAlert.getText();
		            System.out.println("Timesheet submission Succesful Message :-> " + ActualTimesheetSuccesful);
		            String ExpectTimesheetSuccesful ="Please clock out every timesheet for the selected week.";
		            sf.assertEquals(ActualTimesheetSuccesful, ExpectTimesheetSuccesful);
		            // Check final status after submission
		            driverR.navigate().refresh();
		            Utility.waitForSeconds(2);
		            WebElement finalWeek = Utility.waitForElementToBeClickable(driverR, By.xpath(weekXPath), 10);
		            Utility.scrollIntoView(driverR, js, finalWeek);
		            Utility.safeClick(driverR, js, finalWeek);
		            
		            

//		            WebElement finalStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'bg-warning rounded text-black')]")));
//		            String newStatus = finalStatus.getText().replace("\u00A0", " ").trim();
//		            System.out.println("🟢 Post-Submission Status: '" + newStatus + "'");

		            if (ActualTimesheetSuccesful.equalsIgnoreCase("Please clock out every timesheet for the selected week.")) {
		                System.out.println("if clock out is Missing for timesheet for the selected week.then breaks the loop");
		                break; // STOP the main loop
		            }

		        } catch (Exception e) {
		            System.out.println("⚠️ Error during final submission: " + e.getMessage());
		        }
		    }
		}
		sf.assertAll();
	}


		public String addHours(String baseTime, int hoursToAdd) {
		    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		    LocalTime time = LocalTime.parse(baseTime, timeFormatter);
		    return time.plusHours(hoursToAdd).format(timeFormatter);
		}

		public void sendAttendanceData(String token, String timestamp, String endpoint) {
		    HashMap<String, String> data = new HashMap<>();
		    data.put("test_timestamp", timestamp);

		    System.out.println("→ Sending to " + endpoint + ": " + timestamp);

		    given()
		        .contentType("application/json")
		        .header("Authorization", "Token " + token)
		        .body(data)
		        .when()
		        .post("http://192.168.1.10:8085/api/attendance-test/" + endpoint + "/")
		        .then()
		        .statusCode(201)
		        .log().all();
		}
		public void sendAttendanceData2(String token, String timestamp, String endpoint) {
		    HashMap<String, String> data = new HashMap<>();
		    data.put("test_timestamp", timestamp);

		    System.out.println("→ Sending to " + endpoint + ": " + timestamp);

		    given()
		        .contentType("application/json")
		        .header("Authorization", "Token " + token)
		        .body(data)
		        .when()
		        .patch("http://192.168.1.10:8085/api/attendance-test/" + endpoint + "/")
		        .then()
		        .statusCode(201)
		        .log().all();
		}

		public void DeleteAutomationTestUserRecords(String token) {
//		    HashMap<String, String> data = new HashMap<>();
//		    data.put("test_timestamp", timestamp);

//		    System.out.println("→ Sending to " + endpoint + ": " + timestamp);

		    given()
		        .contentType("application/json")
		        .header("Authorization", "Token " + token)
//		        .body(data)
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



