package com.cms.testcases;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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

public class NewTimesheetSubmission extends BaseTest {
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
	String weekXPath;
	WebElement weekElement;
	WebDriverWait wait;

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
	public void ValidateaddNewTimesheetFunctionality() throws InterruptedException, IOException {
	  
		launchLocalUrl();

		Thread.sleep(2000);

		lp.SendUserName();

		lp.SendPassword();

		lp.ClickonLoginBtn();

		String ActualProfileName=lp.GetProfileName();
		String ExpectedProfileName ="Welcome, AutomationTesting";

		sf.assertEquals(ActualProfileName, ExpectedProfileName);
		tsp.ClickonTimesheetSubmission();
		Thread.sleep(1000);
	

		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");



		List<WebElement> allWeeks = driverR.findElements(By.xpath("//div[contains(@class,'p-1 shadow mb-2 bg-gradient border-2')]"));
		System.out.println("Total week blocks found: " + allWeeks.size());
		totalWeeks = allWeeks.size();
       for (int index = 2; index <= totalWeeks; index++) {
	        try {
	            weekXPath = "(//div[contains(@class,'p-1 shadow mb-2 bg-gradient border-2')])[" + index + "]";
	             wait = new WebDriverWait(driverR, Duration.ofSeconds(10));
	             weekElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(weekXPath)));

	            // Scroll to the week container
	            js = (JavascriptExecutor)driverR;
	            js.executeScript("arguments[0].scrollIntoView(true);", weekElement);
	            Thread.sleep(1000);

			    Utility.safeClick(driverR, js, weekElement);

			    WebDriverWait wait = new WebDriverWait(driverR, Duration.ofSeconds(10));
			    WebElement statusElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[contains(@class,'px-3 py-1')])[2]")));
			    String status = statusElement.getText().replace("\u00A0", " ").trim();
			    System.out.println("Week " + index + " Status: '" + status + "'");

	            if (!status.equalsIgnoreCase("Not_Submitted")) {
	                continue; // skip non-editable weeks
	            }

	            // Click the week to expand the view
	            weekElement.click();
	            Thread.sleep(1000);

	            // Check if "Record not found" is visible — means no entries exist
	            boolean noRecordFound = !driverR.findElements(By.xpath("//*[contains(text(),'Record not found')]")).isEmpty();

	            if (noRecordFound) {
	                System.out.println("🟡 No timesheet found. Creating entries...");
	                createTimesheetEntriesMonToFri();
	                Thread.sleep(2000);
	            }

	            // After creation or if already exists, check each day (Mon–Fri)
	            for (int day = 1; day <= 5; day++) {
	                try {
	                    // Get the day container (Mon to Fri)
	                    String dayCardXPath = "(//*[@class=\"border border-danger accordion-item\"])["+day+"]";
	                    WebElement dayCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dayCardXPath)));
	                    js.executeScript("arguments[0].scrollIntoView(true);", dayCard);
	                    Thread.sleep(1000);

	                    // Check for Edit Task
	                    List<WebElement> editTask = dayCard.findElements(By.xpath(".//*[text()='Edit Task']"));
	                    if (!editTask.isEmpty()) {
	                        System.out.println("✔️ Day " + day + ": Task already exists. Skipping...");
	                        continue;
	                    }

	                    // Check for Add Task
	                    List<WebElement> addTask = dayCard.findElements(By.xpath(".//*[text()='Add Task']"));
	                    if (!addTask.isEmpty()) {
	                        System.out.println("🟢 Day " + day + ": No task. Adding task...");
	                        WebElement addBtn = addTask.get(0);
	                        js.executeScript("arguments[0].scrollIntoView(true);", addBtn);
	                        wait.until(ExpectedConditions.elementToBeClickable(addBtn)).click();

	                        // Fill the Add Task form here if needed
	                        fillTaskForm(); // <<< implement this method

	                        Thread.sleep(2000);
	                        driverR.navigate().refresh();
	                        Thread.sleep(2000);
	                        index = 1; // restart loop to catch DOM updates
	                        break;
	                    }

	                } catch (Exception innerEx) {
	                    System.out.println("⚠️ Error processing day " + day + ": " + innerEx.getMessage());
	                }
	            }

	        } catch (Exception e) {
	            System.out.println("❌ Exception at week index " + index + ": " + e.getMessage());
	        }
	    }
	}
	public void createTimesheetEntriesMonToFri() throws InterruptedException {
	    // Your implementation to click "Add Entry" or fill Clock In / Clock Out for each day
	    js = (JavascriptExecutor) driverR;
	    js.executeScript("arguments[0].scrollIntoView(true);", weekElement);
	    Utility.safeClick(driverR, js, weekElement);

		String startText = driverR.findElement(By.xpath("(" + weekXPath + "/div/div/span)[1]")).getText().trim();
        String endText = driverR.findElement(By.xpath("(" + weekXPath + "/div/div/span)[3]")).getText().trim();

        DateTimeFormatter inputFormatter2 = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);
        LocalDate startDate = LocalDate.parse(startText, inputFormatter2);
        LocalDate endDate = LocalDate.parse(endText, inputFormatter2);
        System.out.println("Parsed Start Date: " + startDate + " | End Date: " + endDate);

		RestAssured.baseURI = "https://tsbackend.ndtatlas.com";
        Response loginResponse = RestAssured.given()
                .header("Content-Type", "application/json")
                .body("{ \"username\": \"AutomationTestUser\", \"password\": \"Test@123\" }")
                .when().post("/api/auth/login/")
                .then().statusCode(200)
                .extract().response();
        String token = loginResponse.jsonPath().getString("data.token");
        System.out.println("🔐 Token fetched: " + token);

        // Submit attendance (from 3rd day onwards)
        DateTimeFormatter outputFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String baseTime = "04:30:00";
        for (LocalDate date = startDate.plusDays(2); !date.isAfter(endDate); date = date.plusDays(1)) {
            String dateStr = date.format(outputFormatter2);
            System.out.println("🗓️ Submitting entries for: " + dateStr);
            sendAttendanceData(token, dateStr + "T" + baseTime + "Z", "clockin");
            sendAttendanceData2(token, dateStr + "T" + addHours(baseTime, 1) + "Z", "breakin");
            sendAttendanceData2(token, dateStr + "T" + addHours(baseTime, 2) + "Z", "breakout");
            sendAttendanceData2(token, dateStr + "T" + addHours(baseTime, 9) + "Z", "clockout");
            Thread.sleep(2000);
        }
	
		System.out.println("📌 Creating timesheet entries (Mon–Fri)");
	    // Dummy code structure – you must fill in your real logic
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
	        .post("https://tsbackend.ndtatlas.com/api/attendance-test/" + endpoint + "/")
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
	        .patch("https://tsbackend.ndtatlas.com/api/attendance-test/" + endpoint + "/")
	        .then()
	        .statusCode(201)
	        .log().all();
	}
	
	public void fillTaskForm() throws InterruptedException {
	    // Locate and fill fields
	    // e.g., driverR.findElement(By.id("taskName")).sendKeys("Testing Task");
	  
	// driverR.findElement(By.xpath("//button[text()='Save']")).click();
		  List<WebElement> taskButtons = driverR.findElements(By.xpath("//*[contains(text(),'Add Task')]"));
	        System.out.println("Total Add Task buttons: " + taskButtons.size());

	        for (int i = 1; i <= taskButtons.size(); i++) {
		try
		{
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

        WebElement MinimizeBtn2 = driverR.findElement(By.xpath("(//*[contains(@class,'accordion-button')])[1]"));
        Utility.scrollIntoView(driverR, js, MinimizeBtn2);
        MinimizeBtn2.click();
        Utility.waitForSeconds(2);
		
    } catch (ElementClickInterceptedException e) {
        System.out.println("Add Task Click Intercepted: Retrying via JS click.");
        js.executeScript("arguments[0].click();", driverR.findElement(By.xpath("(//*[contains(text(),'Add Task')])[1]")));
    }
	        
	    System.out.println("📋 Filling and submitting task form");
	}
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



