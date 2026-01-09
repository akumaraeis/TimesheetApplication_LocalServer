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


public class DashboardTest extends BaseTest {
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
	public String UserName;
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
	
	@Test(priority=0)
	public void DeleteTestUserRecord() throws InterruptedException, IOException
	{

        RestAssured.baseURI = "http://192.168.1.10:8085";
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

	public void DeleteAutomationTestUserRecords(String token) {
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
	
	@Test(priority=1)
	public void ValidateUserStatusOnDashboard() throws InterruptedException, IOException, ParseException
	{
		SoftAssert sf = new SoftAssert();
		String ActualProfileName = tdp.ValidateUser();
		String ExpectedProfileName ="Welcome, AutomationTesting!";
		sf.assertEquals(ActualProfileName, ExpectedProfileName);
		Log.info("Profile name is verified");
		Utility.showTooltip("User Profile Name Validated using AutomationScript.");
		String ActualLnmProfileName=tdp.ValidateUserDashboardstatusFunctionality();
		String ExpectedLnmProfileName ="Welcome, LNM Testuser!";
		sf.assertEquals(ActualLnmProfileName, ExpectedLnmProfileName);
		Log.info("User Status is fetched");
		tdp.ValidateUserClockin();
		String ActualUserStatus=tdp.ValidateUserStatusChangeonDashboard();
        String ExpectUserStatus = "AVAILABLE";
		
		sf.assertEquals(ActualUserStatus, ExpectUserStatus);
		Log.info("User Status is successfully Verified");
		sf.assertAll();
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



