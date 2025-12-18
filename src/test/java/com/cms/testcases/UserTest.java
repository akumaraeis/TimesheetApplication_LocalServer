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


public class UserTest extends BaseTest {
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
	
//	@Test(priority=0)
//	public void DeleteTestUserRecord() throws InterruptedException, IOException
//	{
//
//        RestAssured.baseURI = "http://192.168.1.10:8085";
//        Response loginResponse = RestAssured.given()
//                .header("Content-Type", "application/json")
//                .body("{ \"username\": \"AutomationTestUser\", \"password\": \"Test@123\" }")
//                .when().post("/api/auth/login/")
//                .then().statusCode(200)
//                .extract().response();
//        token = loginResponse.jsonPath().getString("data.token");
//        System.out.println("🔐 Token fetched: " + token);
////        Utility.waitForSeconds(2);
//        DeleteAutomationTestUserRecords(token);
////        Utility.waitForSeconds(1);
//
//	}
//
//	public void DeleteAutomationTestUserRecords(String token) {
//		given()
//		.contentType("application/json")
//		.header("Authorization", "Token " + token)
//		//		        .body(data)
//		.when()
//		.post("http://192.168.1.10:8085/api/utils/remove-automation-test-data/")
//		.then()
//		.statusCode(200)
//		.log().all();
//	}


	@Test(priority=1)
	public void ValidateAddTaskFunctionalityAfterClockOut() throws InterruptedException, IOException, ParseException
	{

		SoftAssert sf = new SoftAssert();
		Utility.showTooltip("Step 1 :-> Launching Timesheet Application using Automation Script ");
		
		Log.info(" Application is launched");
		
		launchLocalUrl();
		
		Thread.sleep(2000);

		lp.SendAdminUserName2();

		lp.SendAdminPassword();

		lp.ClickonLoginBtn();
		
		Log.info("Line manager logged in successfully");
		
		Utility.showTooltip("Step 2 :->Now login with Line Manager Credential to Validate User status on Dashboard before user Clock-in ");
		
		String ActualProfileName=lp.GetProfileName();
		String ExpectedProfileName ="Welcome, LNM Testuser!";		
		sf.assertEquals(ActualProfileName, ExpectedProfileName);
		Log.info("Profile name is verified");
		tup.ClickonUsers();
		Utility.waitForSeconds(2);
		Utility.showTooltip("Step 3 :->Now Checked the current status of Respective User on Dashboard");
		Utility.waitForSeconds(2);
		tup.SendUserName("AutomationTesting");
		String ActualUserNameonList = tup.getUserNameonList();
		System.out.println("On UserNameonList , text User Name:->"+ActualUserNameonList);
		String ExpectUserNameonList ="AutomationTesting";
		sf.assertEquals(ActualUserNameonList, ExpectUserNameonList);
		
		tup.ClickonActionBtn();
		Utility.waitForSeconds(2);
		tup.ClickonActionOption();
		Utility.waitForSeconds(2);
		tup.SendTimeIn();
		tup.SendBreakIn();
		tup.SendBreakOut();
		tup.SendTimeOut();
		att.ClickonTaskSubmit();
		String ActualSuccessfulMsg = tup.getNotificationAlert();
		String ExpectSuccessfulMsg = "Time in/out updated successfully!";
		sf.assertEquals(ActualSuccessfulMsg, ExpectSuccessfulMsg);
		Log.info("User Schedule is modified");
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



