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
import org.openqa.selenium.ElementNotInteractableException;
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

public class Validate_UserInterfaceAttendanceFunctionality extends BaseTest {
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
		//		Utility.showTooltip("Browser is Launched using Selenium Automation Tool");
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
        RestAssured.baseURI = "https://tsbackend.ndtatlas.com";

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

        RestAssured.baseURI = "https://tsbackend.ndtatlas.com";
        Response loginResponse = RestAssured.given()
                .header("Content-Type", "application/json")
                .body("{ \"username\": \"AutomationTestUser\", \"password\": \"Test@123\" }")
                .when().post("/api/auth/login/")
                .then().statusCode(200)
                .extract().response();
        String token = loginResponse.jsonPath().getString("data.token");
        System.out.println("🔐 Token fetched: " + token);
//        Utility.waitForSeconds(2);
        DeleteAutomationTestUserRecords(token);
//        Utility.waitForSeconds(1);
//        driverR.navigate().refresh();
//        Utility.waitForSeconds(1);		        
		
	}
	
	@Test(priority =2)
	public void ValidateAddedTimesheetEntry() throws InterruptedException, IOException, ParseException
	{

		try
		{
		launchLocalUrl();

		Thread.sleep(2000);

		lp.SendUserName();

		lp.SendPassword();

		lp.ClickonLoginBtn();

		String ActualProfileName=lp.GetProfileName();
		String ExpectedProfileName ="Welcome, AutomationTesting";

		Thread.sleep(2000);
		att.ClickonAttendance();		
		Thread.sleep(1000);
		
		att.ClickonTimeIn();
		Thread.sleep(4000);
		att.ClickonConfirmationBtn();
		Thread.sleep(2000);
		String ActualSuccessfulMsg = driverR.findElement(By.xpath("//*[contains(text(),'Attendance updated successfully!')]")).getText();
		System.out.println("ActualSuccesful Message found :->"+ActualSuccessfulMsg);
		String ExpectSuccessfulMsg ="Attendance updated successfully!";
		sf.assertEquals(ActualSuccessfulMsg, ExpectSuccessfulMsg);
		Thread.sleep(60000);
		
		att.ClickonBreakIn();
		Thread.sleep(4000);
		att.ClickonConfirmationBtn();
		Thread.sleep(2000);
		String ActualSuccessfulMsg2 = driverR.findElement(By.xpath("//*[contains(text(),'Attendance updated successfully!')]")).getText();
		String ExpectSuccessfulMsg2 ="Attendance updated successfully!";
		sf.assertEquals(ActualSuccessfulMsg2, ExpectSuccessfulMsg2);
		Thread.sleep(60000);
		
		att.ClickonBreakOut();
		Thread.sleep(4000);
		att.ClickonConfirmationBtn();
		Thread.sleep(2000);
		String ActualSuccessfulMsg3 = driverR.findElement(By.xpath("//*[contains(text(),'Attendance updated successfully!')]")).getText();
		String ExpectSuccessfulMsg3 ="Attendance updated successfully!";
		sf.assertEquals(ActualSuccessfulMsg3, ExpectSuccessfulMsg3);
		Thread.sleep(60000);
		
		att.ClickonTimeOut();
		Thread.sleep(4000);
		att.ClickonConfirmationBtn();
		Thread.sleep(2000);
		String ActualSuccessfulMsg4 = driverR.findElement(By.xpath("//*[contains(text(),'Attendance updated successfully!')]")).getText();
		String ExpectSuccessfulMsg4 ="Attendance updated successfully!";
		sf.assertEquals(ActualSuccessfulMsg4, ExpectSuccessfulMsg4);
        sf.assertAll();
		
		} 
	catch (ElementNotInteractableException e) {
	    System.out.println("TimeIn button not interactable: " + e.getMessage());
	}
	}
	public void DeleteAutomationTestUserRecords(String token) {
//	    HashMap<String, String> data = new HashMap<>();
//	    data.put("test_timestamp", timestamp);

//	    System.out.println("→ Sending to " + endpoint + ": " + timestamp);

	    given()
	        .contentType("application/json")
	        .header("Authorization", "Token " + token)
//	        .body(data)
	        .when()
	        .post("https://tsbackend.ndtatlas.com/api/utils/remove-automation-test-data/")
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



