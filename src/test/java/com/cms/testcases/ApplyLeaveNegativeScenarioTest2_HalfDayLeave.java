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

public class ApplyLeaveNegativeScenarioTest2_HalfDayLeave extends BaseTest {
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
	public void ValidateaddNewTimesheetFunctionality() throws InterruptedException, IOException {
		launchLocalUrl();
	    Thread.sleep(2000);
	    lp.SendUserName();
	    lp.SendPassword();
	    lp.ClickonLoginBtn();

	    String ActualProfileName = lp.GetProfileName();
	    String ExpectedProfileName = "Welcome, AutomationTesting";
	    sf.assertEquals(ActualProfileName, ExpectedProfileName);

	    tsp.ClickonTimesheetSubmission();

	    DateTimeFormatter inputFormatter2 = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);
	    DateTimeFormatter apiFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    DateTimeFormatter leavePageFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);

	    List<WebElement> allWeeks = driverR.findElements(By.xpath("//div[contains(@class,'p-1 shadow mb-2 bg-gradient border-2')]"));
	    System.out.println("Total week blocks found: " + allWeeks.size());

	    String weekXPath = "(//div[contains(@class,'p-1 shadow mb-2 bg-gradient border-2')])[2]";
	    WebElement weekElement = driverR.findElement(By.xpath(weekXPath));
	    js = (JavascriptExecutor) driverR;
	    js.executeScript("arguments[0].scrollIntoView(true);", weekElement);
	    Utility.safeClick(driverR, js, weekElement);

	    WebDriverWait wait = new WebDriverWait(driverR, Duration.ofSeconds(10));
	    WebElement statusElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[contains(@class,'px-3 py-1')])[2]")));
	    String status = statusElement.getText().trim();
	    System.out.println("Week Status: '" + status + "'");

	    if (status.equalsIgnoreCase("Not_Submitted")) {
	        String startText = driverR.findElement(By.xpath("(" + weekXPath + "/div/div/span)[1]")).getText().trim();
	        String endText = driverR.findElement(By.xpath("(" + weekXPath + "/div/div/span)[3]")).getText().trim();

	        LocalDate endDate = LocalDate.parse(endText, inputFormatter2);
	        String dateStr = endDate.format(apiFormatter);
	        System.out.println("🗓️ Submitting entries for: " + dateStr);

	        String baseTime = "04:30:00";
	        sendAttendanceData(token, dateStr + "T" + baseTime + "Z", "clockin");
	        sendAttendanceData2(token, dateStr + "T" + addHours(baseTime, 1) + "Z", "breakin");
	        sendAttendanceData2(token, dateStr + "T" + addHours(baseTime, 2) + "Z", "breakout");
	        sendAttendanceData2(token, dateStr + "T" + addHours(baseTime, 5) + "Z", "clockout");
	        Thread.sleep(2000);

	        // Now check for matching Leave entry
	        driverR.navigate().refresh();
	        Utility.waitForSeconds(2);
	        alp.ClickonApplyLeave();
	        Thread.sleep(2000);

	        List<WebElement> leaveDates = driverR.findElements(By.xpath("//*[@class='p-5 card-body']//span[@class='ms-2 fs-6 fw-bold']"));
	        List<WebElement> applyLeaveButtons = driverR.findElements(By.xpath("//button[@class=\"text-uppercase btn btn-warning btn-sm\"]"));
	                                           
	        int size=leaveDates.size();
	        System.out.println("Apply Leave Size :->"+ size);

	        LocalDate apiDate = LocalDate.parse(dateStr, apiFormatter);
	        String formattedDate = apiDate.format(leavePageFormatter);

	        System.out.println("Formatted API Date for Comparison: " + formattedDate);

	        boolean leaveMatched = false;

	        for (int i = 1; i < leaveDates.size(); i++) {
	            String leaveDateText = driverR.findElement(By.xpath("(//*[@class='p-5 card-body']//span[@class='ms-2 fs-6 fw-bold'])["+i+"]")).getText().trim();
	            System.out.println("Checking Leave Date:-> " + leaveDateText);
	            System.out.println("Formatted date Checking:->"+formattedDate);

	            if (leaveDateText.equalsIgnoreCase(formattedDate)) {
	     //           WebElement applyBtn = applyLeaveButtons.get(i);
	            	WebElement applyBtn =driverR.findElement(By.xpath("(//button[@class=\"text-uppercase btn btn-warning btn-sm\"])["+i+"]"));
	                String applybtnText = applyBtn.getText();
	                System.out.println("On Apply Leave Button detail page ,text present"+applybtnText);
	                Utility.ExplicitWait(applyBtn);
	                String leaveBtnText = applyBtn.getText().trim();
	                System.out.println("on Leave Detail page ,checking text of leave button"+ leaveBtnText);

	                sf.assertEquals(leaveBtnText, "APPLY LEAVE");

	                if (leaveBtnText.equalsIgnoreCase("APPLY LEAVE")) {
	                    Utility.safeClick(driverR, js, applyBtn);
	                    Thread.sleep(2000);
	                    alp.SelectLeaveType2();
	                    Thread.sleep(2000);
	                    alp.SendComment();
	                    alp.ClickonSubmitBtn();
	                    Thread.sleep(4000);

	                    WebElement toaster = driverR.findElement(By.xpath("//*[contains(@class,'Toastify__toast Toastify')]"));
	                    System.out.println("Apply Leave Toaster Message: " + toaster.getText());

	                    System.out.println("✔ Leave matched and applied. Breaking loop.");
	                    leaveMatched = true;
	                    break;
	                }
	            }
	        }

	        if (!leaveMatched) {
	            System.out.println("❌ No matching leave date found for " + formattedDate);
	        }

	        sf.assertAll();
	    }
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



