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

public class ApplyLeaveNegativeScenarioTest extends BaseTest {
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
	DateTimeFormatter outputFormatter2;
	String dateStr ;
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
	public void ValidateaddNewTimesheetFunctionality() throws InterruptedException, IOException
	{

		launchLocalUrl();

		Thread.sleep(2000);

		lp.SendUserName();

		lp.SendPassword();

		lp.ClickonLoginBtn();

		String ActualProfileName=lp.GetProfileName();
		String ExpectedProfileName ="Welcome, AutomationTesting";

		sf.assertEquals(ActualProfileName, ExpectedProfileName);
		tsp.ClickonTimesheetSubmission();
//		Thread.sleep(1000);
	

		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

		List<WebElement> allWeeks = driverR.findElements(By.xpath("//div[contains(@class,'p-1 shadow mb-2 bg-gradient border-2')]"));
		System.out.println("Total week blocks found: " + allWeeks.size());
		totalWeeks = allWeeks.size();
        // API login
		
	
		    String weekXPath = "(//div[contains(@class,'p-1 shadow mb-2 bg-gradient border-2')])[2]";
		    WebElement weekElement = driverR.findElement(By.xpath(weekXPath));
		    js = (JavascriptExecutor) driverR;
		    js.executeScript("arguments[0].scrollIntoView(true);", weekElement);
		    Utility.safeClick(driverR, js, weekElement);

		    WebDriverWait wait = new WebDriverWait(driverR, Duration.ofSeconds(10));
		    WebElement statusElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[contains(@class,'px-3 py-1')])[2]")));
		    String status = statusElement.getText().replace("\u00A0", " ").trim();
		    System.out.println("Week " + 2 + " Status: '" + status + "'");

		    if (status.equalsIgnoreCase("Not_Submitted")) {
		        String startText = driverR.findElement(By.xpath("(" + weekXPath + "/div/div/span)[1]")).getText().trim();
		        String endText = driverR.findElement(By.xpath("(" + weekXPath + "/div/div/span)[3]")).getText().trim();

		        DateTimeFormatter inputFormatter2 = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);
		        LocalDate startDate = LocalDate.parse(startText, inputFormatter2);
		        LocalDate endDate = LocalDate.parse(endText, inputFormatter2);
		        System.out.println("Parsed Start Date: " + startDate + " | End Date: " + endDate);
/*
		        // API login
		        RestAssured.baseURI = "https://tsbackend.ndtatlas.com";
		        Response loginResponse = RestAssured.given()
		                .header("Content-Type", "application/json")
		                .body("{ \"username\": \"AutomationTestUser\", \"password\": \"Test@123\" }")
		                .when().post("/api/auth/login/")
		                .then().statusCode(200)
		                .extract().response();
		        String token = loginResponse.jsonPath().getString("data.token");
		        System.out.println("🔐 Token fetched: " + token);
//		        Utility.waitForSeconds(2);
		        DeleteAutomationTestUserRecords(token);
		        Utility.waitForSeconds(1);
		        driverR.navigate().refresh();
		        Utility.waitForSeconds(2);		        
		        // Submit attendance (from 3rd day onwards) 
*/
		         outputFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        String baseTime = "04:30:00";
		      
		            dateStr = endDate.format(outputFormatter2);
		            System.out.println("🗓️ Submitting entries for: " + dateStr);
		            sendAttendanceData(token, dateStr + "T" + baseTime + "Z", "clockin");
		            sendAttendanceData2(token, dateStr + "T" + addHours(baseTime, 1) + "Z", "breakin");
		            sendAttendanceData2(token, dateStr + "T" + addHours(baseTime, 2) + "Z", "breakout");
		            sendAttendanceData2(token, dateStr + "T" + addHours(baseTime, 9) + "Z", "clockout");
		            Thread.sleep(2000);
		    
		}

		        // Add task and submit timesheet
		        driverR.navigate().refresh();
		        Utility.waitForSeconds(2);
				alp.ClickonApplyLeave();
				
				Thread.sleep(2000);
				
//				alp.ClickonApplyLeaveBtn();
				
				List<WebElement> leaveDates = driverR.findElements(By.xpath("//*[@class=\"p-5 card-body\"]//div[@class=\"p-2 shadow mb-2 bg-light bg-gradient border-2 card\"]//div//div//span[@class=\"ms-2 fs-6 fw-bold\"]"));
				
				int leaveSize = leaveDates.size();
				
				for(int i= 1; i<=leaveSize ;i++ )
				{
					String leaveDate = leaveDates.get(i).getText();
					
					DateTimeFormatter inputFormatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			        // Output formatter: "MMM dd, yyyy"
			        DateTimeFormatter outputFormatter3 = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);

			        // Parse and format
			        LocalDate date = LocalDate.parse(dateStr, inputFormatter3);
			        String formattedDate = date.format(outputFormatter3);

			        System.out.println("Formatted Date: " + formattedDate);	    
			        System.out.println("Date found after Leave detail page :->"+leaveDate);
					if(formattedDate.equals(leaveDate))
					{
						WebElement ApplyText = driverR.findElement(By.xpath("//*[contains(text(),'Not Applicable')]"));
						String LeaveText = ApplyText.getText();
						System.out.println("Leave text found after Leave "+LeaveText);
						
						String ActualLeaveText = LeaveText ;
						String ExpectLeaveText ="NOT APPLICABLE";
						sf.assertEquals(ActualLeaveText, ExpectLeaveText);
						if(LeaveText.equalsIgnoreCase("Not Applicable"))
						{
							System.out.println("Loop Breaks Since Timesheet entry date matches Leave date");
						break;
						}
					}
					else
					{
						System.out.println("Loop Continues Since Timesheet entry date do not matches Leave date");
					}
					
				}
		        sf.assertAll();
		    }
		
		
//		sf.assertAll();
	


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

		
	



//	//	@AfterMethod
//	public void closeURL()
//	{
//		driverR.navigate().to("about:blank");
//	}

	@AfterClass
	public void closebrowser()
	{

		teardown();
	}

}



